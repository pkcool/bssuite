package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.Contact;
import com.enginemobi.bssuite.repository.ContactRepository;
import com.enginemobi.bssuite.repository.search.ContactSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.ContactDTO;
import com.enginemobi.bssuite.web.rest.mapper.ContactMapper;
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
 * REST controller for managing Contact.
 */
@RestController
@RequestMapping("/api")
public class ContactResource {

    private final Logger log = LoggerFactory.getLogger(ContactResource.class);

    @Inject
    private ContactRepository contactRepository;

    @Inject
    private ContactMapper contactMapper;

    @Inject
    private ContactSearchRepository contactSearchRepository;

    /**
     * POST  /contacts -> Create a new contact.
     */
    @RequestMapping(value = "/contacts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contactDTO) throws URISyntaxException {
        log.debug("REST request to save Contact : {}", contactDTO);
        if (contactDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new contact cannot already have an ID").body(null);
        }
        Contact contact = contactMapper.contactDTOToContact(contactDTO);
        Contact result = contactRepository.save(contact);
        contactSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("contact", result.getId().toString()))
            .body(contactMapper.contactToContactDTO(result));
    }

    /**
     * PUT  /contacts -> Updates an existing contact.
     */
    @RequestMapping(value = "/contacts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContactDTO> updateContact(@RequestBody ContactDTO contactDTO) throws URISyntaxException {
        log.debug("REST request to update Contact : {}", contactDTO);
        if (contactDTO.getId() == null) {
            return createContact(contactDTO);
        }
        Contact contact = contactMapper.contactDTOToContact(contactDTO);
        Contact result = contactRepository.save(contact);
        contactSearchRepository.save(contact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("contact", contactDTO.getId().toString()))
            .body(contactMapper.contactToContactDTO(result));
    }

    /**
     * GET  /contacts -> get all the contacts.
     */
    @RequestMapping(value = "/contacts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ContactDTO>> getAllContacts(Pageable pageable)
        throws URISyntaxException {
        Page<Contact> page = contactRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contacts");
        return new ResponseEntity<>(page.getContent().stream()
            .map(contactMapper::contactToContactDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /contacts/:id -> get the "id" contact.
     */
    @RequestMapping(value = "/contacts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContactDTO> getContact(@PathVariable Long id) {
        log.debug("REST request to get Contact : {}", id);
        return Optional.ofNullable(contactRepository.findOne(id))
            .map(contactMapper::contactToContactDTO)
            .map(contactDTO -> new ResponseEntity<>(
                contactDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /contacts/:id -> delete the "id" contact.
     */
    @RequestMapping(value = "/contacts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        log.debug("REST request to delete Contact : {}", id);
        contactRepository.delete(id);
        contactSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("contact", id.toString())).build();
    }

    /**
     * SEARCH  /_search/contacts/:query -> search for the contact corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/contacts/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ContactDTO> searchContacts(@PathVariable String query) {
        return StreamSupport
            .stream(contactSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(contactMapper::contactToContactDTO)
            .collect(Collectors.toList());
    }
}
