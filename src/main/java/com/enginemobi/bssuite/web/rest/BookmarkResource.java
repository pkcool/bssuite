package com.enginemobi.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enginemobi.bssuite.domain.Bookmark;
import com.enginemobi.bssuite.repository.BookmarkRepository;
import com.enginemobi.bssuite.repository.search.BookmarkSearchRepository;
import com.enginemobi.bssuite.web.rest.util.HeaderUtil;
import com.enginemobi.bssuite.web.rest.util.PaginationUtil;
import com.enginemobi.bssuite.web.rest.dto.BookmarkDTO;
import com.enginemobi.bssuite.web.rest.mapper.BookmarkMapper;
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
 * REST controller for managing Bookmark.
 */
@RestController
@RequestMapping("/api")
public class BookmarkResource {

    private final Logger log = LoggerFactory.getLogger(BookmarkResource.class);

    @Inject
    private BookmarkRepository bookmarkRepository;

    @Inject
    private BookmarkMapper bookmarkMapper;

    @Inject
    private BookmarkSearchRepository bookmarkSearchRepository;

    /**
     * POST  /bookmarks -> Create a new bookmark.
     */
    @RequestMapping(value = "/bookmarks",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BookmarkDTO> createBookmark(@RequestBody BookmarkDTO bookmarkDTO) throws URISyntaxException {
        log.debug("REST request to save Bookmark : {}", bookmarkDTO);
        if (bookmarkDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new bookmark cannot already have an ID").body(null);
        }
        Bookmark bookmark = bookmarkMapper.bookmarkDTOToBookmark(bookmarkDTO);
        Bookmark result = bookmarkRepository.save(bookmark);
        bookmarkSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/bookmarks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("bookmark", result.getId().toString()))
            .body(bookmarkMapper.bookmarkToBookmarkDTO(result));
    }

    /**
     * PUT  /bookmarks -> Updates an existing bookmark.
     */
    @RequestMapping(value = "/bookmarks",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BookmarkDTO> updateBookmark(@RequestBody BookmarkDTO bookmarkDTO) throws URISyntaxException {
        log.debug("REST request to update Bookmark : {}", bookmarkDTO);
        if (bookmarkDTO.getId() == null) {
            return createBookmark(bookmarkDTO);
        }
        Bookmark bookmark = bookmarkMapper.bookmarkDTOToBookmark(bookmarkDTO);
        Bookmark result = bookmarkRepository.save(bookmark);
        bookmarkSearchRepository.save(bookmark);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("bookmark", bookmarkDTO.getId().toString()))
            .body(bookmarkMapper.bookmarkToBookmarkDTO(result));
    }

    /**
     * GET  /bookmarks -> get all the bookmarks.
     */
    @RequestMapping(value = "/bookmarks",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<BookmarkDTO>> getAllBookmarks(Pageable pageable)
        throws URISyntaxException {
        Page<Bookmark> page = bookmarkRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bookmarks");
        return new ResponseEntity<>(page.getContent().stream()
            .map(bookmarkMapper::bookmarkToBookmarkDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /bookmarks/:id -> get the "id" bookmark.
     */
    @RequestMapping(value = "/bookmarks/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BookmarkDTO> getBookmark(@PathVariable Long id) {
        log.debug("REST request to get Bookmark : {}", id);
        return Optional.ofNullable(bookmarkRepository.findOne(id))
            .map(bookmarkMapper::bookmarkToBookmarkDTO)
            .map(bookmarkDTO -> new ResponseEntity<>(
                bookmarkDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bookmarks/:id -> delete the "id" bookmark.
     */
    @RequestMapping(value = "/bookmarks/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBookmark(@PathVariable Long id) {
        log.debug("REST request to delete Bookmark : {}", id);
        bookmarkRepository.delete(id);
        bookmarkSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bookmark", id.toString())).build();
    }

    /**
     * SEARCH  /_search/bookmarks/:query -> search for the bookmark corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/bookmarks/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BookmarkDTO> searchBookmarks(@PathVariable String query) {
        return StreamSupport
            .stream(bookmarkSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(bookmarkMapper::bookmarkToBookmarkDTO)
            .collect(Collectors.toList());
    }
}
