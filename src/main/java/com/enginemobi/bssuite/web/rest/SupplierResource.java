package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.Supplier;
import com.enginemobi.bssuite.repository.SupplierRepository;
import com.enginemobi.bssuite.repository.search.SupplierSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.SupplierDTO;
import com.enginemobi.bssuite.web.rest.mapper.SupplierMapper;
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
 * REST controller for managing Supplier.
 */
@RestController
@RequestMapping("/api")
public class SupplierResource {

    private final Logger log = LoggerFactory.getLogger(SupplierResource.class);

    @Inject
    private SupplierRepository supplierRepository;

    @Inject
    private SupplierMapper supplierMapper;

    @Inject
    private SupplierSearchRepository supplierSearchRepository;

    /**
     * POST  /suppliers -> Create a new supplier.
     */
    @RequestMapping(value = "/suppliers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SupplierDTO> createSupplier(@Valid @RequestBody SupplierDTO supplierDTO) throws URISyntaxException {
        log.debug("REST request to save Supplier : {}", supplierDTO);
        if (supplierDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new supplier cannot already have an ID").body(null);
        }
        Supplier supplier = supplierMapper.supplierDTOToSupplier(supplierDTO);
        Supplier result = supplierRepository.save(supplier);
        supplierSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/suppliers/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("supplier", result.getId().toString()))
                .body(supplierMapper.supplierToSupplierDTO(result));
    }

    /**
     * PUT  /suppliers -> Updates an existing supplier.
     */
    @RequestMapping(value = "/suppliers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SupplierDTO> updateSupplier(@Valid @RequestBody SupplierDTO supplierDTO) throws URISyntaxException {
        log.debug("REST request to update Supplier : {}", supplierDTO);
        if (supplierDTO.getId() == null) {
            return createSupplier(supplierDTO);
        }
        Supplier supplier = supplierMapper.supplierDTOToSupplier(supplierDTO);
        Supplier result = supplierRepository.save(supplier);
        supplierSearchRepository.save(supplier);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("supplier", supplierDTO.getId().toString()))
                .body(supplierMapper.supplierToSupplierDTO(result));
    }

    /**
     * GET  /suppliers -> get all the suppliers.
     */
    @RequestMapping(value = "/suppliers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers(Pageable pageable)
        throws URISyntaxException {
        Page<Supplier> page = supplierRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/suppliers");
        return new ResponseEntity<>(page.getContent().stream()
            .map(supplierMapper::supplierToSupplierDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /suppliers/:id -> get the "id" supplier.
     */
    @RequestMapping(value = "/suppliers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SupplierDTO> getSupplier(@PathVariable Long id) {
        log.debug("REST request to get Supplier : {}", id);
        return Optional.ofNullable(supplierRepository.findOne(id))
            .map(supplierMapper::supplierToSupplierDTO)
            .map(supplierDTO -> new ResponseEntity<>(
                supplierDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /suppliers/:id -> delete the "id" supplier.
     */
    @RequestMapping(value = "/suppliers/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        log.debug("REST request to delete Supplier : {}", id);
        supplierRepository.delete(id);
        supplierSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("supplier", id.toString())).build();
    }

    /**
     * SEARCH  /_search/suppliers/:query -> search for the supplier corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/suppliers/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SupplierDTO> searchSuppliers(@PathVariable String query) {
        return StreamSupport
            .stream(supplierSearchRepository.search(queryString(query)).spliterator(), false)
            .map(supplierMapper::supplierToSupplierDTO)
            .collect(Collectors.toList());
    }
}
