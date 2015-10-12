package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.SupplierCategory;
import com.enginemobi.bssuite.repository.SupplierCategoryRepository;
import com.enginemobi.bssuite.repository.search.SupplierCategorySearchRepository;

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
 * Test class for the SupplierCategoryResource REST controller.
 *
 * @see SupplierCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SupplierCategoryResourceTest {

    private static final String DEFAULT_CODE = "AA";
    private static final String UPDATED_CODE = "BB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private SupplierCategoryRepository supplierCategoryRepository;

    @Inject
    private SupplierCategorySearchRepository supplierCategorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSupplierCategoryMockMvc;

    private SupplierCategory supplierCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SupplierCategoryResource supplierCategoryResource = new SupplierCategoryResource();
        ReflectionTestUtils.setField(supplierCategoryResource, "supplierCategoryRepository", supplierCategoryRepository);
        ReflectionTestUtils.setField(supplierCategoryResource, "supplierCategorySearchRepository", supplierCategorySearchRepository);
        this.restSupplierCategoryMockMvc = MockMvcBuilders.standaloneSetup(supplierCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        supplierCategory = new SupplierCategory();
        supplierCategory.setCode(DEFAULT_CODE);
        supplierCategory.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSupplierCategory() throws Exception {
        int databaseSizeBeforeCreate = supplierCategoryRepository.findAll().size();

        // Create the SupplierCategory

        restSupplierCategoryMockMvc.perform(post("/api/supplierCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(supplierCategory)))
                .andExpect(status().isCreated());

        // Validate the SupplierCategory in the database
        List<SupplierCategory> supplierCategorys = supplierCategoryRepository.findAll();
        assertThat(supplierCategorys).hasSize(databaseSizeBeforeCreate + 1);
        SupplierCategory testSupplierCategory = supplierCategorys.get(supplierCategorys.size() - 1);
        assertThat(testSupplierCategory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSupplierCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierCategoryRepository.findAll().size();
        // set the field null
        supplierCategory.setCode(null);

        // Create the SupplierCategory, which fails.

        restSupplierCategoryMockMvc.perform(post("/api/supplierCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(supplierCategory)))
                .andExpect(status().isBadRequest());

        List<SupplierCategory> supplierCategorys = supplierCategoryRepository.findAll();
        assertThat(supplierCategorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplierCategorys() throws Exception {
        // Initialize the database
        supplierCategoryRepository.saveAndFlush(supplierCategory);

        // Get all the supplierCategorys
        restSupplierCategoryMockMvc.perform(get("/api/supplierCategorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(supplierCategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getSupplierCategory() throws Exception {
        // Initialize the database
        supplierCategoryRepository.saveAndFlush(supplierCategory);

        // Get the supplierCategory
        restSupplierCategoryMockMvc.perform(get("/api/supplierCategorys/{id}", supplierCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(supplierCategory.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSupplierCategory() throws Exception {
        // Get the supplierCategory
        restSupplierCategoryMockMvc.perform(get("/api/supplierCategorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplierCategory() throws Exception {
        // Initialize the database
        supplierCategoryRepository.saveAndFlush(supplierCategory);

		int databaseSizeBeforeUpdate = supplierCategoryRepository.findAll().size();

        // Update the supplierCategory
        supplierCategory.setCode(UPDATED_CODE);
        supplierCategory.setName(UPDATED_NAME);

        restSupplierCategoryMockMvc.perform(put("/api/supplierCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(supplierCategory)))
                .andExpect(status().isOk());

        // Validate the SupplierCategory in the database
        List<SupplierCategory> supplierCategorys = supplierCategoryRepository.findAll();
        assertThat(supplierCategorys).hasSize(databaseSizeBeforeUpdate);
        SupplierCategory testSupplierCategory = supplierCategorys.get(supplierCategorys.size() - 1);
        assertThat(testSupplierCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSupplierCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteSupplierCategory() throws Exception {
        // Initialize the database
        supplierCategoryRepository.saveAndFlush(supplierCategory);

		int databaseSizeBeforeDelete = supplierCategoryRepository.findAll().size();

        // Get the supplierCategory
        restSupplierCategoryMockMvc.perform(delete("/api/supplierCategorys/{id}", supplierCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SupplierCategory> supplierCategorys = supplierCategoryRepository.findAll();
        assertThat(supplierCategorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
