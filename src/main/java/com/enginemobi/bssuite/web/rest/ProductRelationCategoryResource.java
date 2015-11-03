package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.ProductRelationCategory;
import com.enginemobi.bssuite.repository.ProductRelationCategoryRepository;
import com.enginemobi.bssuite.repository.search.ProductRelationCategorySearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ProductRelationCategory.
 */
@RestController
@RequestMapping("/api")
public class ProductRelationCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ProductRelationCategoryResource.class);

    @Inject
    private ProductRelationCategoryRepository productRelationCategoryRepository;

    @Inject
    private ProductRelationCategorySearchRepository productRelationCategorySearchRepository;

    /**
     * POST  /productRelationCategorys -> Create a new productRelationCategory.
     */
    @RequestMapping(value = "/productRelationCategorys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductRelationCategory> createProductRelationCategory(@RequestBody ProductRelationCategory productRelationCategory) throws URISyntaxException {
        log.debug("REST request to save ProductRelationCategory : {}", productRelationCategory);
        if (productRelationCategory.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new productRelationCategory cannot already have an ID").body(null);
        }
        ProductRelationCategory result = productRelationCategoryRepository.save(productRelationCategory);
        productRelationCategorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/productRelationCategorys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("productRelationCategory", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /productRelationCategorys -> Updates an existing productRelationCategory.
     */
    @RequestMapping(value = "/productRelationCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductRelationCategory> updateProductRelationCategory(@RequestBody ProductRelationCategory productRelationCategory) throws URISyntaxException {
        log.debug("REST request to update ProductRelationCategory : {}", productRelationCategory);
        if (productRelationCategory.getId() == null) {
            return createProductRelationCategory(productRelationCategory);
        }
        ProductRelationCategory result = productRelationCategoryRepository.save(productRelationCategory);
        productRelationCategorySearchRepository.save(productRelationCategory);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("productRelationCategory", productRelationCategory.getId().toString()))
                .body(result);
    }

    /**
     * GET  /productRelationCategorys -> get all the productRelationCategorys.
     */
    @RequestMapping(value = "/productRelationCategorys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ProductRelationCategory>> getAllProductRelationCategorys(Pageable pageable)
        throws URISyntaxException {
        Page<ProductRelationCategory> page = productRelationCategoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/productRelationCategorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /productRelationCategorys/:id -> get the "id" productRelationCategory.
     */
    @RequestMapping(value = "/productRelationCategorys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductRelationCategory> getProductRelationCategory(@PathVariable Long id) {
        log.debug("REST request to get ProductRelationCategory : {}", id);
        return Optional.ofNullable(productRelationCategoryRepository.findOne(id))
            .map(productRelationCategory -> new ResponseEntity<>(
                productRelationCategory,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /productRelationCategorys/:id -> delete the "id" productRelationCategory.
     */
    @RequestMapping(value = "/productRelationCategorys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProductRelationCategory(@PathVariable Long id) {
        log.debug("REST request to delete ProductRelationCategory : {}", id);
        productRelationCategoryRepository.delete(id);
        productRelationCategorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("productRelationCategory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/productRelationCategorys/:query -> search for the productRelationCategory corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/productRelationCategorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProductRelationCategory> searchProductRelationCategorys(@PathVariable String query) {
        return StreamSupport
            .stream(productRelationCategorySearchRepository.search(queryString(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
