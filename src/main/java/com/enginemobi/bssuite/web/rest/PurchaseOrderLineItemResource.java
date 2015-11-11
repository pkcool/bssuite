package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.PurchaseOrderLineItem;
import com.enginemobi.bssuite.repository.PurchaseOrderLineItemRepository;
import com.enginemobi.bssuite.repository.search.PurchaseOrderLineItemSearchRepository;
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
 * REST controller for managing PurchaseOrderLineItem.
 */
@RestController
@RequestMapping("/api")
public class PurchaseOrderLineItemResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderLineItemResource.class);

    @Inject
    private PurchaseOrderLineItemRepository purchaseOrderLineItemRepository;

    @Inject
    private PurchaseOrderLineItemSearchRepository purchaseOrderLineItemSearchRepository;

    /**
     * POST  /purchaseOrderLineItems -> Create a new purchaseOrderLineItem.
     */
    @RequestMapping(value = "/purchaseOrderLineItems",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PurchaseOrderLineItem> createPurchaseOrderLineItem(@RequestBody PurchaseOrderLineItem purchaseOrderLineItem) throws URISyntaxException {
        log.debug("REST request to save PurchaseOrderLineItem : {}", purchaseOrderLineItem);
        if (purchaseOrderLineItem.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new purchaseOrderLineItem cannot already have an ID").body(null);
        }
        PurchaseOrderLineItem result = purchaseOrderLineItemRepository.save(purchaseOrderLineItem);
        purchaseOrderLineItemSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/purchaseOrderLineItems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("purchaseOrderLineItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /purchaseOrderLineItems -> Updates an existing purchaseOrderLineItem.
     */
    @RequestMapping(value = "/purchaseOrderLineItems",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PurchaseOrderLineItem> updatePurchaseOrderLineItem(@RequestBody PurchaseOrderLineItem purchaseOrderLineItem) throws URISyntaxException {
        log.debug("REST request to update PurchaseOrderLineItem : {}", purchaseOrderLineItem);
        if (purchaseOrderLineItem.getId() == null) {
            return createPurchaseOrderLineItem(purchaseOrderLineItem);
        }
        PurchaseOrderLineItem result = purchaseOrderLineItemRepository.save(purchaseOrderLineItem);
        purchaseOrderLineItemSearchRepository.save(purchaseOrderLineItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("purchaseOrderLineItem", purchaseOrderLineItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /purchaseOrderLineItems -> get all the purchaseOrderLineItems.
     */
    @RequestMapping(value = "/purchaseOrderLineItems",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PurchaseOrderLineItem>> getAllPurchaseOrderLineItems(Pageable pageable)
        throws URISyntaxException {
        Page<PurchaseOrderLineItem> page = purchaseOrderLineItemRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/purchaseOrderLineItems");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /purchaseOrderLineItems/:id -> get the "id" purchaseOrderLineItem.
     */
    @RequestMapping(value = "/purchaseOrderLineItems/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PurchaseOrderLineItem> getPurchaseOrderLineItem(@PathVariable Long id) {
        log.debug("REST request to get PurchaseOrderLineItem : {}", id);
        return Optional.ofNullable(purchaseOrderLineItemRepository.findOne(id))
            .map(purchaseOrderLineItem -> new ResponseEntity<>(
                purchaseOrderLineItem,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /purchaseOrderLineItems/:id -> delete the "id" purchaseOrderLineItem.
     */
    @RequestMapping(value = "/purchaseOrderLineItems/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePurchaseOrderLineItem(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseOrderLineItem : {}", id);
        purchaseOrderLineItemRepository.delete(id);
        purchaseOrderLineItemSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("purchaseOrderLineItem", id.toString())).build();
    }

    /**
     * SEARCH  /_search/purchaseOrderLineItems/:query -> search for the purchaseOrderLineItem corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/purchaseOrderLineItems/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PurchaseOrderLineItem> searchPurchaseOrderLineItems(@PathVariable String query) {
        return StreamSupport
            .stream(purchaseOrderLineItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
