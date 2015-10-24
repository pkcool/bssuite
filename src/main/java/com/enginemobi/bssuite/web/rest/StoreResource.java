package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.Store;
import com.enginemobi.bssuite.repository.StoreRepository;
import com.enginemobi.bssuite.repository.search.StoreSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.StoreDTO;
import com.enginemobi.bssuite.web.rest.mapper.StoreMapper;
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
 * REST controller for managing Store.
 */
@RestController
@RequestMapping("/api")
public class StoreResource {

    private final Logger log = LoggerFactory.getLogger(StoreResource.class);

    @Inject
    private StoreRepository storeRepository;

    @Inject
    private StoreMapper storeMapper;

    @Inject
    private StoreSearchRepository storeSearchRepository;

    /**
     * POST  /stores -> Create a new store.
     */
    @RequestMapping(value = "/stores",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO) throws URISyntaxException {
        log.debug("REST request to save Store : {}", storeDTO);
        if (storeDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new store cannot already have an ID").body(null);
        }
        Store store = storeMapper.storeDTOToStore(storeDTO);
        Store result = storeRepository.save(store);
        storeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/stores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("store", result.getId().toString()))
            .body(storeMapper.storeToStoreDTO(result));
    }

    /**
     * PUT  /stores -> Updates an existing store.
     */
    @RequestMapping(value = "/stores",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StoreDTO> updateStore(@RequestBody StoreDTO storeDTO) throws URISyntaxException {
        log.debug("REST request to update Store : {}", storeDTO);
        if (storeDTO.getId() == null) {
            return createStore(storeDTO);
        }
        Store store = storeMapper.storeDTOToStore(storeDTO);
        Store result = storeRepository.save(store);
        storeSearchRepository.save(store);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("store", storeDTO.getId().toString()))
            .body(storeMapper.storeToStoreDTO(result));
    }

    /**
     * GET  /stores -> get all the stores.
     */
    @RequestMapping(value = "/stores",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<StoreDTO>> getAllStores(Pageable pageable)
        throws URISyntaxException {
        Page<Store> page = storeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stores");
        return new ResponseEntity<>(page.getContent().stream()
            .map(storeMapper::storeToStoreDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /stores/:id -> get the "id" store.
     */
    @RequestMapping(value = "/stores/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StoreDTO> getStore(@PathVariable Long id) {
        log.debug("REST request to get Store : {}", id);
        return Optional.ofNullable(storeRepository.findOne(id))
            .map(storeMapper::storeToStoreDTO)
            .map(storeDTO -> new ResponseEntity<>(
                storeDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /stores/:id -> delete the "id" store.
     */
    @RequestMapping(value = "/stores/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        log.debug("REST request to delete Store : {}", id);
        storeRepository.delete(id);
        storeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("store", id.toString())).build();
    }

    /**
     * SEARCH  /_search/stores/:query -> search for the store corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/stores/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StoreDTO> searchStores(@PathVariable String query) {
        return StreamSupport
            .stream(storeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(storeMapper::storeToStoreDTO)
            .collect(Collectors.toList());
    }
}
