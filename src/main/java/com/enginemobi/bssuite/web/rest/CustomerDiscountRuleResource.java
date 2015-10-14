package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.CustomerDiscountRule;
import com.enginemobi.bssuite.repository.CustomerDiscountRuleRepository;
import com.enginemobi.bssuite.repository.search.CustomerDiscountRuleSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.CustomerDiscountRuleDTO;
import com.enginemobi.bssuite.web.rest.mapper.CustomerDiscountRuleMapper;
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
 * REST controller for managing CustomerDiscountRule.
 */
@RestController
@RequestMapping("/api")
public class CustomerDiscountRuleResource {

    private final Logger log = LoggerFactory.getLogger(CustomerDiscountRuleResource.class);

    @Inject
    private CustomerDiscountRuleRepository customerDiscountRuleRepository;

    @Inject
    private CustomerDiscountRuleMapper customerDiscountRuleMapper;

    @Inject
    private CustomerDiscountRuleSearchRepository customerDiscountRuleSearchRepository;

    /**
     * POST  /customerDiscountRules -> Create a new customerDiscountRule.
     */
    @RequestMapping(value = "/customerDiscountRules",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerDiscountRuleDTO> createCustomerDiscountRule(@RequestBody CustomerDiscountRuleDTO customerDiscountRuleDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerDiscountRule : {}", customerDiscountRuleDTO);
        if (customerDiscountRuleDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new customerDiscountRule cannot already have an ID").body(null);
        }
        CustomerDiscountRule customerDiscountRule = customerDiscountRuleMapper.customerDiscountRuleDTOToCustomerDiscountRule(customerDiscountRuleDTO);
        CustomerDiscountRule result = customerDiscountRuleRepository.save(customerDiscountRule);
        customerDiscountRuleSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/customerDiscountRules/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("customerDiscountRule", result.getId().toString()))
                .body(customerDiscountRuleMapper.customerDiscountRuleToCustomerDiscountRuleDTO(result));
    }

    /**
     * PUT  /customerDiscountRules -> Updates an existing customerDiscountRule.
     */
    @RequestMapping(value = "/customerDiscountRules",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerDiscountRuleDTO> updateCustomerDiscountRule(@RequestBody CustomerDiscountRuleDTO customerDiscountRuleDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerDiscountRule : {}", customerDiscountRuleDTO);
        if (customerDiscountRuleDTO.getId() == null) {
            return createCustomerDiscountRule(customerDiscountRuleDTO);
        }
        CustomerDiscountRule customerDiscountRule = customerDiscountRuleMapper.customerDiscountRuleDTOToCustomerDiscountRule(customerDiscountRuleDTO);
        CustomerDiscountRule result = customerDiscountRuleRepository.save(customerDiscountRule);
        customerDiscountRuleSearchRepository.save(customerDiscountRule);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("customerDiscountRule", customerDiscountRuleDTO.getId().toString()))
                .body(customerDiscountRuleMapper.customerDiscountRuleToCustomerDiscountRuleDTO(result));
    }

    /**
     * GET  /customerDiscountRules -> get all the customerDiscountRules.
     */
    @RequestMapping(value = "/customerDiscountRules",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<CustomerDiscountRuleDTO>> getAllCustomerDiscountRules(Pageable pageable)
        throws URISyntaxException {
        Page<CustomerDiscountRule> page = customerDiscountRuleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customerDiscountRules");
        return new ResponseEntity<>(page.getContent().stream()
            .map(customerDiscountRuleMapper::customerDiscountRuleToCustomerDiscountRuleDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /customerDiscountRules/:id -> get the "id" customerDiscountRule.
     */
    @RequestMapping(value = "/customerDiscountRules/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerDiscountRuleDTO> getCustomerDiscountRule(@PathVariable Long id) {
        log.debug("REST request to get CustomerDiscountRule : {}", id);
        return Optional.ofNullable(customerDiscountRuleRepository.findOne(id))
            .map(customerDiscountRuleMapper::customerDiscountRuleToCustomerDiscountRuleDTO)
            .map(customerDiscountRuleDTO -> new ResponseEntity<>(
                customerDiscountRuleDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /customerDiscountRules/:id -> delete the "id" customerDiscountRule.
     */
    @RequestMapping(value = "/customerDiscountRules/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCustomerDiscountRule(@PathVariable Long id) {
        log.debug("REST request to delete CustomerDiscountRule : {}", id);
        customerDiscountRuleRepository.delete(id);
        customerDiscountRuleSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("customerDiscountRule", id.toString())).build();
    }

    /**
     * SEARCH  /_search/customerDiscountRules/:query -> search for the customerDiscountRule corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/customerDiscountRules/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CustomerDiscountRuleDTO> searchCustomerDiscountRules(@PathVariable String query) {
        return StreamSupport
            .stream(customerDiscountRuleSearchRepository.search(queryString(query)).spliterator(), false)
            .map(customerDiscountRuleMapper::customerDiscountRuleToCustomerDiscountRuleDTO)
            .collect(Collectors.toList());
    }
}
