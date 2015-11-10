package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.ProductActivityAudit;
import com.enginemobi.bssuite.repository.ProductActivityAuditRepository;
import com.enginemobi.bssuite.repository.search.ProductActivityAuditSearchRepository;
import com.enginemobi.bssuite.web.rest.dto.ProductActivityAuditDTO;
import com.enginemobi.bssuite.web.rest.mapper.ProductActivityAuditMapper;

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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.enginemobi.bssuite.domain.enumeration.ProductActivityType;

/**
 * Test class for the ProductActivityAuditResource REST controller.
 *
 * @see ProductActivityAuditResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProductActivityAuditResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final DateTime DEFAULT_CREATED_ON = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_CREATED_ON = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_CREATED_ON_STR = dateTimeFormatter.print(DEFAULT_CREATED_ON);
    private static final String DEFAULT_TXN_NUMBER = "AAAAA";
    private static final String UPDATED_TXN_NUMBER = "BBBBB";


private static final ProductActivityType DEFAULT_ACTIVITY_TYPE = ProductActivityType.PURCHASEORDER;
    private static final ProductActivityType UPDATED_ACTIVITY_TYPE = ProductActivityType.SALESORDER;

    private static final Double DEFAULT_QTY_TXN = 1D;
    private static final Double UPDATED_QTY_TXN = 2D;

    private static final Double DEFAULT_QTY_ADJUSTED = 1D;
    private static final Double UPDATED_QTY_ADJUSTED = 2D;

    private static final Double DEFAULT_QTY_STOCK_ON_HOLD = 1D;
    private static final Double UPDATED_QTY_STOCK_ON_HOLD = 2D;
    private static final String DEFAULT_LINE_NO = "AAAAA";
    private static final String UPDATED_LINE_NO = "BBBBB";
    private static final String DEFAULT_TXN_ACCOUNT_CODE = "AAAAA";
    private static final String UPDATED_TXN_ACCOUNT_CODE = "BBBBB";

    @Inject
    private ProductActivityAuditRepository productActivityAuditRepository;

    @Inject
    private ProductActivityAuditMapper productActivityAuditMapper;

    @Inject
    private ProductActivityAuditSearchRepository productActivityAuditSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProductActivityAuditMockMvc;

    private ProductActivityAudit productActivityAudit;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductActivityAuditResource productActivityAuditResource = new ProductActivityAuditResource();
        ReflectionTestUtils.setField(productActivityAuditResource, "productActivityAuditRepository", productActivityAuditRepository);
        ReflectionTestUtils.setField(productActivityAuditResource, "productActivityAuditMapper", productActivityAuditMapper);
        ReflectionTestUtils.setField(productActivityAuditResource, "productActivityAuditSearchRepository", productActivityAuditSearchRepository);
        this.restProductActivityAuditMockMvc = MockMvcBuilders.standaloneSetup(productActivityAuditResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        productActivityAudit = new ProductActivityAudit();
        productActivityAudit.setCreatedOn(DEFAULT_CREATED_ON);
        productActivityAudit.setTxnNumber(DEFAULT_TXN_NUMBER);
        productActivityAudit.setActivityType(DEFAULT_ACTIVITY_TYPE);
        productActivityAudit.setQtyTxn(DEFAULT_QTY_TXN);
        productActivityAudit.setQtyAdjusted(DEFAULT_QTY_ADJUSTED);
        productActivityAudit.setQtyStockOnHold(DEFAULT_QTY_STOCK_ON_HOLD);
        productActivityAudit.setLineNo(DEFAULT_LINE_NO);
        productActivityAudit.setTxnAccountCode(DEFAULT_TXN_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    public void createProductActivityAudit() throws Exception {
        int databaseSizeBeforeCreate = productActivityAuditRepository.findAll().size();

        // Create the ProductActivityAudit
        ProductActivityAuditDTO productActivityAuditDTO = productActivityAuditMapper.productActivityAuditToProductActivityAuditDTO(productActivityAudit);

        restProductActivityAuditMockMvc.perform(post("/api/productActivityAudits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productActivityAuditDTO)))
                .andExpect(status().isCreated());

        // Validate the ProductActivityAudit in the database
        List<ProductActivityAudit> productActivityAudits = productActivityAuditRepository.findAll();
        assertThat(productActivityAudits).hasSize(databaseSizeBeforeCreate + 1);
        ProductActivityAudit testProductActivityAudit = productActivityAudits.get(productActivityAudits.size() - 1);
        assertThat(testProductActivityAudit.getCreatedOn().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testProductActivityAudit.getTxnNumber()).isEqualTo(DEFAULT_TXN_NUMBER);
        assertThat(testProductActivityAudit.getActivityType()).isEqualTo(DEFAULT_ACTIVITY_TYPE);
        assertThat(testProductActivityAudit.getQtyTxn()).isEqualTo(DEFAULT_QTY_TXN);
        assertThat(testProductActivityAudit.getQtyAdjusted()).isEqualTo(DEFAULT_QTY_ADJUSTED);
        assertThat(testProductActivityAudit.getQtyStockOnHold()).isEqualTo(DEFAULT_QTY_STOCK_ON_HOLD);
        assertThat(testProductActivityAudit.getLineNo()).isEqualTo(DEFAULT_LINE_NO);
        assertThat(testProductActivityAudit.getTxnAccountCode()).isEqualTo(DEFAULT_TXN_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    public void getAllProductActivityAudits() throws Exception {
        // Initialize the database
        productActivityAuditRepository.saveAndFlush(productActivityAudit);

        // Get all the productActivityAudits
        restProductActivityAuditMockMvc.perform(get("/api/productActivityAudits"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(productActivityAudit.getId().intValue())))
                .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON_STR)))
                .andExpect(jsonPath("$.[*].txnNumber").value(hasItem(DEFAULT_TXN_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].activityType").value(hasItem(DEFAULT_ACTIVITY_TYPE.toString())))
                .andExpect(jsonPath("$.[*].qtyTxn").value(hasItem(DEFAULT_QTY_TXN.doubleValue())))
                .andExpect(jsonPath("$.[*].qtyAdjusted").value(hasItem(DEFAULT_QTY_ADJUSTED.doubleValue())))
                .andExpect(jsonPath("$.[*].qtyStockOnHold").value(hasItem(DEFAULT_QTY_STOCK_ON_HOLD.doubleValue())))
                .andExpect(jsonPath("$.[*].lineNo").value(hasItem(DEFAULT_LINE_NO.toString())))
                .andExpect(jsonPath("$.[*].txnAccountCode").value(hasItem(DEFAULT_TXN_ACCOUNT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getProductActivityAudit() throws Exception {
        // Initialize the database
        productActivityAuditRepository.saveAndFlush(productActivityAudit);

        // Get the productActivityAudit
        restProductActivityAuditMockMvc.perform(get("/api/productActivityAudits/{id}", productActivityAudit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(productActivityAudit.getId().intValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON_STR))
            .andExpect(jsonPath("$.txnNumber").value(DEFAULT_TXN_NUMBER.toString()))
            .andExpect(jsonPath("$.activityType").value(DEFAULT_ACTIVITY_TYPE.toString()))
            .andExpect(jsonPath("$.qtyTxn").value(DEFAULT_QTY_TXN.doubleValue()))
            .andExpect(jsonPath("$.qtyAdjusted").value(DEFAULT_QTY_ADJUSTED.doubleValue()))
            .andExpect(jsonPath("$.qtyStockOnHold").value(DEFAULT_QTY_STOCK_ON_HOLD.doubleValue()))
            .andExpect(jsonPath("$.lineNo").value(DEFAULT_LINE_NO.toString()))
            .andExpect(jsonPath("$.txnAccountCode").value(DEFAULT_TXN_ACCOUNT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductActivityAudit() throws Exception {
        // Get the productActivityAudit
        restProductActivityAuditMockMvc.perform(get("/api/productActivityAudits/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductActivityAudit() throws Exception {
        // Initialize the database
        productActivityAuditRepository.saveAndFlush(productActivityAudit);

		int databaseSizeBeforeUpdate = productActivityAuditRepository.findAll().size();

        // Update the productActivityAudit
        productActivityAudit.setCreatedOn(UPDATED_CREATED_ON);
        productActivityAudit.setTxnNumber(UPDATED_TXN_NUMBER);
        productActivityAudit.setActivityType(UPDATED_ACTIVITY_TYPE);
        productActivityAudit.setQtyTxn(UPDATED_QTY_TXN);
        productActivityAudit.setQtyAdjusted(UPDATED_QTY_ADJUSTED);
        productActivityAudit.setQtyStockOnHold(UPDATED_QTY_STOCK_ON_HOLD);
        productActivityAudit.setLineNo(UPDATED_LINE_NO);
        productActivityAudit.setTxnAccountCode(UPDATED_TXN_ACCOUNT_CODE);
        ProductActivityAuditDTO productActivityAuditDTO = productActivityAuditMapper.productActivityAuditToProductActivityAuditDTO(productActivityAudit);

        restProductActivityAuditMockMvc.perform(put("/api/productActivityAudits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productActivityAuditDTO)))
                .andExpect(status().isOk());

        // Validate the ProductActivityAudit in the database
        List<ProductActivityAudit> productActivityAudits = productActivityAuditRepository.findAll();
        assertThat(productActivityAudits).hasSize(databaseSizeBeforeUpdate);
        ProductActivityAudit testProductActivityAudit = productActivityAudits.get(productActivityAudits.size() - 1);
        assertThat(testProductActivityAudit.getCreatedOn().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testProductActivityAudit.getTxnNumber()).isEqualTo(UPDATED_TXN_NUMBER);
        assertThat(testProductActivityAudit.getActivityType()).isEqualTo(UPDATED_ACTIVITY_TYPE);
        assertThat(testProductActivityAudit.getQtyTxn()).isEqualTo(UPDATED_QTY_TXN);
        assertThat(testProductActivityAudit.getQtyAdjusted()).isEqualTo(UPDATED_QTY_ADJUSTED);
        assertThat(testProductActivityAudit.getQtyStockOnHold()).isEqualTo(UPDATED_QTY_STOCK_ON_HOLD);
        assertThat(testProductActivityAudit.getLineNo()).isEqualTo(UPDATED_LINE_NO);
        assertThat(testProductActivityAudit.getTxnAccountCode()).isEqualTo(UPDATED_TXN_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    public void deleteProductActivityAudit() throws Exception {
        // Initialize the database
        productActivityAuditRepository.saveAndFlush(productActivityAudit);

		int databaseSizeBeforeDelete = productActivityAuditRepository.findAll().size();

        // Get the productActivityAudit
        restProductActivityAuditMockMvc.perform(delete("/api/productActivityAudits/{id}", productActivityAudit.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductActivityAudit> productActivityAudits = productActivityAuditRepository.findAll();
        assertThat(productActivityAudits).hasSize(databaseSizeBeforeDelete - 1);
    }
}
