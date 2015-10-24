package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.BackOrderLineItem;
import com.enginemobi.bssuite.repository.BackOrderLineItemRepository;
import com.enginemobi.bssuite.repository.search.BackOrderLineItemSearchRepository;
import com.enginemobi.bssuite.web.rest.dto.BackOrderLineItemDTO;
import com.enginemobi.bssuite.web.rest.mapper.BackOrderLineItemMapper;

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


/**
 * Test class for the BackOrderLineItemResource REST controller.
 *
 * @see BackOrderLineItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BackOrderLineItemResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final Boolean DEFAULT_IS_READY_TO_RELEASE = false;
    private static final Boolean UPDATED_IS_READY_TO_RELEASE = true;

    private static final Double DEFAULT_QTY_ALLOCATED = 1D;
    private static final Double UPDATED_QTY_ALLOCATED = 2D;

    private static final Boolean DEFAULT_IS_MARKED_FOR_AUTO_PURCHASE_ORDERING = false;
    private static final Boolean UPDATED_IS_MARKED_FOR_AUTO_PURCHASE_ORDERING = true;

    private static final Boolean DEFAULT_IS_ON_HOLD = false;
    private static final Boolean UPDATED_IS_ON_HOLD = true;

    private static final ZonedDateTime DEFAULT_ALLOCATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_ALLOCATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_ALLOCATED_DATE_STR = dateTimeFormatter.format(DEFAULT_ALLOCATED_DATE);
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    private static final Boolean DEFAULT_IS_PICKED = false;
    private static final Boolean UPDATED_IS_PICKED = true;

    private static final Boolean DEFAULT_IS_MARKED = false;
    private static final Boolean UPDATED_IS_MARKED = true;

    @Inject
    private BackOrderLineItemRepository backOrderLineItemRepository;

    @Inject
    private BackOrderLineItemMapper backOrderLineItemMapper;

    @Inject
    private BackOrderLineItemSearchRepository backOrderLineItemSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBackOrderLineItemMockMvc;

    private BackOrderLineItem backOrderLineItem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BackOrderLineItemResource backOrderLineItemResource = new BackOrderLineItemResource();
        ReflectionTestUtils.setField(backOrderLineItemResource, "backOrderLineItemRepository", backOrderLineItemRepository);
        ReflectionTestUtils.setField(backOrderLineItemResource, "backOrderLineItemMapper", backOrderLineItemMapper);
        ReflectionTestUtils.setField(backOrderLineItemResource, "backOrderLineItemSearchRepository", backOrderLineItemSearchRepository);
        this.restBackOrderLineItemMockMvc = MockMvcBuilders.standaloneSetup(backOrderLineItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        backOrderLineItem = new BackOrderLineItem();
        backOrderLineItem.setIsReadyToRelease(DEFAULT_IS_READY_TO_RELEASE);
        backOrderLineItem.setQtyAllocated(DEFAULT_QTY_ALLOCATED);
        backOrderLineItem.setIsMarkedForAutoPurchaseOrdering(DEFAULT_IS_MARKED_FOR_AUTO_PURCHASE_ORDERING);
        backOrderLineItem.setIsOnHold(DEFAULT_IS_ON_HOLD);
        backOrderLineItem.setAllocatedDate(DEFAULT_ALLOCATED_DATE);
        backOrderLineItem.setComment(DEFAULT_COMMENT);
        backOrderLineItem.setIsPicked(DEFAULT_IS_PICKED);
        backOrderLineItem.setIsMarked(DEFAULT_IS_MARKED);
    }

    @Test
    @Transactional
    public void createBackOrderLineItem() throws Exception {
        int databaseSizeBeforeCreate = backOrderLineItemRepository.findAll().size();

        // Create the BackOrderLineItem
        BackOrderLineItemDTO backOrderLineItemDTO = backOrderLineItemMapper.backOrderLineItemToBackOrderLineItemDTO(backOrderLineItem);

        restBackOrderLineItemMockMvc.perform(post("/api/backOrderLineItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(backOrderLineItemDTO)))
                .andExpect(status().isCreated());

        // Validate the BackOrderLineItem in the database
        List<BackOrderLineItem> backOrderLineItems = backOrderLineItemRepository.findAll();
        assertThat(backOrderLineItems).hasSize(databaseSizeBeforeCreate + 1);
        BackOrderLineItem testBackOrderLineItem = backOrderLineItems.get(backOrderLineItems.size() - 1);
        assertThat(testBackOrderLineItem.getIsReadyToRelease()).isEqualTo(DEFAULT_IS_READY_TO_RELEASE);
        assertThat(testBackOrderLineItem.getQtyAllocated()).isEqualTo(DEFAULT_QTY_ALLOCATED);
        assertThat(testBackOrderLineItem.getIsMarkedForAutoPurchaseOrdering()).isEqualTo(DEFAULT_IS_MARKED_FOR_AUTO_PURCHASE_ORDERING);
        assertThat(testBackOrderLineItem.getIsOnHold()).isEqualTo(DEFAULT_IS_ON_HOLD);
        assertThat(testBackOrderLineItem.getAllocatedDate()).isEqualTo(DEFAULT_ALLOCATED_DATE);
        assertThat(testBackOrderLineItem.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testBackOrderLineItem.getIsPicked()).isEqualTo(DEFAULT_IS_PICKED);
        assertThat(testBackOrderLineItem.getIsMarked()).isEqualTo(DEFAULT_IS_MARKED);
    }

    @Test
    @Transactional
    public void getAllBackOrderLineItems() throws Exception {
        // Initialize the database
        backOrderLineItemRepository.saveAndFlush(backOrderLineItem);

        // Get all the backOrderLineItems
        restBackOrderLineItemMockMvc.perform(get("/api/backOrderLineItems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(backOrderLineItem.getId().intValue())))
                .andExpect(jsonPath("$.[*].isReadyToRelease").value(hasItem(DEFAULT_IS_READY_TO_RELEASE.booleanValue())))
                .andExpect(jsonPath("$.[*].qtyAllocated").value(hasItem(DEFAULT_QTY_ALLOCATED.doubleValue())))
                .andExpect(jsonPath("$.[*].isMarkedForAutoPurchaseOrdering").value(hasItem(DEFAULT_IS_MARKED_FOR_AUTO_PURCHASE_ORDERING.booleanValue())))
                .andExpect(jsonPath("$.[*].isOnHold").value(hasItem(DEFAULT_IS_ON_HOLD.booleanValue())))
                .andExpect(jsonPath("$.[*].allocatedDate").value(hasItem(DEFAULT_ALLOCATED_DATE_STR)))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
                .andExpect(jsonPath("$.[*].isPicked").value(hasItem(DEFAULT_IS_PICKED.booleanValue())))
                .andExpect(jsonPath("$.[*].isMarked").value(hasItem(DEFAULT_IS_MARKED.booleanValue())));
    }

    @Test
    @Transactional
    public void getBackOrderLineItem() throws Exception {
        // Initialize the database
        backOrderLineItemRepository.saveAndFlush(backOrderLineItem);

        // Get the backOrderLineItem
        restBackOrderLineItemMockMvc.perform(get("/api/backOrderLineItems/{id}", backOrderLineItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(backOrderLineItem.getId().intValue()))
            .andExpect(jsonPath("$.isReadyToRelease").value(DEFAULT_IS_READY_TO_RELEASE.booleanValue()))
            .andExpect(jsonPath("$.qtyAllocated").value(DEFAULT_QTY_ALLOCATED.doubleValue()))
            .andExpect(jsonPath("$.isMarkedForAutoPurchaseOrdering").value(DEFAULT_IS_MARKED_FOR_AUTO_PURCHASE_ORDERING.booleanValue()))
            .andExpect(jsonPath("$.isOnHold").value(DEFAULT_IS_ON_HOLD.booleanValue()))
            .andExpect(jsonPath("$.allocatedDate").value(DEFAULT_ALLOCATED_DATE_STR))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.isPicked").value(DEFAULT_IS_PICKED.booleanValue()))
            .andExpect(jsonPath("$.isMarked").value(DEFAULT_IS_MARKED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBackOrderLineItem() throws Exception {
        // Get the backOrderLineItem
        restBackOrderLineItemMockMvc.perform(get("/api/backOrderLineItems/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBackOrderLineItem() throws Exception {
        // Initialize the database
        backOrderLineItemRepository.saveAndFlush(backOrderLineItem);

		int databaseSizeBeforeUpdate = backOrderLineItemRepository.findAll().size();

        // Update the backOrderLineItem
        backOrderLineItem.setIsReadyToRelease(UPDATED_IS_READY_TO_RELEASE);
        backOrderLineItem.setQtyAllocated(UPDATED_QTY_ALLOCATED);
        backOrderLineItem.setIsMarkedForAutoPurchaseOrdering(UPDATED_IS_MARKED_FOR_AUTO_PURCHASE_ORDERING);
        backOrderLineItem.setIsOnHold(UPDATED_IS_ON_HOLD);
        backOrderLineItem.setAllocatedDate(UPDATED_ALLOCATED_DATE);
        backOrderLineItem.setComment(UPDATED_COMMENT);
        backOrderLineItem.setIsPicked(UPDATED_IS_PICKED);
        backOrderLineItem.setIsMarked(UPDATED_IS_MARKED);
        BackOrderLineItemDTO backOrderLineItemDTO = backOrderLineItemMapper.backOrderLineItemToBackOrderLineItemDTO(backOrderLineItem);

        restBackOrderLineItemMockMvc.perform(put("/api/backOrderLineItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(backOrderLineItemDTO)))
                .andExpect(status().isOk());

        // Validate the BackOrderLineItem in the database
        List<BackOrderLineItem> backOrderLineItems = backOrderLineItemRepository.findAll();
        assertThat(backOrderLineItems).hasSize(databaseSizeBeforeUpdate);
        BackOrderLineItem testBackOrderLineItem = backOrderLineItems.get(backOrderLineItems.size() - 1);
        assertThat(testBackOrderLineItem.getIsReadyToRelease()).isEqualTo(UPDATED_IS_READY_TO_RELEASE);
        assertThat(testBackOrderLineItem.getQtyAllocated()).isEqualTo(UPDATED_QTY_ALLOCATED);
        assertThat(testBackOrderLineItem.getIsMarkedForAutoPurchaseOrdering()).isEqualTo(UPDATED_IS_MARKED_FOR_AUTO_PURCHASE_ORDERING);
        assertThat(testBackOrderLineItem.getIsOnHold()).isEqualTo(UPDATED_IS_ON_HOLD);
        assertThat(testBackOrderLineItem.getAllocatedDate()).isEqualTo(UPDATED_ALLOCATED_DATE);
        assertThat(testBackOrderLineItem.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testBackOrderLineItem.getIsPicked()).isEqualTo(UPDATED_IS_PICKED);
        assertThat(testBackOrderLineItem.getIsMarked()).isEqualTo(UPDATED_IS_MARKED);
    }

    @Test
    @Transactional
    public void deleteBackOrderLineItem() throws Exception {
        // Initialize the database
        backOrderLineItemRepository.saveAndFlush(backOrderLineItem);

		int databaseSizeBeforeDelete = backOrderLineItemRepository.findAll().size();

        // Get the backOrderLineItem
        restBackOrderLineItemMockMvc.perform(delete("/api/backOrderLineItems/{id}", backOrderLineItem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BackOrderLineItem> backOrderLineItems = backOrderLineItemRepository.findAll();
        assertThat(backOrderLineItems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
