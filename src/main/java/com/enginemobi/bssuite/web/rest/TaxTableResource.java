package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.TaxTable;
import com.enginemobi.bssuite.repository.TaxTableRepository;
import com.enginemobi.bssuite.repository.search.TaxTableSearchRepository;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TaxTable.
 */
@RestController
@RequestMapping("/api")
public class TaxTableResource {

    private final Logger log = LoggerFactory.getLogger(TaxTableResource.class);
        
    @Inject
    private TaxTableRepository taxTableRepository;
    
    @Inject
    private TaxTableSearchRepository taxTableSearchRepository;
    
    /**
     * POST  /taxTables -> Create a new taxTable.
     */
    @RequestMapping(value = "/taxTables",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TaxTable> createTaxTable(@Valid @RequestBody TaxTable taxTable) throws URISyntaxException {
        log.debug("REST request to save TaxTable : {}", taxTable);
        if (taxTable.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("taxTable", "idexists", "A new taxTable cannot already have an ID")).body(null);
        }
        TaxTable result = taxTableRepository.save(taxTable);
        taxTableSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/taxTables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("taxTable", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taxTables -> Updates an existing taxTable.
     */
    @RequestMapping(value = "/taxTables",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TaxTable> updateTaxTable(@Valid @RequestBody TaxTable taxTable) throws URISyntaxException {
        log.debug("REST request to update TaxTable : {}", taxTable);
        if (taxTable.getId() == null) {
            return createTaxTable(taxTable);
        }
        TaxTable result = taxTableRepository.save(taxTable);
        taxTableSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("taxTable", taxTable.getId().toString()))
            .body(result);
    }

    /**
     * GET  /taxTables -> get all the taxTables.
     */
    @RequestMapping(value = "/taxTables",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TaxTable>> getAllTaxTables(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TaxTables");
        Page<TaxTable> page = taxTableRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/taxTables");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /taxTables/:id -> get the "id" taxTable.
     */
    @RequestMapping(value = "/taxTables/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TaxTable> getTaxTable(@PathVariable Long id) {
        log.debug("REST request to get TaxTable : {}", id);
        TaxTable taxTable = taxTableRepository.findOne(id);
        return Optional.ofNullable(taxTable)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /taxTables/:id -> delete the "id" taxTable.
     */
    @RequestMapping(value = "/taxTables/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTaxTable(@PathVariable Long id) {
        log.debug("REST request to delete TaxTable : {}", id);
        taxTableRepository.delete(id);
        taxTableSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("taxTable", id.toString())).build();
    }

    /**
     * SEARCH  /_search/taxTables/:query -> search for the taxTable corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/taxTables/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TaxTable> searchTaxTables(@PathVariable String query) {
        log.debug("REST request to search TaxTables for query {}", query);
        return StreamSupport
            .stream(taxTableSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
