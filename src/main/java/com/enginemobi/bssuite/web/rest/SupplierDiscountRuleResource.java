package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.SupplierDiscountRule;
import com.enginemobi.bssuite.repository.SupplierDiscountRuleRepository;
import com.enginemobi.bssuite.repository.search.SupplierDiscountRuleSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.SupplierDiscountRuleDTO;
import com.enginemobi.bssuite.web.rest.mapper.SupplierDiscountRuleMapper;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing SupplierDiscountRule.
 */
@RestController
@RequestMapping("/api")
public class SupplierDiscountRuleResource {

    private final Logger log = LoggerFactory.getLogger(SupplierDiscountRuleResource.class);
        
    @Inject
    private SupplierDiscountRuleRepository supplierDiscountRuleRepository;
    
    @Inject
    private SupplierDiscountRuleMapper supplierDiscountRuleMapper;
    
    @Inject
    private SupplierDiscountRuleSearchRepository supplierDiscountRuleSearchRepository;
    
    /**
     * POST  /supplierDiscountRules -> Create a new supplierDiscountRule.
     */
    @RequestMapping(value = "/supplierDiscountRules",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SupplierDiscountRuleDTO> createSupplierDiscountRule(@Valid @RequestBody SupplierDiscountRuleDTO supplierDiscountRuleDTO) throws URISyntaxException {
        log.debug("REST request to save SupplierDiscountRule : {}", supplierDiscountRuleDTO);
        if (supplierDiscountRuleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("supplierDiscountRule", "idexists", "A new supplierDiscountRule cannot already have an ID")).body(null);
        }
        SupplierDiscountRule supplierDiscountRule = supplierDiscountRuleMapper.supplierDiscountRuleDTOToSupplierDiscountRule(supplierDiscountRuleDTO);
        supplierDiscountRule = supplierDiscountRuleRepository.save(supplierDiscountRule);
        SupplierDiscountRuleDTO result = supplierDiscountRuleMapper.supplierDiscountRuleToSupplierDiscountRuleDTO(supplierDiscountRule);
        supplierDiscountRuleSearchRepository.save(supplierDiscountRule);
        return ResponseEntity.created(new URI("/api/supplierDiscountRules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("supplierDiscountRule", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supplierDiscountRules -> Updates an existing supplierDiscountRule.
     */
    @RequestMapping(value = "/supplierDiscountRules",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SupplierDiscountRuleDTO> updateSupplierDiscountRule(@Valid @RequestBody SupplierDiscountRuleDTO supplierDiscountRuleDTO) throws URISyntaxException {
        log.debug("REST request to update SupplierDiscountRule : {}", supplierDiscountRuleDTO);
        if (supplierDiscountRuleDTO.getId() == null) {
            return createSupplierDiscountRule(supplierDiscountRuleDTO);
        }
        SupplierDiscountRule supplierDiscountRule = supplierDiscountRuleMapper.supplierDiscountRuleDTOToSupplierDiscountRule(supplierDiscountRuleDTO);
        supplierDiscountRule = supplierDiscountRuleRepository.save(supplierDiscountRule);
        SupplierDiscountRuleDTO result = supplierDiscountRuleMapper.supplierDiscountRuleToSupplierDiscountRuleDTO(supplierDiscountRule);
        supplierDiscountRuleSearchRepository.save(supplierDiscountRule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("supplierDiscountRule", supplierDiscountRuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supplierDiscountRules -> get all the supplierDiscountRules.
     */
    @RequestMapping(value = "/supplierDiscountRules",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<SupplierDiscountRuleDTO>> getAllSupplierDiscountRules(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SupplierDiscountRules");
        Page<SupplierDiscountRule> page = supplierDiscountRuleRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/supplierDiscountRules");
        return new ResponseEntity<>(page.getContent().stream()
            .map(supplierDiscountRuleMapper::supplierDiscountRuleToSupplierDiscountRuleDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /supplierDiscountRules/:id -> get the "id" supplierDiscountRule.
     */
    @RequestMapping(value = "/supplierDiscountRules/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SupplierDiscountRuleDTO> getSupplierDiscountRule(@PathVariable Long id) {
        log.debug("REST request to get SupplierDiscountRule : {}", id);
        SupplierDiscountRule supplierDiscountRule = supplierDiscountRuleRepository.findOne(id);
        SupplierDiscountRuleDTO supplierDiscountRuleDTO = supplierDiscountRuleMapper.supplierDiscountRuleToSupplierDiscountRuleDTO(supplierDiscountRule);
        return Optional.ofNullable(supplierDiscountRuleDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /supplierDiscountRules/:id -> delete the "id" supplierDiscountRule.
     */
    @RequestMapping(value = "/supplierDiscountRules/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSupplierDiscountRule(@PathVariable Long id) {
        log.debug("REST request to delete SupplierDiscountRule : {}", id);
        supplierDiscountRuleRepository.delete(id);
        supplierDiscountRuleSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("supplierDiscountRule", id.toString())).build();
    }

    /**
     * SEARCH  /_search/supplierDiscountRules/:query -> search for the supplierDiscountRule corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/supplierDiscountRules/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SupplierDiscountRuleDTO> searchSupplierDiscountRules(@PathVariable String query) {
        log.debug("REST request to search SupplierDiscountRules for query {}", query);
        return StreamSupport
            .stream(supplierDiscountRuleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(supplierDiscountRuleMapper::supplierDiscountRuleToSupplierDiscountRuleDTO)
            .collect(Collectors.toList());
    }
}
