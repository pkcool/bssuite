package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.SupplierCategory;
import com.enginemobi.bssuite.repository.SupplierCategoryRepository;
import com.enginemobi.bssuite.repository.search.SupplierCategorySearchRepository;
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
 * REST controller for managing SupplierCategory.
 */
@RestController
@RequestMapping("/api")
public class SupplierCategoryResource {

    private final Logger log = LoggerFactory.getLogger(SupplierCategoryResource.class);

    @Inject
    private SupplierCategoryRepository supplierCategoryRepository;

    @Inject
    private SupplierCategorySearchRepository supplierCategorySearchRepository;

    /**
     * POST  /supplierCategorys -> Create a new supplierCategory.
     */
    @RequestMapping(value = "/supplierCategorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SupplierCategory> createSupplierCategory(@Valid @RequestBody SupplierCategory supplierCategory) throws URISyntaxException {
        log.debug("REST request to save SupplierCategory : {}", supplierCategory);
        if (supplierCategory.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new supplierCategory cannot already have an ID").body(null);
        }
        SupplierCategory result = supplierCategoryRepository.save(supplierCategory);
        supplierCategorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/supplierCategorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("supplierCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supplierCategorys -> Updates an existing supplierCategory.
     */
    @RequestMapping(value = "/supplierCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SupplierCategory> updateSupplierCategory(@Valid @RequestBody SupplierCategory supplierCategory) throws URISyntaxException {
        log.debug("REST request to update SupplierCategory : {}", supplierCategory);
        if (supplierCategory.getId() == null) {
            return createSupplierCategory(supplierCategory);
        }
        SupplierCategory result = supplierCategoryRepository.save(supplierCategory);
        supplierCategorySearchRepository.save(supplierCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("supplierCategory", supplierCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supplierCategorys -> get all the supplierCategorys.
     */
    @RequestMapping(value = "/supplierCategorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SupplierCategory>> getAllSupplierCategorys(Pageable pageable)
        throws URISyntaxException {
        Page<SupplierCategory> page = supplierCategoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/supplierCategorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /supplierCategorys/:id -> get the "id" supplierCategory.
     */
    @RequestMapping(value = "/supplierCategorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SupplierCategory> getSupplierCategory(@PathVariable Long id) {
        log.debug("REST request to get SupplierCategory : {}", id);
        return Optional.ofNullable(supplierCategoryRepository.findOne(id))
            .map(supplierCategory -> new ResponseEntity<>(
                supplierCategory,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /supplierCategorys/:id -> delete the "id" supplierCategory.
     */
    @RequestMapping(value = "/supplierCategorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSupplierCategory(@PathVariable Long id) {
        log.debug("REST request to delete SupplierCategory : {}", id);
        supplierCategoryRepository.delete(id);
        supplierCategorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("supplierCategory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/supplierCategorys/:query -> search for the supplierCategory corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/supplierCategorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SupplierCategory> searchSupplierCategorys(@PathVariable String query) {
        return StreamSupport
            .stream(supplierCategorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
