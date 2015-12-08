package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.StockFamily;
import com.enginemobi.bssuite.repository.StockFamilyRepository;
import com.enginemobi.bssuite.repository.search.StockFamilySearchRepository;
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
 * REST controller for managing StockFamily.
 */
@RestController
@RequestMapping("/api")
public class StockFamilyResource {

    private final Logger log = LoggerFactory.getLogger(StockFamilyResource.class);
        
    @Inject
    private StockFamilyRepository stockFamilyRepository;
    
    @Inject
    private StockFamilySearchRepository stockFamilySearchRepository;
    
    /**
     * POST  /stockFamilys -> Create a new stockFamily.
     */
    @RequestMapping(value = "/stockFamilys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StockFamily> createStockFamily(@Valid @RequestBody StockFamily stockFamily) throws URISyntaxException {
        log.debug("REST request to save StockFamily : {}", stockFamily);
        if (stockFamily.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("stockFamily", "idexists", "A new stockFamily cannot already have an ID")).body(null);
        }
        StockFamily result = stockFamilyRepository.save(stockFamily);
        stockFamilySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/stockFamilys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("stockFamily", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stockFamilys -> Updates an existing stockFamily.
     */
    @RequestMapping(value = "/stockFamilys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StockFamily> updateStockFamily(@Valid @RequestBody StockFamily stockFamily) throws URISyntaxException {
        log.debug("REST request to update StockFamily : {}", stockFamily);
        if (stockFamily.getId() == null) {
            return createStockFamily(stockFamily);
        }
        StockFamily result = stockFamilyRepository.save(stockFamily);
        stockFamilySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("stockFamily", stockFamily.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stockFamilys -> get all the stockFamilys.
     */
    @RequestMapping(value = "/stockFamilys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<StockFamily>> getAllStockFamilys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of StockFamilys");
        Page<StockFamily> page = stockFamilyRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stockFamilys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /stockFamilys/:id -> get the "id" stockFamily.
     */
    @RequestMapping(value = "/stockFamilys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StockFamily> getStockFamily(@PathVariable Long id) {
        log.debug("REST request to get StockFamily : {}", id);
        StockFamily stockFamily = stockFamilyRepository.findOne(id);
        return Optional.ofNullable(stockFamily)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /stockFamilys/:id -> delete the "id" stockFamily.
     */
    @RequestMapping(value = "/stockFamilys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStockFamily(@PathVariable Long id) {
        log.debug("REST request to delete StockFamily : {}", id);
        stockFamilyRepository.delete(id);
        stockFamilySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("stockFamily", id.toString())).build();
    }

    /**
     * SEARCH  /_search/stockFamilys/:query -> search for the stockFamily corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/stockFamilys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StockFamily> searchStockFamilys(@PathVariable String query) {
        log.debug("REST request to search StockFamilys for query {}", query);
        return StreamSupport
            .stream(stockFamilySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
