package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.Carrier;
import com.enginemobi.bssuite.repository.CarrierRepository;
import com.enginemobi.bssuite.repository.search.CarrierSearchRepository;
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
 * REST controller for managing Carrier.
 */
@RestController
@RequestMapping("/api")
public class CarrierResource {

    private final Logger log = LoggerFactory.getLogger(CarrierResource.class);
        
    @Inject
    private CarrierRepository carrierRepository;
    
    @Inject
    private CarrierSearchRepository carrierSearchRepository;
    
    /**
     * POST  /carriers -> Create a new carrier.
     */
    @RequestMapping(value = "/carriers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Carrier> createCarrier(@Valid @RequestBody Carrier carrier) throws URISyntaxException {
        log.debug("REST request to save Carrier : {}", carrier);
        if (carrier.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("carrier", "idexists", "A new carrier cannot already have an ID")).body(null);
        }
        Carrier result = carrierRepository.save(carrier);
        carrierSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/carriers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("carrier", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carriers -> Updates an existing carrier.
     */
    @RequestMapping(value = "/carriers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Carrier> updateCarrier(@Valid @RequestBody Carrier carrier) throws URISyntaxException {
        log.debug("REST request to update Carrier : {}", carrier);
        if (carrier.getId() == null) {
            return createCarrier(carrier);
        }
        Carrier result = carrierRepository.save(carrier);
        carrierSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("carrier", carrier.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carriers -> get all the carriers.
     */
    @RequestMapping(value = "/carriers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Carrier>> getAllCarriers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Carriers");
        Page<Carrier> page = carrierRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/carriers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /carriers/:id -> get the "id" carrier.
     */
    @RequestMapping(value = "/carriers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Carrier> getCarrier(@PathVariable Long id) {
        log.debug("REST request to get Carrier : {}", id);
        Carrier carrier = carrierRepository.findOne(id);
        return Optional.ofNullable(carrier)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /carriers/:id -> delete the "id" carrier.
     */
    @RequestMapping(value = "/carriers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCarrier(@PathVariable Long id) {
        log.debug("REST request to delete Carrier : {}", id);
        carrierRepository.delete(id);
        carrierSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("carrier", id.toString())).build();
    }

    /**
     * SEARCH  /_search/carriers/:query -> search for the carrier corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/carriers/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Carrier> searchCarriers(@PathVariable String query) {
        log.debug("REST request to search Carriers for query {}", query);
        return StreamSupport
            .stream(carrierSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
