package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.InvoiceLineItem;
import com.enginemobi.bssuite.repository.InvoiceLineItemRepository;
import com.enginemobi.bssuite.repository.search.InvoiceLineItemSearchRepository;
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
 * REST controller for managing InvoiceLineItem.
 */
@RestController
@RequestMapping("/api")
public class InvoiceLineItemResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceLineItemResource.class);

    @Inject
    private InvoiceLineItemRepository invoiceLineItemRepository;

    @Inject
    private InvoiceLineItemSearchRepository invoiceLineItemSearchRepository;

    /**
     * POST  /invoiceLineItems -> Create a new invoiceLineItem.
     */
    @RequestMapping(value = "/invoiceLineItems",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InvoiceLineItem> createInvoiceLineItem(@RequestBody InvoiceLineItem invoiceLineItem) throws URISyntaxException {
        log.debug("REST request to save InvoiceLineItem : {}", invoiceLineItem);
        if (invoiceLineItem.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new invoiceLineItem cannot already have an ID").body(null);
        }
        InvoiceLineItem result = invoiceLineItemRepository.save(invoiceLineItem);
        invoiceLineItemSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/invoiceLineItems/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("invoiceLineItem", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /invoiceLineItems -> Updates an existing invoiceLineItem.
     */
    @RequestMapping(value = "/invoiceLineItems",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InvoiceLineItem> updateInvoiceLineItem(@RequestBody InvoiceLineItem invoiceLineItem) throws URISyntaxException {
        log.debug("REST request to update InvoiceLineItem : {}", invoiceLineItem);
        if (invoiceLineItem.getId() == null) {
            return createInvoiceLineItem(invoiceLineItem);
        }
        InvoiceLineItem result = invoiceLineItemRepository.save(invoiceLineItem);
        invoiceLineItemSearchRepository.save(invoiceLineItem);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("invoiceLineItem", invoiceLineItem.getId().toString()))
                .body(result);
    }

    /**
     * GET  /invoiceLineItems -> get all the invoiceLineItems.
     */
    @RequestMapping(value = "/invoiceLineItems",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InvoiceLineItem>> getAllInvoiceLineItems(Pageable pageable)
        throws URISyntaxException {
        Page<InvoiceLineItem> page = invoiceLineItemRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/invoiceLineItems");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /invoiceLineItems/:id -> get the "id" invoiceLineItem.
     */
    @RequestMapping(value = "/invoiceLineItems/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InvoiceLineItem> getInvoiceLineItem(@PathVariable Long id) {
        log.debug("REST request to get InvoiceLineItem : {}", id);
        return Optional.ofNullable(invoiceLineItemRepository.findOne(id))
            .map(invoiceLineItem -> new ResponseEntity<>(
                invoiceLineItem,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /invoiceLineItems/:id -> delete the "id" invoiceLineItem.
     */
    @RequestMapping(value = "/invoiceLineItems/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInvoiceLineItem(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceLineItem : {}", id);
        invoiceLineItemRepository.delete(id);
        invoiceLineItemSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("invoiceLineItem", id.toString())).build();
    }

    /**
     * SEARCH  /_search/invoiceLineItems/:query -> search for the invoiceLineItem corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/invoiceLineItems/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InvoiceLineItem> searchInvoiceLineItems(@PathVariable String query) {
        return StreamSupport
            .stream(invoiceLineItemSearchRepository.search(queryString(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
