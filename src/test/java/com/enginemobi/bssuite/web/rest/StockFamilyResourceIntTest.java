package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.StockFamily;
import com.enginemobi.bssuite.repository.StockFamilyRepository;
import com.enginemobi.bssuite.repository.search.StockFamilySearchRepository;

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
 * Test class for the StockFamilyResource REST controller.
 *
 * @see StockFamilyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StockFamilyResourceIntTest {

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
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    @Inject
    private StockFamilyRepository stockFamilyRepository;

    @Inject
    private StockFamilySearchRepository stockFamilySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStockFamilyMockMvc;

    private StockFamily stockFamily;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StockFamilyResource stockFamilyResource = new StockFamilyResource();
        ReflectionTestUtils.setField(stockFamilyResource, "stockFamilySearchRepository", stockFamilySearchRepository);
        ReflectionTestUtils.setField(stockFamilyResource, "stockFamilyRepository", stockFamilyRepository);
        this.restStockFamilyMockMvc = MockMvcBuilders.standaloneSetup(stockFamilyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        stockFamily = new StockFamily();
        stockFamily.setCode(DEFAULT_CODE);
        stockFamily.setName(DEFAULT_NAME);
        stockFamily.setIsDiminishing(DEFAULT_IS_DIMINISHING);
        stockFamily.setLowestMargin(DEFAULT_LOWEST_MARGIN);
        stockFamily.setHighestMargin(DEFAULT_HIGHEST_MARGIN);
        stockFamily.setComment(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createStockFamily() throws Exception {
        int databaseSizeBeforeCreate = stockFamilyRepository.findAll().size();

        // Create the StockFamily

        restStockFamilyMockMvc.perform(post("/api/stockFamilys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stockFamily)))
                .andExpect(status().isCreated());

        // Validate the StockFamily in the database
        List<StockFamily> stockFamilys = stockFamilyRepository.findAll();
        assertThat(stockFamilys).hasSize(databaseSizeBeforeCreate + 1);
        StockFamily testStockFamily = stockFamilys.get(stockFamilys.size() - 1);
        assertThat(testStockFamily.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testStockFamily.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStockFamily.getIsDiminishing()).isEqualTo(DEFAULT_IS_DIMINISHING);
        assertThat(testStockFamily.getLowestMargin()).isEqualTo(DEFAULT_LOWEST_MARGIN);
        assertThat(testStockFamily.getHighestMargin()).isEqualTo(DEFAULT_HIGHEST_MARGIN);
        assertThat(testStockFamily.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockFamilyRepository.findAll().size();
        // set the field null
        stockFamily.setCode(null);

        // Create the StockFamily, which fails.

        restStockFamilyMockMvc.perform(post("/api/stockFamilys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stockFamily)))
                .andExpect(status().isBadRequest());

        List<StockFamily> stockFamilys = stockFamilyRepository.findAll();
        assertThat(stockFamilys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockFamilys() throws Exception {
        // Initialize the database
        stockFamilyRepository.saveAndFlush(stockFamily);

        // Get all the stockFamilys
        restStockFamilyMockMvc.perform(get("/api/stockFamilys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(stockFamily.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].isDiminishing").value(hasItem(DEFAULT_IS_DIMINISHING.booleanValue())))
                .andExpect(jsonPath("$.[*].lowestMargin").value(hasItem(DEFAULT_LOWEST_MARGIN.doubleValue())))
                .andExpect(jsonPath("$.[*].highestMargin").value(hasItem(DEFAULT_HIGHEST_MARGIN.doubleValue())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getStockFamily() throws Exception {
        // Initialize the database
        stockFamilyRepository.saveAndFlush(stockFamily);

        // Get the stockFamily
        restStockFamilyMockMvc.perform(get("/api/stockFamilys/{id}", stockFamily.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(stockFamily.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isDiminishing").value(DEFAULT_IS_DIMINISHING.booleanValue()))
            .andExpect(jsonPath("$.lowestMargin").value(DEFAULT_LOWEST_MARGIN.doubleValue()))
            .andExpect(jsonPath("$.highestMargin").value(DEFAULT_HIGHEST_MARGIN.doubleValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStockFamily() throws Exception {
        // Get the stockFamily
        restStockFamilyMockMvc.perform(get("/api/stockFamilys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockFamily() throws Exception {
        // Initialize the database
        stockFamilyRepository.saveAndFlush(stockFamily);

		int databaseSizeBeforeUpdate = stockFamilyRepository.findAll().size();

        // Update the stockFamily
        stockFamily.setCode(UPDATED_CODE);
        stockFamily.setName(UPDATED_NAME);
        stockFamily.setIsDiminishing(UPDATED_IS_DIMINISHING);
        stockFamily.setLowestMargin(UPDATED_LOWEST_MARGIN);
        stockFamily.setHighestMargin(UPDATED_HIGHEST_MARGIN);
        stockFamily.setComment(UPDATED_COMMENT);

        restStockFamilyMockMvc.perform(put("/api/stockFamilys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stockFamily)))
                .andExpect(status().isOk());

        // Validate the StockFamily in the database
        List<StockFamily> stockFamilys = stockFamilyRepository.findAll();
        assertThat(stockFamilys).hasSize(databaseSizeBeforeUpdate);
        StockFamily testStockFamily = stockFamilys.get(stockFamilys.size() - 1);
        assertThat(testStockFamily.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStockFamily.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStockFamily.getIsDiminishing()).isEqualTo(UPDATED_IS_DIMINISHING);
        assertThat(testStockFamily.getLowestMargin()).isEqualTo(UPDATED_LOWEST_MARGIN);
        assertThat(testStockFamily.getHighestMargin()).isEqualTo(UPDATED_HIGHEST_MARGIN);
        assertThat(testStockFamily.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void deleteStockFamily() throws Exception {
        // Initialize the database
        stockFamilyRepository.saveAndFlush(stockFamily);

		int databaseSizeBeforeDelete = stockFamilyRepository.findAll().size();

        // Get the stockFamily
        restStockFamilyMockMvc.perform(delete("/api/stockFamilys/{id}", stockFamily.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<StockFamily> stockFamilys = stockFamilyRepository.findAll();
        assertThat(stockFamilys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
