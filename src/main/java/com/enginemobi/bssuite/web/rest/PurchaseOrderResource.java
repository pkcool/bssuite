package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.PurchaseOrder;
import com.enginemobi.bssuite.repository.PurchaseOrderRepository;
import com.enginemobi.bssuite.repository.search.PurchaseOrderSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.PurchaseOrderDTO;
import com.enginemobi.bssuite.web.rest.mapper.PurchaseOrderMapper;
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
 * REST controller for managing PurchaseOrder.
 */
@RestController
@RequestMapping("/api")
public class PurchaseOrderResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderResource.class);

    @Inject
    private PurchaseOrderRepository purchaseOrderRepository;

    @Inject
    private PurchaseOrderMapper purchaseOrderMapper;

    @Inject
    private PurchaseOrderSearchRepository purchaseOrderSearchRepository;

    /**
     * POST  /purchaseOrders -> Create a new purchaseOrder.
     */
    @RequestMapping(value = "/purchaseOrders",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PurchaseOrderDTO> createPurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO) throws URISyntaxException {
        log.debug("REST request to save PurchaseOrder : {}", purchaseOrderDTO);
        if (purchaseOrderDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new purchaseOrder cannot already have an ID").body(null);
        }
        PurchaseOrder purchaseOrder = purchaseOrderMapper.purchaseOrderDTOToPurchaseOrder(purchaseOrderDTO);
        PurchaseOrder result = purchaseOrderRepository.save(purchaseOrder);
        purchaseOrderSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/purchaseOrders/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("purchaseOrder", result.getId().toString()))
                .body(purchaseOrderMapper.purchaseOrderToPurchaseOrderDTO(result));
    }

    /**
     * PUT  /purchaseOrders -> Updates an existing purchaseOrder.
     */
    @RequestMapping(value = "/purchaseOrders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PurchaseOrderDTO> updatePurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO) throws URISyntaxException {
        log.debug("REST request to update PurchaseOrder : {}", purchaseOrderDTO);
        if (purchaseOrderDTO.getId() == null) {
            return createPurchaseOrder(purchaseOrderDTO);
        }
        PurchaseOrder purchaseOrder = purchaseOrderMapper.purchaseOrderDTOToPurchaseOrder(purchaseOrderDTO);
        PurchaseOrder result = purchaseOrderRepository.save(purchaseOrder);
        purchaseOrderSearchRepository.save(purchaseOrder);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("purchaseOrder", purchaseOrderDTO.getId().toString()))
                .body(purchaseOrderMapper.purchaseOrderToPurchaseOrderDTO(result));
    }

    /**
     * GET  /purchaseOrders -> get all the purchaseOrders.
     */
    @RequestMapping(value = "/purchaseOrders",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<PurchaseOrderDTO>> getAllPurchaseOrders(Pageable pageable)
        throws URISyntaxException {
        Page<PurchaseOrder> page = purchaseOrderRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/purchaseOrders");
        return new ResponseEntity<>(page.getContent().stream()
            .map(purchaseOrderMapper::purchaseOrderToPurchaseOrderDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /purchaseOrders/:id -> get the "id" purchaseOrder.
     */
    @RequestMapping(value = "/purchaseOrders/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PurchaseOrderDTO> getPurchaseOrder(@PathVariable Long id) {
        log.debug("REST request to get PurchaseOrder : {}", id);
        return Optional.ofNullable(purchaseOrderRepository.findOne(id))
            .map(purchaseOrderMapper::purchaseOrderToPurchaseOrderDTO)
            .map(purchaseOrderDTO -> new ResponseEntity<>(
                purchaseOrderDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /purchaseOrders/:id -> delete the "id" purchaseOrder.
     */
    @RequestMapping(value = "/purchaseOrders/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseOrder : {}", id);
        purchaseOrderRepository.delete(id);
        purchaseOrderSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("purchaseOrder", id.toString())).build();
    }

    /**
     * SEARCH  /_search/purchaseOrders/:query -> search for the purchaseOrder corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/purchaseOrders/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PurchaseOrderDTO> searchPurchaseOrders(@PathVariable String query) {
        return StreamSupport
            .stream(purchaseOrderSearchRepository.search(queryString(query)).spliterator(), false)
            .map(purchaseOrderMapper::purchaseOrderToPurchaseOrderDTO)
            .collect(Collectors.toList());
    }
}
