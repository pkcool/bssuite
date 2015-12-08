package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.BackOrderLineItem;
import com.enginemobi.bssuite.repository.BackOrderLineItemRepository;
import com.enginemobi.bssuite.repository.search.BackOrderLineItemSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.BackOrderLineItemDTO;
import com.enginemobi.bssuite.web.rest.mapper.BackOrderLineItemMapper;
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
 * REST controller for managing BackOrderLineItem.
 */
@RestController
@RequestMapping("/api")
public class BackOrderLineItemResource {

    private final Logger log = LoggerFactory.getLogger(BackOrderLineItemResource.class);
        
    @Inject
    private BackOrderLineItemRepository backOrderLineItemRepository;
    
    @Inject
    private BackOrderLineItemMapper backOrderLineItemMapper;
    
    @Inject
    private BackOrderLineItemSearchRepository backOrderLineItemSearchRepository;
    
    /**
     * POST  /backOrderLineItems -> Create a new backOrderLineItem.
     */
    @RequestMapping(value = "/backOrderLineItems",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BackOrderLineItemDTO> createBackOrderLineItem(@RequestBody BackOrderLineItemDTO backOrderLineItemDTO) throws URISyntaxException {
        log.debug("REST request to save BackOrderLineItem : {}", backOrderLineItemDTO);
        if (backOrderLineItemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("backOrderLineItem", "idexists", "A new backOrderLineItem cannot already have an ID")).body(null);
        }
        BackOrderLineItem backOrderLineItem = backOrderLineItemMapper.backOrderLineItemDTOToBackOrderLineItem(backOrderLineItemDTO);
        backOrderLineItem = backOrderLineItemRepository.save(backOrderLineItem);
        BackOrderLineItemDTO result = backOrderLineItemMapper.backOrderLineItemToBackOrderLineItemDTO(backOrderLineItem);
        backOrderLineItemSearchRepository.save(backOrderLineItem);
        return ResponseEntity.created(new URI("/api/backOrderLineItems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("backOrderLineItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /backOrderLineItems -> Updates an existing backOrderLineItem.
     */
    @RequestMapping(value = "/backOrderLineItems",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BackOrderLineItemDTO> updateBackOrderLineItem(@RequestBody BackOrderLineItemDTO backOrderLineItemDTO) throws URISyntaxException {
        log.debug("REST request to update BackOrderLineItem : {}", backOrderLineItemDTO);
        if (backOrderLineItemDTO.getId() == null) {
            return createBackOrderLineItem(backOrderLineItemDTO);
        }
        BackOrderLineItem backOrderLineItem = backOrderLineItemMapper.backOrderLineItemDTOToBackOrderLineItem(backOrderLineItemDTO);
        backOrderLineItem = backOrderLineItemRepository.save(backOrderLineItem);
        BackOrderLineItemDTO result = backOrderLineItemMapper.backOrderLineItemToBackOrderLineItemDTO(backOrderLineItem);
        backOrderLineItemSearchRepository.save(backOrderLineItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("backOrderLineItem", backOrderLineItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /backOrderLineItems -> get all the backOrderLineItems.
     */
    @RequestMapping(value = "/backOrderLineItems",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<BackOrderLineItemDTO>> getAllBackOrderLineItems(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of BackOrderLineItems");
        Page<BackOrderLineItem> page = backOrderLineItemRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/backOrderLineItems");
        return new ResponseEntity<>(page.getContent().stream()
            .map(backOrderLineItemMapper::backOrderLineItemToBackOrderLineItemDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /backOrderLineItems/:id -> get the "id" backOrderLineItem.
     */
    @RequestMapping(value = "/backOrderLineItems/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BackOrderLineItemDTO> getBackOrderLineItem(@PathVariable Long id) {
        log.debug("REST request to get BackOrderLineItem : {}", id);
        BackOrderLineItem backOrderLineItem = backOrderLineItemRepository.findOne(id);
        BackOrderLineItemDTO backOrderLineItemDTO = backOrderLineItemMapper.backOrderLineItemToBackOrderLineItemDTO(backOrderLineItem);
        return Optional.ofNullable(backOrderLineItemDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /backOrderLineItems/:id -> delete the "id" backOrderLineItem.
     */
    @RequestMapping(value = "/backOrderLineItems/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBackOrderLineItem(@PathVariable Long id) {
        log.debug("REST request to delete BackOrderLineItem : {}", id);
        backOrderLineItemRepository.delete(id);
        backOrderLineItemSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("backOrderLineItem", id.toString())).build();
    }

    /**
     * SEARCH  /_search/backOrderLineItems/:query -> search for the backOrderLineItem corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/backOrderLineItems/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BackOrderLineItemDTO> searchBackOrderLineItems(@PathVariable String query) {
        log.debug("REST request to search BackOrderLineItems for query {}", query);
        return StreamSupport
            .stream(backOrderLineItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(backOrderLineItemMapper::backOrderLineItemToBackOrderLineItemDTO)
            .collect(Collectors.toList());
    }
}
