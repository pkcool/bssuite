package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.Store;
import com.enginemobi.bssuite.repository.StoreRepository;
import com.enginemobi.bssuite.repository.search.StoreSearchRepository;
import com.enginemobi.bssuite.web.rest.dto.StoreDTO;
import com.enginemobi.bssuite.web.rest.mapper.StoreMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the StoreResource REST controller.
 *
 * @see StoreResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StoreResourceTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_ADDRESS1 = "AAAAA";
    private static final String UPDATED_ADDRESS1 = "BBBBB";
    private static final String DEFAULT_ADDRESS2 = "AAAAA";
    private static final String UPDATED_ADDRESS2 = "BBBBB";
    private static final String DEFAULT_SUBURB = "AAAAA";
    private static final String UPDATED_SUBURB = "BBBBB";
    private static final String DEFAULT_STATE = "AAAAA";
    private static final String UPDATED_STATE = "BBBBB";
    private static final String DEFAULT_POSTCODE = "AAAAA";
    private static final String UPDATED_POSTCODE = "BBBBB";
    private static final String DEFAULT_COUNTRY = "AAAAA";
    private static final String UPDATED_COUNTRY = "BBBBB";
    private static final String DEFAULT_PHONE = "AAAAA";
    private static final String UPDATED_PHONE = "BBBBB";
    private static final String DEFAULT_FAX = "AAAAA";
    private static final String UPDATED_FAX = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_WEB_URL = "AAAAA";
    private static final String UPDATED_WEB_URL = "BBBBB";

    private static final LocalDate DEFAULT_IN_BUSINESS_SINCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_IN_BUSINESS_SINCE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_ARCHIVED = false;
    private static final Boolean UPDATED_IS_ARCHIVED = true;

    @Inject
    private StoreRepository storeRepository;

    @Inject
    private StoreMapper storeMapper;

    @Inject
    private StoreSearchRepository storeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStoreMockMvc;

    private Store store;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StoreResource storeResource = new StoreResource();
        ReflectionTestUtils.setField(storeResource, "storeRepository", storeRepository);
        ReflectionTestUtils.setField(storeResource, "storeMapper", storeMapper);
        ReflectionTestUtils.setField(storeResource, "storeSearchRepository", storeSearchRepository);
        this.restStoreMockMvc = MockMvcBuilders.standaloneSetup(storeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        store = new Store();
        store.setCode(DEFAULT_CODE);
        store.setName(DEFAULT_NAME);
        store.setAddress1(DEFAULT_ADDRESS1);
        store.setAddress2(DEFAULT_ADDRESS2);
        store.setSuburb(DEFAULT_SUBURB);
        store.setState(DEFAULT_STATE);
        store.setPostcode(DEFAULT_POSTCODE);
        store.setCountry(DEFAULT_COUNTRY);
        store.setPhone(DEFAULT_PHONE);
        store.setFax(DEFAULT_FAX);
        store.setEmail(DEFAULT_EMAIL);
        store.setWebUrl(DEFAULT_WEB_URL);
        store.setInBusinessSince(DEFAULT_IN_BUSINESS_SINCE);
        store.setIsArchived(DEFAULT_IS_ARCHIVED);
    }

    @Test
    @Transactional
    public void createStore() throws Exception {
        int databaseSizeBeforeCreate = storeRepository.findAll().size();

        // Create the Store
        StoreDTO storeDTO = storeMapper.storeToStoreDTO(store);

        restStoreMockMvc.perform(post("/api/stores")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
                .andExpect(status().isCreated());

        // Validate the Store in the database
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(databaseSizeBeforeCreate + 1);
        Store testStore = stores.get(stores.size() - 1);
        assertThat(testStore.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testStore.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStore.getAddress1()).isEqualTo(DEFAULT_ADDRESS1);
        assertThat(testStore.getAddress2()).isEqualTo(DEFAULT_ADDRESS2);
        assertThat(testStore.getSuburb()).isEqualTo(DEFAULT_SUBURB);
        assertThat(testStore.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testStore.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testStore.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testStore.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testStore.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testStore.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testStore.getWebUrl()).isEqualTo(DEFAULT_WEB_URL);
        assertThat(testStore.getInBusinessSince()).isEqualTo(DEFAULT_IN_BUSINESS_SINCE);
        assertThat(testStore.getIsArchived()).isEqualTo(DEFAULT_IS_ARCHIVED);
    }

    @Test
    @Transactional
    public void getAllStores() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the stores
        restStoreMockMvc.perform(get("/api/stores"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(store.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS1.toString())))
                .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS2.toString())))
                .andExpect(jsonPath("$.[*].suburb").value(hasItem(DEFAULT_SUBURB.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
                .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE.toString())))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].webUrl").value(hasItem(DEFAULT_WEB_URL.toString())))
                .andExpect(jsonPath("$.[*].inBusinessSince").value(hasItem(DEFAULT_IN_BUSINESS_SINCE.toString())))
                .andExpect(jsonPath("$.[*].isArchived").value(hasItem(DEFAULT_IS_ARCHIVED.booleanValue())));
    }

    @Test
    @Transactional
    public void getStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get the store
        restStoreMockMvc.perform(get("/api/stores/{id}", store.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(store.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS1.toString()))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS2.toString()))
            .andExpect(jsonPath("$.suburb").value(DEFAULT_SUBURB.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.webUrl").value(DEFAULT_WEB_URL.toString()))
            .andExpect(jsonPath("$.inBusinessSince").value(DEFAULT_IN_BUSINESS_SINCE.toString()))
            .andExpect(jsonPath("$.isArchived").value(DEFAULT_IS_ARCHIVED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStore() throws Exception {
        // Get the store
        restStoreMockMvc.perform(get("/api/stores/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

		int databaseSizeBeforeUpdate = storeRepository.findAll().size();

        // Update the store
        store.setCode(UPDATED_CODE);
        store.setName(UPDATED_NAME);
        store.setAddress1(UPDATED_ADDRESS1);
        store.setAddress2(UPDATED_ADDRESS2);
        store.setSuburb(UPDATED_SUBURB);
        store.setState(UPDATED_STATE);
        store.setPostcode(UPDATED_POSTCODE);
        store.setCountry(UPDATED_COUNTRY);
        store.setPhone(UPDATED_PHONE);
        store.setFax(UPDATED_FAX);
        store.setEmail(UPDATED_EMAIL);
        store.setWebUrl(UPDATED_WEB_URL);
        store.setInBusinessSince(UPDATED_IN_BUSINESS_SINCE);
        store.setIsArchived(UPDATED_IS_ARCHIVED);
        StoreDTO storeDTO = storeMapper.storeToStoreDTO(store);

        restStoreMockMvc.perform(put("/api/stores")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
                .andExpect(status().isOk());

        // Validate the Store in the database
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(databaseSizeBeforeUpdate);
        Store testStore = stores.get(stores.size() - 1);
        assertThat(testStore.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStore.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStore.getAddress1()).isEqualTo(UPDATED_ADDRESS1);
        assertThat(testStore.getAddress2()).isEqualTo(UPDATED_ADDRESS2);
        assertThat(testStore.getSuburb()).isEqualTo(UPDATED_SUBURB);
        assertThat(testStore.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testStore.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testStore.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testStore.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testStore.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testStore.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testStore.getWebUrl()).isEqualTo(UPDATED_WEB_URL);
        assertThat(testStore.getInBusinessSince()).isEqualTo(UPDATED_IN_BUSINESS_SINCE);
        assertThat(testStore.getIsArchived()).isEqualTo(UPDATED_IS_ARCHIVED);
    }

    @Test
    @Transactional
    public void deleteStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

		int databaseSizeBeforeDelete = storeRepository.findAll().size();

        // Get the store
        restStoreMockMvc.perform(delete("/api/stores/{id}", store.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(databaseSizeBeforeDelete - 1);
    }
}
