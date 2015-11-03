package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.ProductRelationCategory;
import com.enginemobi.bssuite.repository.ProductRelationCategoryRepository;
import com.enginemobi.bssuite.repository.search.ProductRelationCategorySearchRepository;

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
 * Test class for the ProductRelationCategoryResource REST controller.
 *
 * @see ProductRelationCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProductRelationCategoryResourceTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private ProductRelationCategoryRepository productRelationCategoryRepository;

    @Inject
    private ProductRelationCategorySearchRepository productRelationCategorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProductRelationCategoryMockMvc;

    private ProductRelationCategory productRelationCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductRelationCategoryResource productRelationCategoryResource = new ProductRelationCategoryResource();
        ReflectionTestUtils.setField(productRelationCategoryResource, "productRelationCategoryRepository", productRelationCategoryRepository);
        ReflectionTestUtils.setField(productRelationCategoryResource, "productRelationCategorySearchRepository", productRelationCategorySearchRepository);
        this.restProductRelationCategoryMockMvc = MockMvcBuilders.standaloneSetup(productRelationCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        productRelationCategory = new ProductRelationCategory();
        productRelationCategory.setCode(DEFAULT_CODE);
        productRelationCategory.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProductRelationCategory() throws Exception {
        int databaseSizeBeforeCreate = productRelationCategoryRepository.findAll().size();

        // Create the ProductRelationCategory

        restProductRelationCategoryMockMvc.perform(post("/api/productRelationCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productRelationCategory)))
                .andExpect(status().isCreated());

        // Validate the ProductRelationCategory in the database
        List<ProductRelationCategory> productRelationCategorys = productRelationCategoryRepository.findAll();
        assertThat(productRelationCategorys).hasSize(databaseSizeBeforeCreate + 1);
        ProductRelationCategory testProductRelationCategory = productRelationCategorys.get(productRelationCategorys.size() - 1);
        assertThat(testProductRelationCategory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProductRelationCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductRelationCategorys() throws Exception {
        // Initialize the database
        productRelationCategoryRepository.saveAndFlush(productRelationCategory);

        // Get all the productRelationCategorys
        restProductRelationCategoryMockMvc.perform(get("/api/productRelationCategorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(productRelationCategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getProductRelationCategory() throws Exception {
        // Initialize the database
        productRelationCategoryRepository.saveAndFlush(productRelationCategory);

        // Get the productRelationCategory
        restProductRelationCategoryMockMvc.perform(get("/api/productRelationCategorys/{id}", productRelationCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(productRelationCategory.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductRelationCategory() throws Exception {
        // Get the productRelationCategory
        restProductRelationCategoryMockMvc.perform(get("/api/productRelationCategorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductRelationCategory() throws Exception {
        // Initialize the database
        productRelationCategoryRepository.saveAndFlush(productRelationCategory);

		int databaseSizeBeforeUpdate = productRelationCategoryRepository.findAll().size();

        // Update the productRelationCategory
        productRelationCategory.setCode(UPDATED_CODE);
        productRelationCategory.setDescription(UPDATED_DESCRIPTION);

        restProductRelationCategoryMockMvc.perform(put("/api/productRelationCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productRelationCategory)))
                .andExpect(status().isOk());

        // Validate the ProductRelationCategory in the database
        List<ProductRelationCategory> productRelationCategorys = productRelationCategoryRepository.findAll();
        assertThat(productRelationCategorys).hasSize(databaseSizeBeforeUpdate);
        ProductRelationCategory testProductRelationCategory = productRelationCategorys.get(productRelationCategorys.size() - 1);
        assertThat(testProductRelationCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProductRelationCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteProductRelationCategory() throws Exception {
        // Initialize the database
        productRelationCategoryRepository.saveAndFlush(productRelationCategory);

		int databaseSizeBeforeDelete = productRelationCategoryRepository.findAll().size();

        // Get the productRelationCategory
        restProductRelationCategoryMockMvc.perform(delete("/api/productRelationCategorys/{id}", productRelationCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductRelationCategory> productRelationCategorys = productRelationCategoryRepository.findAll();
        assertThat(productRelationCategorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
