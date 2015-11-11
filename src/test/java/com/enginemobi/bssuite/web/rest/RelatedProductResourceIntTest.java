package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.RelatedProduct;
import com.enginemobi.bssuite.repository.RelatedProductRepository;
import com.enginemobi.bssuite.repository.search.RelatedProductSearchRepository;
import com.enginemobi.bssuite.web.rest.dto.RelatedProductDTO;
import com.enginemobi.bssuite.web.rest.mapper.RelatedProductMapper;

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


/**
 * Test class for the RelatedProductResource REST controller.
 *
 * @see RelatedProductResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RelatedProductResourceIntTest {


    private static final Boolean DEFAULT_IS_SUGGESTED = false;
    private static final Boolean UPDATED_IS_SUGGESTED = true;
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    @Inject
    private RelatedProductRepository relatedProductRepository;

    @Inject
    private RelatedProductMapper relatedProductMapper;

    @Inject
    private RelatedProductSearchRepository relatedProductSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRelatedProductMockMvc;

    private RelatedProduct relatedProduct;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RelatedProductResource relatedProductResource = new RelatedProductResource();
        ReflectionTestUtils.setField(relatedProductResource, "relatedProductRepository", relatedProductRepository);
        ReflectionTestUtils.setField(relatedProductResource, "relatedProductMapper", relatedProductMapper);
        ReflectionTestUtils.setField(relatedProductResource, "relatedProductSearchRepository", relatedProductSearchRepository);
        this.restRelatedProductMockMvc = MockMvcBuilders.standaloneSetup(relatedProductResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        relatedProduct = new RelatedProduct();
        relatedProduct.setIsSuggested(DEFAULT_IS_SUGGESTED);
        relatedProduct.setComment(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createRelatedProduct() throws Exception {
        int databaseSizeBeforeCreate = relatedProductRepository.findAll().size();

        // Create the RelatedProduct
        RelatedProductDTO relatedProductDTO = relatedProductMapper.relatedProductToRelatedProductDTO(relatedProduct);

        restRelatedProductMockMvc.perform(post("/api/relatedProducts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(relatedProductDTO)))
                .andExpect(status().isCreated());

        // Validate the RelatedProduct in the database
        List<RelatedProduct> relatedProducts = relatedProductRepository.findAll();
        assertThat(relatedProducts).hasSize(databaseSizeBeforeCreate + 1);
        RelatedProduct testRelatedProduct = relatedProducts.get(relatedProducts.size() - 1);
        assertThat(testRelatedProduct.getIsSuggested()).isEqualTo(DEFAULT_IS_SUGGESTED);
        assertThat(testRelatedProduct.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void getAllRelatedProducts() throws Exception {
        // Initialize the database
        relatedProductRepository.saveAndFlush(relatedProduct);

        // Get all the relatedProducts
        restRelatedProductMockMvc.perform(get("/api/relatedProducts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(relatedProduct.getId().intValue())))
                .andExpect(jsonPath("$.[*].isSuggested").value(hasItem(DEFAULT_IS_SUGGESTED.booleanValue())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getRelatedProduct() throws Exception {
        // Initialize the database
        relatedProductRepository.saveAndFlush(relatedProduct);

        // Get the relatedProduct
        restRelatedProductMockMvc.perform(get("/api/relatedProducts/{id}", relatedProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(relatedProduct.getId().intValue()))
            .andExpect(jsonPath("$.isSuggested").value(DEFAULT_IS_SUGGESTED.booleanValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRelatedProduct() throws Exception {
        // Get the relatedProduct
        restRelatedProductMockMvc.perform(get("/api/relatedProducts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRelatedProduct() throws Exception {
        // Initialize the database
        relatedProductRepository.saveAndFlush(relatedProduct);

		int databaseSizeBeforeUpdate = relatedProductRepository.findAll().size();

        // Update the relatedProduct
        relatedProduct.setIsSuggested(UPDATED_IS_SUGGESTED);
        relatedProduct.setComment(UPDATED_COMMENT);
        RelatedProductDTO relatedProductDTO = relatedProductMapper.relatedProductToRelatedProductDTO(relatedProduct);

        restRelatedProductMockMvc.perform(put("/api/relatedProducts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(relatedProductDTO)))
                .andExpect(status().isOk());

        // Validate the RelatedProduct in the database
        List<RelatedProduct> relatedProducts = relatedProductRepository.findAll();
        assertThat(relatedProducts).hasSize(databaseSizeBeforeUpdate);
        RelatedProduct testRelatedProduct = relatedProducts.get(relatedProducts.size() - 1);
        assertThat(testRelatedProduct.getIsSuggested()).isEqualTo(UPDATED_IS_SUGGESTED);
        assertThat(testRelatedProduct.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void deleteRelatedProduct() throws Exception {
        // Initialize the database
        relatedProductRepository.saveAndFlush(relatedProduct);

		int databaseSizeBeforeDelete = relatedProductRepository.findAll().size();

        // Get the relatedProduct
        restRelatedProductMockMvc.perform(delete("/api/relatedProducts/{id}", relatedProduct.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RelatedProduct> relatedProducts = relatedProductRepository.findAll();
        assertThat(relatedProducts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
