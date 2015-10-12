package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.Address;
import com.enginemobi.bssuite.repository.AddressRepository;
import com.enginemobi.bssuite.repository.search.AddressSearchRepository;

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
 * Test class for the AddressResource REST controller.
 *
 * @see AddressResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AddressResourceTest {

    private static final String DEFAULT_ADDRESS_LINE1 = "AAAAA";
    private static final String UPDATED_ADDRESS_LINE1 = "BBBBB";
    private static final String DEFAULT_ADDRESS_LINE2 = "AAAAA";
    private static final String UPDATED_ADDRESS_LINE2 = "BBBBB";
    private static final String DEFAULT_SUBURB = "AAAAA";
    private static final String UPDATED_SUBURB = "BBBBB";
    private static final String DEFAULT_STATE = "AAAAA";
    private static final String UPDATED_STATE = "BBBBB";
    private static final String DEFAULT_POSTCODE = "AAAAA";
    private static final String UPDATED_POSTCODE = "BBBBB";
    private static final String DEFAULT_COUNTRY = "AAAAA";
    private static final String UPDATED_COUNTRY = "BBBBB";

    @Inject
    private AddressRepository addressRepository;

    @Inject
    private AddressSearchRepository addressSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAddressMockMvc;

    private Address address;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AddressResource addressResource = new AddressResource();
        ReflectionTestUtils.setField(addressResource, "addressRepository", addressRepository);
        ReflectionTestUtils.setField(addressResource, "addressSearchRepository", addressSearchRepository);
        this.restAddressMockMvc = MockMvcBuilders.standaloneSetup(addressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        address = new Address();
        address.setAddressLine1(DEFAULT_ADDRESS_LINE1);
        address.setAddressLine2(DEFAULT_ADDRESS_LINE2);
        address.setSuburb(DEFAULT_SUBURB);
        address.setState(DEFAULT_STATE);
        address.setPostcode(DEFAULT_POSTCODE);
        address.setCountry(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // Create the Address

        restAddressMockMvc.perform(post("/api/addresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(address)))
                .andExpect(status().isCreated());

        // Validate the Address in the database
        List<Address> addresss = addressRepository.findAll();
        assertThat(addresss).hasSize(databaseSizeBeforeCreate + 1);
        Address testAddress = addresss.get(addresss.size() - 1);
        assertThat(testAddress.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE1);
        assertThat(testAddress.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE2);
        assertThat(testAddress.getSuburb()).isEqualTo(DEFAULT_SUBURB);
        assertThat(testAddress.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testAddress.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllAddresss() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addresss
        restAddressMockMvc.perform(get("/api/addresss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
                .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE1.toString())))
                .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE2.toString())))
                .andExpect(jsonPath("$.[*].suburb").value(hasItem(DEFAULT_SUBURB.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
                .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE.toString())))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())));
    }

    @Test
    @Transactional
    public void getAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get the address
        restAddressMockMvc.perform(get("/api/addresss/{id}", address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(address.getId().intValue()))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE1.toString()))
            .andExpect(jsonPath("$.addressLine2").value(DEFAULT_ADDRESS_LINE2.toString()))
            .andExpect(jsonPath("$.suburb").value(DEFAULT_SUBURB.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get("/api/addresss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

		int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address
        address.setAddressLine1(UPDATED_ADDRESS_LINE1);
        address.setAddressLine2(UPDATED_ADDRESS_LINE2);
        address.setSuburb(UPDATED_SUBURB);
        address.setState(UPDATED_STATE);
        address.setPostcode(UPDATED_POSTCODE);
        address.setCountry(UPDATED_COUNTRY);

        restAddressMockMvc.perform(put("/api/addresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(address)))
                .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addresss = addressRepository.findAll();
        assertThat(addresss).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addresss.get(addresss.size() - 1);
        assertThat(testAddress.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE1);
        assertThat(testAddress.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE2);
        assertThat(testAddress.getSuburb()).isEqualTo(UPDATED_SUBURB);
        assertThat(testAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAddress.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void deleteAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

		int databaseSizeBeforeDelete = addressRepository.findAll().size();

        // Get the address
        restAddressMockMvc.perform(delete("/api/addresss/{id}", address.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Address> addresss = addressRepository.findAll();
        assertThat(addresss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
