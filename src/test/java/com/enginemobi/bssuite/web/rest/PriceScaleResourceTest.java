package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.PriceScale;
import com.enginemobi.bssuite.repository.PriceScaleRepository;
import com.enginemobi.bssuite.repository.search.PriceScaleSearchRepository;

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

import com.enginemobi.bssuite.domain.enumeration.RoundingMethod;

/**
 * Test class for the PriceScaleResource REST controller.
 *
 * @see PriceScaleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PriceScaleResourceTest {

    private static final String DEFAULT_CODE = "AA";
    private static final String UPDATED_CODE = "BB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_PRICE = "AAAAA";
    private static final String UPDATED_PRICE = "BBBBB";
    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";


private static final RoundingMethod DEFAULT_ROUND = RoundingMethod.UP;
    private static final RoundingMethod UPDATED_ROUND = RoundingMethod.DOWN;

    @Inject
    private PriceScaleRepository priceScaleRepository;

    @Inject
    private PriceScaleSearchRepository priceScaleSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPriceScaleMockMvc;

    private PriceScale priceScale;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PriceScaleResource priceScaleResource = new PriceScaleResource();
        ReflectionTestUtils.setField(priceScaleResource, "priceScaleRepository", priceScaleRepository);
        ReflectionTestUtils.setField(priceScaleResource, "priceScaleSearchRepository", priceScaleSearchRepository);
        this.restPriceScaleMockMvc = MockMvcBuilders.standaloneSetup(priceScaleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        priceScale = new PriceScale();
        priceScale.setCode(DEFAULT_CODE);
        priceScale.setName(DEFAULT_NAME);
        priceScale.setPrice(DEFAULT_PRICE);
        priceScale.setValue(DEFAULT_VALUE);
        priceScale.setRound(DEFAULT_ROUND);
    }

    @Test
    @Transactional
    public void createPriceScale() throws Exception {
        int databaseSizeBeforeCreate = priceScaleRepository.findAll().size();

        // Create the PriceScale

        restPriceScaleMockMvc.perform(post("/api/priceScales")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(priceScale)))
                .andExpect(status().isCreated());

        // Validate the PriceScale in the database
        List<PriceScale> priceScales = priceScaleRepository.findAll();
        assertThat(priceScales).hasSize(databaseSizeBeforeCreate + 1);
        PriceScale testPriceScale = priceScales.get(priceScales.size() - 1);
        assertThat(testPriceScale.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPriceScale.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPriceScale.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPriceScale.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPriceScale.getRound()).isEqualTo(DEFAULT_ROUND);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceScaleRepository.findAll().size();
        // set the field null
        priceScale.setCode(null);

        // Create the PriceScale, which fails.

        restPriceScaleMockMvc.perform(post("/api/priceScales")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(priceScale)))
                .andExpect(status().isBadRequest());

        List<PriceScale> priceScales = priceScaleRepository.findAll();
        assertThat(priceScales).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPriceScales() throws Exception {
        // Initialize the database
        priceScaleRepository.saveAndFlush(priceScale);

        // Get all the priceScales
        restPriceScaleMockMvc.perform(get("/api/priceScales"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(priceScale.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
                .andExpect(jsonPath("$.[*].round").value(hasItem(DEFAULT_ROUND.toString())));
    }

    @Test
    @Transactional
    public void getPriceScale() throws Exception {
        // Initialize the database
        priceScaleRepository.saveAndFlush(priceScale);

        // Get the priceScale
        restPriceScaleMockMvc.perform(get("/api/priceScales/{id}", priceScale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(priceScale.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.round").value(DEFAULT_ROUND.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPriceScale() throws Exception {
        // Get the priceScale
        restPriceScaleMockMvc.perform(get("/api/priceScales/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePriceScale() throws Exception {
        // Initialize the database
        priceScaleRepository.saveAndFlush(priceScale);

		int databaseSizeBeforeUpdate = priceScaleRepository.findAll().size();

        // Update the priceScale
        priceScale.setCode(UPDATED_CODE);
        priceScale.setName(UPDATED_NAME);
        priceScale.setPrice(UPDATED_PRICE);
        priceScale.setValue(UPDATED_VALUE);
        priceScale.setRound(UPDATED_ROUND);

        restPriceScaleMockMvc.perform(put("/api/priceScales")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(priceScale)))
                .andExpect(status().isOk());

        // Validate the PriceScale in the database
        List<PriceScale> priceScales = priceScaleRepository.findAll();
        assertThat(priceScales).hasSize(databaseSizeBeforeUpdate);
        PriceScale testPriceScale = priceScales.get(priceScales.size() - 1);
        assertThat(testPriceScale.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPriceScale.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPriceScale.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPriceScale.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPriceScale.getRound()).isEqualTo(UPDATED_ROUND);
    }

    @Test
    @Transactional
    public void deletePriceScale() throws Exception {
        // Initialize the database
        priceScaleRepository.saveAndFlush(priceScale);

		int databaseSizeBeforeDelete = priceScaleRepository.findAll().size();

        // Get the priceScale
        restPriceScaleMockMvc.perform(delete("/api/priceScales/{id}", priceScale.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PriceScale> priceScales = priceScaleRepository.findAll();
        assertThat(priceScales).hasSize(databaseSizeBeforeDelete - 1);
    }
}
