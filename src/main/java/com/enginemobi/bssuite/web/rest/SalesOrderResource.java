package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.SalesOrder;
import com.enginemobi.bssuite.repository.SalesOrderRepository;
import com.enginemobi.bssuite.repository.search.SalesOrderSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.SalesOrderDTO;
import com.enginemobi.bssuite.web.rest.mapper.SalesOrderMapper;
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
 * REST controller for managing SalesOrder.
 */
@RestController
@RequestMapping("/api")
public class SalesOrderResource {

    private final Logger log = LoggerFactory.getLogger(SalesOrderResource.class);

    @Inject
    private SalesOrderRepository salesOrderRepository;

    @Inject
    private SalesOrderMapper salesOrderMapper;

    @Inject
    private SalesOrderSearchRepository salesOrderSearchRepository;

    /**
     * POST  /salesOrders -> Create a new salesOrder.
     */
    @RequestMapping(value = "/salesOrders",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SalesOrderDTO> createSalesOrder(@RequestBody SalesOrderDTO salesOrderDTO) throws URISyntaxException {
        log.debug("REST request to save SalesOrder : {}", salesOrderDTO);
        if (salesOrderDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new salesOrder cannot already have an ID").body(null);
        }
        SalesOrder salesOrder = salesOrderMapper.salesOrderDTOToSalesOrder(salesOrderDTO);
        SalesOrder result = salesOrderRepository.save(salesOrder);
        salesOrderSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/salesOrders/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("salesOrder", result.getId().toString()))
                .body(salesOrderMapper.salesOrderToSalesOrderDTO(result));
    }

    /**
     * PUT  /salesOrders -> Updates an existing salesOrder.
     */
    @RequestMapping(value = "/salesOrders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SalesOrderDTO> updateSalesOrder(@RequestBody SalesOrderDTO salesOrderDTO) throws URISyntaxException {
        log.debug("REST request to update SalesOrder : {}", salesOrderDTO);
        if (salesOrderDTO.getId() == null) {
            return createSalesOrder(salesOrderDTO);
        }
        SalesOrder salesOrder = salesOrderMapper.salesOrderDTOToSalesOrder(salesOrderDTO);
        SalesOrder result = salesOrderRepository.save(salesOrder);
        salesOrderSearchRepository.save(salesOrder);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("salesOrder", salesOrderDTO.getId().toString()))
                .body(salesOrderMapper.salesOrderToSalesOrderDTO(result));
    }

    /**
     * GET  /salesOrders -> get all the salesOrders.
     */
    @RequestMapping(value = "/salesOrders",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<SalesOrderDTO>> getAllSalesOrders(Pageable pageable)
        throws URISyntaxException {
        Page<SalesOrder> page = salesOrderRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/salesOrders");
        return new ResponseEntity<>(page.getContent().stream()
            .map(salesOrderMapper::salesOrderToSalesOrderDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /salesOrders/:id -> get the "id" salesOrder.
     */
    @RequestMapping(value = "/salesOrders/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SalesOrderDTO> getSalesOrder(@PathVariable Long id) {
        log.debug("REST request to get SalesOrder : {}", id);
        return Optional.ofNullable(salesOrderRepository.findOne(id))
            .map(salesOrderMapper::salesOrderToSalesOrderDTO)
            .map(salesOrderDTO -> new ResponseEntity<>(
                salesOrderDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /salesOrders/:id -> delete the "id" salesOrder.
     */
    @RequestMapping(value = "/salesOrders/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSalesOrder(@PathVariable Long id) {
        log.debug("REST request to delete SalesOrder : {}", id);
        salesOrderRepository.delete(id);
        salesOrderSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("salesOrder", id.toString())).build();
    }

    /**
     * SEARCH  /_search/salesOrders/:query -> search for the salesOrder corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/salesOrders/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SalesOrderDTO> searchSalesOrders(@PathVariable String query) {
        return StreamSupport
            .stream(salesOrderSearchRepository.search(queryString(query)).spliterator(), false)
            .map(salesOrderMapper::salesOrderToSalesOrderDTO)
            .collect(Collectors.toList());
    }
}
