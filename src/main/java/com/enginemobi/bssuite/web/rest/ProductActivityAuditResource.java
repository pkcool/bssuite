package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.ProductActivityAudit;
import com.enginemobi.bssuite.repository.ProductActivityAuditRepository;
import com.enginemobi.bssuite.repository.search.ProductActivityAuditSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.ProductActivityAuditDTO;
import com.enginemobi.bssuite.web.rest.mapper.ProductActivityAuditMapper;
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
 * REST controller for managing ProductActivityAudit.
 */
@RestController
@RequestMapping("/api")
public class ProductActivityAuditResource {

    private final Logger log = LoggerFactory.getLogger(ProductActivityAuditResource.class);
        
    @Inject
    private ProductActivityAuditRepository productActivityAuditRepository;
    
    @Inject
    private ProductActivityAuditMapper productActivityAuditMapper;
    
    @Inject
    private ProductActivityAuditSearchRepository productActivityAuditSearchRepository;
    
    /**
     * POST  /productActivityAudits -> Create a new productActivityAudit.
     */
    @RequestMapping(value = "/productActivityAudits",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductActivityAuditDTO> createProductActivityAudit(@RequestBody ProductActivityAuditDTO productActivityAuditDTO) throws URISyntaxException {
        log.debug("REST request to save ProductActivityAudit : {}", productActivityAuditDTO);
        if (productActivityAuditDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("productActivityAudit", "idexists", "A new productActivityAudit cannot already have an ID")).body(null);
        }
        ProductActivityAudit productActivityAudit = productActivityAuditMapper.productActivityAuditDTOToProductActivityAudit(productActivityAuditDTO);
        productActivityAudit = productActivityAuditRepository.save(productActivityAudit);
        ProductActivityAuditDTO result = productActivityAuditMapper.productActivityAuditToProductActivityAuditDTO(productActivityAudit);
        productActivityAuditSearchRepository.save(productActivityAudit);
        return ResponseEntity.created(new URI("/api/productActivityAudits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("productActivityAudit", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /productActivityAudits -> Updates an existing productActivityAudit.
     */
    @RequestMapping(value = "/productActivityAudits",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductActivityAuditDTO> updateProductActivityAudit(@RequestBody ProductActivityAuditDTO productActivityAuditDTO) throws URISyntaxException {
        log.debug("REST request to update ProductActivityAudit : {}", productActivityAuditDTO);
        if (productActivityAuditDTO.getId() == null) {
            return createProductActivityAudit(productActivityAuditDTO);
        }
        ProductActivityAudit productActivityAudit = productActivityAuditMapper.productActivityAuditDTOToProductActivityAudit(productActivityAuditDTO);
        productActivityAudit = productActivityAuditRepository.save(productActivityAudit);
        ProductActivityAuditDTO result = productActivityAuditMapper.productActivityAuditToProductActivityAuditDTO(productActivityAudit);
        productActivityAuditSearchRepository.save(productActivityAudit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("productActivityAudit", productActivityAuditDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /productActivityAudits -> get all the productActivityAudits.
     */
    @RequestMapping(value = "/productActivityAudits",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ProductActivityAuditDTO>> getAllProductActivityAudits(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ProductActivityAudits");
        Page<ProductActivityAudit> page = productActivityAuditRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/productActivityAudits");
        return new ResponseEntity<>(page.getContent().stream()
            .map(productActivityAuditMapper::productActivityAuditToProductActivityAuditDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /productActivityAudits/:id -> get the "id" productActivityAudit.
     */
    @RequestMapping(value = "/productActivityAudits/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductActivityAuditDTO> getProductActivityAudit(@PathVariable Long id) {
        log.debug("REST request to get ProductActivityAudit : {}", id);
        ProductActivityAudit productActivityAudit = productActivityAuditRepository.findOne(id);
        ProductActivityAuditDTO productActivityAuditDTO = productActivityAuditMapper.productActivityAuditToProductActivityAuditDTO(productActivityAudit);
        return Optional.ofNullable(productActivityAuditDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /productActivityAudits/:id -> delete the "id" productActivityAudit.
     */
    @RequestMapping(value = "/productActivityAudits/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProductActivityAudit(@PathVariable Long id) {
        log.debug("REST request to delete ProductActivityAudit : {}", id);
        productActivityAuditRepository.delete(id);
        productActivityAuditSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("productActivityAudit", id.toString())).build();
    }

    /**
     * SEARCH  /_search/productActivityAudits/:query -> search for the productActivityAudit corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/productActivityAudits/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProductActivityAuditDTO> searchProductActivityAudits(@PathVariable String query) {
        log.debug("REST request to search ProductActivityAudits for query {}", query);
        return StreamSupport
            .stream(productActivityAuditSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(productActivityAuditMapper::productActivityAuditToProductActivityAuditDTO)
            .collect(Collectors.toList());
    }
}
