package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.GoodsReceivedAudit;
import com.enginemobi.bssuite.repository.GoodsReceivedAuditRepository;
import com.enginemobi.bssuite.repository.search.GoodsReceivedAuditSearchRepository;
import com.enginemobi.bssuite.web.rest.dto.GoodsReceivedAuditDTO;
import com.enginemobi.bssuite.web.rest.mapper.GoodsReceivedAuditMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.enginemobi.bssuite.domain.enumeration.GoodsReceiptType;

/**
 * Test class for the GoodsReceivedAuditResource REST controller.
 *
 * @see GoodsReceivedAuditResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GoodsReceivedAuditResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_RECEIVED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_RECEIVED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_RECEIVED_ON_STR = dateTimeFormatter.format(DEFAULT_RECEIVED_ON);
    private static final String DEFAULT_TXN_NUMBER = "AAAAA";
    private static final String UPDATED_TXN_NUMBER = "BBBBB";


private static final GoodsReceiptType DEFAULT_TYPE_RECEIPT = GoodsReceiptType.PURCHASEORDER;
    private static final GoodsReceiptType UPDATED_TYPE_RECEIPT = GoodsReceiptType.STOCKRETURN;

    private static final Double DEFAULT_QTY_RECEIVED = 1D;
    private static final Double UPDATED_QTY_RECEIVED = 2D;

    @Inject
    private GoodsReceivedAuditRepository goodsReceivedAuditRepository;

    @Inject
    private GoodsReceivedAuditMapper goodsReceivedAuditMapper;

    @Inject
    private GoodsReceivedAuditSearchRepository goodsReceivedAuditSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGoodsReceivedAuditMockMvc;

    private GoodsReceivedAudit goodsReceivedAudit;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GoodsReceivedAuditResource goodsReceivedAuditResource = new GoodsReceivedAuditResource();
        ReflectionTestUtils.setField(goodsReceivedAuditResource, "goodsReceivedAuditRepository", goodsReceivedAuditRepository);
        ReflectionTestUtils.setField(goodsReceivedAuditResource, "goodsReceivedAuditMapper", goodsReceivedAuditMapper);
        ReflectionTestUtils.setField(goodsReceivedAuditResource, "goodsReceivedAuditSearchRepository", goodsReceivedAuditSearchRepository);
        this.restGoodsReceivedAuditMockMvc = MockMvcBuilders.standaloneSetup(goodsReceivedAuditResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        goodsReceivedAudit = new GoodsReceivedAudit();
        goodsReceivedAudit.setReceivedOn(DEFAULT_RECEIVED_ON);
        goodsReceivedAudit.setTxnNumber(DEFAULT_TXN_NUMBER);
        goodsReceivedAudit.setTypeReceipt(DEFAULT_TYPE_RECEIPT);
        goodsReceivedAudit.setQtyReceived(DEFAULT_QTY_RECEIVED);
    }

    @Test
    @Transactional
    public void createGoodsReceivedAudit() throws Exception {
        int databaseSizeBeforeCreate = goodsReceivedAuditRepository.findAll().size();

        // Create the GoodsReceivedAudit
        GoodsReceivedAuditDTO goodsReceivedAuditDTO = goodsReceivedAuditMapper.goodsReceivedAuditToGoodsReceivedAuditDTO(goodsReceivedAudit);

        restGoodsReceivedAuditMockMvc.perform(post("/api/goodsReceivedAudits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(goodsReceivedAuditDTO)))
                .andExpect(status().isCreated());

        // Validate the GoodsReceivedAudit in the database
        List<GoodsReceivedAudit> goodsReceivedAudits = goodsReceivedAuditRepository.findAll();
        assertThat(goodsReceivedAudits).hasSize(databaseSizeBeforeCreate + 1);
        GoodsReceivedAudit testGoodsReceivedAudit = goodsReceivedAudits.get(goodsReceivedAudits.size() - 1);
        assertThat(testGoodsReceivedAudit.getReceivedOn()).isEqualTo(DEFAULT_RECEIVED_ON);
        assertThat(testGoodsReceivedAudit.getTxnNumber()).isEqualTo(DEFAULT_TXN_NUMBER);
        assertThat(testGoodsReceivedAudit.getTypeReceipt()).isEqualTo(DEFAULT_TYPE_RECEIPT);
        assertThat(testGoodsReceivedAudit.getQtyReceived()).isEqualTo(DEFAULT_QTY_RECEIVED);
    }

    @Test
    @Transactional
    public void getAllGoodsReceivedAudits() throws Exception {
        // Initialize the database
        goodsReceivedAuditRepository.saveAndFlush(goodsReceivedAudit);

        // Get all the goodsReceivedAudits
        restGoodsReceivedAuditMockMvc.perform(get("/api/goodsReceivedAudits"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(goodsReceivedAudit.getId().intValue())))
                .andExpect(jsonPath("$.[*].receivedOn").value(hasItem(DEFAULT_RECEIVED_ON_STR)))
                .andExpect(jsonPath("$.[*].txnNumber").value(hasItem(DEFAULT_TXN_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].typeReceipt").value(hasItem(DEFAULT_TYPE_RECEIPT.toString())))
                .andExpect(jsonPath("$.[*].qtyReceived").value(hasItem(DEFAULT_QTY_RECEIVED.doubleValue())));
    }

    @Test
    @Transactional
    public void getGoodsReceivedAudit() throws Exception {
        // Initialize the database
        goodsReceivedAuditRepository.saveAndFlush(goodsReceivedAudit);

        // Get the goodsReceivedAudit
        restGoodsReceivedAuditMockMvc.perform(get("/api/goodsReceivedAudits/{id}", goodsReceivedAudit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(goodsReceivedAudit.getId().intValue()))
            .andExpect(jsonPath("$.receivedOn").value(DEFAULT_RECEIVED_ON_STR))
            .andExpect(jsonPath("$.txnNumber").value(DEFAULT_TXN_NUMBER.toString()))
            .andExpect(jsonPath("$.typeReceipt").value(DEFAULT_TYPE_RECEIPT.toString()))
            .andExpect(jsonPath("$.qtyReceived").value(DEFAULT_QTY_RECEIVED.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGoodsReceivedAudit() throws Exception {
        // Get the goodsReceivedAudit
        restGoodsReceivedAuditMockMvc.perform(get("/api/goodsReceivedAudits/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGoodsReceivedAudit() throws Exception {
        // Initialize the database
        goodsReceivedAuditRepository.saveAndFlush(goodsReceivedAudit);

		int databaseSizeBeforeUpdate = goodsReceivedAuditRepository.findAll().size();

        // Update the goodsReceivedAudit
        goodsReceivedAudit.setReceivedOn(UPDATED_RECEIVED_ON);
        goodsReceivedAudit.setTxnNumber(UPDATED_TXN_NUMBER);
        goodsReceivedAudit.setTypeReceipt(UPDATED_TYPE_RECEIPT);
        goodsReceivedAudit.setQtyReceived(UPDATED_QTY_RECEIVED);
        GoodsReceivedAuditDTO goodsReceivedAuditDTO = goodsReceivedAuditMapper.goodsReceivedAuditToGoodsReceivedAuditDTO(goodsReceivedAudit);

        restGoodsReceivedAuditMockMvc.perform(put("/api/goodsReceivedAudits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(goodsReceivedAuditDTO)))
                .andExpect(status().isOk());

        // Validate the GoodsReceivedAudit in the database
        List<GoodsReceivedAudit> goodsReceivedAudits = goodsReceivedAuditRepository.findAll();
        assertThat(goodsReceivedAudits).hasSize(databaseSizeBeforeUpdate);
        GoodsReceivedAudit testGoodsReceivedAudit = goodsReceivedAudits.get(goodsReceivedAudits.size() - 1);
        assertThat(testGoodsReceivedAudit.getReceivedOn()).isEqualTo(UPDATED_RECEIVED_ON);
        assertThat(testGoodsReceivedAudit.getTxnNumber()).isEqualTo(UPDATED_TXN_NUMBER);
        assertThat(testGoodsReceivedAudit.getTypeReceipt()).isEqualTo(UPDATED_TYPE_RECEIPT);
        assertThat(testGoodsReceivedAudit.getQtyReceived()).isEqualTo(UPDATED_QTY_RECEIVED);
    }

    @Test
    @Transactional
    public void deleteGoodsReceivedAudit() throws Exception {
        // Initialize the database
        goodsReceivedAuditRepository.saveAndFlush(goodsReceivedAudit);

		int databaseSizeBeforeDelete = goodsReceivedAuditRepository.findAll().size();

        // Get the goodsReceivedAudit
        restGoodsReceivedAuditMockMvc.perform(delete("/api/goodsReceivedAudits/{id}", goodsReceivedAudit.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GoodsReceivedAudit> goodsReceivedAudits = goodsReceivedAuditRepository.findAll();
        assertThat(goodsReceivedAudits).hasSize(databaseSizeBeforeDelete - 1);
    }
}
