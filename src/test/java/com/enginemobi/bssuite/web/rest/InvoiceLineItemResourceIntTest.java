package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.InvoiceLineItem;
import com.enginemobi.bssuite.repository.InvoiceLineItemRepository;
import com.enginemobi.bssuite.repository.search.InvoiceLineItemSearchRepository;

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
 * Test class for the InvoiceLineItemResource REST controller.
 *
 * @see InvoiceLineItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InvoiceLineItemResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final BigDecimal DEFAULT_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SOLD_FOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_SOLD_FOR = new BigDecimal(2);

    private static final Double DEFAULT_QTY_ORDERED = 1D;
    private static final Double UPDATED_QTY_ORDERED = 2D;

    private static final Double DEFAULT_QTY_SOLD = 1D;
    private static final Double UPDATED_QTY_SOLD = 2D;

    private static final Double DEFAULT_QTY_RETURNED = 1D;
    private static final Double UPDATED_QTY_RETURNED = 2D;

    private static final Double DEFAULT_QTY_PICKED = 1D;
    private static final Double UPDATED_QTY_PICKED = 2D;

    private static final BigDecimal DEFAULT_TOTAL_TAX_CHARGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_TAX_CHARGE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DISCOUNT_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT_PERCENTAGE = new BigDecimal(2);
    private static final String DEFAULT_DISCOUNT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DISCOUNT_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_DISCOUNT_PRICE_GROUP_CODE = "AAAAA";
    private static final String UPDATED_DISCOUNT_PRICE_GROUP_CODE = "BBBBB";

    private static final Integer DEFAULT_LINE_NO = 1;
    private static final Integer UPDATED_LINE_NO = 2;

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
    private InvoiceLineItemRepository invoiceLineItemRepository;

    @Inject
    private InvoiceLineItemSearchRepository invoiceLineItemSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInvoiceLineItemMockMvc;

    private InvoiceLineItem invoiceLineItem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InvoiceLineItemResource invoiceLineItemResource = new InvoiceLineItemResource();
        ReflectionTestUtils.setField(invoiceLineItemResource, "invoiceLineItemRepository", invoiceLineItemRepository);
        ReflectionTestUtils.setField(invoiceLineItemResource, "invoiceLineItemSearchRepository", invoiceLineItemSearchRepository);
        this.restInvoiceLineItemMockMvc = MockMvcBuilders.standaloneSetup(invoiceLineItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        invoiceLineItem = new InvoiceLineItem();
        invoiceLineItem.setDescription(DEFAULT_DESCRIPTION);
        invoiceLineItem.setCost(DEFAULT_COST);
        invoiceLineItem.setSoldFor(DEFAULT_SOLD_FOR);
        invoiceLineItem.setQtyOrdered(DEFAULT_QTY_ORDERED);
        invoiceLineItem.setQtySold(DEFAULT_QTY_SOLD);
        invoiceLineItem.setQtyReturned(DEFAULT_QTY_RETURNED);
        invoiceLineItem.setQtyPicked(DEFAULT_QTY_PICKED);
        invoiceLineItem.setTotalTaxCharge(DEFAULT_TOTAL_TAX_CHARGE);
        invoiceLineItem.setDiscountPercentage(DEFAULT_DISCOUNT_PERCENTAGE);
        invoiceLineItem.setDiscountDescription(DEFAULT_DISCOUNT_DESCRIPTION);
        invoiceLineItem.setDiscountPriceGroupCode(DEFAULT_DISCOUNT_PRICE_GROUP_CODE);
        invoiceLineItem.setLineNo(DEFAULT_LINE_NO);
        invoiceLineItem.setListPrice(DEFAULT_LIST_PRICE);
        invoiceLineItem.setListPriceDiscount(DEFAULT_LIST_PRICE_DISCOUNT);
        invoiceLineItem.setCost2(DEFAULT_COST2);
        invoiceLineItem.setIsHidden(DEFAULT_IS_HIDDEN);
        invoiceLineItem.setRef1(DEFAULT_REF1);
        invoiceLineItem.setRef2(DEFAULT_REF2);
    }

    @Test
    @Transactional
    public void createInvoiceLineItem() throws Exception {
        int databaseSizeBeforeCreate = invoiceLineItemRepository.findAll().size();

        // Create the InvoiceLineItem

        restInvoiceLineItemMockMvc.perform(post("/api/invoiceLineItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invoiceLineItem)))
                .andExpect(status().isCreated());

        // Validate the InvoiceLineItem in the database
        List<InvoiceLineItem> invoiceLineItems = invoiceLineItemRepository.findAll();
        assertThat(invoiceLineItems).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceLineItem testInvoiceLineItem = invoiceLineItems.get(invoiceLineItems.size() - 1);
        assertThat(testInvoiceLineItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInvoiceLineItem.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testInvoiceLineItem.getSoldFor()).isEqualTo(DEFAULT_SOLD_FOR);
        assertThat(testInvoiceLineItem.getQtyOrdered()).isEqualTo(DEFAULT_QTY_ORDERED);
        assertThat(testInvoiceLineItem.getQtySold()).isEqualTo(DEFAULT_QTY_SOLD);
        assertThat(testInvoiceLineItem.getQtyReturned()).isEqualTo(DEFAULT_QTY_RETURNED);
        assertThat(testInvoiceLineItem.getQtyPicked()).isEqualTo(DEFAULT_QTY_PICKED);
        assertThat(testInvoiceLineItem.getTotalTaxCharge()).isEqualTo(DEFAULT_TOTAL_TAX_CHARGE);
        assertThat(testInvoiceLineItem.getDiscountPercentage()).isEqualTo(DEFAULT_DISCOUNT_PERCENTAGE);
        assertThat(testInvoiceLineItem.getDiscountDescription()).isEqualTo(DEFAULT_DISCOUNT_DESCRIPTION);
        assertThat(testInvoiceLineItem.getDiscountPriceGroupCode()).isEqualTo(DEFAULT_DISCOUNT_PRICE_GROUP_CODE);
        assertThat(testInvoiceLineItem.getLineNo()).isEqualTo(DEFAULT_LINE_NO);
        assertThat(testInvoiceLineItem.getListPrice()).isEqualTo(DEFAULT_LIST_PRICE);
        assertThat(testInvoiceLineItem.getListPriceDiscount()).isEqualTo(DEFAULT_LIST_PRICE_DISCOUNT);
        assertThat(testInvoiceLineItem.getCost2()).isEqualTo(DEFAULT_COST2);
        assertThat(testInvoiceLineItem.getIsHidden()).isEqualTo(DEFAULT_IS_HIDDEN);
        assertThat(testInvoiceLineItem.getRef1()).isEqualTo(DEFAULT_REF1);
        assertThat(testInvoiceLineItem.getRef2()).isEqualTo(DEFAULT_REF2);
    }

    @Test
    @Transactional
    public void getAllInvoiceLineItems() throws Exception {
        // Initialize the database
        invoiceLineItemRepository.saveAndFlush(invoiceLineItem);

        // Get all the invoiceLineItems
        restInvoiceLineItemMockMvc.perform(get("/api/invoiceLineItems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceLineItem.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.intValue())))
                .andExpect(jsonPath("$.[*].soldFor").value(hasItem(DEFAULT_SOLD_FOR.intValue())))
                .andExpect(jsonPath("$.[*].qtyOrdered").value(hasItem(DEFAULT_QTY_ORDERED.doubleValue())))
                .andExpect(jsonPath("$.[*].qtySold").value(hasItem(DEFAULT_QTY_SOLD.doubleValue())))
                .andExpect(jsonPath("$.[*].qtyReturned").value(hasItem(DEFAULT_QTY_RETURNED.doubleValue())))
                .andExpect(jsonPath("$.[*].qtyPicked").value(hasItem(DEFAULT_QTY_PICKED.doubleValue())))
                .andExpect(jsonPath("$.[*].totalTaxCharge").value(hasItem(DEFAULT_TOTAL_TAX_CHARGE.intValue())))
                .andExpect(jsonPath("$.[*].discountPercentage").value(hasItem(DEFAULT_DISCOUNT_PERCENTAGE.intValue())))
                .andExpect(jsonPath("$.[*].discountDescription").value(hasItem(DEFAULT_DISCOUNT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].discountPriceGroupCode").value(hasItem(DEFAULT_DISCOUNT_PRICE_GROUP_CODE.toString())))
                .andExpect(jsonPath("$.[*].lineNo").value(hasItem(DEFAULT_LINE_NO)))
                .andExpect(jsonPath("$.[*].listPrice").value(hasItem(DEFAULT_LIST_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].listPriceDiscount").value(hasItem(DEFAULT_LIST_PRICE_DISCOUNT.intValue())))
                .andExpect(jsonPath("$.[*].cost2").value(hasItem(DEFAULT_COST2.intValue())))
                .andExpect(jsonPath("$.[*].isHidden").value(hasItem(DEFAULT_IS_HIDDEN.booleanValue())))
                .andExpect(jsonPath("$.[*].Ref1").value(hasItem(DEFAULT_REF1.toString())))
                .andExpect(jsonPath("$.[*].Ref2").value(hasItem(DEFAULT_REF2.toString())));
    }

    @Test
    @Transactional
    public void getInvoiceLineItem() throws Exception {
        // Initialize the database
        invoiceLineItemRepository.saveAndFlush(invoiceLineItem);

        // Get the invoiceLineItem
        restInvoiceLineItemMockMvc.perform(get("/api/invoiceLineItems/{id}", invoiceLineItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(invoiceLineItem.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.intValue()))
            .andExpect(jsonPath("$.soldFor").value(DEFAULT_SOLD_FOR.intValue()))
            .andExpect(jsonPath("$.qtyOrdered").value(DEFAULT_QTY_ORDERED.doubleValue()))
            .andExpect(jsonPath("$.qtySold").value(DEFAULT_QTY_SOLD.doubleValue()))
            .andExpect(jsonPath("$.qtyReturned").value(DEFAULT_QTY_RETURNED.doubleValue()))
            .andExpect(jsonPath("$.qtyPicked").value(DEFAULT_QTY_PICKED.doubleValue()))
            .andExpect(jsonPath("$.totalTaxCharge").value(DEFAULT_TOTAL_TAX_CHARGE.intValue()))
            .andExpect(jsonPath("$.discountPercentage").value(DEFAULT_DISCOUNT_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.discountDescription").value(DEFAULT_DISCOUNT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.discountPriceGroupCode").value(DEFAULT_DISCOUNT_PRICE_GROUP_CODE.toString()))
            .andExpect(jsonPath("$.lineNo").value(DEFAULT_LINE_NO))
            .andExpect(jsonPath("$.listPrice").value(DEFAULT_LIST_PRICE.intValue()))
            .andExpect(jsonPath("$.listPriceDiscount").value(DEFAULT_LIST_PRICE_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.cost2").value(DEFAULT_COST2.intValue()))
            .andExpect(jsonPath("$.isHidden").value(DEFAULT_IS_HIDDEN.booleanValue()))
            .andExpect(jsonPath("$.Ref1").value(DEFAULT_REF1.toString()))
            .andExpect(jsonPath("$.Ref2").value(DEFAULT_REF2.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvoiceLineItem() throws Exception {
        // Get the invoiceLineItem
        restInvoiceLineItemMockMvc.perform(get("/api/invoiceLineItems/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceLineItem() throws Exception {
        // Initialize the database
        invoiceLineItemRepository.saveAndFlush(invoiceLineItem);

		int databaseSizeBeforeUpdate = invoiceLineItemRepository.findAll().size();

        // Update the invoiceLineItem
        invoiceLineItem.setDescription(UPDATED_DESCRIPTION);
        invoiceLineItem.setCost(UPDATED_COST);
        invoiceLineItem.setSoldFor(UPDATED_SOLD_FOR);
        invoiceLineItem.setQtyOrdered(UPDATED_QTY_ORDERED);
        invoiceLineItem.setQtySold(UPDATED_QTY_SOLD);
        invoiceLineItem.setQtyReturned(UPDATED_QTY_RETURNED);
        invoiceLineItem.setQtyPicked(UPDATED_QTY_PICKED);
        invoiceLineItem.setTotalTaxCharge(UPDATED_TOTAL_TAX_CHARGE);
        invoiceLineItem.setDiscountPercentage(UPDATED_DISCOUNT_PERCENTAGE);
        invoiceLineItem.setDiscountDescription(UPDATED_DISCOUNT_DESCRIPTION);
        invoiceLineItem.setDiscountPriceGroupCode(UPDATED_DISCOUNT_PRICE_GROUP_CODE);
        invoiceLineItem.setLineNo(UPDATED_LINE_NO);
        invoiceLineItem.setListPrice(UPDATED_LIST_PRICE);
        invoiceLineItem.setListPriceDiscount(UPDATED_LIST_PRICE_DISCOUNT);
        invoiceLineItem.setCost2(UPDATED_COST2);
        invoiceLineItem.setIsHidden(UPDATED_IS_HIDDEN);
        invoiceLineItem.setRef1(UPDATED_REF1);
        invoiceLineItem.setRef2(UPDATED_REF2);

        restInvoiceLineItemMockMvc.perform(put("/api/invoiceLineItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invoiceLineItem)))
                .andExpect(status().isOk());

        // Validate the InvoiceLineItem in the database
        List<InvoiceLineItem> invoiceLineItems = invoiceLineItemRepository.findAll();
        assertThat(invoiceLineItems).hasSize(databaseSizeBeforeUpdate);
        InvoiceLineItem testInvoiceLineItem = invoiceLineItems.get(invoiceLineItems.size() - 1);
        assertThat(testInvoiceLineItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInvoiceLineItem.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testInvoiceLineItem.getSoldFor()).isEqualTo(UPDATED_SOLD_FOR);
        assertThat(testInvoiceLineItem.getQtyOrdered()).isEqualTo(UPDATED_QTY_ORDERED);
        assertThat(testInvoiceLineItem.getQtySold()).isEqualTo(UPDATED_QTY_SOLD);
        assertThat(testInvoiceLineItem.getQtyReturned()).isEqualTo(UPDATED_QTY_RETURNED);
        assertThat(testInvoiceLineItem.getQtyPicked()).isEqualTo(UPDATED_QTY_PICKED);
        assertThat(testInvoiceLineItem.getTotalTaxCharge()).isEqualTo(UPDATED_TOTAL_TAX_CHARGE);
        assertThat(testInvoiceLineItem.getDiscountPercentage()).isEqualTo(UPDATED_DISCOUNT_PERCENTAGE);
        assertThat(testInvoiceLineItem.getDiscountDescription()).isEqualTo(UPDATED_DISCOUNT_DESCRIPTION);
        assertThat(testInvoiceLineItem.getDiscountPriceGroupCode()).isEqualTo(UPDATED_DISCOUNT_PRICE_GROUP_CODE);
        assertThat(testInvoiceLineItem.getLineNo()).isEqualTo(UPDATED_LINE_NO);
        assertThat(testInvoiceLineItem.getListPrice()).isEqualTo(UPDATED_LIST_PRICE);
        assertThat(testInvoiceLineItem.getListPriceDiscount()).isEqualTo(UPDATED_LIST_PRICE_DISCOUNT);
        assertThat(testInvoiceLineItem.getCost2()).isEqualTo(UPDATED_COST2);
        assertThat(testInvoiceLineItem.getIsHidden()).isEqualTo(UPDATED_IS_HIDDEN);
        assertThat(testInvoiceLineItem.getRef1()).isEqualTo(UPDATED_REF1);
        assertThat(testInvoiceLineItem.getRef2()).isEqualTo(UPDATED_REF2);
    }

    @Test
    @Transactional
    public void deleteInvoiceLineItem() throws Exception {
        // Initialize the database
        invoiceLineItemRepository.saveAndFlush(invoiceLineItem);

		int databaseSizeBeforeDelete = invoiceLineItemRepository.findAll().size();

        // Get the invoiceLineItem
        restInvoiceLineItemMockMvc.perform(delete("/api/invoiceLineItems/{id}", invoiceLineItem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InvoiceLineItem> invoiceLineItems = invoiceLineItemRepository.findAll();
        assertThat(invoiceLineItems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
