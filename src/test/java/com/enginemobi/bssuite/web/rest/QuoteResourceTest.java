package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.Quote;
import com.enginemobi.bssuite.repository.QuoteRepository;
import com.enginemobi.bssuite.repository.search.QuoteSearchRepository;
import com.enginemobi.bssuite.web.rest.dto.QuoteDTO;
import com.enginemobi.bssuite.web.rest.mapper.QuoteMapper;

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

import com.enginemobi.bssuite.domain.enumeration.QuoteStatus;

/**
 * Test class for the QuoteResource REST controller.
 *
 * @see QuoteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class QuoteResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_QUOTE_NO = "AAAAA";
    private static final String UPDATED_QUOTE_NO = "BBBBB";


private static final QuoteStatus DEFAULT_STATUS = QuoteStatus.OPEN;
    private static final QuoteStatus UPDATED_STATUS = QuoteStatus.ACCEPTED;

    private static final ZonedDateTime DEFAULT_QUOTE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_QUOTE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_QUOTE_DATE_STR = dateTimeFormatter.format(DEFAULT_QUOTE_DATE);

    private static final LocalDate DEFAULT_EXPIRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FOLLOWUP_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FOLLOWUP_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_REFERENCE = "AAAAA";
    private static final String UPDATED_REFERENCE = "BBBBB";
    private static final String DEFAULT_OUR_REF = "AAAAA";
    private static final String UPDATED_OUR_REF = "BBBBB";

    private static final BigDecimal DEFAULT_FREIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_FREIGHT = new BigDecimal(2);
    private static final String DEFAULT_REASON_FOR_LOSS = "AAAAA";
    private static final String UPDATED_REASON_FOR_LOSS = "BBBBB";

    private static final Boolean DEFAULT_IS_TAXABLE = false;
    private static final Boolean UPDATED_IS_TAXABLE = true;
    private static final String DEFAULT_TAX_EXEMPTION_CODE = "AAAAA";
    private static final String UPDATED_TAX_EXEMPTION_CODE = "BBBBB";

    private static final Boolean DEFAULT_IS_LOCKED = false;
    private static final Boolean UPDATED_IS_LOCKED = true;

    private static final BigDecimal DEFAULT_ADJUST_TAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_ADJUST_TAX = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ADJUST_TAX_EXEMPT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ADJUST_TAX_EXEMPT = new BigDecimal(2);
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
    private QuoteRepository quoteRepository;

    @Inject
    private QuoteMapper quoteMapper;

    @Inject
    private QuoteSearchRepository quoteSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restQuoteMockMvc;

    private Quote quote;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QuoteResource quoteResource = new QuoteResource();
        ReflectionTestUtils.setField(quoteResource, "quoteRepository", quoteRepository);
        ReflectionTestUtils.setField(quoteResource, "quoteMapper", quoteMapper);
        ReflectionTestUtils.setField(quoteResource, "quoteSearchRepository", quoteSearchRepository);
        this.restQuoteMockMvc = MockMvcBuilders.standaloneSetup(quoteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        quote = new Quote();
        quote.setQuoteNo(DEFAULT_QUOTE_NO);
        quote.setStatus(DEFAULT_STATUS);
        quote.setQuoteDate(DEFAULT_QUOTE_DATE);
        quote.setExpiryDate(DEFAULT_EXPIRY_DATE);
        quote.setFollowupDate(DEFAULT_FOLLOWUP_DATE);
        quote.setReference(DEFAULT_REFERENCE);
        quote.setOurRef(DEFAULT_OUR_REF);
        quote.setFreight(DEFAULT_FREIGHT);
        quote.setReasonForLoss(DEFAULT_REASON_FOR_LOSS);
        quote.setIsTaxable(DEFAULT_IS_TAXABLE);
        quote.setTaxExemptionCode(DEFAULT_TAX_EXEMPTION_CODE);
        quote.setIsLocked(DEFAULT_IS_LOCKED);
        quote.setAdjustTax(DEFAULT_ADJUST_TAX);
        quote.setAdjustTaxExempt(DEFAULT_ADJUST_TAX_EXEMPT);
        quote.setComment(DEFAULT_COMMENT);
        quote.setTotalTaxAmount(DEFAULT_TOTAL_TAX_AMOUNT);
        quote.setTotalSellPrice(DEFAULT_TOTAL_SELL_PRICE);
        quote.setTotalCost(DEFAULT_TOTAL_COST);
        quote.setIsSuspended(DEFAULT_IS_SUSPENDED);
    }

    @Test
    @Transactional
    public void createQuote() throws Exception {
        int databaseSizeBeforeCreate = quoteRepository.findAll().size();

        // Create the Quote
        QuoteDTO quoteDTO = quoteMapper.quoteToQuoteDTO(quote);

        restQuoteMockMvc.perform(post("/api/quotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
                .andExpect(status().isCreated());

        // Validate the Quote in the database
        List<Quote> quotes = quoteRepository.findAll();
        assertThat(quotes).hasSize(databaseSizeBeforeCreate + 1);
        Quote testQuote = quotes.get(quotes.size() - 1);
        assertThat(testQuote.getQuoteNo()).isEqualTo(DEFAULT_QUOTE_NO);
        assertThat(testQuote.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testQuote.getQuoteDate()).isEqualTo(DEFAULT_QUOTE_DATE);
        assertThat(testQuote.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testQuote.getFollowupDate()).isEqualTo(DEFAULT_FOLLOWUP_DATE);
        assertThat(testQuote.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testQuote.getOurRef()).isEqualTo(DEFAULT_OUR_REF);
        assertThat(testQuote.getFreight()).isEqualTo(DEFAULT_FREIGHT);
        assertThat(testQuote.getReasonForLoss()).isEqualTo(DEFAULT_REASON_FOR_LOSS);
        assertThat(testQuote.getIsTaxable()).isEqualTo(DEFAULT_IS_TAXABLE);
        assertThat(testQuote.getTaxExemptionCode()).isEqualTo(DEFAULT_TAX_EXEMPTION_CODE);
        assertThat(testQuote.getIsLocked()).isEqualTo(DEFAULT_IS_LOCKED);
        assertThat(testQuote.getAdjustTax()).isEqualTo(DEFAULT_ADJUST_TAX);
        assertThat(testQuote.getAdjustTaxExempt()).isEqualTo(DEFAULT_ADJUST_TAX_EXEMPT);
        assertThat(testQuote.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testQuote.getTotalTaxAmount()).isEqualTo(DEFAULT_TOTAL_TAX_AMOUNT);
        assertThat(testQuote.getTotalSellPrice()).isEqualTo(DEFAULT_TOTAL_SELL_PRICE);
        assertThat(testQuote.getTotalCost()).isEqualTo(DEFAULT_TOTAL_COST);
        assertThat(testQuote.getIsSuspended()).isEqualTo(DEFAULT_IS_SUSPENDED);
    }

    @Test
    @Transactional
    public void getAllQuotes() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get all the quotes
        restQuoteMockMvc.perform(get("/api/quotes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(quote.getId().intValue())))
                .andExpect(jsonPath("$.[*].quoteNo").value(hasItem(DEFAULT_QUOTE_NO.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].quoteDate").value(hasItem(DEFAULT_QUOTE_DATE_STR)))
                .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
                .andExpect(jsonPath("$.[*].followupDate").value(hasItem(DEFAULT_FOLLOWUP_DATE.toString())))
                .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
                .andExpect(jsonPath("$.[*].ourRef").value(hasItem(DEFAULT_OUR_REF.toString())))
                .andExpect(jsonPath("$.[*].freight").value(hasItem(DEFAULT_FREIGHT.intValue())))
                .andExpect(jsonPath("$.[*].reasonForLoss").value(hasItem(DEFAULT_REASON_FOR_LOSS.toString())))
                .andExpect(jsonPath("$.[*].isTaxable").value(hasItem(DEFAULT_IS_TAXABLE.booleanValue())))
                .andExpect(jsonPath("$.[*].taxExemptionCode").value(hasItem(DEFAULT_TAX_EXEMPTION_CODE.toString())))
                .andExpect(jsonPath("$.[*].isLocked").value(hasItem(DEFAULT_IS_LOCKED.booleanValue())))
                .andExpect(jsonPath("$.[*].adjustTax").value(hasItem(DEFAULT_ADJUST_TAX.intValue())))
                .andExpect(jsonPath("$.[*].adjustTaxExempt").value(hasItem(DEFAULT_ADJUST_TAX_EXEMPT.intValue())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
                .andExpect(jsonPath("$.[*].totalTaxAmount").value(hasItem(DEFAULT_TOTAL_TAX_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].totalSellPrice").value(hasItem(DEFAULT_TOTAL_SELL_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].totalCost").value(hasItem(DEFAULT_TOTAL_COST.intValue())))
                .andExpect(jsonPath("$.[*].isSuspended").value(hasItem(DEFAULT_IS_SUSPENDED.booleanValue())));
    }

    @Test
    @Transactional
    public void getQuote() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get the quote
        restQuoteMockMvc.perform(get("/api/quotes/{id}", quote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(quote.getId().intValue()))
            .andExpect(jsonPath("$.quoteNo").value(DEFAULT_QUOTE_NO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.quoteDate").value(DEFAULT_QUOTE_DATE_STR))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.followupDate").value(DEFAULT_FOLLOWUP_DATE.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.ourRef").value(DEFAULT_OUR_REF.toString()))
            .andExpect(jsonPath("$.freight").value(DEFAULT_FREIGHT.intValue()))
            .andExpect(jsonPath("$.reasonForLoss").value(DEFAULT_REASON_FOR_LOSS.toString()))
            .andExpect(jsonPath("$.isTaxable").value(DEFAULT_IS_TAXABLE.booleanValue()))
            .andExpect(jsonPath("$.taxExemptionCode").value(DEFAULT_TAX_EXEMPTION_CODE.toString()))
            .andExpect(jsonPath("$.isLocked").value(DEFAULT_IS_LOCKED.booleanValue()))
            .andExpect(jsonPath("$.adjustTax").value(DEFAULT_ADJUST_TAX.intValue()))
            .andExpect(jsonPath("$.adjustTaxExempt").value(DEFAULT_ADJUST_TAX_EXEMPT.intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.totalTaxAmount").value(DEFAULT_TOTAL_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.totalSellPrice").value(DEFAULT_TOTAL_SELL_PRICE.intValue()))
            .andExpect(jsonPath("$.totalCost").value(DEFAULT_TOTAL_COST.intValue()))
            .andExpect(jsonPath("$.isSuspended").value(DEFAULT_IS_SUSPENDED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingQuote() throws Exception {
        // Get the quote
        restQuoteMockMvc.perform(get("/api/quotes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuote() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

		int databaseSizeBeforeUpdate = quoteRepository.findAll().size();

        // Update the quote
        quote.setQuoteNo(UPDATED_QUOTE_NO);
        quote.setStatus(UPDATED_STATUS);
        quote.setQuoteDate(UPDATED_QUOTE_DATE);
        quote.setExpiryDate(UPDATED_EXPIRY_DATE);
        quote.setFollowupDate(UPDATED_FOLLOWUP_DATE);
        quote.setReference(UPDATED_REFERENCE);
        quote.setOurRef(UPDATED_OUR_REF);
        quote.setFreight(UPDATED_FREIGHT);
        quote.setReasonForLoss(UPDATED_REASON_FOR_LOSS);
        quote.setIsTaxable(UPDATED_IS_TAXABLE);
        quote.setTaxExemptionCode(UPDATED_TAX_EXEMPTION_CODE);
        quote.setIsLocked(UPDATED_IS_LOCKED);
        quote.setAdjustTax(UPDATED_ADJUST_TAX);
        quote.setAdjustTaxExempt(UPDATED_ADJUST_TAX_EXEMPT);
        quote.setComment(UPDATED_COMMENT);
        quote.setTotalTaxAmount(UPDATED_TOTAL_TAX_AMOUNT);
        quote.setTotalSellPrice(UPDATED_TOTAL_SELL_PRICE);
        quote.setTotalCost(UPDATED_TOTAL_COST);
        quote.setIsSuspended(UPDATED_IS_SUSPENDED);
        QuoteDTO quoteDTO = quoteMapper.quoteToQuoteDTO(quote);

        restQuoteMockMvc.perform(put("/api/quotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
                .andExpect(status().isOk());

        // Validate the Quote in the database
        List<Quote> quotes = quoteRepository.findAll();
        assertThat(quotes).hasSize(databaseSizeBeforeUpdate);
        Quote testQuote = quotes.get(quotes.size() - 1);
        assertThat(testQuote.getQuoteNo()).isEqualTo(UPDATED_QUOTE_NO);
        assertThat(testQuote.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testQuote.getQuoteDate()).isEqualTo(UPDATED_QUOTE_DATE);
        assertThat(testQuote.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testQuote.getFollowupDate()).isEqualTo(UPDATED_FOLLOWUP_DATE);
        assertThat(testQuote.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testQuote.getOurRef()).isEqualTo(UPDATED_OUR_REF);
        assertThat(testQuote.getFreight()).isEqualTo(UPDATED_FREIGHT);
        assertThat(testQuote.getReasonForLoss()).isEqualTo(UPDATED_REASON_FOR_LOSS);
        assertThat(testQuote.getIsTaxable()).isEqualTo(UPDATED_IS_TAXABLE);
        assertThat(testQuote.getTaxExemptionCode()).isEqualTo(UPDATED_TAX_EXEMPTION_CODE);
        assertThat(testQuote.getIsLocked()).isEqualTo(UPDATED_IS_LOCKED);
        assertThat(testQuote.getAdjustTax()).isEqualTo(UPDATED_ADJUST_TAX);
        assertThat(testQuote.getAdjustTaxExempt()).isEqualTo(UPDATED_ADJUST_TAX_EXEMPT);
        assertThat(testQuote.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testQuote.getTotalTaxAmount()).isEqualTo(UPDATED_TOTAL_TAX_AMOUNT);
        assertThat(testQuote.getTotalSellPrice()).isEqualTo(UPDATED_TOTAL_SELL_PRICE);
        assertThat(testQuote.getTotalCost()).isEqualTo(UPDATED_TOTAL_COST);
        assertThat(testQuote.getIsSuspended()).isEqualTo(UPDATED_IS_SUSPENDED);
    }

    @Test
    @Transactional
    public void deleteQuote() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

		int databaseSizeBeforeDelete = quoteRepository.findAll().size();

        // Get the quote
        restQuoteMockMvc.perform(delete("/api/quotes/{id}", quote.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Quote> quotes = quoteRepository.findAll();
        assertThat(quotes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
