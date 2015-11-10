package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.GoodsReceivedAudit;
import com.enginemobi.bssuite.repository.GoodsReceivedAuditRepository;
import com.enginemobi.bssuite.repository.search.GoodsReceivedAuditSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.GoodsReceivedAuditDTO;
import com.enginemobi.bssuite.web.rest.mapper.GoodsReceivedAuditMapper;
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
 * REST controller for managing GoodsReceivedAudit.
 */
@RestController
@RequestMapping("/api")
public class GoodsReceivedAuditResource {

    private final Logger log = LoggerFactory.getLogger(GoodsReceivedAuditResource.class);

    @Inject
    private GoodsReceivedAuditRepository goodsReceivedAuditRepository;

    @Inject
    private GoodsReceivedAuditMapper goodsReceivedAuditMapper;

    @Inject
    private GoodsReceivedAuditSearchRepository goodsReceivedAuditSearchRepository;

    /**
     * POST  /goodsReceivedAudits -> Create a new goodsReceivedAudit.
     */
    @RequestMapping(value = "/goodsReceivedAudits",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GoodsReceivedAuditDTO> createGoodsReceivedAudit(@RequestBody GoodsReceivedAuditDTO goodsReceivedAuditDTO) throws URISyntaxException {
        log.debug("REST request to save GoodsReceivedAudit : {}", goodsReceivedAuditDTO);
        if (goodsReceivedAuditDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new goodsReceivedAudit cannot already have an ID").body(null);
        }
        GoodsReceivedAudit goodsReceivedAudit = goodsReceivedAuditMapper.goodsReceivedAuditDTOToGoodsReceivedAudit(goodsReceivedAuditDTO);
        GoodsReceivedAudit result = goodsReceivedAuditRepository.save(goodsReceivedAudit);
        goodsReceivedAuditSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/goodsReceivedAudits/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("goodsReceivedAudit", result.getId().toString()))
                .body(goodsReceivedAuditMapper.goodsReceivedAuditToGoodsReceivedAuditDTO(result));
    }

    /**
     * PUT  /goodsReceivedAudits -> Updates an existing goodsReceivedAudit.
     */
    @RequestMapping(value = "/goodsReceivedAudits",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GoodsReceivedAuditDTO> updateGoodsReceivedAudit(@RequestBody GoodsReceivedAuditDTO goodsReceivedAuditDTO) throws URISyntaxException {
        log.debug("REST request to update GoodsReceivedAudit : {}", goodsReceivedAuditDTO);
        if (goodsReceivedAuditDTO.getId() == null) {
            return createGoodsReceivedAudit(goodsReceivedAuditDTO);
        }
        GoodsReceivedAudit goodsReceivedAudit = goodsReceivedAuditMapper.goodsReceivedAuditDTOToGoodsReceivedAudit(goodsReceivedAuditDTO);
        GoodsReceivedAudit result = goodsReceivedAuditRepository.save(goodsReceivedAudit);
        goodsReceivedAuditSearchRepository.save(goodsReceivedAudit);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("goodsReceivedAudit", goodsReceivedAuditDTO.getId().toString()))
                .body(goodsReceivedAuditMapper.goodsReceivedAuditToGoodsReceivedAuditDTO(result));
    }

    /**
     * GET  /goodsReceivedAudits -> get all the goodsReceivedAudits.
     */
    @RequestMapping(value = "/goodsReceivedAudits",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<GoodsReceivedAuditDTO>> getAllGoodsReceivedAudits(Pageable pageable)
        throws URISyntaxException {
        Page<GoodsReceivedAudit> page = goodsReceivedAuditRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/goodsReceivedAudits");
        return new ResponseEntity<>(page.getContent().stream()
            .map(goodsReceivedAuditMapper::goodsReceivedAuditToGoodsReceivedAuditDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /goodsReceivedAudits/:id -> get the "id" goodsReceivedAudit.
     */
    @RequestMapping(value = "/goodsReceivedAudits/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GoodsReceivedAuditDTO> getGoodsReceivedAudit(@PathVariable Long id) {
        log.debug("REST request to get GoodsReceivedAudit : {}", id);
        return Optional.ofNullable(goodsReceivedAuditRepository.findOne(id))
            .map(goodsReceivedAuditMapper::goodsReceivedAuditToGoodsReceivedAuditDTO)
            .map(goodsReceivedAuditDTO -> new ResponseEntity<>(
                goodsReceivedAuditDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /goodsReceivedAudits/:id -> delete the "id" goodsReceivedAudit.
     */
    @RequestMapping(value = "/goodsReceivedAudits/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGoodsReceivedAudit(@PathVariable Long id) {
        log.debug("REST request to delete GoodsReceivedAudit : {}", id);
        goodsReceivedAuditRepository.delete(id);
        goodsReceivedAuditSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("goodsReceivedAudit", id.toString())).build();
    }

    /**
     * SEARCH  /_search/goodsReceivedAudits/:query -> search for the goodsReceivedAudit corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/goodsReceivedAudits/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GoodsReceivedAuditDTO> searchGoodsReceivedAudits(@PathVariable String query) {
        return StreamSupport
            .stream(goodsReceivedAuditSearchRepository.search(queryString(query)).spliterator(), false)
            .map(goodsReceivedAuditMapper::goodsReceivedAuditToGoodsReceivedAuditDTO)
            .collect(Collectors.toList());
    }
}
