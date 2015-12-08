package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.CustomerCategory;
import com.enginemobi.bssuite.repository.CustomerCategoryRepository;
import com.enginemobi.bssuite.repository.search.CustomerCategorySearchRepository;

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
 * Test class for the CustomerCategoryResource REST controller.
 *
 * @see CustomerCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CustomerCategoryResourceIntTest {

    private static final String DEFAULT_CODE = "AA";
    private static final String UPDATED_CODE = "BB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private CustomerCategoryRepository customerCategoryRepository;

    @Inject
    private CustomerCategorySearchRepository customerCategorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCustomerCategoryMockMvc;

    private CustomerCategory customerCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerCategoryResource customerCategoryResource = new CustomerCategoryResource();
        ReflectionTestUtils.setField(customerCategoryResource, "customerCategorySearchRepository", customerCategorySearchRepository);
        ReflectionTestUtils.setField(customerCategoryResource, "customerCategoryRepository", customerCategoryRepository);
        this.restCustomerCategoryMockMvc = MockMvcBuilders.standaloneSetup(customerCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        customerCategory = new CustomerCategory();
        customerCategory.setCode(DEFAULT_CODE);
        customerCategory.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCustomerCategory() throws Exception {
        int databaseSizeBeforeCreate = customerCategoryRepository.findAll().size();

        // Create the CustomerCategory

        restCustomerCategoryMockMvc.perform(post("/api/customerCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customerCategory)))
                .andExpect(status().isCreated());

        // Validate the CustomerCategory in the database
        List<CustomerCategory> customerCategorys = customerCategoryRepository.findAll();
        assertThat(customerCategorys).hasSize(databaseSizeBeforeCreate + 1);
        CustomerCategory testCustomerCategory = customerCategorys.get(customerCategorys.size() - 1);
        assertThat(testCustomerCategory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCustomerCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerCategoryRepository.findAll().size();
        // set the field null
        customerCategory.setCode(null);

        // Create the CustomerCategory, which fails.

        restCustomerCategoryMockMvc.perform(post("/api/customerCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customerCategory)))
                .andExpect(status().isBadRequest());

        List<CustomerCategory> customerCategorys = customerCategoryRepository.findAll();
        assertThat(customerCategorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerCategorys() throws Exception {
        // Initialize the database
        customerCategoryRepository.saveAndFlush(customerCategory);

        // Get all the customerCategorys
        restCustomerCategoryMockMvc.perform(get("/api/customerCategorys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(customerCategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCustomerCategory() throws Exception {
        // Initialize the database
        customerCategoryRepository.saveAndFlush(customerCategory);

        // Get the customerCategory
        restCustomerCategoryMockMvc.perform(get("/api/customerCategorys/{id}", customerCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(customerCategory.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerCategory() throws Exception {
        // Get the customerCategory
        restCustomerCategoryMockMvc.perform(get("/api/customerCategorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerCategory() throws Exception {
        // Initialize the database
        customerCategoryRepository.saveAndFlush(customerCategory);

		int databaseSizeBeforeUpdate = customerCategoryRepository.findAll().size();

        // Update the customerCategory
        customerCategory.setCode(UPDATED_CODE);
        customerCategory.setName(UPDATED_NAME);

        restCustomerCategoryMockMvc.perform(put("/api/customerCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customerCategory)))
                .andExpect(status().isOk());

        // Validate the CustomerCategory in the database
        List<CustomerCategory> customerCategorys = customerCategoryRepository.findAll();
        assertThat(customerCategorys).hasSize(databaseSizeBeforeUpdate);
        CustomerCategory testCustomerCategory = customerCategorys.get(customerCategorys.size() - 1);
        assertThat(testCustomerCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCustomerCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteCustomerCategory() throws Exception {
        // Initialize the database
        customerCategoryRepository.saveAndFlush(customerCategory);

		int databaseSizeBeforeDelete = customerCategoryRepository.findAll().size();

        // Get the customerCategory
        restCustomerCategoryMockMvc.perform(delete("/api/customerCategorys/{id}", customerCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerCategory> customerCategorys = customerCategoryRepository.findAll();
        assertThat(customerCategorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
