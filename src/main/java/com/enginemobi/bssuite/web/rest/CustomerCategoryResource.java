package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.CustomerCategory;
import com.enginemobi.bssuite.repository.CustomerCategoryRepository;
import com.enginemobi.bssuite.repository.search.CustomerCategorySearchRepository;
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
 * REST controller for managing CustomerCategory.
 */
@RestController
@RequestMapping("/api")
public class CustomerCategoryResource {

    private final Logger log = LoggerFactory.getLogger(CustomerCategoryResource.class);
        
    @Inject
    private CustomerCategoryRepository customerCategoryRepository;
    
    @Inject
    private CustomerCategorySearchRepository customerCategorySearchRepository;
    
    /**
     * POST  /customerCategorys -> Create a new customerCategory.
     */
    @RequestMapping(value = "/customerCategorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerCategory> createCustomerCategory(@Valid @RequestBody CustomerCategory customerCategory) throws URISyntaxException {
        log.debug("REST request to save CustomerCategory : {}", customerCategory);
        if (customerCategory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("customerCategory", "idexists", "A new customerCategory cannot already have an ID")).body(null);
        }
        CustomerCategory result = customerCategoryRepository.save(customerCategory);
        customerCategorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/customerCategorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("customerCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customerCategorys -> Updates an existing customerCategory.
     */
    @RequestMapping(value = "/customerCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerCategory> updateCustomerCategory(@Valid @RequestBody CustomerCategory customerCategory) throws URISyntaxException {
        log.debug("REST request to update CustomerCategory : {}", customerCategory);
        if (customerCategory.getId() == null) {
            return createCustomerCategory(customerCategory);
        }
        CustomerCategory result = customerCategoryRepository.save(customerCategory);
        customerCategorySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("customerCategory", customerCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customerCategorys -> get all the customerCategorys.
     */
    @RequestMapping(value = "/customerCategorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CustomerCategory>> getAllCustomerCategorys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CustomerCategorys");
        Page<CustomerCategory> page = customerCategoryRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customerCategorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customerCategorys/:id -> get the "id" customerCategory.
     */
    @RequestMapping(value = "/customerCategorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerCategory> getCustomerCategory(@PathVariable Long id) {
        log.debug("REST request to get CustomerCategory : {}", id);
        CustomerCategory customerCategory = customerCategoryRepository.findOne(id);
        return Optional.ofNullable(customerCategory)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /customerCategorys/:id -> delete the "id" customerCategory.
     */
    @RequestMapping(value = "/customerCategorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCustomerCategory(@PathVariable Long id) {
        log.debug("REST request to delete CustomerCategory : {}", id);
        customerCategoryRepository.delete(id);
        customerCategorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("customerCategory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/customerCategorys/:query -> search for the customerCategory corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/customerCategorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CustomerCategory> searchCustomerCategorys(@PathVariable String query) {
        log.debug("REST request to search CustomerCategorys for query {}", query);
        return StreamSupport
            .stream(customerCategorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
