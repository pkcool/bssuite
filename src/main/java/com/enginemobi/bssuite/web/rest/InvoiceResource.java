package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.Invoice;
import com.enginemobi.bssuite.repository.InvoiceRepository;
import com.enginemobi.bssuite.repository.search.InvoiceSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.InvoiceDTO;
import com.enginemobi.bssuite.web.rest.mapper.InvoiceMapper;
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
 * REST controller for managing Invoice.
 */
@RestController
@RequestMapping("/api")
public class InvoiceResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceResource.class);

    @Inject
    private InvoiceRepository invoiceRepository;

    @Inject
    private InvoiceMapper invoiceMapper;

    @Inject
    private InvoiceSearchRepository invoiceSearchRepository;

    /**
     * POST  /invoices -> Create a new invoice.
     */
    @RequestMapping(value = "/invoices",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InvoiceDTO> createInvoice(@RequestBody InvoiceDTO invoiceDTO) throws URISyntaxException {
        log.debug("REST request to save Invoice : {}", invoiceDTO);
        if (invoiceDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new invoice cannot already have an ID").body(null);
        }
        Invoice invoice = invoiceMapper.invoiceDTOToInvoice(invoiceDTO);
        Invoice result = invoiceRepository.save(invoice);
        invoiceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/invoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("invoice", result.getId().toString()))
            .body(invoiceMapper.invoiceToInvoiceDTO(result));
    }

    /**
     * PUT  /invoices -> Updates an existing invoice.
     */
    @RequestMapping(value = "/invoices",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InvoiceDTO> updateInvoice(@RequestBody InvoiceDTO invoiceDTO) throws URISyntaxException {
        log.debug("REST request to update Invoice : {}", invoiceDTO);
        if (invoiceDTO.getId() == null) {
            return createInvoice(invoiceDTO);
        }
        Invoice invoice = invoiceMapper.invoiceDTOToInvoice(invoiceDTO);
        Invoice result = invoiceRepository.save(invoice);
        invoiceSearchRepository.save(invoice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("invoice", invoiceDTO.getId().toString()))
            .body(invoiceMapper.invoiceToInvoiceDTO(result));
    }

    /**
     * GET  /invoices -> get all the invoices.
     */
    @RequestMapping(value = "/invoices",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices(Pageable pageable)
        throws URISyntaxException {
        Page<Invoice> page = invoiceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/invoices");
        return new ResponseEntity<>(page.getContent().stream()
            .map(invoiceMapper::invoiceToInvoiceDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /invoices/:id -> get the "id" invoice.
     */
    @RequestMapping(value = "/invoices/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InvoiceDTO> getInvoice(@PathVariable Long id) {
        log.debug("REST request to get Invoice : {}", id);
        return Optional.ofNullable(invoiceRepository.findOne(id))
            .map(invoiceMapper::invoiceToInvoiceDTO)
            .map(invoiceDTO -> new ResponseEntity<>(
                invoiceDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /invoices/:id -> delete the "id" invoice.
     */
    @RequestMapping(value = "/invoices/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        log.debug("REST request to delete Invoice : {}", id);
        invoiceRepository.delete(id);
        invoiceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("invoice", id.toString())).build();
    }

    /**
     * SEARCH  /_search/invoices/:query -> search for the invoice corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/invoices/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InvoiceDTO> searchInvoices(@PathVariable String query) {
        return StreamSupport
            .stream(invoiceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(invoiceMapper::invoiceToInvoiceDTO)
            .collect(Collectors.toList());
    }
}
