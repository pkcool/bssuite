package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.SalesOrderLineItem;
import com.enginemobi.bssuite.repository.SalesOrderLineItemRepository;
import com.enginemobi.bssuite.repository.search.SalesOrderLineItemSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SalesOrderLineItemResource REST controller.
 *
 * @see SalesOrderLineItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SalesOrderLineItemResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final BigDecimal DEFAULT_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SOLD_FOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_SOLD_FOR = new BigDecimal(2);

    private static final Double DEFAULT_QTY_ORDERED = 1D;
    private static final Double UPDATED_QTY_ORDERED = 2D;

    private static final Double DEFAULT_QTY_ALLOCATED = 1D;
    private static final Double UPDATED_QTY_ALLOCATED = 2D;

    private static final BigDecimal DEFAULT_TOTAL_TAX_CHARGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_TAX_CHARGE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DISCOUNT_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT_PERCENTAGE = new BigDecimal(2);

    private static final Integer DEFAULT_LINE_NO = 1;
    private static final Integer UPDATED_LINE_NO = 2;

    private static final LocalDate DEFAULT_REQUIRED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REQUIRED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_LIST_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LIST_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LIST_PRICE_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_LIST_PRICE_DISCOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_COST2 = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST2 = new BigDecimal(2);

    private static final Boolean DEFAULT_IS_HIDDEN = false;
    private static final Boolean UPDATED_IS_HIDDEN = true;
    private static final String DEFAULT_REF1 = "AAAAA";
    private static final String UPDATED_REF1 = "BBBBB";
    private static final String DEFAULT_REF2 = "AAAAA";
    private static final String UPDATED_REF2 = "BBBBB";

    @Inject
    private SalesOrderLineItemRepository salesOrderLineItemRepository;

    @Inject
    private SalesOrderLineItemSearchRepository salesOrderLineItemSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSalesOrderLineItemMockMvc;

    private SalesOrderLineItem salesOrderLineItem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SalesOrderLineItemResource salesOrderLineItemResource = new SalesOrderLineItemResource();
        ReflectionTestUtils.setField(salesOrderLineItemResource, "salesOrderLineItemSearchRepository", salesOrderLineItemSearchRepository);
        ReflectionTestUtils.setField(salesOrderLineItemResource, "salesOrderLineItemRepository", salesOrderLineItemRepository);
        this.restSalesOrderLineItemMockMvc = MockMvcBuilders.standaloneSetup(salesOrderLineItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        salesOrderLineItem = new SalesOrderLineItem();
        salesOrderLineItem.setDescription(DEFAULT_DESCRIPTION);
        salesOrderLineItem.setCost(DEFAULT_COST);
        salesOrderLineItem.setSoldFor(DEFAULT_SOLD_FOR);
        salesOrderLineItem.setQtyOrdered(DEFAULT_QTY_ORDERED);
        salesOrderLineItem.setQtyAllocated(DEFAULT_QTY_ALLOCATED);
        salesOrderLineItem.setTotalTaxCharge(DEFAULT_TOTAL_TAX_CHARGE);
        salesOrderLineItem.setDiscountPercentage(DEFAULT_DISCOUNT_PERCENTAGE);
        salesOrderLineItem.setLineNo(DEFAULT_LINE_NO);
        salesOrderLineItem.setRequiredDate(DEFAULT_REQUIRED_DATE);
        salesOrderLineItem.setListPrice(DEFAULT_LIST_PRICE);
        salesOrderLineItem.setListPriceDiscount(DEFAULT_LIST_PRICE_DISCOUNT);
        salesOrderLineItem.setCost2(DEFAULT_COST2);
        salesOrderLineItem.setIsHidden(DEFAULT_IS_HIDDEN);
        salesOrderLineItem.setRef1(DEFAULT_REF1);
        salesOrderLineItem.setRef2(DEFAULT_REF2);
    }

    @Test
    @Transactional
    public void createSalesOrderLineItem() throws Exception {
        int databaseSizeBeforeCreate = salesOrderLineItemRepository.findAll().size();

        // Create the SalesOrderLineItem

        restSalesOrderLineItemMockMvc.perform(post("/api/salesOrderLineItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(salesOrderLineItem)))
                .andExpect(status().isCreated());

        // Validate the SalesOrderLineItem in the database
        List<SalesOrderLineItem> salesOrderLineItems = salesOrderLineItemRepository.findAll();
        assertThat(salesOrderLineItems).hasSize(databaseSizeBeforeCreate + 1);
        SalesOrderLineItem testSalesOrderLineItem = salesOrderLineItems.get(salesOrderLineItems.size() - 1);
        assertThat(testSalesOrderLineItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSalesOrderLineItem.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testSalesOrderLineItem.getSoldFor()).isEqualTo(DEFAULT_SOLD_FOR);
        assertThat(testSalesOrderLineItem.getQtyOrdered()).isEqualTo(DEFAULT_QTY_ORDERED);
        assertThat(testSalesOrderLineItem.getQtyAllocated()).isEqualTo(DEFAULT_QTY_ALLOCATED);
        assertThat(testSalesOrderLineItem.getTotalTaxCharge()).isEqualTo(DEFAULT_TOTAL_TAX_CHARGE);
        assertThat(testSalesOrderLineItem.getDiscountPercentage()).isEqualTo(DEFAULT_DISCOUNT_PERCENTAGE);
        assertThat(testSalesOrderLineItem.getLineNo()).isEqualTo(DEFAULT_LINE_NO);
        assertThat(testSalesOrderLineItem.getRequiredDate()).isEqualTo(DEFAULT_REQUIRED_DATE);
        assertThat(testSalesOrderLineItem.getListPrice()).isEqualTo(DEFAULT_LIST_PRICE);
        assertThat(testSalesOrderLineItem.getListPriceDiscount()).isEqualTo(DEFAULT_LIST_PRICE_DISCOUNT);
        assertThat(testSalesOrderLineItem.getCost2()).isEqualTo(DEFAULT_COST2);
        assertThat(testSalesOrderLineItem.getIsHidden()).isEqualTo(DEFAULT_IS_HIDDEN);
        assertThat(testSalesOrderLineItem.getRef1()).isEqualTo(DEFAULT_REF1);
        assertThat(testSalesOrderLineItem.getRef2()).isEqualTo(DEFAULT_REF2);
    }

    @Test
    @Transactional
    public void getAllSalesOrderLineItems() throws Exception {
        // Initialize the database
        salesOrderLineItemRepository.saveAndFlush(salesOrderLineItem);

        // Get all the salesOrderLineItems
        restSalesOrderLineItemMockMvc.perform(get("/api/salesOrderLineItems?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(salesOrderLineItem.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.intValue())))
                .andExpect(jsonPath("$.[*].soldFor").value(hasItem(DEFAULT_SOLD_FOR.intValue())))
                .andExpect(jsonPath("$.[*].qtyOrdered").value(hasItem(DEFAULT_QTY_ORDERED.doubleValue())))
                .andExpect(jsonPath("$.[*].qtyAllocated").value(hasItem(DEFAULT_QTY_ALLOCATED.doubleValue())))
                .andExpect(jsonPath("$.[*].totalTaxCharge").value(hasItem(DEFAULT_TOTAL_TAX_CHARGE.intValue())))
                .andExpect(jsonPath("$.[*].discountPercentage").value(hasItem(DEFAULT_DISCOUNT_PERCENTAGE.intValue())))
                .andExpect(jsonPath("$.[*].lineNo").value(hasItem(DEFAULT_LINE_NO)))
                .andExpect(jsonPath("$.[*].requiredDate").value(hasItem(DEFAULT_REQUIRED_DATE.toString())))
                .andExpect(jsonPath("$.[*].listPrice").value(hasItem(DEFAULT_LIST_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].listPriceDiscount").value(hasItem(DEFAULT_LIST_PRICE_DISCOUNT.intValue())))
                .andExpect(jsonPath("$.[*].cost2").value(hasItem(DEFAULT_COST2.intValue())))
                .andExpect(jsonPath("$.[*].isHidden").value(hasItem(DEFAULT_IS_HIDDEN.booleanValue())))
                .andExpect(jsonPath("$.[*].Ref1").value(hasItem(DEFAULT_REF1.toString())))
                .andExpect(jsonPath("$.[*].Ref2").value(hasItem(DEFAULT_REF2.toString())));
    }

    @Test
    @Transactional
    public void getSalesOrderLineItem() throws Exception {
        // Initialize the database
        salesOrderLineItemRepository.saveAndFlush(salesOrderLineItem);

        // Get the salesOrderLineItem
        restSalesOrderLineItemMockMvc.perform(get("/api/salesOrderLineItems/{id}", salesOrderLineItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(salesOrderLineItem.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.intValue()))
            .andExpect(jsonPath("$.soldFor").value(DEFAULT_SOLD_FOR.intValue()))
            .andExpect(jsonPath("$.qtyOrdered").value(DEFAULT_QTY_ORDERED.doubleValue()))
            .andExpect(jsonPath("$.qtyAllocated").value(DEFAULT_QTY_ALLOCATED.doubleValue()))
            .andExpect(jsonPath("$.totalTaxCharge").value(DEFAULT_TOTAL_TAX_CHARGE.intValue()))
            .andExpect(jsonPath("$.discountPercentage").value(DEFAULT_DISCOUNT_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.lineNo").value(DEFAULT_LINE_NO))
            .andExpect(jsonPath("$.requiredDate").value(DEFAULT_REQUIRED_DATE.toString()))
            .andExpect(jsonPath("$.listPrice").value(DEFAULT_LIST_PRICE.intValue()))
            .andExpect(jsonPath("$.listPriceDiscount").value(DEFAULT_LIST_PRICE_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.cost2").value(DEFAULT_COST2.intValue()))
            .andExpect(jsonPath("$.isHidden").value(DEFAULT_IS_HIDDEN.booleanValue()))
            .andExpect(jsonPath("$.Ref1").value(DEFAULT_REF1.toString()))
            .andExpect(jsonPath("$.Ref2").value(DEFAULT_REF2.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSalesOrderLineItem() throws Exception {
        // Get the salesOrderLineItem
        restSalesOrderLineItemMockMvc.perform(get("/api/salesOrderLineItems/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalesOrderLineItem() throws Exception {
        // Initialize the database
        salesOrderLineItemRepository.saveAndFlush(salesOrderLineItem);

		int databaseSizeBeforeUpdate = salesOrderLineItemRepository.findAll().size();

        // Update the salesOrderLineItem
        salesOrderLineItem.setDescription(UPDATED_DESCRIPTION);
        salesOrderLineItem.setCost(UPDATED_COST);
        salesOrderLineItem.setSoldFor(UPDATED_SOLD_FOR);
        salesOrderLineItem.setQtyOrdered(UPDATED_QTY_ORDERED);
        salesOrderLineItem.setQtyAllocated(UPDATED_QTY_ALLOCATED);
        salesOrderLineItem.setTotalTaxCharge(UPDATED_TOTAL_TAX_CHARGE);
        salesOrderLineItem.setDiscountPercentage(UPDATED_DISCOUNT_PERCENTAGE);
        salesOrderLineItem.setLineNo(UPDATED_LINE_NO);
        salesOrderLineItem.setRequiredDate(UPDATED_REQUIRED_DATE);
        salesOrderLineItem.setListPrice(UPDATED_LIST_PRICE);
        salesOrderLineItem.setListPriceDiscount(UPDATED_LIST_PRICE_DISCOUNT);
        salesOrderLineItem.setCost2(UPDATED_COST2);
        salesOrderLineItem.setIsHidden(UPDATED_IS_HIDDEN);
        salesOrderLineItem.setRef1(UPDATED_REF1);
        salesOrderLineItem.setRef2(UPDATED_REF2);

        restSalesOrderLineItemMockMvc.perform(put("/api/salesOrderLineItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(salesOrderLineItem)))
                .andExpect(status().isOk());

        // Validate the SalesOrderLineItem in the database
        List<SalesOrderLineItem> salesOrderLineItems = salesOrderLineItemRepository.findAll();
        assertThat(salesOrderLineItems).hasSize(databaseSizeBeforeUpdate);
        SalesOrderLineItem testSalesOrderLineItem = salesOrderLineItems.get(salesOrderLineItems.size() - 1);
        assertThat(testSalesOrderLineItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSalesOrderLineItem.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testSalesOrderLineItem.getSoldFor()).isEqualTo(UPDATED_SOLD_FOR);
        assertThat(testSalesOrderLineItem.getQtyOrdered()).isEqualTo(UPDATED_QTY_ORDERED);
        assertThat(testSalesOrderLineItem.getQtyAllocated()).isEqualTo(UPDATED_QTY_ALLOCATED);
        assertThat(testSalesOrderLineItem.getTotalTaxCharge()).isEqualTo(UPDATED_TOTAL_TAX_CHARGE);
        assertThat(testSalesOrderLineItem.getDiscountPercentage()).isEqualTo(UPDATED_DISCOUNT_PERCENTAGE);
        assertThat(testSalesOrderLineItem.getLineNo()).isEqualTo(UPDATED_LINE_NO);
        assertThat(testSalesOrderLineItem.getRequiredDate()).isEqualTo(UPDATED_REQUIRED_DATE);
        assertThat(testSalesOrderLineItem.getListPrice()).isEqualTo(UPDATED_LIST_PRICE);
        assertThat(testSalesOrderLineItem.getListPriceDiscount()).isEqualTo(UPDATED_LIST_PRICE_DISCOUNT);
        assertThat(testSalesOrderLineItem.getCost2()).isEqualTo(UPDATED_COST2);
        assertThat(testSalesOrderLineItem.getIsHidden()).isEqualTo(UPDATED_IS_HIDDEN);
        assertThat(testSalesOrderLineItem.getRef1()).isEqualTo(UPDATED_REF1);
        assertThat(testSalesOrderLineItem.getRef2()).isEqualTo(UPDATED_REF2);
    }

    @Test
    @Transactional
    public void deleteSalesOrderLineItem() throws Exception {
        // Initialize the database
        salesOrderLineItemRepository.saveAndFlush(salesOrderLineItem);

		int databaseSizeBeforeDelete = salesOrderLineItemRepository.findAll().size();

        // Get the salesOrderLineItem
        restSalesOrderLineItemMockMvc.perform(delete("/api/salesOrderLineItems/{id}", salesOrderLineItem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SalesOrderLineItem> salesOrderLineItems = salesOrderLineItemRepository.findAll();
        assertThat(salesOrderLineItems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
