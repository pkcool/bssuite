package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.StockGroup;
import com.enginemobi.bssuite.repository.StockGroupRepository;
import com.enginemobi.bssuite.repository.search.StockGroupSearchRepository;
import com.enginemobi.bssuite.web.rest.dto.StockGroupDTO;
import com.enginemobi.bssuite.web.rest.mapper.StockGroupMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the StockGroupResource REST controller.
 *
 * @see StockGroupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StockGroupResourceTest {

    private static final String DEFAULT_CODE = "AA";
    private static final String UPDATED_CODE = "BB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Boolean DEFAULT_IS_DIMINISHING = false;
    private static final Boolean UPDATED_IS_DIMINISHING = true;

    private static final Double DEFAULT_LOWEST_MARGIN = 1D;
    private static final Double UPDATED_LOWEST_MARGIN = 2D;

    private static final Double DEFAULT_HIGHEST_MARGIN = 1D;
    private static final Double UPDATED_HIGHEST_MARGIN = 2D;

    private static final Boolean DEFAULT_IS_DISCOUNT_ALLOWED = false;
    private static final Boolean UPDATED_IS_DISCOUNT_ALLOWED = true;
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    private static final Boolean DEFAULT_IS_ARCHIVED = false;
    private static final Boolean UPDATED_IS_ARCHIVED = true;

    @Inject
    private StockGroupRepository stockGroupRepository;

    @Inject
    private StockGroupMapper stockGroupMapper;

    @Inject
    private StockGroupSearchRepository stockGroupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStockGroupMockMvc;

    private StockGroup stockGroup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StockGroupResource stockGroupResource = new StockGroupResource();
        ReflectionTestUtils.setField(stockGroupResource, "stockGroupRepository", stockGroupRepository);
        ReflectionTestUtils.setField(stockGroupResource, "stockGroupMapper", stockGroupMapper);
        ReflectionTestUtils.setField(stockGroupResource, "stockGroupSearchRepository", stockGroupSearchRepository);
        this.restStockGroupMockMvc = MockMvcBuilders.standaloneSetup(stockGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        stockGroup = new StockGroup();
        stockGroup.setCode(DEFAULT_CODE);
        stockGroup.setName(DEFAULT_NAME);
        stockGroup.setIsDiminishing(DEFAULT_IS_DIMINISHING);
        stockGroup.setLowestMargin(DEFAULT_LOWEST_MARGIN);
        stockGroup.setHighestMargin(DEFAULT_HIGHEST_MARGIN);
        stockGroup.setIsDiscountAllowed(DEFAULT_IS_DISCOUNT_ALLOWED);
        stockGroup.setComment(DEFAULT_COMMENT);
        stockGroup.setIsArchived(DEFAULT_IS_ARCHIVED);
    }

    @Test
    @Transactional
    public void createStockGroup() throws Exception {
        int databaseSizeBeforeCreate = stockGroupRepository.findAll().size();

        // Create the StockGroup
        StockGroupDTO stockGroupDTO = stockGroupMapper.stockGroupToStockGroupDTO(stockGroup);

        restStockGroupMockMvc.perform(post("/api/stockGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stockGroupDTO)))
                .andExpect(status().isCreated());

        // Validate the StockGroup in the database
        List<StockGroup> stockGroups = stockGroupRepository.findAll();
        assertThat(stockGroups).hasSize(databaseSizeBeforeCreate + 1);
        StockGroup testStockGroup = stockGroups.get(stockGroups.size() - 1);
        assertThat(testStockGroup.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testStockGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStockGroup.getIsDiminishing()).isEqualTo(DEFAULT_IS_DIMINISHING);
        assertThat(testStockGroup.getLowestMargin()).isEqualTo(DEFAULT_LOWEST_MARGIN);
        assertThat(testStockGroup.getHighestMargin()).isEqualTo(DEFAULT_HIGHEST_MARGIN);
        assertThat(testStockGroup.getIsDiscountAllowed()).isEqualTo(DEFAULT_IS_DISCOUNT_ALLOWED);
        assertThat(testStockGroup.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testStockGroup.getIsArchived()).isEqualTo(DEFAULT_IS_ARCHIVED);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockGroupRepository.findAll().size();
        // set the field null
        stockGroup.setCode(null);

        // Create the StockGroup, which fails.
        StockGroupDTO stockGroupDTO = stockGroupMapper.stockGroupToStockGroupDTO(stockGroup);

        restStockGroupMockMvc.perform(post("/api/stockGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stockGroupDTO)))
                .andExpect(status().isBadRequest());

        List<StockGroup> stockGroups = stockGroupRepository.findAll();
        assertThat(stockGroups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockGroups() throws Exception {
        // Initialize the database
        stockGroupRepository.saveAndFlush(stockGroup);

        // Get all the stockGroups
        restStockGroupMockMvc.perform(get("/api/stockGroups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(stockGroup.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].isDiminishing").value(hasItem(DEFAULT_IS_DIMINISHING.booleanValue())))
                .andExpect(jsonPath("$.[*].lowestMargin").value(hasItem(DEFAULT_LOWEST_MARGIN.doubleValue())))
                .andExpect(jsonPath("$.[*].highestMargin").value(hasItem(DEFAULT_HIGHEST_MARGIN.doubleValue())))
                .andExpect(jsonPath("$.[*].isDiscountAllowed").value(hasItem(DEFAULT_IS_DISCOUNT_ALLOWED.booleanValue())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
                .andExpect(jsonPath("$.[*].isArchived").value(hasItem(DEFAULT_IS_ARCHIVED.booleanValue())));
    }

    @Test
    @Transactional
    public void getStockGroup() throws Exception {
        // Initialize the database
        stockGroupRepository.saveAndFlush(stockGroup);

        // Get the stockGroup
        restStockGroupMockMvc.perform(get("/api/stockGroups/{id}", stockGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(stockGroup.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isDiminishing").value(DEFAULT_IS_DIMINISHING.booleanValue()))
            .andExpect(jsonPath("$.lowestMargin").value(DEFAULT_LOWEST_MARGIN.doubleValue()))
            .andExpect(jsonPath("$.highestMargin").value(DEFAULT_HIGHEST_MARGIN.doubleValue()))
            .andExpect(jsonPath("$.isDiscountAllowed").value(DEFAULT_IS_DISCOUNT_ALLOWED.booleanValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.isArchived").value(DEFAULT_IS_ARCHIVED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStockGroup() throws Exception {
        // Get the stockGroup
        restStockGroupMockMvc.perform(get("/api/stockGroups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockGroup() throws Exception {
        // Initialize the database
        stockGroupRepository.saveAndFlush(stockGroup);

		int databaseSizeBeforeUpdate = stockGroupRepository.findAll().size();

        // Update the stockGroup
        stockGroup.setCode(UPDATED_CODE);
        stockGroup.setName(UPDATED_NAME);
        stockGroup.setIsDiminishing(UPDATED_IS_DIMINISHING);
        stockGroup.setLowestMargin(UPDATED_LOWEST_MARGIN);
        stockGroup.setHighestMargin(UPDATED_HIGHEST_MARGIN);
        stockGroup.setIsDiscountAllowed(UPDATED_IS_DISCOUNT_ALLOWED);
        stockGroup.setComment(UPDATED_COMMENT);
        stockGroup.setIsArchived(UPDATED_IS_ARCHIVED);
        StockGroupDTO stockGroupDTO = stockGroupMapper.stockGroupToStockGroupDTO(stockGroup);

        restStockGroupMockMvc.perform(put("/api/stockGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stockGroupDTO)))
                .andExpect(status().isOk());

        // Validate the StockGroup in the database
        List<StockGroup> stockGroups = stockGroupRepository.findAll();
        assertThat(stockGroups).hasSize(databaseSizeBeforeUpdate);
        StockGroup testStockGroup = stockGroups.get(stockGroups.size() - 1);
        assertThat(testStockGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStockGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStockGroup.getIsDiminishing()).isEqualTo(UPDATED_IS_DIMINISHING);
        assertThat(testStockGroup.getLowestMargin()).isEqualTo(UPDATED_LOWEST_MARGIN);
        assertThat(testStockGroup.getHighestMargin()).isEqualTo(UPDATED_HIGHEST_MARGIN);
        assertThat(testStockGroup.getIsDiscountAllowed()).isEqualTo(UPDATED_IS_DISCOUNT_ALLOWED);
        assertThat(testStockGroup.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testStockGroup.getIsArchived()).isEqualTo(UPDATED_IS_ARCHIVED);
    }

    @Test
    @Transactional
    public void deleteStockGroup() throws Exception {
        // Initialize the database
        stockGroupRepository.saveAndFlush(stockGroup);

		int databaseSizeBeforeDelete = stockGroupRepository.findAll().size();

        // Get the stockGroup
        restStockGroupMockMvc.perform(delete("/api/stockGroups/{id}", stockGroup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<StockGroup> stockGroups = stockGroupRepository.findAll();
        assertThat(stockGroups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
