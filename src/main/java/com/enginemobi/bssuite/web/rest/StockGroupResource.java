package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.StockGroup;
import com.enginemobi.bssuite.repository.StockGroupRepository;
import com.enginemobi.bssuite.repository.search.StockGroupSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.StockGroupDTO;
import com.enginemobi.bssuite.web.rest.mapper.StockGroupMapper;
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
 * REST controller for managing StockGroup.
 */
@RestController
@RequestMapping("/api")
public class StockGroupResource {

    private final Logger log = LoggerFactory.getLogger(StockGroupResource.class);
        
    @Inject
    private StockGroupRepository stockGroupRepository;
    
    @Inject
    private StockGroupMapper stockGroupMapper;
    
    @Inject
    private StockGroupSearchRepository stockGroupSearchRepository;
    
    /**
     * POST  /stockGroups -> Create a new stockGroup.
     */
    @RequestMapping(value = "/stockGroups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StockGroupDTO> createStockGroup(@Valid @RequestBody StockGroupDTO stockGroupDTO) throws URISyntaxException {
        log.debug("REST request to save StockGroup : {}", stockGroupDTO);
        if (stockGroupDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("stockGroup", "idexists", "A new stockGroup cannot already have an ID")).body(null);
        }
        StockGroup stockGroup = stockGroupMapper.stockGroupDTOToStockGroup(stockGroupDTO);
        stockGroup = stockGroupRepository.save(stockGroup);
        StockGroupDTO result = stockGroupMapper.stockGroupToStockGroupDTO(stockGroup);
        stockGroupSearchRepository.save(stockGroup);
        return ResponseEntity.created(new URI("/api/stockGroups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("stockGroup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stockGroups -> Updates an existing stockGroup.
     */
    @RequestMapping(value = "/stockGroups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StockGroupDTO> updateStockGroup(@Valid @RequestBody StockGroupDTO stockGroupDTO) throws URISyntaxException {
        log.debug("REST request to update StockGroup : {}", stockGroupDTO);
        if (stockGroupDTO.getId() == null) {
            return createStockGroup(stockGroupDTO);
        }
        StockGroup stockGroup = stockGroupMapper.stockGroupDTOToStockGroup(stockGroupDTO);
        stockGroup = stockGroupRepository.save(stockGroup);
        StockGroupDTO result = stockGroupMapper.stockGroupToStockGroupDTO(stockGroup);
        stockGroupSearchRepository.save(stockGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("stockGroup", stockGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stockGroups -> get all the stockGroups.
     */
    @RequestMapping(value = "/stockGroups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<StockGroupDTO>> getAllStockGroups(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of StockGroups");
        Page<StockGroup> page = stockGroupRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stockGroups");
        return new ResponseEntity<>(page.getContent().stream()
            .map(stockGroupMapper::stockGroupToStockGroupDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /stockGroups/:id -> get the "id" stockGroup.
     */
    @RequestMapping(value = "/stockGroups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StockGroupDTO> getStockGroup(@PathVariable Long id) {
        log.debug("REST request to get StockGroup : {}", id);
        StockGroup stockGroup = stockGroupRepository.findOne(id);
        StockGroupDTO stockGroupDTO = stockGroupMapper.stockGroupToStockGroupDTO(stockGroup);
        return Optional.ofNullable(stockGroupDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /stockGroups/:id -> delete the "id" stockGroup.
     */
    @RequestMapping(value = "/stockGroups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStockGroup(@PathVariable Long id) {
        log.debug("REST request to delete StockGroup : {}", id);
        stockGroupRepository.delete(id);
        stockGroupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("stockGroup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/stockGroups/:query -> search for the stockGroup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/stockGroups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StockGroupDTO> searchStockGroups(@PathVariable String query) {
        log.debug("REST request to search StockGroups for query {}", query);
        return StreamSupport
            .stream(stockGroupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(stockGroupMapper::stockGroupToStockGroupDTO)
            .collect(Collectors.toList());
    }
}
