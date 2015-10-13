package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.Customer;
import com.enginemobi.bssuite.repository.CustomerRepository;
import com.enginemobi.bssuite.repository.search.CustomerSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.CustomerDTO;
import com.enginemobi.bssuite.web.rest.mapper.CustomerMapper;
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
 * REST controller for managing Customer.
 */
@RestController
@RequestMapping("/api")
public class CustomerResource {

    private final Logger log = LoggerFactory.getLogger(CustomerResource.class);

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private CustomerMapper customerMapper;

    @Inject
    private CustomerSearchRepository customerSearchRepository;

    /**
     * POST  /customers -> Create a new customer.
     */
    @RequestMapping(value = "/customers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) throws URISyntaxException {
        log.debug("REST request to save Customer : {}", customerDTO);
        if (customerDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new customer cannot already have an ID").body(null);
        }
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        Customer result = customerRepository.save(customer);
        customerSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/customers/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("customer", result.getId().toString()))
                .body(customerMapper.customerToCustomerDTO(result));
    }

    /**
     * PUT  /customers -> Updates an existing customer.
     */
    @RequestMapping(value = "/customers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerDTO> updateCustomer(@Valid @RequestBody CustomerDTO customerDTO) throws URISyntaxException {
        log.debug("REST request to update Customer : {}", customerDTO);
        if (customerDTO.getId() == null) {
            return createCustomer(customerDTO);
        }
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        Customer result = customerRepository.save(customer);
        customerSearchRepository.save(customer);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("customer", customerDTO.getId().toString()))
                .body(customerMapper.customerToCustomerDTO(result));
    }

    /**
     * GET  /customers -> get all the customers.
     */
    @RequestMapping(value = "/customers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(Pageable pageable)
        throws URISyntaxException {
        Page<Customer> page = customerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customers");
        return new ResponseEntity<>(page.getContent().stream()
            .map(customerMapper::customerToCustomerDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /customers/:id -> get the "id" customer.
     */
    @RequestMapping(value = "/customers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long id) {
        log.debug("REST request to get Customer : {}", id);
        return Optional.ofNullable(customerRepository.findOne(id))
            .map(customerMapper::customerToCustomerDTO)
            .map(customerDTO -> new ResponseEntity<>(
                customerDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /customers/:id -> delete the "id" customer.
     */
    @RequestMapping(value = "/customers/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        log.debug("REST request to delete Customer : {}", id);
        customerRepository.delete(id);
        customerSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("customer", id.toString())).build();
    }

    /**
     * SEARCH  /_search/customers/:query -> search for the customer corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/customers/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CustomerDTO> searchCustomers(@PathVariable String query) {
        return StreamSupport
            .stream(customerSearchRepository.search(queryString(query)).spliterator(), false)
            .map(customerMapper::customerToCustomerDTO)
            .collect(Collectors.toList());
    }
}
