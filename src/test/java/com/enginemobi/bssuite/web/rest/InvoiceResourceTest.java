package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.Invoice;
import com.enginemobi.bssuite.repository.InvoiceRepository;
import com.enginemobi.bssuite.repository.search.InvoiceSearchRepository;
import com.enginemobi.bssuite.web.rest.dto.InvoiceDTO;
import com.enginemobi.bssuite.web.rest.mapper.InvoiceMapper;

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
import org.joda.time.LocalDate;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.enginemobi.bssuite.domain.enumeration.InvoiceTxnType;
import com.enginemobi.bssuite.domain.enumeration.InvoiceSource;

/**
 * Test class for the InvoiceResource REST controller.
 *
 * @see InvoiceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InvoiceResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_INVOICE_NO = "AAAAA";
    private static final String UPDATED_INVOICE_NO = "BBBBB";


private static final InvoiceTxnType DEFAULT_INVOICE_TXN_TYPE = InvoiceTxnType.INVOICE;
    private static final InvoiceTxnType UPDATED_INVOICE_TXN_TYPE = InvoiceTxnType.ADJUSTMENTNOTE;

    private static final DateTime DEFAULT_TXN_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_TXN_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_TXN_DATE_STR = dateTimeFormatter.print(DEFAULT_TXN_DATE);

    private static final LocalDate DEFAULT_DUE_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_DUE_DATE = new LocalDate();
    private static final String DEFAULT_REFERENCE = "AAAAA";
    private static final String UPDATED_REFERENCE = "BBBBB";
    private static final String DEFAULT_OUR_REF = "AAAAA";
    private static final String UPDATED_OUR_REF = "BBBBB";
    private static final String DEFAULT_OUR_REF2 = "AAAAA";
    private static final String UPDATED_OUR_REF2 = "BBBBB";
    private static final String DEFAULT_OUR_REF3 = "AAAAA";
    private static final String UPDATED_OUR_REF3 = "BBBBB";
    private static final String DEFAULT_TAX_EXEMPTION_CODE = "AAAAA";
    private static final String UPDATED_TAX_EXEMPTION_CODE = "BBBBB";

    private static final Boolean DEFAULT_IS_PENALTY_ISSUED = false;
    private static final Boolean UPDATED_IS_PENALTY_ISSUED = true;

    private static final BigDecimal DEFAULT_FREIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_FREIGHT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_HANDLING_CHARGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_HANDLING_CHARGE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CHARGE2 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CHARGE2 = new BigDecimal(2);

    private static final Boolean DEFAULT_IS_TAXABLE = false;
    private static final Boolean UPDATED_IS_TAXABLE = true;

    private static final Boolean DEFAULT_IS_LOCKED = false;
    private static final Boolean UPDATED_IS_LOCKED = true;

    private static final BigDecimal DEFAULT_ADJUST_TAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_ADJUST_TAX = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ADJUST_TAX_EXEMPT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ADJUST_TAX_EXEMPT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PAYMENT_CASH = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAYMENT_CASH = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PAYMENT_CHEQUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAYMENT_CHEQUE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PAYMENT_CREDIT_CARD = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAYMENT_CREDIT_CARD = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PAYMENT_DIRECT_DEPOSIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAYMENT_DIRECT_DEPOSIT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PAYMENT_VOUCHER = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAYMENT_VOUCHER = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PAYMENT_DIRECT_DEBIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAYMENT_DIRECT_DEBIT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PAYMENT1 = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAYMENT1 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PAYMENT2 = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAYMENT2 = new BigDecimal(2);
    private static final String DEFAULT_BANK = "AAAAA";
    private static final String UPDATED_BANK = "BBBBB";
    private static final String DEFAULT_BANK_BRANCH = "AAAAA";
    private static final String UPDATED_BANK_BRANCH = "BBBBB";
    private static final String DEFAULT_BANK_ACCOUNT = "AAAAA";
    private static final String UPDATED_BANK_ACCOUNT = "BBBBB";

    private static final LocalDate DEFAULT_DATE_OF_DEPOSIT = new LocalDate(0L);
    private static final LocalDate UPDATED_DATE_OF_DEPOSIT = new LocalDate();
    private static final String DEFAULT_DRAWER_NAME = "AAAAA";
    private static final String UPDATED_DRAWER_NAME = "BBBBB";

    private static final BigDecimal DEFAULT_NO_ALLOC = new BigDecimal(1);
    private static final BigDecimal UPDATED_NO_ALLOC = new BigDecimal(2);

    private static final BigDecimal DEFAULT_COIN_ROUND = new BigDecimal(1);
    private static final BigDecimal UPDATED_COIN_ROUND = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PREPAYMENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PREPAYMENT = new BigDecimal(2);
    private static final String DEFAULT_IN2 = "AAAAA";
    private static final String UPDATED_IN2 = "BBBBB";
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    private static final BigDecimal DEFAULT_TOTAL_TAX_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_TAX_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_SELL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_SELL_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_COST = new BigDecimal(2);

    private static final Boolean DEFAULT_IS_ON_HOLD = false;
    private static final Boolean UPDATED_IS_ON_HOLD = true;

    private static final Boolean DEFAULT_IS_LAY_BY = false;
    private static final Boolean UPDATED_IS_LAY_BY = true;

    private static final Boolean DEFAULT_IS_EXTERNAL_TXN = false;
    private static final Boolean UPDATED_IS_EXTERNAL_TXN = true;

    private static final Boolean DEFAULT_IS_SUSPENDED = false;
    private static final Boolean UPDATED_IS_SUSPENDED = true;


private static final InvoiceSource DEFAULT_GENERATED_FROM = InvoiceSource.INVOICE;
    private static final InvoiceSource UPDATED_GENERATED_FROM = InvoiceSource.SALESORDER;

    @Inject
    private InvoiceRepository invoiceRepository;

    @Inject
    private InvoiceMapper invoiceMapper;

    @Inject
    private InvoiceSearchRepository invoiceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInvoiceMockMvc;

    private Invoice invoice;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InvoiceResource invoiceResource = new InvoiceResource();
        ReflectionTestUtils.setField(invoiceResource, "invoiceRepository", invoiceRepository);
        ReflectionTestUtils.setField(invoiceResource, "invoiceMapper", invoiceMapper);
        ReflectionTestUtils.setField(invoiceResource, "invoiceSearchRepository", invoiceSearchRepository);
        this.restInvoiceMockMvc = MockMvcBuilders.standaloneSetup(invoiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        invoice = new Invoice();
        invoice.setInvoiceNo(DEFAULT_INVOICE_NO);
        invoice.setInvoiceTxnType(DEFAULT_INVOICE_TXN_TYPE);
        invoice.setTxnDate(DEFAULT_TXN_DATE);
        invoice.setDueDate(DEFAULT_DUE_DATE);
        invoice.setReference(DEFAULT_REFERENCE);
        invoice.setOurRef(DEFAULT_OUR_REF);
        invoice.setOurRef2(DEFAULT_OUR_REF2);
        invoice.setOurRef3(DEFAULT_OUR_REF3);
        invoice.setTaxExemptionCode(DEFAULT_TAX_EXEMPTION_CODE);
        invoice.setIsPenaltyIssued(DEFAULT_IS_PENALTY_ISSUED);
        invoice.setFreight(DEFAULT_FREIGHT);
        invoice.setHandlingCharge(DEFAULT_HANDLING_CHARGE);
        invoice.setCharge2(DEFAULT_CHARGE2);
        invoice.setIsTaxable(DEFAULT_IS_TAXABLE);
        invoice.setIsLocked(DEFAULT_IS_LOCKED);
        invoice.setAdjustTax(DEFAULT_ADJUST_TAX);
        invoice.setAdjustTaxExempt(DEFAULT_ADJUST_TAX_EXEMPT);
        invoice.setPaymentCash(DEFAULT_PAYMENT_CASH);
        invoice.setPaymentCheque(DEFAULT_PAYMENT_CHEQUE);
        invoice.setPaymentCreditCard(DEFAULT_PAYMENT_CREDIT_CARD);
        invoice.setPaymentDirectDeposit(DEFAULT_PAYMENT_DIRECT_DEPOSIT);
        invoice.setPaymentVoucher(DEFAULT_PAYMENT_VOUCHER);
        invoice.setPaymentDirectDebit(DEFAULT_PAYMENT_DIRECT_DEBIT);
        invoice.setPayment1(DEFAULT_PAYMENT1);
        invoice.setPayment2(DEFAULT_PAYMENT2);
        invoice.setBank(DEFAULT_BANK);
        invoice.setBankBranch(DEFAULT_BANK_BRANCH);
        invoice.setBankAccount(DEFAULT_BANK_ACCOUNT);
        invoice.setDateOfDeposit(DEFAULT_DATE_OF_DEPOSIT);
        invoice.setDrawerName(DEFAULT_DRAWER_NAME);
        invoice.setNoAlloc(DEFAULT_NO_ALLOC);
        invoice.setCoinRound(DEFAULT_COIN_ROUND);
        invoice.setPrepayment(DEFAULT_PREPAYMENT);
        invoice.setIn2(DEFAULT_IN2);
        invoice.setComment(DEFAULT_COMMENT);
        invoice.setTotalTaxAmount(DEFAULT_TOTAL_TAX_AMOUNT);
        invoice.setTotalSellPrice(DEFAULT_TOTAL_SELL_PRICE);
        invoice.setTotalCost(DEFAULT_TOTAL_COST);
        invoice.setIsOnHold(DEFAULT_IS_ON_HOLD);
        invoice.setIsLayBy(DEFAULT_IS_LAY_BY);
        invoice.setIsExternalTxn(DEFAULT_IS_EXTERNAL_TXN);
        invoice.setIsSuspended(DEFAULT_IS_SUSPENDED);
        invoice.setGeneratedFrom(DEFAULT_GENERATED_FROM);
    }

    @Test
    @Transactional
    public void createInvoice() throws Exception {
        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();

        // Create the Invoice
        InvoiceDTO invoiceDTO = invoiceMapper.invoiceToInvoiceDTO(invoice);

        restInvoiceMockMvc.perform(post("/api/invoices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
                .andExpect(status().isCreated());

        // Validate the Invoice in the database
        List<Invoice> invoices = invoiceRepository.findAll();
        assertThat(invoices).hasSize(databaseSizeBeforeCreate + 1);
        Invoice testInvoice = invoices.get(invoices.size() - 1);
        assertThat(testInvoice.getInvoiceNo()).isEqualTo(DEFAULT_INVOICE_NO);
        assertThat(testInvoice.getInvoiceTxnType()).isEqualTo(DEFAULT_INVOICE_TXN_TYPE);
        assertThat(testInvoice.getTxnDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_TXN_DATE);
        assertThat(testInvoice.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testInvoice.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testInvoice.getOurRef()).isEqualTo(DEFAULT_OUR_REF);
        assertThat(testInvoice.getOurRef2()).isEqualTo(DEFAULT_OUR_REF2);
        assertThat(testInvoice.getOurRef3()).isEqualTo(DEFAULT_OUR_REF3);
        assertThat(testInvoice.getTaxExemptionCode()).isEqualTo(DEFAULT_TAX_EXEMPTION_CODE);
        assertThat(testInvoice.getIsPenaltyIssued()).isEqualTo(DEFAULT_IS_PENALTY_ISSUED);
        assertThat(testInvoice.getFreight()).isEqualTo(DEFAULT_FREIGHT);
        assertThat(testInvoice.getHandlingCharge()).isEqualTo(DEFAULT_HANDLING_CHARGE);
        assertThat(testInvoice.getCharge2()).isEqualTo(DEFAULT_CHARGE2);
        assertThat(testInvoice.getIsTaxable()).isEqualTo(DEFAULT_IS_TAXABLE);
        assertThat(testInvoice.getIsLocked()).isEqualTo(DEFAULT_IS_LOCKED);
        assertThat(testInvoice.getAdjustTax()).isEqualTo(DEFAULT_ADJUST_TAX);
        assertThat(testInvoice.getAdjustTaxExempt()).isEqualTo(DEFAULT_ADJUST_TAX_EXEMPT);
        assertThat(testInvoice.getPaymentCash()).isEqualTo(DEFAULT_PAYMENT_CASH);
        assertThat(testInvoice.getPaymentCheque()).isEqualTo(DEFAULT_PAYMENT_CHEQUE);
        assertThat(testInvoice.getPaymentCreditCard()).isEqualTo(DEFAULT_PAYMENT_CREDIT_CARD);
        assertThat(testInvoice.getPaymentDirectDeposit()).isEqualTo(DEFAULT_PAYMENT_DIRECT_DEPOSIT);
        assertThat(testInvoice.getPaymentVoucher()).isEqualTo(DEFAULT_PAYMENT_VOUCHER);
        assertThat(testInvoice.getPaymentDirectDebit()).isEqualTo(DEFAULT_PAYMENT_DIRECT_DEBIT);
        assertThat(testInvoice.getPayment1()).isEqualTo(DEFAULT_PAYMENT1);
        assertThat(testInvoice.getPayment2()).isEqualTo(DEFAULT_PAYMENT2);
        assertThat(testInvoice.getBank()).isEqualTo(DEFAULT_BANK);
        assertThat(testInvoice.getBankBranch()).isEqualTo(DEFAULT_BANK_BRANCH);
        assertThat(testInvoice.getBankAccount()).isEqualTo(DEFAULT_BANK_ACCOUNT);
        assertThat(testInvoice.getDateOfDeposit()).isEqualTo(DEFAULT_DATE_OF_DEPOSIT);
        assertThat(testInvoice.getDrawerName()).isEqualTo(DEFAULT_DRAWER_NAME);
        assertThat(testInvoice.getNoAlloc()).isEqualTo(DEFAULT_NO_ALLOC);
        assertThat(testInvoice.getCoinRound()).isEqualTo(DEFAULT_COIN_ROUND);
        assertThat(testInvoice.getPrepayment()).isEqualTo(DEFAULT_PREPAYMENT);
        assertThat(testInvoice.getIn2()).isEqualTo(DEFAULT_IN2);
        assertThat(testInvoice.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testInvoice.getTotalTaxAmount()).isEqualTo(DEFAULT_TOTAL_TAX_AMOUNT);
        assertThat(testInvoice.getTotalSellPrice()).isEqualTo(DEFAULT_TOTAL_SELL_PRICE);
        assertThat(testInvoice.getTotalCost()).isEqualTo(DEFAULT_TOTAL_COST);
        assertThat(testInvoice.getIsOnHold()).isEqualTo(DEFAULT_IS_ON_HOLD);
        assertThat(testInvoice.getIsLayBy()).isEqualTo(DEFAULT_IS_LAY_BY);
        assertThat(testInvoice.getIsExternalTxn()).isEqualTo(DEFAULT_IS_EXTERNAL_TXN);
        assertThat(testInvoice.getIsSuspended()).isEqualTo(DEFAULT_IS_SUSPENDED);
        assertThat(testInvoice.getGeneratedFrom()).isEqualTo(DEFAULT_GENERATED_FROM);
    }

    @Test
    @Transactional
    public void getAllInvoices() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoices
        restInvoiceMockMvc.perform(get("/api/invoices"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
                .andExpect(jsonPath("$.[*].invoiceNo").value(hasItem(DEFAULT_INVOICE_NO.toString())))
                .andExpect(jsonPath("$.[*].invoiceTxnType").value(hasItem(DEFAULT_INVOICE_TXN_TYPE.toString())))
                .andExpect(jsonPath("$.[*].txnDate").value(hasItem(DEFAULT_TXN_DATE_STR)))
                .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
                .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
                .andExpect(jsonPath("$.[*].ourRef").value(hasItem(DEFAULT_OUR_REF.toString())))
                .andExpect(jsonPath("$.[*].ourRef2").value(hasItem(DEFAULT_OUR_REF2.toString())))
                .andExpect(jsonPath("$.[*].ourRef3").value(hasItem(DEFAULT_OUR_REF3.toString())))
                .andExpect(jsonPath("$.[*].taxExemptionCode").value(hasItem(DEFAULT_TAX_EXEMPTION_CODE.toString())))
                .andExpect(jsonPath("$.[*].isPenaltyIssued").value(hasItem(DEFAULT_IS_PENALTY_ISSUED.booleanValue())))
                .andExpect(jsonPath("$.[*].freight").value(hasItem(DEFAULT_FREIGHT.intValue())))
                .andExpect(jsonPath("$.[*].handlingCharge").value(hasItem(DEFAULT_HANDLING_CHARGE.intValue())))
                .andExpect(jsonPath("$.[*].charge2").value(hasItem(DEFAULT_CHARGE2.intValue())))
                .andExpect(jsonPath("$.[*].isTaxable").value(hasItem(DEFAULT_IS_TAXABLE.booleanValue())))
                .andExpect(jsonPath("$.[*].isLocked").value(hasItem(DEFAULT_IS_LOCKED.booleanValue())))
                .andExpect(jsonPath("$.[*].adjustTax").value(hasItem(DEFAULT_ADJUST_TAX.intValue())))
                .andExpect(jsonPath("$.[*].adjustTaxExempt").value(hasItem(DEFAULT_ADJUST_TAX_EXEMPT.intValue())))
                .andExpect(jsonPath("$.[*].paymentCash").value(hasItem(DEFAULT_PAYMENT_CASH.intValue())))
                .andExpect(jsonPath("$.[*].paymentCheque").value(hasItem(DEFAULT_PAYMENT_CHEQUE.intValue())))
                .andExpect(jsonPath("$.[*].paymentCreditCard").value(hasItem(DEFAULT_PAYMENT_CREDIT_CARD.intValue())))
                .andExpect(jsonPath("$.[*].paymentDirectDeposit").value(hasItem(DEFAULT_PAYMENT_DIRECT_DEPOSIT.intValue())))
                .andExpect(jsonPath("$.[*].paymentVoucher").value(hasItem(DEFAULT_PAYMENT_VOUCHER.intValue())))
                .andExpect(jsonPath("$.[*].paymentDirectDebit").value(hasItem(DEFAULT_PAYMENT_DIRECT_DEBIT.intValue())))
                .andExpect(jsonPath("$.[*].payment1").value(hasItem(DEFAULT_PAYMENT1.intValue())))
                .andExpect(jsonPath("$.[*].payment2").value(hasItem(DEFAULT_PAYMENT2.intValue())))
                .andExpect(jsonPath("$.[*].bank").value(hasItem(DEFAULT_BANK.toString())))
                .andExpect(jsonPath("$.[*].bankBranch").value(hasItem(DEFAULT_BANK_BRANCH.toString())))
                .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT.toString())))
                .andExpect(jsonPath("$.[*].dateOfDeposit").value(hasItem(DEFAULT_DATE_OF_DEPOSIT.toString())))
                .andExpect(jsonPath("$.[*].drawerName").value(hasItem(DEFAULT_DRAWER_NAME.toString())))
                .andExpect(jsonPath("$.[*].NoAlloc").value(hasItem(DEFAULT_NO_ALLOC.intValue())))
                .andExpect(jsonPath("$.[*].coinRound").value(hasItem(DEFAULT_COIN_ROUND.intValue())))
                .andExpect(jsonPath("$.[*].prepayment").value(hasItem(DEFAULT_PREPAYMENT.intValue())))
                .andExpect(jsonPath("$.[*].in2").value(hasItem(DEFAULT_IN2.toString())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
                .andExpect(jsonPath("$.[*].totalTaxAmount").value(hasItem(DEFAULT_TOTAL_TAX_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].totalSellPrice").value(hasItem(DEFAULT_TOTAL_SELL_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].totalCost").value(hasItem(DEFAULT_TOTAL_COST.intValue())))
                .andExpect(jsonPath("$.[*].isOnHold").value(hasItem(DEFAULT_IS_ON_HOLD.booleanValue())))
                .andExpect(jsonPath("$.[*].isLayBy").value(hasItem(DEFAULT_IS_LAY_BY.booleanValue())))
                .andExpect(jsonPath("$.[*].isExternalTxn").value(hasItem(DEFAULT_IS_EXTERNAL_TXN.booleanValue())))
                .andExpect(jsonPath("$.[*].isSuspended").value(hasItem(DEFAULT_IS_SUSPENDED.booleanValue())))
                .andExpect(jsonPath("$.[*].generatedFrom").value(hasItem(DEFAULT_GENERATED_FROM.toString())));
    }

    @Test
    @Transactional
    public void getInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", invoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(invoice.getId().intValue()))
            .andExpect(jsonPath("$.invoiceNo").value(DEFAULT_INVOICE_NO.toString()))
            .andExpect(jsonPath("$.invoiceTxnType").value(DEFAULT_INVOICE_TXN_TYPE.toString()))
            .andExpect(jsonPath("$.txnDate").value(DEFAULT_TXN_DATE_STR))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.ourRef").value(DEFAULT_OUR_REF.toString()))
            .andExpect(jsonPath("$.ourRef2").value(DEFAULT_OUR_REF2.toString()))
            .andExpect(jsonPath("$.ourRef3").value(DEFAULT_OUR_REF3.toString()))
            .andExpect(jsonPath("$.taxExemptionCode").value(DEFAULT_TAX_EXEMPTION_CODE.toString()))
            .andExpect(jsonPath("$.isPenaltyIssued").value(DEFAULT_IS_PENALTY_ISSUED.booleanValue()))
            .andExpect(jsonPath("$.freight").value(DEFAULT_FREIGHT.intValue()))
            .andExpect(jsonPath("$.handlingCharge").value(DEFAULT_HANDLING_CHARGE.intValue()))
            .andExpect(jsonPath("$.charge2").value(DEFAULT_CHARGE2.intValue()))
            .andExpect(jsonPath("$.isTaxable").value(DEFAULT_IS_TAXABLE.booleanValue()))
            .andExpect(jsonPath("$.isLocked").value(DEFAULT_IS_LOCKED.booleanValue()))
            .andExpect(jsonPath("$.adjustTax").value(DEFAULT_ADJUST_TAX.intValue()))
            .andExpect(jsonPath("$.adjustTaxExempt").value(DEFAULT_ADJUST_TAX_EXEMPT.intValue()))
            .andExpect(jsonPath("$.paymentCash").value(DEFAULT_PAYMENT_CASH.intValue()))
            .andExpect(jsonPath("$.paymentCheque").value(DEFAULT_PAYMENT_CHEQUE.intValue()))
            .andExpect(jsonPath("$.paymentCreditCard").value(DEFAULT_PAYMENT_CREDIT_CARD.intValue()))
            .andExpect(jsonPath("$.paymentDirectDeposit").value(DEFAULT_PAYMENT_DIRECT_DEPOSIT.intValue()))
            .andExpect(jsonPath("$.paymentVoucher").value(DEFAULT_PAYMENT_VOUCHER.intValue()))
            .andExpect(jsonPath("$.paymentDirectDebit").value(DEFAULT_PAYMENT_DIRECT_DEBIT.intValue()))
            .andExpect(jsonPath("$.payment1").value(DEFAULT_PAYMENT1.intValue()))
            .andExpect(jsonPath("$.payment2").value(DEFAULT_PAYMENT2.intValue()))
            .andExpect(jsonPath("$.bank").value(DEFAULT_BANK.toString()))
            .andExpect(jsonPath("$.bankBranch").value(DEFAULT_BANK_BRANCH.toString()))
            .andExpect(jsonPath("$.bankAccount").value(DEFAULT_BANK_ACCOUNT.toString()))
            .andExpect(jsonPath("$.dateOfDeposit").value(DEFAULT_DATE_OF_DEPOSIT.toString()))
            .andExpect(jsonPath("$.drawerName").value(DEFAULT_DRAWER_NAME.toString()))
            .andExpect(jsonPath("$.NoAlloc").value(DEFAULT_NO_ALLOC.intValue()))
            .andExpect(jsonPath("$.coinRound").value(DEFAULT_COIN_ROUND.intValue()))
            .andExpect(jsonPath("$.prepayment").value(DEFAULT_PREPAYMENT.intValue()))
            .andExpect(jsonPath("$.in2").value(DEFAULT_IN2.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.totalTaxAmount").value(DEFAULT_TOTAL_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.totalSellPrice").value(DEFAULT_TOTAL_SELL_PRICE.intValue()))
            .andExpect(jsonPath("$.totalCost").value(DEFAULT_TOTAL_COST.intValue()))
            .andExpect(jsonPath("$.isOnHold").value(DEFAULT_IS_ON_HOLD.booleanValue()))
            .andExpect(jsonPath("$.isLayBy").value(DEFAULT_IS_LAY_BY.booleanValue()))
            .andExpect(jsonPath("$.isExternalTxn").value(DEFAULT_IS_EXTERNAL_TXN.booleanValue()))
            .andExpect(jsonPath("$.isSuspended").value(DEFAULT_IS_SUSPENDED.booleanValue()))
            .andExpect(jsonPath("$.generatedFrom").value(DEFAULT_GENERATED_FROM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvoice() throws Exception {
        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

		int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // Update the invoice
        invoice.setInvoiceNo(UPDATED_INVOICE_NO);
        invoice.setInvoiceTxnType(UPDATED_INVOICE_TXN_TYPE);
        invoice.setTxnDate(UPDATED_TXN_DATE);
        invoice.setDueDate(UPDATED_DUE_DATE);
        invoice.setReference(UPDATED_REFERENCE);
        invoice.setOurRef(UPDATED_OUR_REF);
        invoice.setOurRef2(UPDATED_OUR_REF2);
        invoice.setOurRef3(UPDATED_OUR_REF3);
        invoice.setTaxExemptionCode(UPDATED_TAX_EXEMPTION_CODE);
        invoice.setIsPenaltyIssued(UPDATED_IS_PENALTY_ISSUED);
        invoice.setFreight(UPDATED_FREIGHT);
        invoice.setHandlingCharge(UPDATED_HANDLING_CHARGE);
        invoice.setCharge2(UPDATED_CHARGE2);
        invoice.setIsTaxable(UPDATED_IS_TAXABLE);
        invoice.setIsLocked(UPDATED_IS_LOCKED);
        invoice.setAdjustTax(UPDATED_ADJUST_TAX);
        invoice.setAdjustTaxExempt(UPDATED_ADJUST_TAX_EXEMPT);
        invoice.setPaymentCash(UPDATED_PAYMENT_CASH);
        invoice.setPaymentCheque(UPDATED_PAYMENT_CHEQUE);
        invoice.setPaymentCreditCard(UPDATED_PAYMENT_CREDIT_CARD);
        invoice.setPaymentDirectDeposit(UPDATED_PAYMENT_DIRECT_DEPOSIT);
        invoice.setPaymentVoucher(UPDATED_PAYMENT_VOUCHER);
        invoice.setPaymentDirectDebit(UPDATED_PAYMENT_DIRECT_DEBIT);
        invoice.setPayment1(UPDATED_PAYMENT1);
        invoice.setPayment2(UPDATED_PAYMENT2);
        invoice.setBank(UPDATED_BANK);
        invoice.setBankBranch(UPDATED_BANK_BRANCH);
        invoice.setBankAccount(UPDATED_BANK_ACCOUNT);
        invoice.setDateOfDeposit(UPDATED_DATE_OF_DEPOSIT);
        invoice.setDrawerName(UPDATED_DRAWER_NAME);
        invoice.setNoAlloc(UPDATED_NO_ALLOC);
        invoice.setCoinRound(UPDATED_COIN_ROUND);
        invoice.setPrepayment(UPDATED_PREPAYMENT);
        invoice.setIn2(UPDATED_IN2);
        invoice.setComment(UPDATED_COMMENT);
        invoice.setTotalTaxAmount(UPDATED_TOTAL_TAX_AMOUNT);
        invoice.setTotalSellPrice(UPDATED_TOTAL_SELL_PRICE);
        invoice.setTotalCost(UPDATED_TOTAL_COST);
        invoice.setIsOnHold(UPDATED_IS_ON_HOLD);
        invoice.setIsLayBy(UPDATED_IS_LAY_BY);
        invoice.setIsExternalTxn(UPDATED_IS_EXTERNAL_TXN);
        invoice.setIsSuspended(UPDATED_IS_SUSPENDED);
        invoice.setGeneratedFrom(UPDATED_GENERATED_FROM);
        InvoiceDTO invoiceDTO = invoiceMapper.invoiceToInvoiceDTO(invoice);

        restInvoiceMockMvc.perform(put("/api/invoices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
                .andExpect(status().isOk());

        // Validate the Invoice in the database
        List<Invoice> invoices = invoiceRepository.findAll();
        assertThat(invoices).hasSize(databaseSizeBeforeUpdate);
        Invoice testInvoice = invoices.get(invoices.size() - 1);
        assertThat(testInvoice.getInvoiceNo()).isEqualTo(UPDATED_INVOICE_NO);
        assertThat(testInvoice.getInvoiceTxnType()).isEqualTo(UPDATED_INVOICE_TXN_TYPE);
        assertThat(testInvoice.getTxnDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_TXN_DATE);
        assertThat(testInvoice.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testInvoice.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testInvoice.getOurRef()).isEqualTo(UPDATED_OUR_REF);
        assertThat(testInvoice.getOurRef2()).isEqualTo(UPDATED_OUR_REF2);
        assertThat(testInvoice.getOurRef3()).isEqualTo(UPDATED_OUR_REF3);
        assertThat(testInvoice.getTaxExemptionCode()).isEqualTo(UPDATED_TAX_EXEMPTION_CODE);
        assertThat(testInvoice.getIsPenaltyIssued()).isEqualTo(UPDATED_IS_PENALTY_ISSUED);
        assertThat(testInvoice.getFreight()).isEqualTo(UPDATED_FREIGHT);
        assertThat(testInvoice.getHandlingCharge()).isEqualTo(UPDATED_HANDLING_CHARGE);
        assertThat(testInvoice.getCharge2()).isEqualTo(UPDATED_CHARGE2);
        assertThat(testInvoice.getIsTaxable()).isEqualTo(UPDATED_IS_TAXABLE);
        assertThat(testInvoice.getIsLocked()).isEqualTo(UPDATED_IS_LOCKED);
        assertThat(testInvoice.getAdjustTax()).isEqualTo(UPDATED_ADJUST_TAX);
        assertThat(testInvoice.getAdjustTaxExempt()).isEqualTo(UPDATED_ADJUST_TAX_EXEMPT);
        assertThat(testInvoice.getPaymentCash()).isEqualTo(UPDATED_PAYMENT_CASH);
        assertThat(testInvoice.getPaymentCheque()).isEqualTo(UPDATED_PAYMENT_CHEQUE);
        assertThat(testInvoice.getPaymentCreditCard()).isEqualTo(UPDATED_PAYMENT_CREDIT_CARD);
        assertThat(testInvoice.getPaymentDirectDeposit()).isEqualTo(UPDATED_PAYMENT_DIRECT_DEPOSIT);
        assertThat(testInvoice.getPaymentVoucher()).isEqualTo(UPDATED_PAYMENT_VOUCHER);
        assertThat(testInvoice.getPaymentDirectDebit()).isEqualTo(UPDATED_PAYMENT_DIRECT_DEBIT);
        assertThat(testInvoice.getPayment1()).isEqualTo(UPDATED_PAYMENT1);
        assertThat(testInvoice.getPayment2()).isEqualTo(UPDATED_PAYMENT2);
        assertThat(testInvoice.getBank()).isEqualTo(UPDATED_BANK);
        assertThat(testInvoice.getBankBranch()).isEqualTo(UPDATED_BANK_BRANCH);
        assertThat(testInvoice.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testInvoice.getDateOfDeposit()).isEqualTo(UPDATED_DATE_OF_DEPOSIT);
        assertThat(testInvoice.getDrawerName()).isEqualTo(UPDATED_DRAWER_NAME);
        assertThat(testInvoice.getNoAlloc()).isEqualTo(UPDATED_NO_ALLOC);
        assertThat(testInvoice.getCoinRound()).isEqualTo(UPDATED_COIN_ROUND);
        assertThat(testInvoice.getPrepayment()).isEqualTo(UPDATED_PREPAYMENT);
        assertThat(testInvoice.getIn2()).isEqualTo(UPDATED_IN2);
        assertThat(testInvoice.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testInvoice.getTotalTaxAmount()).isEqualTo(UPDATED_TOTAL_TAX_AMOUNT);
        assertThat(testInvoice.getTotalSellPrice()).isEqualTo(UPDATED_TOTAL_SELL_PRICE);
        assertThat(testInvoice.getTotalCost()).isEqualTo(UPDATED_TOTAL_COST);
        assertThat(testInvoice.getIsOnHold()).isEqualTo(UPDATED_IS_ON_HOLD);
        assertThat(testInvoice.getIsLayBy()).isEqualTo(UPDATED_IS_LAY_BY);
        assertThat(testInvoice.getIsExternalTxn()).isEqualTo(UPDATED_IS_EXTERNAL_TXN);
        assertThat(testInvoice.getIsSuspended()).isEqualTo(UPDATED_IS_SUSPENDED);
        assertThat(testInvoice.getGeneratedFrom()).isEqualTo(UPDATED_GENERATED_FROM);
    }

    @Test
    @Transactional
    public void deleteInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

		int databaseSizeBeforeDelete = invoiceRepository.findAll().size();

        // Get the invoice
        restInvoiceMockMvc.perform(delete("/api/invoices/{id}", invoice.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Invoice> invoices = invoiceRepository.findAll();
        assertThat(invoices).hasSize(databaseSizeBeforeDelete - 1);
    }
}
