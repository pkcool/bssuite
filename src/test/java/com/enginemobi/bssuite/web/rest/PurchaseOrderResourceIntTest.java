package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.PurchaseOrder;
import com.enginemobi.bssuite.repository.PurchaseOrderRepository;
import com.enginemobi.bssuite.repository.search.PurchaseOrderSearchRepository;
import com.enginemobi.bssuite.web.rest.dto.PurchaseOrderDTO;
import com.enginemobi.bssuite.web.rest.mapper.PurchaseOrderMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.enginemobi.bssuite.domain.enumeration.PurchaseOrderStatus;

/**
 * Test class for the PurchaseOrderResource REST controller.
 *
 * @see PurchaseOrderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PurchaseOrderResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_ORDER_NO = "AAAAA";
    private static final String UPDATED_ORDER_NO = "BBBBB";


    private static final PurchaseOrderStatus DEFAULT_STATUS = PurchaseOrderStatus.ONORDER;
    private static final PurchaseOrderStatus UPDATED_STATUS = PurchaseOrderStatus.DELIVERED;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATED_DATE);
    private static final String DEFAULT_REF = "AAAAA";
    private static final String UPDATED_REF = "BBBBB";

    private static final LocalDate DEFAULT_EXPECTED_DELIVERY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPECTED_DELIVERY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_TAXABLE = false;
    private static final Boolean UPDATED_IS_TAXABLE = true;

    private static final Boolean DEFAULT_IS_LOCKED = false;
    private static final Boolean UPDATED_IS_LOCKED = true;
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    private static final BigDecimal DEFAULT_TOTAL_TAX_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_TAX_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_COST = new BigDecimal(2);
    private static final String DEFAULT_TAX_EXEMPTION_CODE = "AAAAA";
    private static final String UPDATED_TAX_EXEMPTION_CODE = "BBBBB";

    private static final Boolean DEFAULT_IS_SUSPENDED = false;
    private static final Boolean UPDATED_IS_SUSPENDED = true;

    @Inject
    private PurchaseOrderRepository purchaseOrderRepository;

    @Inject
    private PurchaseOrderMapper purchaseOrderMapper;

    @Inject
    private PurchaseOrderSearchRepository purchaseOrderSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPurchaseOrderMockMvc;

    private PurchaseOrder purchaseOrder;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PurchaseOrderResource purchaseOrderResource = new PurchaseOrderResource();
        ReflectionTestUtils.setField(purchaseOrderResource, "purchaseOrderSearchRepository", purchaseOrderSearchRepository);
        ReflectionTestUtils.setField(purchaseOrderResource, "purchaseOrderRepository", purchaseOrderRepository);
        ReflectionTestUtils.setField(purchaseOrderResource, "purchaseOrderMapper", purchaseOrderMapper);
        this.restPurchaseOrderMockMvc = MockMvcBuilders.standaloneSetup(purchaseOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderNo(DEFAULT_ORDER_NO);
        purchaseOrder.setStatus(DEFAULT_STATUS);
        purchaseOrder.setCreatedDate(DEFAULT_CREATED_DATE);
        purchaseOrder.setRef(DEFAULT_REF);
        purchaseOrder.setExpectedDeliveryDate(DEFAULT_EXPECTED_DELIVERY_DATE);
        purchaseOrder.setIsTaxable(DEFAULT_IS_TAXABLE);
        purchaseOrder.setIsLocked(DEFAULT_IS_LOCKED);
        purchaseOrder.setComment(DEFAULT_COMMENT);
        purchaseOrder.setTotalTaxAmount(DEFAULT_TOTAL_TAX_AMOUNT);
        purchaseOrder.setTotalCost(DEFAULT_TOTAL_COST);
        purchaseOrder.setTaxExemptionCode(DEFAULT_TAX_EXEMPTION_CODE);
        purchaseOrder.setIsSuspended(DEFAULT_IS_SUSPENDED);
    }

    @Test
    @Transactional
    public void createPurchaseOrder() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderRepository.findAll().size();

        // Create the PurchaseOrder
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.purchaseOrderToPurchaseOrderDTO(purchaseOrder);

        restPurchaseOrderMockMvc.perform(post("/api/purchaseOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO)))
                .andExpect(status().isCreated());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        assertThat(purchaseOrders).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseOrder testPurchaseOrder = purchaseOrders.get(purchaseOrders.size() - 1);
        assertThat(testPurchaseOrder.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testPurchaseOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPurchaseOrder.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPurchaseOrder.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testPurchaseOrder.getExpectedDeliveryDate()).isEqualTo(DEFAULT_EXPECTED_DELIVERY_DATE);
        assertThat(testPurchaseOrder.getIsTaxable()).isEqualTo(DEFAULT_IS_TAXABLE);
        assertThat(testPurchaseOrder.getIsLocked()).isEqualTo(DEFAULT_IS_LOCKED);
        assertThat(testPurchaseOrder.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testPurchaseOrder.getTotalTaxAmount()).isEqualTo(DEFAULT_TOTAL_TAX_AMOUNT);
        assertThat(testPurchaseOrder.getTotalCost()).isEqualTo(DEFAULT_TOTAL_COST);
        assertThat(testPurchaseOrder.getTaxExemptionCode()).isEqualTo(DEFAULT_TAX_EXEMPTION_CODE);
        assertThat(testPurchaseOrder.getIsSuspended()).isEqualTo(DEFAULT_IS_SUSPENDED);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrders
        restPurchaseOrderMockMvc.perform(get("/api/purchaseOrders?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrder.getId().intValue())))
                .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE_STR)))
                .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF.toString())))
                .andExpect(jsonPath("$.[*].expectedDeliveryDate").value(hasItem(DEFAULT_EXPECTED_DELIVERY_DATE.toString())))
                .andExpect(jsonPath("$.[*].isTaxable").value(hasItem(DEFAULT_IS_TAXABLE.booleanValue())))
                .andExpect(jsonPath("$.[*].isLocked").value(hasItem(DEFAULT_IS_LOCKED.booleanValue())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
                .andExpect(jsonPath("$.[*].totalTaxAmount").value(hasItem(DEFAULT_TOTAL_TAX_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].totalCost").value(hasItem(DEFAULT_TOTAL_COST.intValue())))
                .andExpect(jsonPath("$.[*].taxExemptionCode").value(hasItem(DEFAULT_TAX_EXEMPTION_CODE.toString())))
                .andExpect(jsonPath("$.[*].isSuspended").value(hasItem(DEFAULT_IS_SUSPENDED.booleanValue())));
    }

    @Test
    @Transactional
    public void getPurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get the purchaseOrder
        restPurchaseOrderMockMvc.perform(get("/api/purchaseOrders/{id}", purchaseOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(purchaseOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE_STR))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF.toString()))
            .andExpect(jsonPath("$.expectedDeliveryDate").value(DEFAULT_EXPECTED_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.isTaxable").value(DEFAULT_IS_TAXABLE.booleanValue()))
            .andExpect(jsonPath("$.isLocked").value(DEFAULT_IS_LOCKED.booleanValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.totalTaxAmount").value(DEFAULT_TOTAL_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.totalCost").value(DEFAULT_TOTAL_COST.intValue()))
            .andExpect(jsonPath("$.taxExemptionCode").value(DEFAULT_TAX_EXEMPTION_CODE.toString()))
            .andExpect(jsonPath("$.isSuspended").value(DEFAULT_IS_SUSPENDED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPurchaseOrder() throws Exception {
        // Get the purchaseOrder
        restPurchaseOrderMockMvc.perform(get("/api/purchaseOrders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

		int databaseSizeBeforeUpdate = purchaseOrderRepository.findAll().size();

        // Update the purchaseOrder
        purchaseOrder.setOrderNo(UPDATED_ORDER_NO);
        purchaseOrder.setStatus(UPDATED_STATUS);
        purchaseOrder.setCreatedDate(UPDATED_CREATED_DATE);
        purchaseOrder.setRef(UPDATED_REF);
        purchaseOrder.setExpectedDeliveryDate(UPDATED_EXPECTED_DELIVERY_DATE);
        purchaseOrder.setIsTaxable(UPDATED_IS_TAXABLE);
        purchaseOrder.setIsLocked(UPDATED_IS_LOCKED);
        purchaseOrder.setComment(UPDATED_COMMENT);
        purchaseOrder.setTotalTaxAmount(UPDATED_TOTAL_TAX_AMOUNT);
        purchaseOrder.setTotalCost(UPDATED_TOTAL_COST);
        purchaseOrder.setTaxExemptionCode(UPDATED_TAX_EXEMPTION_CODE);
        purchaseOrder.setIsSuspended(UPDATED_IS_SUSPENDED);
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.purchaseOrderToPurchaseOrderDTO(purchaseOrder);

        restPurchaseOrderMockMvc.perform(put("/api/purchaseOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO)))
                .andExpect(status().isOk());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        assertThat(purchaseOrders).hasSize(databaseSizeBeforeUpdate);
        PurchaseOrder testPurchaseOrder = purchaseOrders.get(purchaseOrders.size() - 1);
        assertThat(testPurchaseOrder.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testPurchaseOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPurchaseOrder.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPurchaseOrder.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testPurchaseOrder.getExpectedDeliveryDate()).isEqualTo(UPDATED_EXPECTED_DELIVERY_DATE);
        assertThat(testPurchaseOrder.getIsTaxable()).isEqualTo(UPDATED_IS_TAXABLE);
        assertThat(testPurchaseOrder.getIsLocked()).isEqualTo(UPDATED_IS_LOCKED);
        assertThat(testPurchaseOrder.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testPurchaseOrder.getTotalTaxAmount()).isEqualTo(UPDATED_TOTAL_TAX_AMOUNT);
        assertThat(testPurchaseOrder.getTotalCost()).isEqualTo(UPDATED_TOTAL_COST);
        assertThat(testPurchaseOrder.getTaxExemptionCode()).isEqualTo(UPDATED_TAX_EXEMPTION_CODE);
        assertThat(testPurchaseOrder.getIsSuspended()).isEqualTo(UPDATED_IS_SUSPENDED);
    }

    @Test
    @Transactional
    public void deletePurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

		int databaseSizeBeforeDelete = purchaseOrderRepository.findAll().size();

        // Get the purchaseOrder
        restPurchaseOrderMockMvc.perform(delete("/api/purchaseOrders/{id}", purchaseOrder.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        assertThat(purchaseOrders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
