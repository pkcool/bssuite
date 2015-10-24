package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.Promotion;
import com.enginemobi.bssuite.repository.PromotionRepository;
import com.enginemobi.bssuite.repository.search.PromotionSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PromotionResource REST controller.
 *
 * @see PromotionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PromotionResourceTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_COST = 1D;
    private static final Double UPDATED_COST = 2D;

    private static final Double DEFAULT_INCOME = 1D;
    private static final Double UPDATED_INCOME = 2D;

    private static final Double DEFAULT_EXPENSE = 1D;
    private static final Double UPDATED_EXPENSE = 2D;

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private PromotionRepository promotionRepository;

    @Inject
    private PromotionSearchRepository promotionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPromotionMockMvc;

    private Promotion promotion;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PromotionResource promotionResource = new PromotionResource();
        ReflectionTestUtils.setField(promotionResource, "promotionRepository", promotionRepository);
        ReflectionTestUtils.setField(promotionResource, "promotionSearchRepository", promotionSearchRepository);
        this.restPromotionMockMvc = MockMvcBuilders.standaloneSetup(promotionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        promotion = new Promotion();
        promotion.setCode(DEFAULT_CODE);
        promotion.setName(DEFAULT_NAME);
        promotion.setDescription(DEFAULT_DESCRIPTION);
        promotion.setStartDate(DEFAULT_START_DATE);
        promotion.setEndDate(DEFAULT_END_DATE);
        promotion.setCost(DEFAULT_COST);
        promotion.setIncome(DEFAULT_INCOME);
        promotion.setExpense(DEFAULT_EXPENSE);
        promotion.setDateCreated(DEFAULT_DATE_CREATED);
    }

    @Test
    @Transactional
    public void createPromotion() throws Exception {
        int databaseSizeBeforeCreate = promotionRepository.findAll().size();

        // Create the Promotion

        restPromotionMockMvc.perform(post("/api/promotions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(promotion)))
                .andExpect(status().isCreated());

        // Validate the Promotion in the database
        List<Promotion> promotions = promotionRepository.findAll();
        assertThat(promotions).hasSize(databaseSizeBeforeCreate + 1);
        Promotion testPromotion = promotions.get(promotions.size() - 1);
        assertThat(testPromotion.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPromotion.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPromotion.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPromotion.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPromotion.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPromotion.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testPromotion.getIncome()).isEqualTo(DEFAULT_INCOME);
        assertThat(testPromotion.getExpense()).isEqualTo(DEFAULT_EXPENSE);
        assertThat(testPromotion.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = promotionRepository.findAll().size();
        // set the field null
        promotion.setCode(null);

        // Create the Promotion, which fails.

        restPromotionMockMvc.perform(post("/api/promotions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(promotion)))
                .andExpect(status().isBadRequest());

        List<Promotion> promotions = promotionRepository.findAll();
        assertThat(promotions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPromotions() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotions
        restPromotionMockMvc.perform(get("/api/promotions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(promotion.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
                .andExpect(jsonPath("$.[*].income").value(hasItem(DEFAULT_INCOME.doubleValue())))
                .andExpect(jsonPath("$.[*].expense").value(hasItem(DEFAULT_EXPENSE.doubleValue())))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())));
    }

    @Test
    @Transactional
    public void getPromotion() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get the promotion
        restPromotionMockMvc.perform(get("/api/promotions/{id}", promotion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(promotion.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()))
            .andExpect(jsonPath("$.income").value(DEFAULT_INCOME.doubleValue()))
            .andExpect(jsonPath("$.expense").value(DEFAULT_EXPENSE.doubleValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPromotion() throws Exception {
        // Get the promotion
        restPromotionMockMvc.perform(get("/api/promotions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePromotion() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

		int databaseSizeBeforeUpdate = promotionRepository.findAll().size();

        // Update the promotion
        promotion.setCode(UPDATED_CODE);
        promotion.setName(UPDATED_NAME);
        promotion.setDescription(UPDATED_DESCRIPTION);
        promotion.setStartDate(UPDATED_START_DATE);
        promotion.setEndDate(UPDATED_END_DATE);
        promotion.setCost(UPDATED_COST);
        promotion.setIncome(UPDATED_INCOME);
        promotion.setExpense(UPDATED_EXPENSE);
        promotion.setDateCreated(UPDATED_DATE_CREATED);

        restPromotionMockMvc.perform(put("/api/promotions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(promotion)))
                .andExpect(status().isOk());

        // Validate the Promotion in the database
        List<Promotion> promotions = promotionRepository.findAll();
        assertThat(promotions).hasSize(databaseSizeBeforeUpdate);
        Promotion testPromotion = promotions.get(promotions.size() - 1);
        assertThat(testPromotion.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPromotion.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPromotion.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPromotion.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPromotion.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPromotion.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testPromotion.getIncome()).isEqualTo(UPDATED_INCOME);
        assertThat(testPromotion.getExpense()).isEqualTo(UPDATED_EXPENSE);
        assertThat(testPromotion.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void deletePromotion() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

		int databaseSizeBeforeDelete = promotionRepository.findAll().size();

        // Get the promotion
        restPromotionMockMvc.perform(delete("/api/promotions/{id}", promotion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Promotion> promotions = promotionRepository.findAll();
        assertThat(promotions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
