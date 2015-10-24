package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.QuoteLineItem;
import com.enginemobi.bssuite.repository.QuoteLineItemRepository;
import com.enginemobi.bssuite.repository.search.QuoteLineItemSearchRepository;
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
 * REST controller for managing QuoteLineItem.
 */
@RestController
@RequestMapping("/api")
public class QuoteLineItemResource {

    private final Logger log = LoggerFactory.getLogger(QuoteLineItemResource.class);

    @Inject
    private QuoteLineItemRepository quoteLineItemRepository;

    @Inject
    private QuoteLineItemSearchRepository quoteLineItemSearchRepository;

    /**
     * POST  /quoteLineItems -> Create a new quoteLineItem.
     */
    @RequestMapping(value = "/quoteLineItems",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<QuoteLineItem> createQuoteLineItem(@RequestBody QuoteLineItem quoteLineItem) throws URISyntaxException {
        log.debug("REST request to save QuoteLineItem : {}", quoteLineItem);
        if (quoteLineItem.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new quoteLineItem cannot already have an ID").body(null);
        }
        QuoteLineItem result = quoteLineItemRepository.save(quoteLineItem);
        quoteLineItemSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/quoteLineItems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("quoteLineItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /quoteLineItems -> Updates an existing quoteLineItem.
     */
    @RequestMapping(value = "/quoteLineItems",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<QuoteLineItem> updateQuoteLineItem(@RequestBody QuoteLineItem quoteLineItem) throws URISyntaxException {
        log.debug("REST request to update QuoteLineItem : {}", quoteLineItem);
        if (quoteLineItem.getId() == null) {
            return createQuoteLineItem(quoteLineItem);
        }
        QuoteLineItem result = quoteLineItemRepository.save(quoteLineItem);
        quoteLineItemSearchRepository.save(quoteLineItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("quoteLineItem", quoteLineItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /quoteLineItems -> get all the quoteLineItems.
     */
    @RequestMapping(value = "/quoteLineItems",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<QuoteLineItem>> getAllQuoteLineItems(Pageable pageable)
        throws URISyntaxException {
        Page<QuoteLineItem> page = quoteLineItemRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/quoteLineItems");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /quoteLineItems/:id -> get the "id" quoteLineItem.
     */
    @RequestMapping(value = "/quoteLineItems/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<QuoteLineItem> getQuoteLineItem(@PathVariable Long id) {
        log.debug("REST request to get QuoteLineItem : {}", id);
        return Optional.ofNullable(quoteLineItemRepository.findOne(id))
            .map(quoteLineItem -> new ResponseEntity<>(
                quoteLineItem,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /quoteLineItems/:id -> delete the "id" quoteLineItem.
     */
    @RequestMapping(value = "/quoteLineItems/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteQuoteLineItem(@PathVariable Long id) {
        log.debug("REST request to delete QuoteLineItem : {}", id);
        quoteLineItemRepository.delete(id);
        quoteLineItemSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("quoteLineItem", id.toString())).build();
    }

    /**
     * SEARCH  /_search/quoteLineItems/:query -> search for the quoteLineItem corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/quoteLineItems/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<QuoteLineItem> searchQuoteLineItems(@PathVariable String query) {
        return StreamSupport
            .stream(quoteLineItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
