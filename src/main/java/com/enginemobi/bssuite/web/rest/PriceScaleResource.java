package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.PriceScale;
import com.enginemobi.bssuite.repository.PriceScaleRepository;
import com.enginemobi.bssuite.repository.search.PriceScaleSearchRepository;
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
 * REST controller for managing PriceScale.
 */
@RestController
@RequestMapping("/api")
public class PriceScaleResource {

    private final Logger log = LoggerFactory.getLogger(PriceScaleResource.class);

    @Inject
    private PriceScaleRepository priceScaleRepository;

    @Inject
    private PriceScaleSearchRepository priceScaleSearchRepository;

    /**
     * POST  /priceScales -> Create a new priceScale.
     */
    @RequestMapping(value = "/priceScales",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PriceScale> createPriceScale(@Valid @RequestBody PriceScale priceScale) throws URISyntaxException {
        log.debug("REST request to save PriceScale : {}", priceScale);
        if (priceScale.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new priceScale cannot already have an ID").body(null);
        }
        PriceScale result = priceScaleRepository.save(priceScale);
        priceScaleSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/priceScales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("priceScale", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /priceScales -> Updates an existing priceScale.
     */
    @RequestMapping(value = "/priceScales",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PriceScale> updatePriceScale(@Valid @RequestBody PriceScale priceScale) throws URISyntaxException {
        log.debug("REST request to update PriceScale : {}", priceScale);
        if (priceScale.getId() == null) {
            return createPriceScale(priceScale);
        }
        PriceScale result = priceScaleRepository.save(priceScale);
        priceScaleSearchRepository.save(priceScale);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("priceScale", priceScale.getId().toString()))
            .body(result);
    }

    /**
     * GET  /priceScales -> get all the priceScales.
     */
    @RequestMapping(value = "/priceScales",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PriceScale>> getAllPriceScales(Pageable pageable)
        throws URISyntaxException {
        Page<PriceScale> page = priceScaleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/priceScales");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /priceScales/:id -> get the "id" priceScale.
     */
    @RequestMapping(value = "/priceScales/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PriceScale> getPriceScale(@PathVariable Long id) {
        log.debug("REST request to get PriceScale : {}", id);
        return Optional.ofNullable(priceScaleRepository.findOne(id))
            .map(priceScale -> new ResponseEntity<>(
                priceScale,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /priceScales/:id -> delete the "id" priceScale.
     */
    @RequestMapping(value = "/priceScales/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePriceScale(@PathVariable Long id) {
        log.debug("REST request to delete PriceScale : {}", id);
        priceScaleRepository.delete(id);
        priceScaleSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("priceScale", id.toString())).build();
    }

    /**
     * SEARCH  /_search/priceScales/:query -> search for the priceScale corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/priceScales/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PriceScale> searchPriceScales(@PathVariable String query) {
        return StreamSupport
            .stream(priceScaleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
