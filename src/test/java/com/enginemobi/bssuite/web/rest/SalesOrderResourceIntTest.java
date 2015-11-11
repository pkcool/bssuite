package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.SalesOrder;
import com.enginemobi.bssuite.repository.SalesOrderRepository;
import com.enginemobi.bssuite.repository.search.SalesOrderSearchRepository;
import com.enginemobi.bssuite.web.rest.dto.SalesOrderDTO;
import com.enginemobi.bssuite.web.rest.mapper.SalesOrderMapper;

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

import com.enginemobi.bssuite.domain.enumeration.SalesOrderStatus;

/**
 * Test class for the SalesOrderResource REST controller.
 *
 * @see SalesOrderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SalesOrderResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_ORDER_NO = "AAAAA";
    private static final String UPDATED_ORDER_NO = "BBBBB";


private static final SalesOrderStatus DEFAULT_STATUS = SalesOrderStatus.ONORDER;
    private static final SalesOrderStatus UPDATED_STATUS = SalesOrderStatus.DELIVERED;

    private static final ZonedDateTime DEFAULT_TXN_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_TXN_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_TXN_DATE_STR = dateTimeFormatter.format(DEFAULT_TXN_DATE);

    private static final LocalDate DEFAULT_FORWARD_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FORWARD_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_REQUIRED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REQUIRED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CUSTOMER_ORDER_NO = "AAAAA";
    private static final String UPDATED_CUSTOMER_ORDER_NO = "BBBBB";
    private static final String DEFAULT_OUR_REF = "AAAAA";
    private static final String UPDATED_OUR_REF = "BBBBB";

    private static final BigDecimal DEFAULT_FREIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_FREIGHT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_HANDLING_CHARGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_HANDLING_CHARGE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CHARGE2 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CHARGE2 = new BigDecimal(2);

    private static final Boolean DEFAULT_IS_TAXABLE = false;
    private static final Boolean UPDATED_IS_TAXABLE = true;

    private static final Boolean DEFAULT_IS_LOCKED = false;
    private static final Boolean UPDATED_IS_LOCKED = true;

    private static final BigDecimal DEFAULT_ADJUST_TAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_ADJUST_TAX = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ADJUST_TAX_EXEMPT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ADJUST_TAX_EXEMPT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PREPAYMENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PREPAYMENT = new BigDecimal(2);
    private static final String DEFAULT_PREPAYMENT_NO = "AAAAA";
    private static final String UPDATED_PREPAYMENT_NO = "BBBBB";
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    private static final BigDecimal DEFAULT_TOTAL_TAX_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_TAX_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_SELL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_SELL_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_COST = new BigDecimal(2);

    private static final Boolean DEFAULT_IS_SUSPENDED = false;
    private static final Boolean UPDATED_IS_SUSPENDED = true;

    @Inject
    private SalesOrderRepository salesOrderRepository;

    @Inject
    private SalesOrderMapper salesOrderMapper;

    @Inject
    private SalesOrderSearchRepository salesOrderSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSalesOrderMockMvc;

    private SalesOrder salesOrder;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SalesOrderResource salesOrderResource = new SalesOrderResource();
        ReflectionTestUtils.setField(salesOrderResource, "salesOrderRepository", salesOrderRepository);
        ReflectionTestUtils.setField(salesOrderResource, "salesOrderMapper", salesOrderMapper);
        ReflectionTestUtils.setField(salesOrderResource, "salesOrderSearchRepository", salesOrderSearchRepository);
        this.restSalesOrderMockMvc = MockMvcBuilders.standaloneSetup(salesOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        salesOrder = new SalesOrder();
        salesOrder.setOrderNo(DEFAULT_ORDER_NO);
        salesOrder.setStatus(DEFAULT_STATUS);
        salesOrder.setTxnDate(DEFAULT_TXN_DATE);
        salesOrder.setForwardDate(DEFAULT_FORWARD_DATE);
        salesOrder.setRequiredDate(DEFAULT_REQUIRED_DATE);
        salesOrder.setCustomerOrderNo(DEFAULT_CUSTOMER_ORDER_NO);
        salesOrder.setOurRef(DEFAULT_OUR_REF);
        salesOrder.setFreight(DEFAULT_FREIGHT);
        salesOrder.setHandlingCharge(DEFAULT_HANDLING_CHARGE);
        salesOrder.setCharge2(DEFAULT_CHARGE2);
        salesOrder.setIsTaxable(DEFAULT_IS_TAXABLE);
        salesOrder.setIsLocked(DEFAULT_IS_LOCKED);
        salesOrder.setAdjustTax(DEFAULT_ADJUST_TAX);
        salesOrder.setAdjustTaxExempt(DEFAULT_ADJUST_TAX_EXEMPT);
        salesOrder.setPrepayment(DEFAULT_PREPAYMENT);
        salesOrder.setPrepaymentNo(DEFAULT_PREPAYMENT_NO);
        salesOrder.setComment(DEFAULT_COMMENT);
        salesOrder.setTotalTaxAmount(DEFAULT_TOTAL_TAX_AMOUNT);
        salesOrder.setTotalSellPrice(DEFAULT_TOTAL_SELL_PRICE);
        salesOrder.setTotalCost(DEFAULT_TOTAL_COST);
        salesOrder.setIsSuspended(DEFAULT_IS_SUSPENDED);
    }

    @Test
    @Transactional
    public void createSalesOrder() throws Exception {
        int databaseSizeBeforeCreate = salesOrderRepository.findAll().size();

        // Create the SalesOrder
        SalesOrderDTO salesOrderDTO = salesOrderMapper.salesOrderToSalesOrderDTO(salesOrder);

        restSalesOrderMockMvc.perform(post("/api/salesOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(salesOrderDTO)))
                .andExpect(status().isCreated());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrders = salesOrderRepository.findAll();
        assertThat(salesOrders).hasSize(databaseSizeBeforeCreate + 1);
        SalesOrder testSalesOrder = salesOrders.get(salesOrders.size() - 1);
        assertThat(testSalesOrder.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testSalesOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSalesOrder.getTxnDate()).isEqualTo(DEFAULT_TXN_DATE);
        assertThat(testSalesOrder.getForwardDate()).isEqualTo(DEFAULT_FORWARD_DATE);
        assertThat(testSalesOrder.getRequiredDate()).isEqualTo(DEFAULT_REQUIRED_DATE);
        assertThat(testSalesOrder.getCustomerOrderNo()).isEqualTo(DEFAULT_CUSTOMER_ORDER_NO);
        assertThat(testSalesOrder.getOurRef()).isEqualTo(DEFAULT_OUR_REF);
        assertThat(testSalesOrder.getFreight()).isEqualTo(DEFAULT_FREIGHT);
        assertThat(testSalesOrder.getHandlingCharge()).isEqualTo(DEFAULT_HANDLING_CHARGE);
        assertThat(testSalesOrder.getCharge2()).isEqualTo(DEFAULT_CHARGE2);
        assertThat(testSalesOrder.getIsTaxable()).isEqualTo(DEFAULT_IS_TAXABLE);
        assertThat(testSalesOrder.getIsLocked()).isEqualTo(DEFAULT_IS_LOCKED);
        assertThat(testSalesOrder.getAdjustTax()).isEqualTo(DEFAULT_ADJUST_TAX);
        assertThat(testSalesOrder.getAdjustTaxExempt()).isEqualTo(DEFAULT_ADJUST_TAX_EXEMPT);
        assertThat(testSalesOrder.getPrepayment()).isEqualTo(DEFAULT_PREPAYMENT);
        assertThat(testSalesOrder.getPrepaymentNo()).isEqualTo(DEFAULT_PREPAYMENT_NO);
        assertThat(testSalesOrder.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testSalesOrder.getTotalTaxAmount()).isEqualTo(DEFAULT_TOTAL_TAX_AMOUNT);
        assertThat(testSalesOrder.getTotalSellPrice()).isEqualTo(DEFAULT_TOTAL_SELL_PRICE);
        assertThat(testSalesOrder.getTotalCost()).isEqualTo(DEFAULT_TOTAL_COST);
        assertThat(testSalesOrder.getIsSuspended()).isEqualTo(DEFAULT_IS_SUSPENDED);
    }

    @Test
    @Transactional
    public void getAllSalesOrders() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        // Get all the salesOrders
        restSalesOrderMockMvc.perform(get("/api/salesOrders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(salesOrder.getId().intValue())))
                .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].txnDate").value(hasItem(DEFAULT_TXN_DATE_STR)))
                .andExpect(jsonPath("$.[*].forwardDate").value(hasItem(DEFAULT_FORWARD_DATE.toString())))
                .andExpect(jsonPath("$.[*].requiredDate").value(hasItem(DEFAULT_REQUIRED_DATE.toString())))
                .andExpect(jsonPath("$.[*].customerOrderNo").value(hasItem(DEFAULT_CUSTOMER_ORDER_NO.toString())))
                .andExpect(jsonPath("$.[*].ourRef").value(hasItem(DEFAULT_OUR_REF.toString())))
                .andExpect(jsonPath("$.[*].freight").value(hasItem(DEFAULT_FREIGHT.intValue())))
                .andExpect(jsonPath("$.[*].handlingCharge").value(hasItem(DEFAULT_HANDLING_CHARGE.intValue())))
                .andExpect(jsonPath("$.[*].charge2").value(hasItem(DEFAULT_CHARGE2.intValue())))
                .andExpect(jsonPath("$.[*].isTaxable").value(hasItem(DEFAULT_IS_TAXABLE.booleanValue())))
                .andExpect(jsonPath("$.[*].isLocked").value(hasItem(DEFAULT_IS_LOCKED.booleanValue())))
                .andExpect(jsonPath("$.[*].adjustTax").value(hasItem(DEFAULT_ADJUST_TAX.intValue())))
                .andExpect(jsonPath("$.[*].adjustTaxExempt").value(hasItem(DEFAULT_ADJUST_TAX_EXEMPT.intValue())))
                .andExpect(jsonPath("$.[*].prepayment").value(hasItem(DEFAULT_PREPAYMENT.intValue())))
                .andExpect(jsonPath("$.[*].prepaymentNo").value(hasItem(DEFAULT_PREPAYMENT_NO.toString())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
                .andExpect(jsonPath("$.[*].totalTaxAmount").value(hasItem(DEFAULT_TOTAL_TAX_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].totalSellPrice").value(hasItem(DEFAULT_TOTAL_SELL_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].totalCost").value(hasItem(DEFAULT_TOTAL_COST.intValue())))
                .andExpect(jsonPath("$.[*].isSuspended").value(hasItem(DEFAULT_IS_SUSPENDED.booleanValue())));
    }

    @Test
    @Transactional
    public void getSalesOrder() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        // Get the salesOrder
        restSalesOrderMockMvc.perform(get("/api/salesOrders/{id}", salesOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(salesOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.txnDate").value(DEFAULT_TXN_DATE_STR))
            .andExpect(jsonPath("$.forwardDate").value(DEFAULT_FORWARD_DATE.toString()))
            .andExpect(jsonPath("$.requiredDate").value(DEFAULT_REQUIRED_DATE.toString()))
            .andExpect(jsonPath("$.customerOrderNo").value(DEFAULT_CUSTOMER_ORDER_NO.toString()))
            .andExpect(jsonPath("$.ourRef").value(DEFAULT_OUR_REF.toString()))
            .andExpect(jsonPath("$.freight").value(DEFAULT_FREIGHT.intValue()))
            .andExpect(jsonPath("$.handlingCharge").value(DEFAULT_HANDLING_CHARGE.intValue()))
            .andExpect(jsonPath("$.charge2").value(DEFAULT_CHARGE2.intValue()))
            .andExpect(jsonPath("$.isTaxable").value(DEFAULT_IS_TAXABLE.booleanValue()))
            .andExpect(jsonPath("$.isLocked").value(DEFAULT_IS_LOCKED.booleanValue()))
            .andExpect(jsonPath("$.adjustTax").value(DEFAULT_ADJUST_TAX.intValue()))
            .andExpect(jsonPath("$.adjustTaxExempt").value(DEFAULT_ADJUST_TAX_EXEMPT.intValue()))
            .andExpect(jsonPath("$.prepayment").value(DEFAULT_PREPAYMENT.intValue()))
            .andExpect(jsonPath("$.prepaymentNo").value(DEFAULT_PREPAYMENT_NO.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.totalTaxAmount").value(DEFAULT_TOTAL_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.totalSellPrice").value(DEFAULT_TOTAL_SELL_PRICE.intValue()))
            .andExpect(jsonPath("$.totalCost").value(DEFAULT_TOTAL_COST.intValue()))
            .andExpect(jsonPath("$.isSuspended").value(DEFAULT_IS_SUSPENDED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSalesOrder() throws Exception {
        // Get the salesOrder
        restSalesOrderMockMvc.perform(get("/api/salesOrders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalesOrder() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

		int databaseSizeBeforeUpdate = salesOrderRepository.findAll().size();

        // Update the salesOrder
        salesOrder.setOrderNo(UPDATED_ORDER_NO);
        salesOrder.setStatus(UPDATED_STATUS);
        salesOrder.setTxnDate(UPDATED_TXN_DATE);
        salesOrder.setForwardDate(UPDATED_FORWARD_DATE);
        salesOrder.setRequiredDate(UPDATED_REQUIRED_DATE);
        salesOrder.setCustomerOrderNo(UPDATED_CUSTOMER_ORDER_NO);
        salesOrder.setOurRef(UPDATED_OUR_REF);
        salesOrder.setFreight(UPDATED_FREIGHT);
        salesOrder.setHandlingCharge(UPDATED_HANDLING_CHARGE);
        salesOrder.setCharge2(UPDATED_CHARGE2);
        salesOrder.setIsTaxable(UPDATED_IS_TAXABLE);
        salesOrder.setIsLocked(UPDATED_IS_LOCKED);
        salesOrder.setAdjustTax(UPDATED_ADJUST_TAX);
        salesOrder.setAdjustTaxExempt(UPDATED_ADJUST_TAX_EXEMPT);
        salesOrder.setPrepayment(UPDATED_PREPAYMENT);
        salesOrder.setPrepaymentNo(UPDATED_PREPAYMENT_NO);
        salesOrder.setComment(UPDATED_COMMENT);
        salesOrder.setTotalTaxAmount(UPDATED_TOTAL_TAX_AMOUNT);
        salesOrder.setTotalSellPrice(UPDATED_TOTAL_SELL_PRICE);
        salesOrder.setTotalCost(UPDATED_TOTAL_COST);
        salesOrder.setIsSuspended(UPDATED_IS_SUSPENDED);
        SalesOrderDTO salesOrderDTO = salesOrderMapper.salesOrderToSalesOrderDTO(salesOrder);

        restSalesOrderMockMvc.perform(put("/api/salesOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(salesOrderDTO)))
                .andExpect(status().isOk());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrders = salesOrderRepository.findAll();
        assertThat(salesOrders).hasSize(databaseSizeBeforeUpdate);
        SalesOrder testSalesOrder = salesOrders.get(salesOrders.size() - 1);
        assertThat(testSalesOrder.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testSalesOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSalesOrder.getTxnDate()).isEqualTo(UPDATED_TXN_DATE);
        assertThat(testSalesOrder.getForwardDate()).isEqualTo(UPDATED_FORWARD_DATE);
        assertThat(testSalesOrder.getRequiredDate()).isEqualTo(UPDATED_REQUIRED_DATE);
        assertThat(testSalesOrder.getCustomerOrderNo()).isEqualTo(UPDATED_CUSTOMER_ORDER_NO);
        assertThat(testSalesOrder.getOurRef()).isEqualTo(UPDATED_OUR_REF);
        assertThat(testSalesOrder.getFreight()).isEqualTo(UPDATED_FREIGHT);
        assertThat(testSalesOrder.getHandlingCharge()).isEqualTo(UPDATED_HANDLING_CHARGE);
        assertThat(testSalesOrder.getCharge2()).isEqualTo(UPDATED_CHARGE2);
        assertThat(testSalesOrder.getIsTaxable()).isEqualTo(UPDATED_IS_TAXABLE);
        assertThat(testSalesOrder.getIsLocked()).isEqualTo(UPDATED_IS_LOCKED);
        assertThat(testSalesOrder.getAdjustTax()).isEqualTo(UPDATED_ADJUST_TAX);
        assertThat(testSalesOrder.getAdjustTaxExempt()).isEqualTo(UPDATED_ADJUST_TAX_EXEMPT);
        assertThat(testSalesOrder.getPrepayment()).isEqualTo(UPDATED_PREPAYMENT);
        assertThat(testSalesOrder.getPrepaymentNo()).isEqualTo(UPDATED_PREPAYMENT_NO);
        assertThat(testSalesOrder.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testSalesOrder.getTotalTaxAmount()).isEqualTo(UPDATED_TOTAL_TAX_AMOUNT);
        assertThat(testSalesOrder.getTotalSellPrice()).isEqualTo(UPDATED_TOTAL_SELL_PRICE);
        assertThat(testSalesOrder.getTotalCost()).isEqualTo(UPDATED_TOTAL_COST);
        assertThat(testSalesOrder.getIsSuspended()).isEqualTo(UPDATED_IS_SUSPENDED);
    }

    @Test
    @Transactional
    public void deleteSalesOrder() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

		int databaseSizeBeforeDelete = salesOrderRepository.findAll().size();

        // Get the salesOrder
        restSalesOrderMockMvc.perform(delete("/api/salesOrders/{id}", salesOrder.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SalesOrder> salesOrders = salesOrderRepository.findAll();
        assertThat(salesOrders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
