package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.Product;
import com.enginemobi.bssuite.repository.ProductRepository;
import com.enginemobi.bssuite.repository.search.ProductSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.ProductDTO;
import com.enginemobi.bssuite.web.rest.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Product.
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ProductMapper productMapper;

    @Inject
    private ProductSearchRepository productSearchRepository;

    /**
     * POST  /products -> Create a new product.
     */
    @RequestMapping(value = "/products",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) throws URISyntaxException {
        log.debug("REST request to save Product : {}", productDTO);
        if (productDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new product cannot already have an ID").body(null);
        }
        Product product = productMapper.productDTOToProduct(productDTO);
        Product result = productRepository.save(product);
        productSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/products/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("product", result.getId().toString()))
                .body(productMapper.productToProductDTO(result));
    }

    /**
     * PUT  /products -> Updates an existing product.
     */
    @RequestMapping(value = "/products",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO) throws URISyntaxException {
        log.debug("REST request to update Product : {}", productDTO);
        if (productDTO.getId() == null) {
            return createProduct(productDTO);
        }
        Product product = productMapper.productDTOToProduct(productDTO);
        Product result = productRepository.save(product);
        productSearchRepository.save(product);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("product", productDTO.getId().toString()))
                .body(productMapper.productToProductDTO(result));
    }

    /**
     * GET  /products -> get all the products.
     */
    @RequestMapping(value = "/products",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ProductDTO>> getAllProducts(Pageable pageable)
        throws URISyntaxException {
        Page<Product> page = productRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/products");
        return new ResponseEntity<>(page.getContent().stream()
            .map(productMapper::productToProductDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /products/:id -> get the "id" product.
     */
    @RequestMapping(value = "/products/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);
        return Optional.ofNullable(productRepository.findOne(id))
            .map(productMapper::productToProductDTO)
            .map(productDTO -> new ResponseEntity<>(
                productDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /products/:id -> delete the "id" product.
     */
    @RequestMapping(value = "/products/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);
        productRepository.delete(id);
        productSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("product", id.toString())).build();
    }

    /**
     * SEARCH  /_search/products/:query -> search for the product corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/products/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProductDTO> searchProducts(@PathVariable String query) {
        return StreamSupport
            .stream(productSearchRepository.search(queryString(query)).spliterator(), false)
            .map(productMapper::productToProductDTO)
            .collect(Collectors.toList());
    }
}
