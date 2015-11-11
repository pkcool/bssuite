package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.TaxTable;
import com.enginemobi.bssuite.repository.TaxTableRepository;
import com.enginemobi.bssuite.repository.search.TaxTableSearchRepository;

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
 * Test class for the TaxTableResource REST controller.
 *
 * @see TaxTableResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TaxTableResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_FORMULA = "AAAAA";
    private static final String UPDATED_FORMULA = "BBBBB";

    private static final Boolean DEFAULT_IS_ADDED_TO_TOTAL = false;
    private static final Boolean UPDATED_IS_ADDED_TO_TOTAL = true;

    private static final Boolean DEFAULT_IS_SUBTRACTED_FROM_TOTAL = false;
    private static final Boolean UPDATED_IS_SUBTRACTED_FROM_TOTAL = true;

    private static final Boolean DEFAULT_IS_EXCLUDED_ON_REPORTING = false;
    private static final Boolean UPDATED_IS_EXCLUDED_ON_REPORTING = true;
    private static final String DEFAULT_TAX_GROUP = "AAAAA";
    private static final String UPDATED_TAX_GROUP = "BBBBB";
    private static final String DEFAULT_TAX_BASE = "AAAAA";
    private static final String UPDATED_TAX_BASE = "BBBBB";

    @Inject
    private TaxTableRepository taxTableRepository;

    @Inject
    private TaxTableSearchRepository taxTableSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTaxTableMockMvc;

    private TaxTable taxTable;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TaxTableResource taxTableResource = new TaxTableResource();
        ReflectionTestUtils.setField(taxTableResource, "taxTableRepository", taxTableRepository);
        ReflectionTestUtils.setField(taxTableResource, "taxTableSearchRepository", taxTableSearchRepository);
        this.restTaxTableMockMvc = MockMvcBuilders.standaloneSetup(taxTableResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        taxTable = new TaxTable();
        taxTable.setCode(DEFAULT_CODE);
        taxTable.setName(DEFAULT_NAME);
        taxTable.setFormula(DEFAULT_FORMULA);
        taxTable.setIsAddedToTotal(DEFAULT_IS_ADDED_TO_TOTAL);
        taxTable.setIsSubtractedFromTotal(DEFAULT_IS_SUBTRACTED_FROM_TOTAL);
        taxTable.setIsExcludedOnReporting(DEFAULT_IS_EXCLUDED_ON_REPORTING);
        taxTable.setTaxGroup(DEFAULT_TAX_GROUP);
        taxTable.setTaxBase(DEFAULT_TAX_BASE);
    }

    @Test
    @Transactional
    public void createTaxTable() throws Exception {
        int databaseSizeBeforeCreate = taxTableRepository.findAll().size();

        // Create the TaxTable

        restTaxTableMockMvc.perform(post("/api/taxTables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxTable)))
                .andExpect(status().isCreated());

        // Validate the TaxTable in the database
        List<TaxTable> taxTables = taxTableRepository.findAll();
        assertThat(taxTables).hasSize(databaseSizeBeforeCreate + 1);
        TaxTable testTaxTable = taxTables.get(taxTables.size() - 1);
        assertThat(testTaxTable.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTaxTable.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaxTable.getFormula()).isEqualTo(DEFAULT_FORMULA);
        assertThat(testTaxTable.getIsAddedToTotal()).isEqualTo(DEFAULT_IS_ADDED_TO_TOTAL);
        assertThat(testTaxTable.getIsSubtractedFromTotal()).isEqualTo(DEFAULT_IS_SUBTRACTED_FROM_TOTAL);
        assertThat(testTaxTable.getIsExcludedOnReporting()).isEqualTo(DEFAULT_IS_EXCLUDED_ON_REPORTING);
        assertThat(testTaxTable.getTaxGroup()).isEqualTo(DEFAULT_TAX_GROUP);
        assertThat(testTaxTable.getTaxBase()).isEqualTo(DEFAULT_TAX_BASE);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxTableRepository.findAll().size();
        // set the field null
        taxTable.setCode(null);

        // Create the TaxTable, which fails.

        restTaxTableMockMvc.perform(post("/api/taxTables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxTable)))
                .andExpect(status().isBadRequest());

        List<TaxTable> taxTables = taxTableRepository.findAll();
        assertThat(taxTables).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaxTables() throws Exception {
        // Initialize the database
        taxTableRepository.saveAndFlush(taxTable);

        // Get all the taxTables
        restTaxTableMockMvc.perform(get("/api/taxTables"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(taxTable.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].formula").value(hasItem(DEFAULT_FORMULA.toString())))
                .andExpect(jsonPath("$.[*].isAddedToTotal").value(hasItem(DEFAULT_IS_ADDED_TO_TOTAL.booleanValue())))
                .andExpect(jsonPath("$.[*].isSubtractedFromTotal").value(hasItem(DEFAULT_IS_SUBTRACTED_FROM_TOTAL.booleanValue())))
                .andExpect(jsonPath("$.[*].isExcludedOnReporting").value(hasItem(DEFAULT_IS_EXCLUDED_ON_REPORTING.booleanValue())))
                .andExpect(jsonPath("$.[*].taxGroup").value(hasItem(DEFAULT_TAX_GROUP.toString())))
                .andExpect(jsonPath("$.[*].taxBase").value(hasItem(DEFAULT_TAX_BASE.toString())));
    }

    @Test
    @Transactional
    public void getTaxTable() throws Exception {
        // Initialize the database
        taxTableRepository.saveAndFlush(taxTable);

        // Get the taxTable
        restTaxTableMockMvc.perform(get("/api/taxTables/{id}", taxTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(taxTable.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.formula").value(DEFAULT_FORMULA.toString()))
            .andExpect(jsonPath("$.isAddedToTotal").value(DEFAULT_IS_ADDED_TO_TOTAL.booleanValue()))
            .andExpect(jsonPath("$.isSubtractedFromTotal").value(DEFAULT_IS_SUBTRACTED_FROM_TOTAL.booleanValue()))
            .andExpect(jsonPath("$.isExcludedOnReporting").value(DEFAULT_IS_EXCLUDED_ON_REPORTING.booleanValue()))
            .andExpect(jsonPath("$.taxGroup").value(DEFAULT_TAX_GROUP.toString()))
            .andExpect(jsonPath("$.taxBase").value(DEFAULT_TAX_BASE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTaxTable() throws Exception {
        // Get the taxTable
        restTaxTableMockMvc.perform(get("/api/taxTables/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxTable() throws Exception {
        // Initialize the database
        taxTableRepository.saveAndFlush(taxTable);

		int databaseSizeBeforeUpdate = taxTableRepository.findAll().size();

        // Update the taxTable
        taxTable.setCode(UPDATED_CODE);
        taxTable.setName(UPDATED_NAME);
        taxTable.setFormula(UPDATED_FORMULA);
        taxTable.setIsAddedToTotal(UPDATED_IS_ADDED_TO_TOTAL);
        taxTable.setIsSubtractedFromTotal(UPDATED_IS_SUBTRACTED_FROM_TOTAL);
        taxTable.setIsExcludedOnReporting(UPDATED_IS_EXCLUDED_ON_REPORTING);
        taxTable.setTaxGroup(UPDATED_TAX_GROUP);
        taxTable.setTaxBase(UPDATED_TAX_BASE);

        restTaxTableMockMvc.perform(put("/api/taxTables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxTable)))
                .andExpect(status().isOk());

        // Validate the TaxTable in the database
        List<TaxTable> taxTables = taxTableRepository.findAll();
        assertThat(taxTables).hasSize(databaseSizeBeforeUpdate);
        TaxTable testTaxTable = taxTables.get(taxTables.size() - 1);
        assertThat(testTaxTable.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTaxTable.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaxTable.getFormula()).isEqualTo(UPDATED_FORMULA);
        assertThat(testTaxTable.getIsAddedToTotal()).isEqualTo(UPDATED_IS_ADDED_TO_TOTAL);
        assertThat(testTaxTable.getIsSubtractedFromTotal()).isEqualTo(UPDATED_IS_SUBTRACTED_FROM_TOTAL);
        assertThat(testTaxTable.getIsExcludedOnReporting()).isEqualTo(UPDATED_IS_EXCLUDED_ON_REPORTING);
        assertThat(testTaxTable.getTaxGroup()).isEqualTo(UPDATED_TAX_GROUP);
        assertThat(testTaxTable.getTaxBase()).isEqualTo(UPDATED_TAX_BASE);
    }

    @Test
    @Transactional
    public void deleteTaxTable() throws Exception {
        // Initialize the database
        taxTableRepository.saveAndFlush(taxTable);

		int databaseSizeBeforeDelete = taxTableRepository.findAll().size();

        // Get the taxTable
        restTaxTableMockMvc.perform(delete("/api/taxTables/{id}", taxTable.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TaxTable> taxTables = taxTableRepository.findAll();
        assertThat(taxTables).hasSize(databaseSizeBeforeDelete - 1);
    }
}
