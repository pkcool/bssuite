package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.QuoteLineItem;
import com.enginemobi.bssuite.repository.QuoteLineItemRepository;
import com.enginemobi.bssuite.repository.search.QuoteLineItemSearchRepository;

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
 * Test class for the QuoteLineItemResource REST controller.
 *
 * @see QuoteLineItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class QuoteLineItemResourceTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final BigDecimal DEFAULT_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SOLD_FOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_SOLD_FOR = new BigDecimal(2);

    private static final Double DEFAULT_QTY_QUOTED = 1D;
    private static final Double UPDATED_QTY_QUOTED = 2D;
    private static final String DEFAULT_DISCOUNT_PRICE_GROUP_CODE = "AAAAA";
    private static final String UPDATED_DISCOUNT_PRICE_GROUP_CODE = "BBBBB";

    private static final BigDecimal DEFAULT_TOTAL_TAX_CHARGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_TAX_CHARGE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DISCOUNT_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT_PERCENTAGE = new BigDecimal(2);

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
    private QuoteLineItemRepository quoteLineItemRepository;

    @Inject
    private QuoteLineItemSearchRepository quoteLineItemSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restQuoteLineItemMockMvc;

    private QuoteLineItem quoteLineItem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QuoteLineItemResource quoteLineItemResource = new QuoteLineItemResource();
        ReflectionTestUtils.setField(quoteLineItemResource, "quoteLineItemRepository", quoteLineItemRepository);
        ReflectionTestUtils.setField(quoteLineItemResource, "quoteLineItemSearchRepository", quoteLineItemSearchRepository);
        this.restQuoteLineItemMockMvc = MockMvcBuilders.standaloneSetup(quoteLineItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        quoteLineItem = new QuoteLineItem();
        quoteLineItem.setDescription(DEFAULT_DESCRIPTION);
        quoteLineItem.setCost(DEFAULT_COST);
        quoteLineItem.setSoldFor(DEFAULT_SOLD_FOR);
        quoteLineItem.setQtyQuoted(DEFAULT_QTY_QUOTED);
        quoteLineItem.setDiscountPriceGroupCode(DEFAULT_DISCOUNT_PRICE_GROUP_CODE);
        quoteLineItem.setTotalTaxCharge(DEFAULT_TOTAL_TAX_CHARGE);
        quoteLineItem.setDiscountPercentage(DEFAULT_DISCOUNT_PERCENTAGE);
        quoteLineItem.setLineNo(DEFAULT_LINE_NO);
        quoteLineItem.setListPrice(DEFAULT_LIST_PRICE);
        quoteLineItem.setListPriceDiscount(DEFAULT_LIST_PRICE_DISCOUNT);
        quoteLineItem.setCost2(DEFAULT_COST2);
        quoteLineItem.setIsHidden(DEFAULT_IS_HIDDEN);
        quoteLineItem.setRef1(DEFAULT_REF1);
        quoteLineItem.setRef2(DEFAULT_REF2);
    }

    @Test
    @Transactional
    public void createQuoteLineItem() throws Exception {
        int databaseSizeBeforeCreate = quoteLineItemRepository.findAll().size();

        // Create the QuoteLineItem

        restQuoteLineItemMockMvc.perform(post("/api/quoteLineItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quoteLineItem)))
                .andExpect(status().isCreated());

        // Validate the QuoteLineItem in the database
        List<QuoteLineItem> quoteLineItems = quoteLineItemRepository.findAll();
        assertThat(quoteLineItems).hasSize(databaseSizeBeforeCreate + 1);
        QuoteLineItem testQuoteLineItem = quoteLineItems.get(quoteLineItems.size() - 1);
        assertThat(testQuoteLineItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testQuoteLineItem.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testQuoteLineItem.getSoldFor()).isEqualTo(DEFAULT_SOLD_FOR);
        assertThat(testQuoteLineItem.getQtyQuoted()).isEqualTo(DEFAULT_QTY_QUOTED);
        assertThat(testQuoteLineItem.getDiscountPriceGroupCode()).isEqualTo(DEFAULT_DISCOUNT_PRICE_GROUP_CODE);
        assertThat(testQuoteLineItem.getTotalTaxCharge()).isEqualTo(DEFAULT_TOTAL_TAX_CHARGE);
        assertThat(testQuoteLineItem.getDiscountPercentage()).isEqualTo(DEFAULT_DISCOUNT_PERCENTAGE);
        assertThat(testQuoteLineItem.getLineNo()).isEqualTo(DEFAULT_LINE_NO);
        assertThat(testQuoteLineItem.getListPrice()).isEqualTo(DEFAULT_LIST_PRICE);
        assertThat(testQuoteLineItem.getListPriceDiscount()).isEqualTo(DEFAULT_LIST_PRICE_DISCOUNT);
        assertThat(testQuoteLineItem.getCost2()).isEqualTo(DEFAULT_COST2);
        assertThat(testQuoteLineItem.getIsHidden()).isEqualTo(DEFAULT_IS_HIDDEN);
        assertThat(testQuoteLineItem.getRef1()).isEqualTo(DEFAULT_REF1);
        assertThat(testQuoteLineItem.getRef2()).isEqualTo(DEFAULT_REF2);
    }

    @Test
    @Transactional
    public void getAllQuoteLineItems() throws Exception {
        // Initialize the database
        quoteLineItemRepository.saveAndFlush(quoteLineItem);

        // Get all the quoteLineItems
        restQuoteLineItemMockMvc.perform(get("/api/quoteLineItems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(quoteLineItem.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.intValue())))
                .andExpect(jsonPath("$.[*].soldFor").value(hasItem(DEFAULT_SOLD_FOR.intValue())))
                .andExpect(jsonPath("$.[*].qtyQuoted").value(hasItem(DEFAULT_QTY_QUOTED.doubleValue())))
                .andExpect(jsonPath("$.[*].discountPriceGroupCode").value(hasItem(DEFAULT_DISCOUNT_PRICE_GROUP_CODE.toString())))
                .andExpect(jsonPath("$.[*].totalTaxCharge").value(hasItem(DEFAULT_TOTAL_TAX_CHARGE.intValue())))
                .andExpect(jsonPath("$.[*].discountPercentage").value(hasItem(DEFAULT_DISCOUNT_PERCENTAGE.intValue())))
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
    public void getQuoteLineItem() throws Exception {
        // Initialize the database
        quoteLineItemRepository.saveAndFlush(quoteLineItem);

        // Get the quoteLineItem
        restQuoteLineItemMockMvc.perform(get("/api/quoteLineItems/{id}", quoteLineItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(quoteLineItem.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.intValue()))
            .andExpect(jsonPath("$.soldFor").value(DEFAULT_SOLD_FOR.intValue()))
            .andExpect(jsonPath("$.qtyQuoted").value(DEFAULT_QTY_QUOTED.doubleValue()))
            .andExpect(jsonPath("$.discountPriceGroupCode").value(DEFAULT_DISCOUNT_PRICE_GROUP_CODE.toString()))
            .andExpect(jsonPath("$.totalTaxCharge").value(DEFAULT_TOTAL_TAX_CHARGE.intValue()))
            .andExpect(jsonPath("$.discountPercentage").value(DEFAULT_DISCOUNT_PERCENTAGE.intValue()))
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
    public void getNonExistingQuoteLineItem() throws Exception {
        // Get the quoteLineItem
        restQuoteLineItemMockMvc.perform(get("/api/quoteLineItems/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuoteLineItem() throws Exception {
        // Initialize the database
        quoteLineItemRepository.saveAndFlush(quoteLineItem);

		int databaseSizeBeforeUpdate = quoteLineItemRepository.findAll().size();

        // Update the quoteLineItem
        quoteLineItem.setDescription(UPDATED_DESCRIPTION);
        quoteLineItem.setCost(UPDATED_COST);
        quoteLineItem.setSoldFor(UPDATED_SOLD_FOR);
        quoteLineItem.setQtyQuoted(UPDATED_QTY_QUOTED);
        quoteLineItem.setDiscountPriceGroupCode(UPDATED_DISCOUNT_PRICE_GROUP_CODE);
        quoteLineItem.setTotalTaxCharge(UPDATED_TOTAL_TAX_CHARGE);
        quoteLineItem.setDiscountPercentage(UPDATED_DISCOUNT_PERCENTAGE);
        quoteLineItem.setLineNo(UPDATED_LINE_NO);
        quoteLineItem.setListPrice(UPDATED_LIST_PRICE);
        quoteLineItem.setListPriceDiscount(UPDATED_LIST_PRICE_DISCOUNT);
        quoteLineItem.setCost2(UPDATED_COST2);
        quoteLineItem.setIsHidden(UPDATED_IS_HIDDEN);
        quoteLineItem.setRef1(UPDATED_REF1);
        quoteLineItem.setRef2(UPDATED_REF2);

        restQuoteLineItemMockMvc.perform(put("/api/quoteLineItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quoteLineItem)))
                .andExpect(status().isOk());

        // Validate the QuoteLineItem in the database
        List<QuoteLineItem> quoteLineItems = quoteLineItemRepository.findAll();
        assertThat(quoteLineItems).hasSize(databaseSizeBeforeUpdate);
        QuoteLineItem testQuoteLineItem = quoteLineItems.get(quoteLineItems.size() - 1);
        assertThat(testQuoteLineItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testQuoteLineItem.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testQuoteLineItem.getSoldFor()).isEqualTo(UPDATED_SOLD_FOR);
        assertThat(testQuoteLineItem.getQtyQuoted()).isEqualTo(UPDATED_QTY_QUOTED);
        assertThat(testQuoteLineItem.getDiscountPriceGroupCode()).isEqualTo(UPDATED_DISCOUNT_PRICE_GROUP_CODE);
        assertThat(testQuoteLineItem.getTotalTaxCharge()).isEqualTo(UPDATED_TOTAL_TAX_CHARGE);
        assertThat(testQuoteLineItem.getDiscountPercentage()).isEqualTo(UPDATED_DISCOUNT_PERCENTAGE);
        assertThat(testQuoteLineItem.getLineNo()).isEqualTo(UPDATED_LINE_NO);
        assertThat(testQuoteLineItem.getListPrice()).isEqualTo(UPDATED_LIST_PRICE);
        assertThat(testQuoteLineItem.getListPriceDiscount()).isEqualTo(UPDATED_LIST_PRICE_DISCOUNT);
        assertThat(testQuoteLineItem.getCost2()).isEqualTo(UPDATED_COST2);
        assertThat(testQuoteLineItem.getIsHidden()).isEqualTo(UPDATED_IS_HIDDEN);
        assertThat(testQuoteLineItem.getRef1()).isEqualTo(UPDATED_REF1);
        assertThat(testQuoteLineItem.getRef2()).isEqualTo(UPDATED_REF2);
    }

    @Test
    @Transactional
    public void deleteQuoteLineItem() throws Exception {
        // Initialize the database
        quoteLineItemRepository.saveAndFlush(quoteLineItem);

		int databaseSizeBeforeDelete = quoteLineItemRepository.findAll().size();

        // Get the quoteLineItem
        restQuoteLineItemMockMvc.perform(delete("/api/quoteLineItems/{id}", quoteLineItem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<QuoteLineItem> quoteLineItems = quoteLineItemRepository.findAll();
        assertThat(quoteLineItems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
