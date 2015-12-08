package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.PurchaseOrderLineItem;
import com.enginemobi.bssuite.repository.PurchaseOrderLineItemRepository;
import com.enginemobi.bssuite.repository.search.PurchaseOrderLineItemSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PurchaseOrderLineItemResource REST controller.
 *
 * @see PurchaseOrderLineItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PurchaseOrderLineItemResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final BigDecimal DEFAULT_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LIST_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LIST_PRICE = new BigDecimal(2);

    private static final Double DEFAULT_QTY_ORDERED = 1D;
    private static final Double UPDATED_QTY_ORDERED = 2D;

    private static final Double DEFAULT_QTY_DELIVERED = 1D;
    private static final Double UPDATED_QTY_DELIVERED = 2D;

    private static final Double DEFAULT_QTY_PREVIOUSLY_DELIVERED = 1D;
    private static final Double UPDATED_QTY_PREVIOUSLY_DELIVERED = 2D;

    private static final Double DEFAULT_QTY_IMPORTED = 1D;
    private static final Double UPDATED_QTY_IMPORTED = 2D;

    private static final BigDecimal DEFAULT_TOTAL_TAX_CHARGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_TAX_CHARGE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DISCOUNT_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT_PERCENTAGE = new BigDecimal(2);

    private static final Integer DEFAULT_LINE_NO = 1;
    private static final Integer UPDATED_LINE_NO = 2;

    private static final BigDecimal DEFAULT_LIST_PRICE_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_LIST_PRICE_DISCOUNT = new BigDecimal(2);
    private static final String DEFAULT_UNIT_MEASURE = "AAAAA";
    private static final String UPDATED_UNIT_MEASURE = "BBBBB";

    private static final Boolean DEFAULT_IS_HIDDEN = false;
    private static final Boolean UPDATED_IS_HIDDEN = true;
    private static final String DEFAULT_REF1 = "AAAAA";
    private static final String UPDATED_REF1 = "BBBBB";
    private static final String DEFAULT_REF2 = "AAAAA";
    private static final String UPDATED_REF2 = "BBBBB";

    @Inject
    private PurchaseOrderLineItemRepository purchaseOrderLineItemRepository;

    @Inject
    private PurchaseOrderLineItemSearchRepository purchaseOrderLineItemSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPurchaseOrderLineItemMockMvc;

    private PurchaseOrderLineItem purchaseOrderLineItem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PurchaseOrderLineItemResource purchaseOrderLineItemResource = new PurchaseOrderLineItemResource();
        ReflectionTestUtils.setField(purchaseOrderLineItemResource, "purchaseOrderLineItemSearchRepository", purchaseOrderLineItemSearchRepository);
        ReflectionTestUtils.setField(purchaseOrderLineItemResource, "purchaseOrderLineItemRepository", purchaseOrderLineItemRepository);
        this.restPurchaseOrderLineItemMockMvc = MockMvcBuilders.standaloneSetup(purchaseOrderLineItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        purchaseOrderLineItem = new PurchaseOrderLineItem();
        purchaseOrderLineItem.setDescription(DEFAULT_DESCRIPTION);
        purchaseOrderLineItem.setCost(DEFAULT_COST);
        purchaseOrderLineItem.setListPrice(DEFAULT_LIST_PRICE);
        purchaseOrderLineItem.setQtyOrdered(DEFAULT_QTY_ORDERED);
        purchaseOrderLineItem.setQtyDelivered(DEFAULT_QTY_DELIVERED);
        purchaseOrderLineItem.setQtyPreviouslyDelivered(DEFAULT_QTY_PREVIOUSLY_DELIVERED);
        purchaseOrderLineItem.setQtyImported(DEFAULT_QTY_IMPORTED);
        purchaseOrderLineItem.setTotalTaxCharge(DEFAULT_TOTAL_TAX_CHARGE);
        purchaseOrderLineItem.setDiscountPercentage(DEFAULT_DISCOUNT_PERCENTAGE);
        purchaseOrderLineItem.setLineNo(DEFAULT_LINE_NO);
        purchaseOrderLineItem.setListPriceDiscount(DEFAULT_LIST_PRICE_DISCOUNT);
        purchaseOrderLineItem.setUnitMeasure(DEFAULT_UNIT_MEASURE);
        purchaseOrderLineItem.setIsHidden(DEFAULT_IS_HIDDEN);
        purchaseOrderLineItem.setRef1(DEFAULT_REF1);
        purchaseOrderLineItem.setRef2(DEFAULT_REF2);
    }

    @Test
    @Transactional
    public void createPurchaseOrderLineItem() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderLineItemRepository.findAll().size();

        // Create the PurchaseOrderLineItem

        restPurchaseOrderLineItemMockMvc.perform(post("/api/purchaseOrderLineItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(purchaseOrderLineItem)))
                .andExpect(status().isCreated());

        // Validate the PurchaseOrderLineItem in the database
        List<PurchaseOrderLineItem> purchaseOrderLineItems = purchaseOrderLineItemRepository.findAll();
        assertThat(purchaseOrderLineItems).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseOrderLineItem testPurchaseOrderLineItem = purchaseOrderLineItems.get(purchaseOrderLineItems.size() - 1);
        assertThat(testPurchaseOrderLineItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPurchaseOrderLineItem.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testPurchaseOrderLineItem.getListPrice()).isEqualTo(DEFAULT_LIST_PRICE);
        assertThat(testPurchaseOrderLineItem.getQtyOrdered()).isEqualTo(DEFAULT_QTY_ORDERED);
        assertThat(testPurchaseOrderLineItem.getQtyDelivered()).isEqualTo(DEFAULT_QTY_DELIVERED);
        assertThat(testPurchaseOrderLineItem.getQtyPreviouslyDelivered()).isEqualTo(DEFAULT_QTY_PREVIOUSLY_DELIVERED);
        assertThat(testPurchaseOrderLineItem.getQtyImported()).isEqualTo(DEFAULT_QTY_IMPORTED);
        assertThat(testPurchaseOrderLineItem.getTotalTaxCharge()).isEqualTo(DEFAULT_TOTAL_TAX_CHARGE);
        assertThat(testPurchaseOrderLineItem.getDiscountPercentage()).isEqualTo(DEFAULT_DISCOUNT_PERCENTAGE);
        assertThat(testPurchaseOrderLineItem.getLineNo()).isEqualTo(DEFAULT_LINE_NO);
        assertThat(testPurchaseOrderLineItem.getListPriceDiscount()).isEqualTo(DEFAULT_LIST_PRICE_DISCOUNT);
        assertThat(testPurchaseOrderLineItem.getUnitMeasure()).isEqualTo(DEFAULT_UNIT_MEASURE);
        assertThat(testPurchaseOrderLineItem.getIsHidden()).isEqualTo(DEFAULT_IS_HIDDEN);
        assertThat(testPurchaseOrderLineItem.getRef1()).isEqualTo(DEFAULT_REF1);
        assertThat(testPurchaseOrderLineItem.getRef2()).isEqualTo(DEFAULT_REF2);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLineItems() throws Exception {
        // Initialize the database
        purchaseOrderLineItemRepository.saveAndFlush(purchaseOrderLineItem);

        // Get all the purchaseOrderLineItems
        restPurchaseOrderLineItemMockMvc.perform(get("/api/purchaseOrderLineItems?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrderLineItem.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.intValue())))
                .andExpect(jsonPath("$.[*].listPrice").value(hasItem(DEFAULT_LIST_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].qtyOrdered").value(hasItem(DEFAULT_QTY_ORDERED.doubleValue())))
                .andExpect(jsonPath("$.[*].qtyDelivered").value(hasItem(DEFAULT_QTY_DELIVERED.doubleValue())))
                .andExpect(jsonPath("$.[*].qtyPreviouslyDelivered").value(hasItem(DEFAULT_QTY_PREVIOUSLY_DELIVERED.doubleValue())))
                .andExpect(jsonPath("$.[*].qtyImported").value(hasItem(DEFAULT_QTY_IMPORTED.doubleValue())))
                .andExpect(jsonPath("$.[*].totalTaxCharge").value(hasItem(DEFAULT_TOTAL_TAX_CHARGE.intValue())))
                .andExpect(jsonPath("$.[*].discountPercentage").value(hasItem(DEFAULT_DISCOUNT_PERCENTAGE.intValue())))
                .andExpect(jsonPath("$.[*].lineNo").value(hasItem(DEFAULT_LINE_NO)))
                .andExpect(jsonPath("$.[*].listPriceDiscount").value(hasItem(DEFAULT_LIST_PRICE_DISCOUNT.intValue())))
                .andExpect(jsonPath("$.[*].unitMeasure").value(hasItem(DEFAULT_UNIT_MEASURE.toString())))
                .andExpect(jsonPath("$.[*].isHidden").value(hasItem(DEFAULT_IS_HIDDEN.booleanValue())))
                .andExpect(jsonPath("$.[*].Ref1").value(hasItem(DEFAULT_REF1.toString())))
                .andExpect(jsonPath("$.[*].Ref2").value(hasItem(DEFAULT_REF2.toString())));
    }

    @Test
    @Transactional
    public void getPurchaseOrderLineItem() throws Exception {
        // Initialize the database
        purchaseOrderLineItemRepository.saveAndFlush(purchaseOrderLineItem);

        // Get the purchaseOrderLineItem
        restPurchaseOrderLineItemMockMvc.perform(get("/api/purchaseOrderLineItems/{id}", purchaseOrderLineItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(purchaseOrderLineItem.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.intValue()))
            .andExpect(jsonPath("$.listPrice").value(DEFAULT_LIST_PRICE.intValue()))
            .andExpect(jsonPath("$.qtyOrdered").value(DEFAULT_QTY_ORDERED.doubleValue()))
            .andExpect(jsonPath("$.qtyDelivered").value(DEFAULT_QTY_DELIVERED.doubleValue()))
            .andExpect(jsonPath("$.qtyPreviouslyDelivered").value(DEFAULT_QTY_PREVIOUSLY_DELIVERED.doubleValue()))
            .andExpect(jsonPath("$.qtyImported").value(DEFAULT_QTY_IMPORTED.doubleValue()))
            .andExpect(jsonPath("$.totalTaxCharge").value(DEFAULT_TOTAL_TAX_CHARGE.intValue()))
            .andExpect(jsonPath("$.discountPercentage").value(DEFAULT_DISCOUNT_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.lineNo").value(DEFAULT_LINE_NO))
            .andExpect(jsonPath("$.listPriceDiscount").value(DEFAULT_LIST_PRICE_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.unitMeasure").value(DEFAULT_UNIT_MEASURE.toString()))
            .andExpect(jsonPath("$.isHidden").value(DEFAULT_IS_HIDDEN.booleanValue()))
            .andExpect(jsonPath("$.Ref1").value(DEFAULT_REF1.toString()))
            .andExpect(jsonPath("$.Ref2").value(DEFAULT_REF2.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPurchaseOrderLineItem() throws Exception {
        // Get the purchaseOrderLineItem
        restPurchaseOrderLineItemMockMvc.perform(get("/api/purchaseOrderLineItems/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseOrderLineItem() throws Exception {
        // Initialize the database
        purchaseOrderLineItemRepository.saveAndFlush(purchaseOrderLineItem);

		int databaseSizeBeforeUpdate = purchaseOrderLineItemRepository.findAll().size();

        // Update the purchaseOrderLineItem
        purchaseOrderLineItem.setDescription(UPDATED_DESCRIPTION);
        purchaseOrderLineItem.setCost(UPDATED_COST);
        purchaseOrderLineItem.setListPrice(UPDATED_LIST_PRICE);
        purchaseOrderLineItem.setQtyOrdered(UPDATED_QTY_ORDERED);
        purchaseOrderLineItem.setQtyDelivered(UPDATED_QTY_DELIVERED);
        purchaseOrderLineItem.setQtyPreviouslyDelivered(UPDATED_QTY_PREVIOUSLY_DELIVERED);
        purchaseOrderLineItem.setQtyImported(UPDATED_QTY_IMPORTED);
        purchaseOrderLineItem.setTotalTaxCharge(UPDATED_TOTAL_TAX_CHARGE);
        purchaseOrderLineItem.setDiscountPercentage(UPDATED_DISCOUNT_PERCENTAGE);
        purchaseOrderLineItem.setLineNo(UPDATED_LINE_NO);
        purchaseOrderLineItem.setListPriceDiscount(UPDATED_LIST_PRICE_DISCOUNT);
        purchaseOrderLineItem.setUnitMeasure(UPDATED_UNIT_MEASURE);
        purchaseOrderLineItem.setIsHidden(UPDATED_IS_HIDDEN);
        purchaseOrderLineItem.setRef1(UPDATED_REF1);
        purchaseOrderLineItem.setRef2(UPDATED_REF2);

        restPurchaseOrderLineItemMockMvc.perform(put("/api/purchaseOrderLineItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(purchaseOrderLineItem)))
                .andExpect(status().isOk());

        // Validate the PurchaseOrderLineItem in the database
        List<PurchaseOrderLineItem> purchaseOrderLineItems = purchaseOrderLineItemRepository.findAll();
        assertThat(purchaseOrderLineItems).hasSize(databaseSizeBeforeUpdate);
        PurchaseOrderLineItem testPurchaseOrderLineItem = purchaseOrderLineItems.get(purchaseOrderLineItems.size() - 1);
        assertThat(testPurchaseOrderLineItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPurchaseOrderLineItem.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testPurchaseOrderLineItem.getListPrice()).isEqualTo(UPDATED_LIST_PRICE);
        assertThat(testPurchaseOrderLineItem.getQtyOrdered()).isEqualTo(UPDATED_QTY_ORDERED);
        assertThat(testPurchaseOrderLineItem.getQtyDelivered()).isEqualTo(UPDATED_QTY_DELIVERED);
        assertThat(testPurchaseOrderLineItem.getQtyPreviouslyDelivered()).isEqualTo(UPDATED_QTY_PREVIOUSLY_DELIVERED);
        assertThat(testPurchaseOrderLineItem.getQtyImported()).isEqualTo(UPDATED_QTY_IMPORTED);
        assertThat(testPurchaseOrderLineItem.getTotalTaxCharge()).isEqualTo(UPDATED_TOTAL_TAX_CHARGE);
        assertThat(testPurchaseOrderLineItem.getDiscountPercentage()).isEqualTo(UPDATED_DISCOUNT_PERCENTAGE);
        assertThat(testPurchaseOrderLineItem.getLineNo()).isEqualTo(UPDATED_LINE_NO);
        assertThat(testPurchaseOrderLineItem.getListPriceDiscount()).isEqualTo(UPDATED_LIST_PRICE_DISCOUNT);
        assertThat(testPurchaseOrderLineItem.getUnitMeasure()).isEqualTo(UPDATED_UNIT_MEASURE);
        assertThat(testPurchaseOrderLineItem.getIsHidden()).isEqualTo(UPDATED_IS_HIDDEN);
        assertThat(testPurchaseOrderLineItem.getRef1()).isEqualTo(UPDATED_REF1);
        assertThat(testPurchaseOrderLineItem.getRef2()).isEqualTo(UPDATED_REF2);
    }

    @Test
    @Transactional
    public void deletePurchaseOrderLineItem() throws Exception {
        // Initialize the database
        purchaseOrderLineItemRepository.saveAndFlush(purchaseOrderLineItem);

		int databaseSizeBeforeDelete = purchaseOrderLineItemRepository.findAll().size();

        // Get the purchaseOrderLineItem
        restPurchaseOrderLineItemMockMvc.perform(delete("/api/purchaseOrderLineItems/{id}", purchaseOrderLineItem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PurchaseOrderLineItem> purchaseOrderLineItems = purchaseOrderLineItemRepository.findAll();
        assertThat(purchaseOrderLineItems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
