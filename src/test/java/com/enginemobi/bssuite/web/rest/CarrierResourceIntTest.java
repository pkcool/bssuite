package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.Carrier;
import com.enginemobi.bssuite.repository.CarrierRepository;
import com.enginemobi.bssuite.repository.search.CarrierSearchRepository;

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
 * Test class for the CarrierResource REST controller.
 *
 * @see CarrierResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CarrierResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_PHONE = "AAAAA";
    private static final String UPDATED_PHONE = "BBBBB";
    private static final String DEFAULT_MOBILE = "AAAAA";
    private static final String UPDATED_MOBILE = "BBBBB";
    private static final String DEFAULT_ACCOUNT_NO = "AAAAA";
    private static final String UPDATED_ACCOUNT_NO = "BBBBB";
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    private static final Boolean DEFAULT_IS_AVAILABLE_ON_MONDAY = false;
    private static final Boolean UPDATED_IS_AVAILABLE_ON_MONDAY = true;

    private static final Boolean DEFAULT_IS_AVAILABLE_ON_TUESDAY = false;
    private static final Boolean UPDATED_IS_AVAILABLE_ON_TUESDAY = true;

    private static final Boolean DEFAULT_IS_AVAILABLE_ON_WEDNESDAY = false;
    private static final Boolean UPDATED_IS_AVAILABLE_ON_WEDNESDAY = true;

    private static final Boolean DEFAULT_IS_AVAILABLE_ON_THURSDAY = false;
    private static final Boolean UPDATED_IS_AVAILABLE_ON_THURSDAY = true;

    private static final Boolean DEFAULT_IS_AVAILABLE_ON_FRIDAY = false;
    private static final Boolean UPDATED_IS_AVAILABLE_ON_FRIDAY = true;

    private static final Boolean DEFAULT_IS_AVAILABLE_ON_SATURDAY = false;
    private static final Boolean UPDATED_IS_AVAILABLE_ON_SATURDAY = true;

    private static final Boolean DEFAULT_IS_AVAILABLE_ON_SUNDAY = false;
    private static final Boolean UPDATED_IS_AVAILABLE_ON_SUNDAY = true;
    private static final String DEFAULT_DOCKET_REF_NO = "AAAAA";
    private static final String UPDATED_DOCKET_REF_NO = "BBBBB";

    @Inject
    private CarrierRepository carrierRepository;

    @Inject
    private CarrierSearchRepository carrierSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCarrierMockMvc;

    private Carrier carrier;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarrierResource carrierResource = new CarrierResource();
        ReflectionTestUtils.setField(carrierResource, "carrierRepository", carrierRepository);
        ReflectionTestUtils.setField(carrierResource, "carrierSearchRepository", carrierSearchRepository);
        this.restCarrierMockMvc = MockMvcBuilders.standaloneSetup(carrierResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        carrier = new Carrier();
        carrier.setCode(DEFAULT_CODE);
        carrier.setName(DEFAULT_NAME);
        carrier.setPhone(DEFAULT_PHONE);
        carrier.setMobile(DEFAULT_MOBILE);
        carrier.setAccountNo(DEFAULT_ACCOUNT_NO);
        carrier.setComment(DEFAULT_COMMENT);
        carrier.setIsAvailableOnMonday(DEFAULT_IS_AVAILABLE_ON_MONDAY);
        carrier.setIsAvailableOnTuesday(DEFAULT_IS_AVAILABLE_ON_TUESDAY);
        carrier.setIsAvailableOnWednesday(DEFAULT_IS_AVAILABLE_ON_WEDNESDAY);
        carrier.setIsAvailableOnThursday(DEFAULT_IS_AVAILABLE_ON_THURSDAY);
        carrier.setIsAvailableOnFriday(DEFAULT_IS_AVAILABLE_ON_FRIDAY);
        carrier.setIsAvailableOnSaturday(DEFAULT_IS_AVAILABLE_ON_SATURDAY);
        carrier.setIsAvailableOnSunday(DEFAULT_IS_AVAILABLE_ON_SUNDAY);
        carrier.setDocketRefNo(DEFAULT_DOCKET_REF_NO);
    }

    @Test
    @Transactional
    public void createCarrier() throws Exception {
        int databaseSizeBeforeCreate = carrierRepository.findAll().size();

        // Create the Carrier

        restCarrierMockMvc.perform(post("/api/carriers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carrier)))
                .andExpect(status().isCreated());

        // Validate the Carrier in the database
        List<Carrier> carriers = carrierRepository.findAll();
        assertThat(carriers).hasSize(databaseSizeBeforeCreate + 1);
        Carrier testCarrier = carriers.get(carriers.size() - 1);
        assertThat(testCarrier.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCarrier.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCarrier.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCarrier.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testCarrier.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
        assertThat(testCarrier.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testCarrier.getIsAvailableOnMonday()).isEqualTo(DEFAULT_IS_AVAILABLE_ON_MONDAY);
        assertThat(testCarrier.getIsAvailableOnTuesday()).isEqualTo(DEFAULT_IS_AVAILABLE_ON_TUESDAY);
        assertThat(testCarrier.getIsAvailableOnWednesday()).isEqualTo(DEFAULT_IS_AVAILABLE_ON_WEDNESDAY);
        assertThat(testCarrier.getIsAvailableOnThursday()).isEqualTo(DEFAULT_IS_AVAILABLE_ON_THURSDAY);
        assertThat(testCarrier.getIsAvailableOnFriday()).isEqualTo(DEFAULT_IS_AVAILABLE_ON_FRIDAY);
        assertThat(testCarrier.getIsAvailableOnSaturday()).isEqualTo(DEFAULT_IS_AVAILABLE_ON_SATURDAY);
        assertThat(testCarrier.getIsAvailableOnSunday()).isEqualTo(DEFAULT_IS_AVAILABLE_ON_SUNDAY);
        assertThat(testCarrier.getDocketRefNo()).isEqualTo(DEFAULT_DOCKET_REF_NO);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = carrierRepository.findAll().size();
        // set the field null
        carrier.setCode(null);

        // Create the Carrier, which fails.

        restCarrierMockMvc.perform(post("/api/carriers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carrier)))
                .andExpect(status().isBadRequest());

        List<Carrier> carriers = carrierRepository.findAll();
        assertThat(carriers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarriers() throws Exception {
        // Initialize the database
        carrierRepository.saveAndFlush(carrier);

        // Get all the carriers
        restCarrierMockMvc.perform(get("/api/carriers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(carrier.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
                .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO.toString())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
                .andExpect(jsonPath("$.[*].isAvailableOnMonday").value(hasItem(DEFAULT_IS_AVAILABLE_ON_MONDAY.booleanValue())))
                .andExpect(jsonPath("$.[*].isAvailableOnTuesday").value(hasItem(DEFAULT_IS_AVAILABLE_ON_TUESDAY.booleanValue())))
                .andExpect(jsonPath("$.[*].isAvailableOnWednesday").value(hasItem(DEFAULT_IS_AVAILABLE_ON_WEDNESDAY.booleanValue())))
                .andExpect(jsonPath("$.[*].isAvailableOnThursday").value(hasItem(DEFAULT_IS_AVAILABLE_ON_THURSDAY.booleanValue())))
                .andExpect(jsonPath("$.[*].isAvailableOnFriday").value(hasItem(DEFAULT_IS_AVAILABLE_ON_FRIDAY.booleanValue())))
                .andExpect(jsonPath("$.[*].isAvailableOnSaturday").value(hasItem(DEFAULT_IS_AVAILABLE_ON_SATURDAY.booleanValue())))
                .andExpect(jsonPath("$.[*].isAvailableOnSunday").value(hasItem(DEFAULT_IS_AVAILABLE_ON_SUNDAY.booleanValue())))
                .andExpect(jsonPath("$.[*].docketRefNo").value(hasItem(DEFAULT_DOCKET_REF_NO.toString())));
    }

    @Test
    @Transactional
    public void getCarrier() throws Exception {
        // Initialize the database
        carrierRepository.saveAndFlush(carrier);

        // Get the carrier
        restCarrierMockMvc.perform(get("/api/carriers/{id}", carrier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(carrier.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.accountNo").value(DEFAULT_ACCOUNT_NO.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.isAvailableOnMonday").value(DEFAULT_IS_AVAILABLE_ON_MONDAY.booleanValue()))
            .andExpect(jsonPath("$.isAvailableOnTuesday").value(DEFAULT_IS_AVAILABLE_ON_TUESDAY.booleanValue()))
            .andExpect(jsonPath("$.isAvailableOnWednesday").value(DEFAULT_IS_AVAILABLE_ON_WEDNESDAY.booleanValue()))
            .andExpect(jsonPath("$.isAvailableOnThursday").value(DEFAULT_IS_AVAILABLE_ON_THURSDAY.booleanValue()))
            .andExpect(jsonPath("$.isAvailableOnFriday").value(DEFAULT_IS_AVAILABLE_ON_FRIDAY.booleanValue()))
            .andExpect(jsonPath("$.isAvailableOnSaturday").value(DEFAULT_IS_AVAILABLE_ON_SATURDAY.booleanValue()))
            .andExpect(jsonPath("$.isAvailableOnSunday").value(DEFAULT_IS_AVAILABLE_ON_SUNDAY.booleanValue()))
            .andExpect(jsonPath("$.docketRefNo").value(DEFAULT_DOCKET_REF_NO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCarrier() throws Exception {
        // Get the carrier
        restCarrierMockMvc.perform(get("/api/carriers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarrier() throws Exception {
        // Initialize the database
        carrierRepository.saveAndFlush(carrier);

		int databaseSizeBeforeUpdate = carrierRepository.findAll().size();

        // Update the carrier
        carrier.setCode(UPDATED_CODE);
        carrier.setName(UPDATED_NAME);
        carrier.setPhone(UPDATED_PHONE);
        carrier.setMobile(UPDATED_MOBILE);
        carrier.setAccountNo(UPDATED_ACCOUNT_NO);
        carrier.setComment(UPDATED_COMMENT);
        carrier.setIsAvailableOnMonday(UPDATED_IS_AVAILABLE_ON_MONDAY);
        carrier.setIsAvailableOnTuesday(UPDATED_IS_AVAILABLE_ON_TUESDAY);
        carrier.setIsAvailableOnWednesday(UPDATED_IS_AVAILABLE_ON_WEDNESDAY);
        carrier.setIsAvailableOnThursday(UPDATED_IS_AVAILABLE_ON_THURSDAY);
        carrier.setIsAvailableOnFriday(UPDATED_IS_AVAILABLE_ON_FRIDAY);
        carrier.setIsAvailableOnSaturday(UPDATED_IS_AVAILABLE_ON_SATURDAY);
        carrier.setIsAvailableOnSunday(UPDATED_IS_AVAILABLE_ON_SUNDAY);
        carrier.setDocketRefNo(UPDATED_DOCKET_REF_NO);

        restCarrierMockMvc.perform(put("/api/carriers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carrier)))
                .andExpect(status().isOk());

        // Validate the Carrier in the database
        List<Carrier> carriers = carrierRepository.findAll();
        assertThat(carriers).hasSize(databaseSizeBeforeUpdate);
        Carrier testCarrier = carriers.get(carriers.size() - 1);
        assertThat(testCarrier.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCarrier.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCarrier.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCarrier.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testCarrier.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testCarrier.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testCarrier.getIsAvailableOnMonday()).isEqualTo(UPDATED_IS_AVAILABLE_ON_MONDAY);
        assertThat(testCarrier.getIsAvailableOnTuesday()).isEqualTo(UPDATED_IS_AVAILABLE_ON_TUESDAY);
        assertThat(testCarrier.getIsAvailableOnWednesday()).isEqualTo(UPDATED_IS_AVAILABLE_ON_WEDNESDAY);
        assertThat(testCarrier.getIsAvailableOnThursday()).isEqualTo(UPDATED_IS_AVAILABLE_ON_THURSDAY);
        assertThat(testCarrier.getIsAvailableOnFriday()).isEqualTo(UPDATED_IS_AVAILABLE_ON_FRIDAY);
        assertThat(testCarrier.getIsAvailableOnSaturday()).isEqualTo(UPDATED_IS_AVAILABLE_ON_SATURDAY);
        assertThat(testCarrier.getIsAvailableOnSunday()).isEqualTo(UPDATED_IS_AVAILABLE_ON_SUNDAY);
        assertThat(testCarrier.getDocketRefNo()).isEqualTo(UPDATED_DOCKET_REF_NO);
    }

    @Test
    @Transactional
    public void deleteCarrier() throws Exception {
        // Initialize the database
        carrierRepository.saveAndFlush(carrier);

		int databaseSizeBeforeDelete = carrierRepository.findAll().size();

        // Get the carrier
        restCarrierMockMvc.perform(delete("/api/carriers/{id}", carrier.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Carrier> carriers = carrierRepository.findAll();
        assertThat(carriers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
