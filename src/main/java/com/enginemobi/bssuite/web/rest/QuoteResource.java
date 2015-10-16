package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.Quote;
import com.enginemobi.bssuite.repository.QuoteRepository;
import com.enginemobi.bssuite.repository.search.QuoteSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.QuoteDTO;
import com.enginemobi.bssuite.web.rest.mapper.QuoteMapper;
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
 * REST controller for managing Quote.
 */
@RestController
@RequestMapping("/api")
public class QuoteResource {

    private final Logger log = LoggerFactory.getLogger(QuoteResource.class);

    @Inject
    private QuoteRepository quoteRepository;

    @Inject
    private QuoteMapper quoteMapper;

    @Inject
    private QuoteSearchRepository quoteSearchRepository;

    /**
     * POST  /quotes -> Create a new quote.
     */
    @RequestMapping(value = "/quotes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<QuoteDTO> createQuote(@RequestBody QuoteDTO quoteDTO) throws URISyntaxException {
        log.debug("REST request to save Quote : {}", quoteDTO);
        if (quoteDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new quote cannot already have an ID").body(null);
        }
        Quote quote = quoteMapper.quoteDTOToQuote(quoteDTO);
        Quote result = quoteRepository.save(quote);
        quoteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/quotes/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("quote", result.getId().toString()))
                .body(quoteMapper.quoteToQuoteDTO(result));
    }

    /**
     * PUT  /quotes -> Updates an existing quote.
     */
    @RequestMapping(value = "/quotes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<QuoteDTO> updateQuote(@RequestBody QuoteDTO quoteDTO) throws URISyntaxException {
        log.debug("REST request to update Quote : {}", quoteDTO);
        if (quoteDTO.getId() == null) {
            return createQuote(quoteDTO);
        }
        Quote quote = quoteMapper.quoteDTOToQuote(quoteDTO);
        Quote result = quoteRepository.save(quote);
        quoteSearchRepository.save(quote);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("quote", quoteDTO.getId().toString()))
                .body(quoteMapper.quoteToQuoteDTO(result));
    }

    /**
     * GET  /quotes -> get all the quotes.
     */
    @RequestMapping(value = "/quotes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<QuoteDTO>> getAllQuotes(Pageable pageable)
        throws URISyntaxException {
        Page<Quote> page = quoteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/quotes");
        return new ResponseEntity<>(page.getContent().stream()
            .map(quoteMapper::quoteToQuoteDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /quotes/:id -> get the "id" quote.
     */
    @RequestMapping(value = "/quotes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<QuoteDTO> getQuote(@PathVariable Long id) {
        log.debug("REST request to get Quote : {}", id);
        return Optional.ofNullable(quoteRepository.findOne(id))
            .map(quoteMapper::quoteToQuoteDTO)
            .map(quoteDTO -> new ResponseEntity<>(
                quoteDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /quotes/:id -> delete the "id" quote.
     */
    @RequestMapping(value = "/quotes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteQuote(@PathVariable Long id) {
        log.debug("REST request to delete Quote : {}", id);
        quoteRepository.delete(id);
        quoteSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("quote", id.toString())).build();
    }

    /**
     * SEARCH  /_search/quotes/:query -> search for the quote corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/quotes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<QuoteDTO> searchQuotes(@PathVariable String query) {
        return StreamSupport
            .stream(quoteSearchRepository.search(queryString(query)).spliterator(), false)
            .map(quoteMapper::quoteToQuoteDTO)
            .collect(Collectors.toList());
    }
}
