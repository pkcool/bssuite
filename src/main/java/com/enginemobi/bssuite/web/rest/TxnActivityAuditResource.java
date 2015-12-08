package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.TxnActivityAudit;
import com.enginemobi.bssuite.repository.TxnActivityAuditRepository;
import com.enginemobi.bssuite.repository.search.TxnActivityAuditSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.TxnActivityAuditDTO;
import com.enginemobi.bssuite.web.rest.mapper.TxnActivityAuditMapper;
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
 * REST controller for managing TxnActivityAudit.
 */
@RestController
@RequestMapping("/api")
public class TxnActivityAuditResource {

    private final Logger log = LoggerFactory.getLogger(TxnActivityAuditResource.class);
        
    @Inject
    private TxnActivityAuditRepository txnActivityAuditRepository;
    
    @Inject
    private TxnActivityAuditMapper txnActivityAuditMapper;
    
    @Inject
    private TxnActivityAuditSearchRepository txnActivityAuditSearchRepository;
    
    /**
     * POST  /txnActivityAudits -> Create a new txnActivityAudit.
     */
    @RequestMapping(value = "/txnActivityAudits",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TxnActivityAuditDTO> createTxnActivityAudit(@RequestBody TxnActivityAuditDTO txnActivityAuditDTO) throws URISyntaxException {
        log.debug("REST request to save TxnActivityAudit : {}", txnActivityAuditDTO);
        if (txnActivityAuditDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("txnActivityAudit", "idexists", "A new txnActivityAudit cannot already have an ID")).body(null);
        }
        TxnActivityAudit txnActivityAudit = txnActivityAuditMapper.txnActivityAuditDTOToTxnActivityAudit(txnActivityAuditDTO);
        txnActivityAudit = txnActivityAuditRepository.save(txnActivityAudit);
        TxnActivityAuditDTO result = txnActivityAuditMapper.txnActivityAuditToTxnActivityAuditDTO(txnActivityAudit);
        txnActivityAuditSearchRepository.save(txnActivityAudit);
        return ResponseEntity.created(new URI("/api/txnActivityAudits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("txnActivityAudit", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /txnActivityAudits -> Updates an existing txnActivityAudit.
     */
    @RequestMapping(value = "/txnActivityAudits",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TxnActivityAuditDTO> updateTxnActivityAudit(@RequestBody TxnActivityAuditDTO txnActivityAuditDTO) throws URISyntaxException {
        log.debug("REST request to update TxnActivityAudit : {}", txnActivityAuditDTO);
        if (txnActivityAuditDTO.getId() == null) {
            return createTxnActivityAudit(txnActivityAuditDTO);
        }
        TxnActivityAudit txnActivityAudit = txnActivityAuditMapper.txnActivityAuditDTOToTxnActivityAudit(txnActivityAuditDTO);
        txnActivityAudit = txnActivityAuditRepository.save(txnActivityAudit);
        TxnActivityAuditDTO result = txnActivityAuditMapper.txnActivityAuditToTxnActivityAuditDTO(txnActivityAudit);
        txnActivityAuditSearchRepository.save(txnActivityAudit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("txnActivityAudit", txnActivityAuditDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /txnActivityAudits -> get all the txnActivityAudits.
     */
    @RequestMapping(value = "/txnActivityAudits",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<TxnActivityAuditDTO>> getAllTxnActivityAudits(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TxnActivityAudits");
        Page<TxnActivityAudit> page = txnActivityAuditRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/txnActivityAudits");
        return new ResponseEntity<>(page.getContent().stream()
            .map(txnActivityAuditMapper::txnActivityAuditToTxnActivityAuditDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /txnActivityAudits/:id -> get the "id" txnActivityAudit.
     */
    @RequestMapping(value = "/txnActivityAudits/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TxnActivityAuditDTO> getTxnActivityAudit(@PathVariable Long id) {
        log.debug("REST request to get TxnActivityAudit : {}", id);
        TxnActivityAudit txnActivityAudit = txnActivityAuditRepository.findOne(id);
        TxnActivityAuditDTO txnActivityAuditDTO = txnActivityAuditMapper.txnActivityAuditToTxnActivityAuditDTO(txnActivityAudit);
        return Optional.ofNullable(txnActivityAuditDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /txnActivityAudits/:id -> delete the "id" txnActivityAudit.
     */
    @RequestMapping(value = "/txnActivityAudits/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTxnActivityAudit(@PathVariable Long id) {
        log.debug("REST request to delete TxnActivityAudit : {}", id);
        txnActivityAuditRepository.delete(id);
        txnActivityAuditSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("txnActivityAudit", id.toString())).build();
    }

    /**
     * SEARCH  /_search/txnActivityAudits/:query -> search for the txnActivityAudit corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/txnActivityAudits/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TxnActivityAuditDTO> searchTxnActivityAudits(@PathVariable String query) {
        log.debug("REST request to search TxnActivityAudits for query {}", query);
        return StreamSupport
            .stream(txnActivityAuditSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(txnActivityAuditMapper::txnActivityAuditToTxnActivityAuditDTO)
            .collect(Collectors.toList());
    }
}
