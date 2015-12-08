package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.SalesOrderLineItem;
import com.enginemobi.bssuite.repository.SalesOrderLineItemRepository;
import com.enginemobi.bssuite.repository.search.SalesOrderLineItemSearchRepository;
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
 * REST controller for managing SalesOrderLineItem.
 */
@RestController
@RequestMapping("/api")
public class SalesOrderLineItemResource {

    private final Logger log = LoggerFactory.getLogger(SalesOrderLineItemResource.class);
        
    @Inject
    private SalesOrderLineItemRepository salesOrderLineItemRepository;
    
    @Inject
    private SalesOrderLineItemSearchRepository salesOrderLineItemSearchRepository;
    
    /**
     * POST  /salesOrderLineItems -> Create a new salesOrderLineItem.
     */
    @RequestMapping(value = "/salesOrderLineItems",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SalesOrderLineItem> createSalesOrderLineItem(@RequestBody SalesOrderLineItem salesOrderLineItem) throws URISyntaxException {
        log.debug("REST request to save SalesOrderLineItem : {}", salesOrderLineItem);
        if (salesOrderLineItem.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("salesOrderLineItem", "idexists", "A new salesOrderLineItem cannot already have an ID")).body(null);
        }
        SalesOrderLineItem result = salesOrderLineItemRepository.save(salesOrderLineItem);
        salesOrderLineItemSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/salesOrderLineItems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("salesOrderLineItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /salesOrderLineItems -> Updates an existing salesOrderLineItem.
     */
    @RequestMapping(value = "/salesOrderLineItems",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SalesOrderLineItem> updateSalesOrderLineItem(@RequestBody SalesOrderLineItem salesOrderLineItem) throws URISyntaxException {
        log.debug("REST request to update SalesOrderLineItem : {}", salesOrderLineItem);
        if (salesOrderLineItem.getId() == null) {
            return createSalesOrderLineItem(salesOrderLineItem);
        }
        SalesOrderLineItem result = salesOrderLineItemRepository.save(salesOrderLineItem);
        salesOrderLineItemSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("salesOrderLineItem", salesOrderLineItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /salesOrderLineItems -> get all the salesOrderLineItems.
     */
    @RequestMapping(value = "/salesOrderLineItems",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SalesOrderLineItem>> getAllSalesOrderLineItems(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SalesOrderLineItems");
        Page<SalesOrderLineItem> page = salesOrderLineItemRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/salesOrderLineItems");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /salesOrderLineItems/:id -> get the "id" salesOrderLineItem.
     */
    @RequestMapping(value = "/salesOrderLineItems/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SalesOrderLineItem> getSalesOrderLineItem(@PathVariable Long id) {
        log.debug("REST request to get SalesOrderLineItem : {}", id);
        SalesOrderLineItem salesOrderLineItem = salesOrderLineItemRepository.findOne(id);
        return Optional.ofNullable(salesOrderLineItem)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /salesOrderLineItems/:id -> delete the "id" salesOrderLineItem.
     */
    @RequestMapping(value = "/salesOrderLineItems/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSalesOrderLineItem(@PathVariable Long id) {
        log.debug("REST request to delete SalesOrderLineItem : {}", id);
        salesOrderLineItemRepository.delete(id);
        salesOrderLineItemSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("salesOrderLineItem", id.toString())).build();
    }

    /**
     * SEARCH  /_search/salesOrderLineItems/:query -> search for the salesOrderLineItem corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/salesOrderLineItems/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SalesOrderLineItem> searchSalesOrderLineItems(@PathVariable String query) {
        log.debug("REST request to search SalesOrderLineItems for query {}", query);
        return StreamSupport
            .stream(salesOrderLineItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
