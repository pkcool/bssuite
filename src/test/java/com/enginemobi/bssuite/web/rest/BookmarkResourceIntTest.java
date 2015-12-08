package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.Bookmark;
import com.enginemobi.bssuite.repository.BookmarkRepository;
import com.enginemobi.bssuite.repository.search.BookmarkSearchRepository;
import com.enginemobi.bssuite.web.rest.dto.BookmarkDTO;
import com.enginemobi.bssuite.web.rest.mapper.BookmarkMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.enginemobi.bssuite.domain.enumeration.BookmarkType;
import com.enginemobi.bssuite.domain.enumeration.BookmarkArea;

/**
 * Test class for the BookmarkResource REST controller.
 *
 * @see BookmarkResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BookmarkResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_ON_STR = dateTimeFormatter.format(DEFAULT_CREATED_ON);
    private static final String DEFAULT_TXN_NUMBER = "AAAAA";
    private static final String UPDATED_TXN_NUMBER = "BBBBB";


    private static final BookmarkType DEFAULT_BOOKMARK_TYPE = BookmarkType.HISTORY;
    private static final BookmarkType UPDATED_BOOKMARK_TYPE = BookmarkType.FAVOURITE;


    private static final BookmarkArea DEFAULT_BOOKMARK_AREA = BookmarkArea.PURCHASEORDER;
    private static final BookmarkArea UPDATED_BOOKMARK_AREA = BookmarkArea.SALESORDER;
    private static final String DEFAULT_KEY_CODE = "AAAAA";
    private static final String UPDATED_KEY_CODE = "BBBBB";
    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";

    private static final ZonedDateTime DEFAULT_LAST_EDITED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_LAST_EDITED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_LAST_EDITED_ON_STR = dateTimeFormatter.format(DEFAULT_LAST_EDITED_ON);

    private static final Integer DEFAULT_OPEN_COUNT = 1;
    private static final Integer UPDATED_OPEN_COUNT = 2;

    @Inject
    private BookmarkRepository bookmarkRepository;

    @Inject
    private BookmarkMapper bookmarkMapper;

    @Inject
    private BookmarkSearchRepository bookmarkSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBookmarkMockMvc;

    private Bookmark bookmark;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BookmarkResource bookmarkResource = new BookmarkResource();
        ReflectionTestUtils.setField(bookmarkResource, "bookmarkSearchRepository", bookmarkSearchRepository);
        ReflectionTestUtils.setField(bookmarkResource, "bookmarkRepository", bookmarkRepository);
        ReflectionTestUtils.setField(bookmarkResource, "bookmarkMapper", bookmarkMapper);
        this.restBookmarkMockMvc = MockMvcBuilders.standaloneSetup(bookmarkResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        bookmark = new Bookmark();
        bookmark.setCreatedOn(DEFAULT_CREATED_ON);
        bookmark.setTxnNumber(DEFAULT_TXN_NUMBER);
        bookmark.setBookmarkType(DEFAULT_BOOKMARK_TYPE);
        bookmark.setBookmarkArea(DEFAULT_BOOKMARK_AREA);
        bookmark.setKeyCode(DEFAULT_KEY_CODE);
        bookmark.setTitle(DEFAULT_TITLE);
        bookmark.setLastEditedOn(DEFAULT_LAST_EDITED_ON);
        bookmark.setOpenCount(DEFAULT_OPEN_COUNT);
    }

    @Test
    @Transactional
    public void createBookmark() throws Exception {
        int databaseSizeBeforeCreate = bookmarkRepository.findAll().size();

        // Create the Bookmark
        BookmarkDTO bookmarkDTO = bookmarkMapper.bookmarkToBookmarkDTO(bookmark);

        restBookmarkMockMvc.perform(post("/api/bookmarks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bookmarkDTO)))
                .andExpect(status().isCreated());

        // Validate the Bookmark in the database
        List<Bookmark> bookmarks = bookmarkRepository.findAll();
        assertThat(bookmarks).hasSize(databaseSizeBeforeCreate + 1);
        Bookmark testBookmark = bookmarks.get(bookmarks.size() - 1);
        assertThat(testBookmark.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testBookmark.getTxnNumber()).isEqualTo(DEFAULT_TXN_NUMBER);
        assertThat(testBookmark.getBookmarkType()).isEqualTo(DEFAULT_BOOKMARK_TYPE);
        assertThat(testBookmark.getBookmarkArea()).isEqualTo(DEFAULT_BOOKMARK_AREA);
        assertThat(testBookmark.getKeyCode()).isEqualTo(DEFAULT_KEY_CODE);
        assertThat(testBookmark.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBookmark.getLastEditedOn()).isEqualTo(DEFAULT_LAST_EDITED_ON);
        assertThat(testBookmark.getOpenCount()).isEqualTo(DEFAULT_OPEN_COUNT);
    }

    @Test
    @Transactional
    public void getAllBookmarks() throws Exception {
        // Initialize the database
        bookmarkRepository.saveAndFlush(bookmark);

        // Get all the bookmarks
        restBookmarkMockMvc.perform(get("/api/bookmarks?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bookmark.getId().intValue())))
                .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON_STR)))
                .andExpect(jsonPath("$.[*].txnNumber").value(hasItem(DEFAULT_TXN_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].bookmarkType").value(hasItem(DEFAULT_BOOKMARK_TYPE.toString())))
                .andExpect(jsonPath("$.[*].bookmarkArea").value(hasItem(DEFAULT_BOOKMARK_AREA.toString())))
                .andExpect(jsonPath("$.[*].keyCode").value(hasItem(DEFAULT_KEY_CODE.toString())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].lastEditedOn").value(hasItem(DEFAULT_LAST_EDITED_ON_STR)))
                .andExpect(jsonPath("$.[*].openCount").value(hasItem(DEFAULT_OPEN_COUNT)));
    }

    @Test
    @Transactional
    public void getBookmark() throws Exception {
        // Initialize the database
        bookmarkRepository.saveAndFlush(bookmark);

        // Get the bookmark
        restBookmarkMockMvc.perform(get("/api/bookmarks/{id}", bookmark.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bookmark.getId().intValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON_STR))
            .andExpect(jsonPath("$.txnNumber").value(DEFAULT_TXN_NUMBER.toString()))
            .andExpect(jsonPath("$.bookmarkType").value(DEFAULT_BOOKMARK_TYPE.toString()))
            .andExpect(jsonPath("$.bookmarkArea").value(DEFAULT_BOOKMARK_AREA.toString()))
            .andExpect(jsonPath("$.keyCode").value(DEFAULT_KEY_CODE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.lastEditedOn").value(DEFAULT_LAST_EDITED_ON_STR))
            .andExpect(jsonPath("$.openCount").value(DEFAULT_OPEN_COUNT));
    }

    @Test
    @Transactional
    public void getNonExistingBookmark() throws Exception {
        // Get the bookmark
        restBookmarkMockMvc.perform(get("/api/bookmarks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookmark() throws Exception {
        // Initialize the database
        bookmarkRepository.saveAndFlush(bookmark);

		int databaseSizeBeforeUpdate = bookmarkRepository.findAll().size();

        // Update the bookmark
        bookmark.setCreatedOn(UPDATED_CREATED_ON);
        bookmark.setTxnNumber(UPDATED_TXN_NUMBER);
        bookmark.setBookmarkType(UPDATED_BOOKMARK_TYPE);
        bookmark.setBookmarkArea(UPDATED_BOOKMARK_AREA);
        bookmark.setKeyCode(UPDATED_KEY_CODE);
        bookmark.setTitle(UPDATED_TITLE);
        bookmark.setLastEditedOn(UPDATED_LAST_EDITED_ON);
        bookmark.setOpenCount(UPDATED_OPEN_COUNT);
        BookmarkDTO bookmarkDTO = bookmarkMapper.bookmarkToBookmarkDTO(bookmark);

        restBookmarkMockMvc.perform(put("/api/bookmarks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bookmarkDTO)))
                .andExpect(status().isOk());

        // Validate the Bookmark in the database
        List<Bookmark> bookmarks = bookmarkRepository.findAll();
        assertThat(bookmarks).hasSize(databaseSizeBeforeUpdate);
        Bookmark testBookmark = bookmarks.get(bookmarks.size() - 1);
        assertThat(testBookmark.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testBookmark.getTxnNumber()).isEqualTo(UPDATED_TXN_NUMBER);
        assertThat(testBookmark.getBookmarkType()).isEqualTo(UPDATED_BOOKMARK_TYPE);
        assertThat(testBookmark.getBookmarkArea()).isEqualTo(UPDATED_BOOKMARK_AREA);
        assertThat(testBookmark.getKeyCode()).isEqualTo(UPDATED_KEY_CODE);
        assertThat(testBookmark.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBookmark.getLastEditedOn()).isEqualTo(UPDATED_LAST_EDITED_ON);
        assertThat(testBookmark.getOpenCount()).isEqualTo(UPDATED_OPEN_COUNT);
    }

    @Test
    @Transactional
    public void deleteBookmark() throws Exception {
        // Initialize the database
        bookmarkRepository.saveAndFlush(bookmark);

		int databaseSizeBeforeDelete = bookmarkRepository.findAll().size();

        // Get the bookmark
        restBookmarkMockMvc.perform(delete("/api/bookmarks/{id}", bookmark.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Bookmark> bookmarks = bookmarkRepository.findAll();
        assertThat(bookmarks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
