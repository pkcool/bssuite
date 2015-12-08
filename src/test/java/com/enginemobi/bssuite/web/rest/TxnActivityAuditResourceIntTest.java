package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.TxnActivityAudit;
import com.enginemobi.bssuite.repository.TxnActivityAuditRepository;
import com.enginemobi.bssuite.repository.search.TxnActivityAuditSearchRepository;
import com.enginemobi.bssuite.web.rest.dto.TxnActivityAuditDTO;
import com.enginemobi.bssuite.web.rest.mapper.TxnActivityAuditMapper;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.enginemobi.bssuite.domain.enumeration.TxnType;
import com.enginemobi.bssuite.domain.enumeration.TxnEditType;

/**
 * Test class for the TxnActivityAuditResource REST controller.
 *
 * @see TxnActivityAuditResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TxnActivityAuditResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_EDITED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_EDITED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_EDITED_ON_STR = dateTimeFormatter.format(DEFAULT_EDITED_ON);
    private static final String DEFAULT_TXN_NUMBER = "AAAAA";
    private static final String UPDATED_TXN_NUMBER = "BBBBB";


    private static final TxnType DEFAULT_TXN_TYPE = TxnType.PURCHASEORDER;
    private static final TxnType UPDATED_TXN_TYPE = TxnType.SALESORDER;

    private static final BigDecimal DEFAULT_TXN_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TXN_AMOUNT = new BigDecimal(2);
    private static final String DEFAULT_BANK_ACC = "AAAAA";
    private static final String UPDATED_BANK_ACC = "BBBBB";


    private static final TxnEditType DEFAULT_EDIT_TYPE = TxnEditType.ADD;
    private static final TxnEditType UPDATED_EDIT_TYPE = TxnEditType.EDIT;

    @Inject
    private TxnActivityAuditRepository txnActivityAuditRepository;

    @Inject
    private TxnActivityAuditMapper txnActivityAuditMapper;

    @Inject
    private TxnActivityAuditSearchRepository txnActivityAuditSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTxnActivityAuditMockMvc;

    private TxnActivityAudit txnActivityAudit;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TxnActivityAuditResource txnActivityAuditResource = new TxnActivityAuditResource();
        ReflectionTestUtils.setField(txnActivityAuditResource, "txnActivityAuditSearchRepository", txnActivityAuditSearchRepository);
        ReflectionTestUtils.setField(txnActivityAuditResource, "txnActivityAuditRepository", txnActivityAuditRepository);
        ReflectionTestUtils.setField(txnActivityAuditResource, "txnActivityAuditMapper", txnActivityAuditMapper);
        this.restTxnActivityAuditMockMvc = MockMvcBuilders.standaloneSetup(txnActivityAuditResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        txnActivityAudit = new TxnActivityAudit();
        txnActivityAudit.setEditedOn(DEFAULT_EDITED_ON);
        txnActivityAudit.setTxnNumber(DEFAULT_TXN_NUMBER);
        txnActivityAudit.setTxnType(DEFAULT_TXN_TYPE);
        txnActivityAudit.setTxnAmount(DEFAULT_TXN_AMOUNT);
        txnActivityAudit.setBankAcc(DEFAULT_BANK_ACC);
        txnActivityAudit.setEditType(DEFAULT_EDIT_TYPE);
    }

    @Test
    @Transactional
    public void createTxnActivityAudit() throws Exception {
        int databaseSizeBeforeCreate = txnActivityAuditRepository.findAll().size();

        // Create the TxnActivityAudit
        TxnActivityAuditDTO txnActivityAuditDTO = txnActivityAuditMapper.txnActivityAuditToTxnActivityAuditDTO(txnActivityAudit);

        restTxnActivityAuditMockMvc.perform(post("/api/txnActivityAudits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(txnActivityAuditDTO)))
                .andExpect(status().isCreated());

        // Validate the TxnActivityAudit in the database
        List<TxnActivityAudit> txnActivityAudits = txnActivityAuditRepository.findAll();
        assertThat(txnActivityAudits).hasSize(databaseSizeBeforeCreate + 1);
        TxnActivityAudit testTxnActivityAudit = txnActivityAudits.get(txnActivityAudits.size() - 1);
        assertThat(testTxnActivityAudit.getEditedOn()).isEqualTo(DEFAULT_EDITED_ON);
        assertThat(testTxnActivityAudit.getTxnNumber()).isEqualTo(DEFAULT_TXN_NUMBER);
        assertThat(testTxnActivityAudit.getTxnType()).isEqualTo(DEFAULT_TXN_TYPE);
        assertThat(testTxnActivityAudit.getTxnAmount()).isEqualTo(DEFAULT_TXN_AMOUNT);
        assertThat(testTxnActivityAudit.getBankAcc()).isEqualTo(DEFAULT_BANK_ACC);
        assertThat(testTxnActivityAudit.getEditType()).isEqualTo(DEFAULT_EDIT_TYPE);
    }

    @Test
    @Transactional
    public void getAllTxnActivityAudits() throws Exception {
        // Initialize the database
        txnActivityAuditRepository.saveAndFlush(txnActivityAudit);

        // Get all the txnActivityAudits
        restTxnActivityAuditMockMvc.perform(get("/api/txnActivityAudits?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(txnActivityAudit.getId().intValue())))
                .andExpect(jsonPath("$.[*].editedOn").value(hasItem(DEFAULT_EDITED_ON_STR)))
                .andExpect(jsonPath("$.[*].txnNumber").value(hasItem(DEFAULT_TXN_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].txnType").value(hasItem(DEFAULT_TXN_TYPE.toString())))
                .andExpect(jsonPath("$.[*].txnAmount").value(hasItem(DEFAULT_TXN_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].bankAcc").value(hasItem(DEFAULT_BANK_ACC.toString())))
                .andExpect(jsonPath("$.[*].editType").value(hasItem(DEFAULT_EDIT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getTxnActivityAudit() throws Exception {
        // Initialize the database
        txnActivityAuditRepository.saveAndFlush(txnActivityAudit);

        // Get the txnActivityAudit
        restTxnActivityAuditMockMvc.perform(get("/api/txnActivityAudits/{id}", txnActivityAudit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(txnActivityAudit.getId().intValue()))
            .andExpect(jsonPath("$.editedOn").value(DEFAULT_EDITED_ON_STR))
            .andExpect(jsonPath("$.txnNumber").value(DEFAULT_TXN_NUMBER.toString()))
            .andExpect(jsonPath("$.txnType").value(DEFAULT_TXN_TYPE.toString()))
            .andExpect(jsonPath("$.txnAmount").value(DEFAULT_TXN_AMOUNT.intValue()))
            .andExpect(jsonPath("$.bankAcc").value(DEFAULT_BANK_ACC.toString()))
            .andExpect(jsonPath("$.editType").value(DEFAULT_EDIT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTxnActivityAudit() throws Exception {
        // Get the txnActivityAudit
        restTxnActivityAuditMockMvc.perform(get("/api/txnActivityAudits/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTxnActivityAudit() throws Exception {
        // Initialize the database
        txnActivityAuditRepository.saveAndFlush(txnActivityAudit);

		int databaseSizeBeforeUpdate = txnActivityAuditRepository.findAll().size();

        // Update the txnActivityAudit
        txnActivityAudit.setEditedOn(UPDATED_EDITED_ON);
        txnActivityAudit.setTxnNumber(UPDATED_TXN_NUMBER);
        txnActivityAudit.setTxnType(UPDATED_TXN_TYPE);
        txnActivityAudit.setTxnAmount(UPDATED_TXN_AMOUNT);
        txnActivityAudit.setBankAcc(UPDATED_BANK_ACC);
        txnActivityAudit.setEditType(UPDATED_EDIT_TYPE);
        TxnActivityAuditDTO txnActivityAuditDTO = txnActivityAuditMapper.txnActivityAuditToTxnActivityAuditDTO(txnActivityAudit);

        restTxnActivityAuditMockMvc.perform(put("/api/txnActivityAudits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(txnActivityAuditDTO)))
                .andExpect(status().isOk());

        // Validate the TxnActivityAudit in the database
        List<TxnActivityAudit> txnActivityAudits = txnActivityAuditRepository.findAll();
        assertThat(txnActivityAudits).hasSize(databaseSizeBeforeUpdate);
        TxnActivityAudit testTxnActivityAudit = txnActivityAudits.get(txnActivityAudits.size() - 1);
        assertThat(testTxnActivityAudit.getEditedOn()).isEqualTo(UPDATED_EDITED_ON);
        assertThat(testTxnActivityAudit.getTxnNumber()).isEqualTo(UPDATED_TXN_NUMBER);
        assertThat(testTxnActivityAudit.getTxnType()).isEqualTo(UPDATED_TXN_TYPE);
        assertThat(testTxnActivityAudit.getTxnAmount()).isEqualTo(UPDATED_TXN_AMOUNT);
        assertThat(testTxnActivityAudit.getBankAcc()).isEqualTo(UPDATED_BANK_ACC);
        assertThat(testTxnActivityAudit.getEditType()).isEqualTo(UPDATED_EDIT_TYPE);
    }

    @Test
    @Transactional
    public void deleteTxnActivityAudit() throws Exception {
        // Initialize the database
        txnActivityAuditRepository.saveAndFlush(txnActivityAudit);

		int databaseSizeBeforeDelete = txnActivityAuditRepository.findAll().size();

        // Get the txnActivityAudit
        restTxnActivityAuditMockMvc.perform(delete("/api/txnActivityAudits/{id}", txnActivityAudit.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TxnActivityAudit> txnActivityAudits = txnActivityAuditRepository.findAll();
        assertThat(txnActivityAudits).hasSize(databaseSizeBeforeDelete - 1);
    }
}
