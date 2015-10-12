package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.Staff;
import com.enginemobi.bssuite.repository.StaffRepository;
import com.enginemobi.bssuite.repository.search.StaffSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.StaffDTO;
import com.enginemobi.bssuite.web.rest.mapper.StaffMapper;
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
 * REST controller for managing Staff.
 */
@RestController
@RequestMapping("/api")
public class StaffResource {

    private final Logger log = LoggerFactory.getLogger(StaffResource.class);

    @Inject
    private StaffRepository staffRepository;

    @Inject
    private StaffMapper staffMapper;

    @Inject
    private StaffSearchRepository staffSearchRepository;

    /**
     * POST  /staffs -> Create a new staff.
     */
    @RequestMapping(value = "/staffs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StaffDTO> createStaff(@Valid @RequestBody StaffDTO staffDTO) throws URISyntaxException {
        log.debug("REST request to save Staff : {}", staffDTO);
        if (staffDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new staff cannot already have an ID").body(null);
        }
        Staff staff = staffMapper.staffDTOToStaff(staffDTO);
        Staff result = staffRepository.save(staff);
        staffSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/staffs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("staff", result.getId().toString()))
                .body(staffMapper.staffToStaffDTO(result));
    }

    /**
     * PUT  /staffs -> Updates an existing staff.
     */
    @RequestMapping(value = "/staffs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StaffDTO> updateStaff(@Valid @RequestBody StaffDTO staffDTO) throws URISyntaxException {
        log.debug("REST request to update Staff : {}", staffDTO);
        if (staffDTO.getId() == null) {
            return createStaff(staffDTO);
        }
        Staff staff = staffMapper.staffDTOToStaff(staffDTO);
        Staff result = staffRepository.save(staff);
        staffSearchRepository.save(staff);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("staff", staffDTO.getId().toString()))
                .body(staffMapper.staffToStaffDTO(result));
    }

    /**
     * GET  /staffs -> get all the staffs.
     */
    @RequestMapping(value = "/staffs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<StaffDTO>> getAllStaffs(Pageable pageable)
        throws URISyntaxException {
        Page<Staff> page = staffRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/staffs");
        return new ResponseEntity<>(page.getContent().stream()
            .map(staffMapper::staffToStaffDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /staffs/:id -> get the "id" staff.
     */
    @RequestMapping(value = "/staffs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StaffDTO> getStaff(@PathVariable Long id) {
        log.debug("REST request to get Staff : {}", id);
        return Optional.ofNullable(staffRepository.findOne(id))
            .map(staffMapper::staffToStaffDTO)
            .map(staffDTO -> new ResponseEntity<>(
                staffDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /staffs/:id -> delete the "id" staff.
     */
    @RequestMapping(value = "/staffs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        log.debug("REST request to delete Staff : {}", id);
        staffRepository.delete(id);
        staffSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("staff", id.toString())).build();
    }

    /**
     * SEARCH  /_search/staffs/:query -> search for the staff corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/staffs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StaffDTO> searchStaffs(@PathVariable String query) {
        return StreamSupport
            .stream(staffSearchRepository.search(queryString(query)).spliterator(), false)
            .map(staffMapper::staffToStaffDTO)
            .collect(Collectors.toList());
    }
}
