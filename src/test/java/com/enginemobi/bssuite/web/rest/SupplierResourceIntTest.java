package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.Supplier;
import com.enginemobi.bssuite.repository.SupplierRepository;
import com.enginemobi.bssuite.repository.search.SupplierSearchRepository;
import com.enginemobi.bssuite.web.rest.dto.SupplierDTO;
import com.enginemobi.bssuite.web.rest.mapper.SupplierMapper;

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

import com.enginemobi.bssuite.domain.enumeration.SupplierAccountType;
import com.enginemobi.bssuite.domain.enumeration.SupplierAgeingMethod;

/**
 * Test class for the SupplierResource REST controller.
 *
 * @see SupplierResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SupplierResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_WEB_URL = "AAAAA";
    private static final String UPDATED_WEB_URL = "BBBBB";
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    private static final Boolean DEFAULT_IS_ON_HOLD = false;
    private static final Boolean UPDATED_IS_ON_HOLD = true;

    private static final Boolean DEFAULT_IS_HEAD_OFFICE = false;
    private static final Boolean UPDATED_IS_HEAD_OFFICE = true;

    private static final Integer DEFAULT_LEAD_TIME = 1;
    private static final Integer UPDATED_LEAD_TIME = 2;


    private static final SupplierAccountType DEFAULT_ACCOUNT_TYPE = SupplierAccountType.STANDARD;
    private static final SupplierAccountType UPDATED_ACCOUNT_TYPE = SupplierAccountType.INTERNAL;

    private static final Integer DEFAULT_SETTLEMENT_TERMS = 1;
    private static final Integer UPDATED_SETTLEMENT_TERMS = 2;

    private static final Double DEFAULT_CREDIT = 1D;
    private static final Double UPDATED_CREDIT = 2D;

    private static final Integer DEFAULT_TERMS = 1;
    private static final Integer UPDATED_TERMS = 2;


    private static final SupplierAgeingMethod DEFAULT_AGEING_METHOD = SupplierAgeingMethod.MONTH;
    private static final SupplierAgeingMethod UPDATED_AGEING_METHOD = SupplierAgeingMethod.TERMS;

    private static final Boolean DEFAULT_IS_EFTPAYMENTS_USED = false;
    private static final Boolean UPDATED_IS_EFTPAYMENTS_USED = true;
    private static final String DEFAULT_BANK_BSB = "AAAAA";
    private static final String UPDATED_BANK_BSB = "BBBBB";
    private static final String DEFAULT_BANK_NUMBER = "AAAAA";
    private static final String UPDATED_BANK_NUMBER = "BBBBB";
    private static final String DEFAULT_BANK_ACCOUNT = "AAAAA";
    private static final String UPDATED_BANK_ACCOUNT = "BBBBB";

    @Inject
    private SupplierRepository supplierRepository;

    @Inject
    private SupplierMapper supplierMapper;

    @Inject
    private SupplierSearchRepository supplierSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSupplierMockMvc;

    private Supplier supplier;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SupplierResource supplierResource = new SupplierResource();
        ReflectionTestUtils.setField(supplierResource, "supplierSearchRepository", supplierSearchRepository);
        ReflectionTestUtils.setField(supplierResource, "supplierRepository", supplierRepository);
        ReflectionTestUtils.setField(supplierResource, "supplierMapper", supplierMapper);
        this.restSupplierMockMvc = MockMvcBuilders.standaloneSetup(supplierResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        supplier = new Supplier();
        supplier.setCode(DEFAULT_CODE);
        supplier.setName(DEFAULT_NAME);
        supplier.setWebUrl(DEFAULT_WEB_URL);
        supplier.setComment(DEFAULT_COMMENT);
        supplier.setIsOnHold(DEFAULT_IS_ON_HOLD);
        supplier.setIsHeadOffice(DEFAULT_IS_HEAD_OFFICE);
        supplier.setLeadTime(DEFAULT_LEAD_TIME);
        supplier.setAccountType(DEFAULT_ACCOUNT_TYPE);
        supplier.setSettlementTerms(DEFAULT_SETTLEMENT_TERMS);
        supplier.setCredit(DEFAULT_CREDIT);
        supplier.setTerms(DEFAULT_TERMS);
        supplier.setAgeingMethod(DEFAULT_AGEING_METHOD);
        supplier.setIsEFTPaymentsUsed(DEFAULT_IS_EFTPAYMENTS_USED);
        supplier.setBankBSB(DEFAULT_BANK_BSB);
        supplier.setBankNumber(DEFAULT_BANK_NUMBER);
        supplier.setBankAccount(DEFAULT_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void createSupplier() throws Exception {
        int databaseSizeBeforeCreate = supplierRepository.findAll().size();

        // Create the Supplier
        SupplierDTO supplierDTO = supplierMapper.supplierToSupplierDTO(supplier);

        restSupplierMockMvc.perform(post("/api/suppliers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(supplierDTO)))
                .andExpect(status().isCreated());

        // Validate the Supplier in the database
        List<Supplier> suppliers = supplierRepository.findAll();
        assertThat(suppliers).hasSize(databaseSizeBeforeCreate + 1);
        Supplier testSupplier = suppliers.get(suppliers.size() - 1);
        assertThat(testSupplier.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSupplier.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSupplier.getWebUrl()).isEqualTo(DEFAULT_WEB_URL);
        assertThat(testSupplier.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testSupplier.getIsOnHold()).isEqualTo(DEFAULT_IS_ON_HOLD);
        assertThat(testSupplier.getIsHeadOffice()).isEqualTo(DEFAULT_IS_HEAD_OFFICE);
        assertThat(testSupplier.getLeadTime()).isEqualTo(DEFAULT_LEAD_TIME);
        assertThat(testSupplier.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testSupplier.getSettlementTerms()).isEqualTo(DEFAULT_SETTLEMENT_TERMS);
        assertThat(testSupplier.getCredit()).isEqualTo(DEFAULT_CREDIT);
        assertThat(testSupplier.getTerms()).isEqualTo(DEFAULT_TERMS);
        assertThat(testSupplier.getAgeingMethod()).isEqualTo(DEFAULT_AGEING_METHOD);
        assertThat(testSupplier.getIsEFTPaymentsUsed()).isEqualTo(DEFAULT_IS_EFTPAYMENTS_USED);
        assertThat(testSupplier.getBankBSB()).isEqualTo(DEFAULT_BANK_BSB);
        assertThat(testSupplier.getBankNumber()).isEqualTo(DEFAULT_BANK_NUMBER);
        assertThat(testSupplier.getBankAccount()).isEqualTo(DEFAULT_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierRepository.findAll().size();
        // set the field null
        supplier.setCode(null);

        // Create the Supplier, which fails.
        SupplierDTO supplierDTO = supplierMapper.supplierToSupplierDTO(supplier);

        restSupplierMockMvc.perform(post("/api/suppliers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(supplierDTO)))
                .andExpect(status().isBadRequest());

        List<Supplier> suppliers = supplierRepository.findAll();
        assertThat(suppliers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSuppliers() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the suppliers
        restSupplierMockMvc.perform(get("/api/suppliers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(supplier.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].webUrl").value(hasItem(DEFAULT_WEB_URL.toString())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
                .andExpect(jsonPath("$.[*].isOnHold").value(hasItem(DEFAULT_IS_ON_HOLD.booleanValue())))
                .andExpect(jsonPath("$.[*].isHeadOffice").value(hasItem(DEFAULT_IS_HEAD_OFFICE.booleanValue())))
                .andExpect(jsonPath("$.[*].leadTime").value(hasItem(DEFAULT_LEAD_TIME)))
                .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].settlementTerms").value(hasItem(DEFAULT_SETTLEMENT_TERMS)))
                .andExpect(jsonPath("$.[*].credit").value(hasItem(DEFAULT_CREDIT.doubleValue())))
                .andExpect(jsonPath("$.[*].terms").value(hasItem(DEFAULT_TERMS)))
                .andExpect(jsonPath("$.[*].ageingMethod").value(hasItem(DEFAULT_AGEING_METHOD.toString())))
                .andExpect(jsonPath("$.[*].isEFTPaymentsUsed").value(hasItem(DEFAULT_IS_EFTPAYMENTS_USED.booleanValue())))
                .andExpect(jsonPath("$.[*].bankBSB").value(hasItem(DEFAULT_BANK_BSB.toString())))
                .andExpect(jsonPath("$.[*].bankNumber").value(hasItem(DEFAULT_BANK_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT.toString())));
    }

    @Test
    @Transactional
    public void getSupplier() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get the supplier
        restSupplierMockMvc.perform(get("/api/suppliers/{id}", supplier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(supplier.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.webUrl").value(DEFAULT_WEB_URL.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.isOnHold").value(DEFAULT_IS_ON_HOLD.booleanValue()))
            .andExpect(jsonPath("$.isHeadOffice").value(DEFAULT_IS_HEAD_OFFICE.booleanValue()))
            .andExpect(jsonPath("$.leadTime").value(DEFAULT_LEAD_TIME))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.settlementTerms").value(DEFAULT_SETTLEMENT_TERMS))
            .andExpect(jsonPath("$.credit").value(DEFAULT_CREDIT.doubleValue()))
            .andExpect(jsonPath("$.terms").value(DEFAULT_TERMS))
            .andExpect(jsonPath("$.ageingMethod").value(DEFAULT_AGEING_METHOD.toString()))
            .andExpect(jsonPath("$.isEFTPaymentsUsed").value(DEFAULT_IS_EFTPAYMENTS_USED.booleanValue()))
            .andExpect(jsonPath("$.bankBSB").value(DEFAULT_BANK_BSB.toString()))
            .andExpect(jsonPath("$.bankNumber").value(DEFAULT_BANK_NUMBER.toString()))
            .andExpect(jsonPath("$.bankAccount").value(DEFAULT_BANK_ACCOUNT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSupplier() throws Exception {
        // Get the supplier
        restSupplierMockMvc.perform(get("/api/suppliers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplier() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

		int databaseSizeBeforeUpdate = supplierRepository.findAll().size();

        // Update the supplier
        supplier.setCode(UPDATED_CODE);
        supplier.setName(UPDATED_NAME);
        supplier.setWebUrl(UPDATED_WEB_URL);
        supplier.setComment(UPDATED_COMMENT);
        supplier.setIsOnHold(UPDATED_IS_ON_HOLD);
        supplier.setIsHeadOffice(UPDATED_IS_HEAD_OFFICE);
        supplier.setLeadTime(UPDATED_LEAD_TIME);
        supplier.setAccountType(UPDATED_ACCOUNT_TYPE);
        supplier.setSettlementTerms(UPDATED_SETTLEMENT_TERMS);
        supplier.setCredit(UPDATED_CREDIT);
        supplier.setTerms(UPDATED_TERMS);
        supplier.setAgeingMethod(UPDATED_AGEING_METHOD);
        supplier.setIsEFTPaymentsUsed(UPDATED_IS_EFTPAYMENTS_USED);
        supplier.setBankBSB(UPDATED_BANK_BSB);
        supplier.setBankNumber(UPDATED_BANK_NUMBER);
        supplier.setBankAccount(UPDATED_BANK_ACCOUNT);
        SupplierDTO supplierDTO = supplierMapper.supplierToSupplierDTO(supplier);

        restSupplierMockMvc.perform(put("/api/suppliers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(supplierDTO)))
                .andExpect(status().isOk());

        // Validate the Supplier in the database
        List<Supplier> suppliers = supplierRepository.findAll();
        assertThat(suppliers).hasSize(databaseSizeBeforeUpdate);
        Supplier testSupplier = suppliers.get(suppliers.size() - 1);
        assertThat(testSupplier.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSupplier.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSupplier.getWebUrl()).isEqualTo(UPDATED_WEB_URL);
        assertThat(testSupplier.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testSupplier.getIsOnHold()).isEqualTo(UPDATED_IS_ON_HOLD);
        assertThat(testSupplier.getIsHeadOffice()).isEqualTo(UPDATED_IS_HEAD_OFFICE);
        assertThat(testSupplier.getLeadTime()).isEqualTo(UPDATED_LEAD_TIME);
        assertThat(testSupplier.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testSupplier.getSettlementTerms()).isEqualTo(UPDATED_SETTLEMENT_TERMS);
        assertThat(testSupplier.getCredit()).isEqualTo(UPDATED_CREDIT);
        assertThat(testSupplier.getTerms()).isEqualTo(UPDATED_TERMS);
        assertThat(testSupplier.getAgeingMethod()).isEqualTo(UPDATED_AGEING_METHOD);
        assertThat(testSupplier.getIsEFTPaymentsUsed()).isEqualTo(UPDATED_IS_EFTPAYMENTS_USED);
        assertThat(testSupplier.getBankBSB()).isEqualTo(UPDATED_BANK_BSB);
        assertThat(testSupplier.getBankNumber()).isEqualTo(UPDATED_BANK_NUMBER);
        assertThat(testSupplier.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void deleteSupplier() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

		int databaseSizeBeforeDelete = supplierRepository.findAll().size();

        // Get the supplier
        restSupplierMockMvc.perform(delete("/api/suppliers/{id}", supplier.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Supplier> suppliers = supplierRepository.findAll();
        assertThat(suppliers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
