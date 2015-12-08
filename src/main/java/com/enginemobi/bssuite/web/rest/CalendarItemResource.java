package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.CalendarItem;
import com.enginemobi.bssuite.repository.CalendarItemRepository;
import com.enginemobi.bssuite.repository.search.CalendarItemSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.CalendarItemDTO;
import com.enginemobi.bssuite.web.rest.mapper.CalendarItemMapper;
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
 * REST controller for managing CalendarItem.
 */
@RestController
@RequestMapping("/api")
public class CalendarItemResource {

    private final Logger log = LoggerFactory.getLogger(CalendarItemResource.class);
        
    @Inject
    private CalendarItemRepository calendarItemRepository;
    
    @Inject
    private CalendarItemMapper calendarItemMapper;
    
    @Inject
    private CalendarItemSearchRepository calendarItemSearchRepository;
    
    /**
     * POST  /calendarItems -> Create a new calendarItem.
     */
    @RequestMapping(value = "/calendarItems",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CalendarItemDTO> createCalendarItem(@RequestBody CalendarItemDTO calendarItemDTO) throws URISyntaxException {
        log.debug("REST request to save CalendarItem : {}", calendarItemDTO);
        if (calendarItemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("calendarItem", "idexists", "A new calendarItem cannot already have an ID")).body(null);
        }
        CalendarItem calendarItem = calendarItemMapper.calendarItemDTOToCalendarItem(calendarItemDTO);
        calendarItem = calendarItemRepository.save(calendarItem);
        CalendarItemDTO result = calendarItemMapper.calendarItemToCalendarItemDTO(calendarItem);
        calendarItemSearchRepository.save(calendarItem);
        return ResponseEntity.created(new URI("/api/calendarItems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("calendarItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /calendarItems -> Updates an existing calendarItem.
     */
    @RequestMapping(value = "/calendarItems",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CalendarItemDTO> updateCalendarItem(@RequestBody CalendarItemDTO calendarItemDTO) throws URISyntaxException {
        log.debug("REST request to update CalendarItem : {}", calendarItemDTO);
        if (calendarItemDTO.getId() == null) {
            return createCalendarItem(calendarItemDTO);
        }
        CalendarItem calendarItem = calendarItemMapper.calendarItemDTOToCalendarItem(calendarItemDTO);
        calendarItem = calendarItemRepository.save(calendarItem);
        CalendarItemDTO result = calendarItemMapper.calendarItemToCalendarItemDTO(calendarItem);
        calendarItemSearchRepository.save(calendarItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("calendarItem", calendarItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /calendarItems -> get all the calendarItems.
     */
    @RequestMapping(value = "/calendarItems",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<CalendarItemDTO>> getAllCalendarItems(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CalendarItems");
        Page<CalendarItem> page = calendarItemRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/calendarItems");
        return new ResponseEntity<>(page.getContent().stream()
            .map(calendarItemMapper::calendarItemToCalendarItemDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /calendarItems/:id -> get the "id" calendarItem.
     */
    @RequestMapping(value = "/calendarItems/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CalendarItemDTO> getCalendarItem(@PathVariable Long id) {
        log.debug("REST request to get CalendarItem : {}", id);
        CalendarItem calendarItem = calendarItemRepository.findOne(id);
        CalendarItemDTO calendarItemDTO = calendarItemMapper.calendarItemToCalendarItemDTO(calendarItem);
        return Optional.ofNullable(calendarItemDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /calendarItems/:id -> delete the "id" calendarItem.
     */
    @RequestMapping(value = "/calendarItems/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCalendarItem(@PathVariable Long id) {
        log.debug("REST request to delete CalendarItem : {}", id);
        calendarItemRepository.delete(id);
        calendarItemSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("calendarItem", id.toString())).build();
    }

    /**
     * SEARCH  /_search/calendarItems/:query -> search for the calendarItem corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/calendarItems/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CalendarItemDTO> searchCalendarItems(@PathVariable String query) {
        log.debug("REST request to search CalendarItems for query {}", query);
        return StreamSupport
            .stream(calendarItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(calendarItemMapper::calendarItemToCalendarItemDTO)
            .collect(Collectors.toList());
    }
}
