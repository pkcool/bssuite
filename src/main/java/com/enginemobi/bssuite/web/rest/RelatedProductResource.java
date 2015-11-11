package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.RelatedProduct;
import com.enginemobi.bssuite.repository.RelatedProductRepository;
import com.enginemobi.bssuite.repository.search.RelatedProductSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.RelatedProductDTO;
import com.enginemobi.bssuite.web.rest.mapper.RelatedProductMapper;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing RelatedProduct.
 */
@RestController
@RequestMapping("/api")
public class RelatedProductResource {

    private final Logger log = LoggerFactory.getLogger(RelatedProductResource.class);

    @Inject
    private RelatedProductRepository relatedProductRepository;

    @Inject
    private RelatedProductMapper relatedProductMapper;

    @Inject
    private RelatedProductSearchRepository relatedProductSearchRepository;

    /**
     * POST  /relatedProducts -> Create a new relatedProduct.
     */
    @RequestMapping(value = "/relatedProducts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RelatedProductDTO> createRelatedProduct(@RequestBody RelatedProductDTO relatedProductDTO) throws URISyntaxException {
        log.debug("REST request to save RelatedProduct : {}", relatedProductDTO);
        if (relatedProductDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new relatedProduct cannot already have an ID").body(null);
        }
        RelatedProduct relatedProduct = relatedProductMapper.relatedProductDTOToRelatedProduct(relatedProductDTO);
        RelatedProduct result = relatedProductRepository.save(relatedProduct);
        relatedProductSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/relatedProducts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("relatedProduct", result.getId().toString()))
            .body(relatedProductMapper.relatedProductToRelatedProductDTO(result));
    }

    /**
     * PUT  /relatedProducts -> Updates an existing relatedProduct.
     */
    @RequestMapping(value = "/relatedProducts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RelatedProductDTO> updateRelatedProduct(@RequestBody RelatedProductDTO relatedProductDTO) throws URISyntaxException {
        log.debug("REST request to update RelatedProduct : {}", relatedProductDTO);
        if (relatedProductDTO.getId() == null) {
            return createRelatedProduct(relatedProductDTO);
        }
        RelatedProduct relatedProduct = relatedProductMapper.relatedProductDTOToRelatedProduct(relatedProductDTO);
        RelatedProduct result = relatedProductRepository.save(relatedProduct);
        relatedProductSearchRepository.save(relatedProduct);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("relatedProduct", relatedProductDTO.getId().toString()))
            .body(relatedProductMapper.relatedProductToRelatedProductDTO(result));
    }

    /**
     * GET  /relatedProducts -> get all the relatedProducts.
     */
    @RequestMapping(value = "/relatedProducts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<RelatedProductDTO>> getAllRelatedProducts(Pageable pageable)
        throws URISyntaxException {
        Page<RelatedProduct> page = relatedProductRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/relatedProducts");
        return new ResponseEntity<>(page.getContent().stream()
            .map(relatedProductMapper::relatedProductToRelatedProductDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /relatedProducts/:id -> get the "id" relatedProduct.
     */
    @RequestMapping(value = "/relatedProducts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RelatedProductDTO> getRelatedProduct(@PathVariable Long id) {
        log.debug("REST request to get RelatedProduct : {}", id);
        return Optional.ofNullable(relatedProductRepository.findOne(id))
            .map(relatedProductMapper::relatedProductToRelatedProductDTO)
            .map(relatedProductDTO -> new ResponseEntity<>(
                relatedProductDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /relatedProducts/:id -> delete the "id" relatedProduct.
     */
    @RequestMapping(value = "/relatedProducts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRelatedProduct(@PathVariable Long id) {
        log.debug("REST request to delete RelatedProduct : {}", id);
        relatedProductRepository.delete(id);
        relatedProductSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("relatedProduct", id.toString())).build();
    }

    /**
     * SEARCH  /_search/relatedProducts/:query -> search for the relatedProduct corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/relatedProducts/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RelatedProductDTO> searchRelatedProducts(@PathVariable String query) {
        return StreamSupport
            .stream(relatedProductSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(relatedProductMapper::relatedProductToRelatedProductDTO)
            .collect(Collectors.toList());
    }
}
