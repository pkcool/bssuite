package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.Customer;
import com.enginemobi.bssuite.repository.CustomerRepository;
import com.enginemobi.bssuite.repository.search.CustomerSearchRepository;
import com.enginemobi.bssuite.web.rest.dto.CustomerDTO;
import com.enginemobi.bssuite.web.rest.mapper.CustomerMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.enginemobi.bssuite.domain.enumeration.CustomerAgeingMethod;
import com.enginemobi.bssuite.domain.enumeration.CreditCardType;
import com.enginemobi.bssuite.domain.enumeration.CustomerAccountType;
import com.enginemobi.bssuite.domain.enumeration.CustomerInvoiceDeliveryMethod;
import com.enginemobi.bssuite.domain.enumeration.CustomerStatementDeliveryMethod;

/**
 * Test class for the CustomerResource REST controller.
 *
 * @see CustomerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CustomerResourceTest {

    private static final String DEFAULT_CODE = "AA";
    private static final String UPDATED_CODE = "BB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_TRADING_NAME = "AAAAA";
    private static final String UPDATED_TRADING_NAME = "BBBBB";
    private static final String DEFAULT_ABN = "AAAAA";
    private static final String UPDATED_ABN = "BBBBB";
    private static final String DEFAULT_DELIVERY_INSTRUCTION = "AAAAA";
    private static final String UPDATED_DELIVERY_INSTRUCTION = "BBBBB";
    private static final String DEFAULT_WEB_URL = "AAAAA";
    private static final String UPDATED_WEB_URL = "BBBBB";

    private static final Boolean DEFAULT_IS_ON_HOLD = false;
    private static final Boolean UPDATED_IS_ON_HOLD = true;

    private static final Boolean DEFAULT_IS_ORDER_NO_REQUIRED = false;
    private static final Boolean UPDATED_IS_ORDER_NO_REQUIRED = true;

    private static final Boolean DEFAULT_IS_BLACKLISTED = false;
    private static final Boolean UPDATED_IS_BLACKLISTED = true;

    private static final Boolean DEFAULT_IS_BACKORDER_ALLOWED = false;
    private static final Boolean UPDATED_IS_BACKORDER_ALLOWED = true;

    private static final Boolean DEFAULT_IS_ARCHIVED = false;
    private static final Boolean UPDATED_IS_ARCHIVED = true;

    private static final Boolean DEFAULT_IS_HEAD_OFFICE_ACCOUNT = false;
    private static final Boolean UPDATED_IS_HEAD_OFFICE_ACCOUNT = true;


private static final CustomerAgeingMethod DEFAULT_AGEING_METHOD = CustomerAgeingMethod.MONTH;
    private static final CustomerAgeingMethod UPDATED_AGEING_METHOD = CustomerAgeingMethod.TERMS;

    private static final Boolean DEFAULT_IS_WEBACCESS_ALLOWED = false;
    private static final Boolean UPDATED_IS_WEBACCESS_ALLOWED = true;
    private static final String DEFAULT_BANK_NAME = "AAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBB";
    private static final String DEFAULT_BSB = "AAAAA";
    private static final String UPDATED_BSB = "BBBBB";
    private static final String DEFAULT_BANK_ACCOUNT_NO = "AAAAA";
    private static final String UPDATED_BANK_ACCOUNT_NO = "BBBBB";
    private static final String DEFAULT_BANK_SUBURB = "AAAAA";
    private static final String UPDATED_BANK_SUBURB = "BBBBB";
    private static final String DEFAULT_BANK_ACCOUNT_NAME = "AAAAA";
    private static final String UPDATED_BANK_ACCOUNT_NAME = "BBBBB";

    private static final Boolean DEFAULT_IS_CREDIT_CARD_BILLING_ACTIVE = false;
    private static final Boolean UPDATED_IS_CREDIT_CARD_BILLING_ACTIVE = true;
    private static final String DEFAULT_CREDIT_CARD_NO = "AAAAA";
    private static final String UPDATED_CREDIT_CARD_NO = "BBBBB";
    private static final String DEFAULT_CREDIT_CARD_HOLDER_NAME = "AAAAA";
    private static final String UPDATED_CREDIT_CARD_HOLDER_NAME = "BBBBB";

    private static final LocalDate DEFAULT_CREDIT_CARD_EXPIRY_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_CREDIT_CARD_EXPIRY_DATE = new LocalDate();


private static final CreditCardType DEFAULT_CREDIT_CARD_TYPE = CreditCardType.VISA;
    private static final CreditCardType UPDATED_CREDIT_CARD_TYPE = CreditCardType.AMX;


private static final CustomerAccountType DEFAULT_ACCOUNT_TYPE = CustomerAccountType.STANDARD;
    private static final CustomerAccountType UPDATED_ACCOUNT_TYPE = CustomerAccountType.INTERNAL;

    private static final Double DEFAULT_CREDIT_AMOUNT = 1D;
    private static final Double UPDATED_CREDIT_AMOUNT = 2D;

    private static final Integer DEFAULT_TERMS = 1;
    private static final Integer UPDATED_TERMS = 2;

    private static final Integer DEFAULT_SETTLEMENT_TERMS = 1;
    private static final Integer UPDATED_SETTLEMENT_TERMS = 2;


private static final CustomerInvoiceDeliveryMethod DEFAULT_INVOICE_DELIVERY_METHOD = CustomerInvoiceDeliveryMethod.EMAIL;
    private static final CustomerInvoiceDeliveryMethod UPDATED_INVOICE_DELIVERY_METHOD = CustomerInvoiceDeliveryMethod.FAX;


private static final CustomerStatementDeliveryMethod DEFAULT_STATEMENT_DELIVERY_METHOD = CustomerStatementDeliveryMethod.EMAIL;
    private static final CustomerStatementDeliveryMethod UPDATED_STATEMENT_DELIVERY_METHOD = CustomerStatementDeliveryMethod.FAX;
    private static final String DEFAULT_INVOICE_EMAIL_ADDRESS = "AAAAA";
    private static final String UPDATED_INVOICE_EMAIL_ADDRESS = "BBBBB";
    private static final String DEFAULT_INVOICE_FAX_NO = "AAAAA";
    private static final String UPDATED_INVOICE_FAX_NO = "BBBBB";
    private static final String DEFAULT_STATEMENT_EMAIL_ADDRESS = "AAAAA";
    private static final String UPDATED_STATEMENT_EMAIL_ADDRESS = "BBBBB";
    private static final String DEFAULT_STATEMENT_FAX_NO = "AAAAA";
    private static final String UPDATED_STATEMENT_FAX_NO = "BBBBB";

    private static final Boolean DEFAULT_IS_PENALTY_ISSUED = false;
    private static final Boolean UPDATED_IS_PENALTY_ISSUED = true;

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private CustomerMapper customerMapper;

    @Inject
    private CustomerSearchRepository customerSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCustomerMockMvc;

    private Customer customer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerResource customerResource = new CustomerResource();
        ReflectionTestUtils.setField(customerResource, "customerRepository", customerRepository);
        ReflectionTestUtils.setField(customerResource, "customerMapper", customerMapper);
        ReflectionTestUtils.setField(customerResource, "customerSearchRepository", customerSearchRepository);
        this.restCustomerMockMvc = MockMvcBuilders.standaloneSetup(customerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        customer = new Customer();
        customer.setCode(DEFAULT_CODE);
        customer.setName(DEFAULT_NAME);
        customer.setTradingName(DEFAULT_TRADING_NAME);
        customer.setAbn(DEFAULT_ABN);
        customer.setDeliveryInstruction(DEFAULT_DELIVERY_INSTRUCTION);
        customer.setWebUrl(DEFAULT_WEB_URL);
        customer.setIsOnHold(DEFAULT_IS_ON_HOLD);
        customer.setIsOrderNoRequired(DEFAULT_IS_ORDER_NO_REQUIRED);
        customer.setIsBlacklisted(DEFAULT_IS_BLACKLISTED);
        customer.setIsBackorderAllowed(DEFAULT_IS_BACKORDER_ALLOWED);
        customer.setIsArchived(DEFAULT_IS_ARCHIVED);
        customer.setIsHeadOfficeAccount(DEFAULT_IS_HEAD_OFFICE_ACCOUNT);
        customer.setAgeingMethod(DEFAULT_AGEING_METHOD);
        customer.setIsWebaccessAllowed(DEFAULT_IS_WEBACCESS_ALLOWED);
        customer.setBankName(DEFAULT_BANK_NAME);
        customer.setBsb(DEFAULT_BSB);
        customer.setBankAccountNo(DEFAULT_BANK_ACCOUNT_NO);
        customer.setBankSuburb(DEFAULT_BANK_SUBURB);
        customer.setBankAccountName(DEFAULT_BANK_ACCOUNT_NAME);
        customer.setIsCreditCardBillingActive(DEFAULT_IS_CREDIT_CARD_BILLING_ACTIVE);
        customer.setCreditCardNo(DEFAULT_CREDIT_CARD_NO);
        customer.setCreditCardHolderName(DEFAULT_CREDIT_CARD_HOLDER_NAME);
        customer.setCreditCardExpiryDate(DEFAULT_CREDIT_CARD_EXPIRY_DATE);
        customer.setCreditCardType(DEFAULT_CREDIT_CARD_TYPE);
        customer.setAccountType(DEFAULT_ACCOUNT_TYPE);
        customer.setCreditAmount(DEFAULT_CREDIT_AMOUNT);
        customer.setTerms(DEFAULT_TERMS);
        customer.setSettlementTerms(DEFAULT_SETTLEMENT_TERMS);
        customer.setInvoiceDeliveryMethod(DEFAULT_INVOICE_DELIVERY_METHOD);
        customer.setStatementDeliveryMethod(DEFAULT_STATEMENT_DELIVERY_METHOD);
        customer.setInvoiceEmailAddress(DEFAULT_INVOICE_EMAIL_ADDRESS);
        customer.setInvoiceFaxNo(DEFAULT_INVOICE_FAX_NO);
        customer.setStatementEmailAddress(DEFAULT_STATEMENT_EMAIL_ADDRESS);
        customer.setStatementFaxNo(DEFAULT_STATEMENT_FAX_NO);
        customer.setIsPenaltyIssued(DEFAULT_IS_PENALTY_ISSUED);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
                .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customers.get(customers.size() - 1);
        assertThat(testCustomer.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCustomer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomer.getTradingName()).isEqualTo(DEFAULT_TRADING_NAME);
        assertThat(testCustomer.getAbn()).isEqualTo(DEFAULT_ABN);
        assertThat(testCustomer.getDeliveryInstruction()).isEqualTo(DEFAULT_DELIVERY_INSTRUCTION);
        assertThat(testCustomer.getWebUrl()).isEqualTo(DEFAULT_WEB_URL);
        assertThat(testCustomer.getIsOnHold()).isEqualTo(DEFAULT_IS_ON_HOLD);
        assertThat(testCustomer.getIsOrderNoRequired()).isEqualTo(DEFAULT_IS_ORDER_NO_REQUIRED);
        assertThat(testCustomer.getIsBlacklisted()).isEqualTo(DEFAULT_IS_BLACKLISTED);
        assertThat(testCustomer.getIsBackorderAllowed()).isEqualTo(DEFAULT_IS_BACKORDER_ALLOWED);
        assertThat(testCustomer.getIsArchived()).isEqualTo(DEFAULT_IS_ARCHIVED);
        assertThat(testCustomer.getIsHeadOfficeAccount()).isEqualTo(DEFAULT_IS_HEAD_OFFICE_ACCOUNT);
        assertThat(testCustomer.getAgeingMethod()).isEqualTo(DEFAULT_AGEING_METHOD);
        assertThat(testCustomer.getIsWebaccessAllowed()).isEqualTo(DEFAULT_IS_WEBACCESS_ALLOWED);
        assertThat(testCustomer.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testCustomer.getBsb()).isEqualTo(DEFAULT_BSB);
        assertThat(testCustomer.getBankAccountNo()).isEqualTo(DEFAULT_BANK_ACCOUNT_NO);
        assertThat(testCustomer.getBankSuburb()).isEqualTo(DEFAULT_BANK_SUBURB);
        assertThat(testCustomer.getBankAccountName()).isEqualTo(DEFAULT_BANK_ACCOUNT_NAME);
        assertThat(testCustomer.getIsCreditCardBillingActive()).isEqualTo(DEFAULT_IS_CREDIT_CARD_BILLING_ACTIVE);
        assertThat(testCustomer.getCreditCardNo()).isEqualTo(DEFAULT_CREDIT_CARD_NO);
        assertThat(testCustomer.getCreditCardHolderName()).isEqualTo(DEFAULT_CREDIT_CARD_HOLDER_NAME);
        assertThat(testCustomer.getCreditCardExpiryDate()).isEqualTo(DEFAULT_CREDIT_CARD_EXPIRY_DATE);
        assertThat(testCustomer.getCreditCardType()).isEqualTo(DEFAULT_CREDIT_CARD_TYPE);
        assertThat(testCustomer.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testCustomer.getCreditAmount()).isEqualTo(DEFAULT_CREDIT_AMOUNT);
        assertThat(testCustomer.getTerms()).isEqualTo(DEFAULT_TERMS);
        assertThat(testCustomer.getSettlementTerms()).isEqualTo(DEFAULT_SETTLEMENT_TERMS);
        assertThat(testCustomer.getInvoiceDeliveryMethod()).isEqualTo(DEFAULT_INVOICE_DELIVERY_METHOD);
        assertThat(testCustomer.getStatementDeliveryMethod()).isEqualTo(DEFAULT_STATEMENT_DELIVERY_METHOD);
        assertThat(testCustomer.getInvoiceEmailAddress()).isEqualTo(DEFAULT_INVOICE_EMAIL_ADDRESS);
        assertThat(testCustomer.getInvoiceFaxNo()).isEqualTo(DEFAULT_INVOICE_FAX_NO);
        assertThat(testCustomer.getStatementEmailAddress()).isEqualTo(DEFAULT_STATEMENT_EMAIL_ADDRESS);
        assertThat(testCustomer.getStatementFaxNo()).isEqualTo(DEFAULT_STATEMENT_FAX_NO);
        assertThat(testCustomer.getIsPenaltyIssued()).isEqualTo(DEFAULT_IS_PENALTY_ISSUED);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setCode(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
                .andExpect(status().isBadRequest());

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customers
        restCustomerMockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].tradingName").value(hasItem(DEFAULT_TRADING_NAME.toString())))
                .andExpect(jsonPath("$.[*].abn").value(hasItem(DEFAULT_ABN.toString())))
                .andExpect(jsonPath("$.[*].deliveryInstruction").value(hasItem(DEFAULT_DELIVERY_INSTRUCTION.toString())))
                .andExpect(jsonPath("$.[*].webUrl").value(hasItem(DEFAULT_WEB_URL.toString())))
                .andExpect(jsonPath("$.[*].isOnHold").value(hasItem(DEFAULT_IS_ON_HOLD.booleanValue())))
                .andExpect(jsonPath("$.[*].isOrderNoRequired").value(hasItem(DEFAULT_IS_ORDER_NO_REQUIRED.booleanValue())))
                .andExpect(jsonPath("$.[*].isBlacklisted").value(hasItem(DEFAULT_IS_BLACKLISTED.booleanValue())))
                .andExpect(jsonPath("$.[*].isBackorderAllowed").value(hasItem(DEFAULT_IS_BACKORDER_ALLOWED.booleanValue())))
                .andExpect(jsonPath("$.[*].isArchived").value(hasItem(DEFAULT_IS_ARCHIVED.booleanValue())))
                .andExpect(jsonPath("$.[*].isHeadOfficeAccount").value(hasItem(DEFAULT_IS_HEAD_OFFICE_ACCOUNT.booleanValue())))
                .andExpect(jsonPath("$.[*].ageingMethod").value(hasItem(DEFAULT_AGEING_METHOD.toString())))
                .andExpect(jsonPath("$.[*].isWebaccessAllowed").value(hasItem(DEFAULT_IS_WEBACCESS_ALLOWED.booleanValue())))
                .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())))
                .andExpect(jsonPath("$.[*].bsb").value(hasItem(DEFAULT_BSB.toString())))
                .andExpect(jsonPath("$.[*].bankAccountNo").value(hasItem(DEFAULT_BANK_ACCOUNT_NO.toString())))
                .andExpect(jsonPath("$.[*].bankSuburb").value(hasItem(DEFAULT_BANK_SUBURB.toString())))
                .andExpect(jsonPath("$.[*].bankAccountName").value(hasItem(DEFAULT_BANK_ACCOUNT_NAME.toString())))
                .andExpect(jsonPath("$.[*].isCreditCardBillingActive").value(hasItem(DEFAULT_IS_CREDIT_CARD_BILLING_ACTIVE.booleanValue())))
                .andExpect(jsonPath("$.[*].creditCardNo").value(hasItem(DEFAULT_CREDIT_CARD_NO.toString())))
                .andExpect(jsonPath("$.[*].creditCardHolderName").value(hasItem(DEFAULT_CREDIT_CARD_HOLDER_NAME.toString())))
                .andExpect(jsonPath("$.[*].creditCardExpiryDate").value(hasItem(DEFAULT_CREDIT_CARD_EXPIRY_DATE.toString())))
                .andExpect(jsonPath("$.[*].creditCardType").value(hasItem(DEFAULT_CREDIT_CARD_TYPE.toString())))
                .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].creditAmount").value(hasItem(DEFAULT_CREDIT_AMOUNT.doubleValue())))
                .andExpect(jsonPath("$.[*].terms").value(hasItem(DEFAULT_TERMS)))
                .andExpect(jsonPath("$.[*].settlementTerms").value(hasItem(DEFAULT_SETTLEMENT_TERMS)))
                .andExpect(jsonPath("$.[*].invoiceDeliveryMethod").value(hasItem(DEFAULT_INVOICE_DELIVERY_METHOD.toString())))
                .andExpect(jsonPath("$.[*].statementDeliveryMethod").value(hasItem(DEFAULT_STATEMENT_DELIVERY_METHOD.toString())))
                .andExpect(jsonPath("$.[*].invoiceEmailAddress").value(hasItem(DEFAULT_INVOICE_EMAIL_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].invoiceFaxNo").value(hasItem(DEFAULT_INVOICE_FAX_NO.toString())))
                .andExpect(jsonPath("$.[*].statementEmailAddress").value(hasItem(DEFAULT_STATEMENT_EMAIL_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].statementFaxNo").value(hasItem(DEFAULT_STATEMENT_FAX_NO.toString())))
                .andExpect(jsonPath("$.[*].isPenaltyIssued").value(hasItem(DEFAULT_IS_PENALTY_ISSUED.booleanValue())));
    }

    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.tradingName").value(DEFAULT_TRADING_NAME.toString()))
            .andExpect(jsonPath("$.abn").value(DEFAULT_ABN.toString()))
            .andExpect(jsonPath("$.deliveryInstruction").value(DEFAULT_DELIVERY_INSTRUCTION.toString()))
            .andExpect(jsonPath("$.webUrl").value(DEFAULT_WEB_URL.toString()))
            .andExpect(jsonPath("$.isOnHold").value(DEFAULT_IS_ON_HOLD.booleanValue()))
            .andExpect(jsonPath("$.isOrderNoRequired").value(DEFAULT_IS_ORDER_NO_REQUIRED.booleanValue()))
            .andExpect(jsonPath("$.isBlacklisted").value(DEFAULT_IS_BLACKLISTED.booleanValue()))
            .andExpect(jsonPath("$.isBackorderAllowed").value(DEFAULT_IS_BACKORDER_ALLOWED.booleanValue()))
            .andExpect(jsonPath("$.isArchived").value(DEFAULT_IS_ARCHIVED.booleanValue()))
            .andExpect(jsonPath("$.isHeadOfficeAccount").value(DEFAULT_IS_HEAD_OFFICE_ACCOUNT.booleanValue()))
            .andExpect(jsonPath("$.ageingMethod").value(DEFAULT_AGEING_METHOD.toString()))
            .andExpect(jsonPath("$.isWebaccessAllowed").value(DEFAULT_IS_WEBACCESS_ALLOWED.booleanValue()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME.toString()))
            .andExpect(jsonPath("$.bsb").value(DEFAULT_BSB.toString()))
            .andExpect(jsonPath("$.bankAccountNo").value(DEFAULT_BANK_ACCOUNT_NO.toString()))
            .andExpect(jsonPath("$.bankSuburb").value(DEFAULT_BANK_SUBURB.toString()))
            .andExpect(jsonPath("$.bankAccountName").value(DEFAULT_BANK_ACCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.isCreditCardBillingActive").value(DEFAULT_IS_CREDIT_CARD_BILLING_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.creditCardNo").value(DEFAULT_CREDIT_CARD_NO.toString()))
            .andExpect(jsonPath("$.creditCardHolderName").value(DEFAULT_CREDIT_CARD_HOLDER_NAME.toString()))
            .andExpect(jsonPath("$.creditCardExpiryDate").value(DEFAULT_CREDIT_CARD_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.creditCardType").value(DEFAULT_CREDIT_CARD_TYPE.toString()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.creditAmount").value(DEFAULT_CREDIT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.terms").value(DEFAULT_TERMS))
            .andExpect(jsonPath("$.settlementTerms").value(DEFAULT_SETTLEMENT_TERMS))
            .andExpect(jsonPath("$.invoiceDeliveryMethod").value(DEFAULT_INVOICE_DELIVERY_METHOD.toString()))
            .andExpect(jsonPath("$.statementDeliveryMethod").value(DEFAULT_STATEMENT_DELIVERY_METHOD.toString()))
            .andExpect(jsonPath("$.invoiceEmailAddress").value(DEFAULT_INVOICE_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.invoiceFaxNo").value(DEFAULT_INVOICE_FAX_NO.toString()))
            .andExpect(jsonPath("$.statementEmailAddress").value(DEFAULT_STATEMENT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.statementFaxNo").value(DEFAULT_STATEMENT_FAX_NO.toString()))
            .andExpect(jsonPath("$.isPenaltyIssued").value(DEFAULT_IS_PENALTY_ISSUED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

		int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        customer.setCode(UPDATED_CODE);
        customer.setName(UPDATED_NAME);
        customer.setTradingName(UPDATED_TRADING_NAME);
        customer.setAbn(UPDATED_ABN);
        customer.setDeliveryInstruction(UPDATED_DELIVERY_INSTRUCTION);
        customer.setWebUrl(UPDATED_WEB_URL);
        customer.setIsOnHold(UPDATED_IS_ON_HOLD);
        customer.setIsOrderNoRequired(UPDATED_IS_ORDER_NO_REQUIRED);
        customer.setIsBlacklisted(UPDATED_IS_BLACKLISTED);
        customer.setIsBackorderAllowed(UPDATED_IS_BACKORDER_ALLOWED);
        customer.setIsArchived(UPDATED_IS_ARCHIVED);
        customer.setIsHeadOfficeAccount(UPDATED_IS_HEAD_OFFICE_ACCOUNT);
        customer.setAgeingMethod(UPDATED_AGEING_METHOD);
        customer.setIsWebaccessAllowed(UPDATED_IS_WEBACCESS_ALLOWED);
        customer.setBankName(UPDATED_BANK_NAME);
        customer.setBsb(UPDATED_BSB);
        customer.setBankAccountNo(UPDATED_BANK_ACCOUNT_NO);
        customer.setBankSuburb(UPDATED_BANK_SUBURB);
        customer.setBankAccountName(UPDATED_BANK_ACCOUNT_NAME);
        customer.setIsCreditCardBillingActive(UPDATED_IS_CREDIT_CARD_BILLING_ACTIVE);
        customer.setCreditCardNo(UPDATED_CREDIT_CARD_NO);
        customer.setCreditCardHolderName(UPDATED_CREDIT_CARD_HOLDER_NAME);
        customer.setCreditCardExpiryDate(UPDATED_CREDIT_CARD_EXPIRY_DATE);
        customer.setCreditCardType(UPDATED_CREDIT_CARD_TYPE);
        customer.setAccountType(UPDATED_ACCOUNT_TYPE);
        customer.setCreditAmount(UPDATED_CREDIT_AMOUNT);
        customer.setTerms(UPDATED_TERMS);
        customer.setSettlementTerms(UPDATED_SETTLEMENT_TERMS);
        customer.setInvoiceDeliveryMethod(UPDATED_INVOICE_DELIVERY_METHOD);
        customer.setStatementDeliveryMethod(UPDATED_STATEMENT_DELIVERY_METHOD);
        customer.setInvoiceEmailAddress(UPDATED_INVOICE_EMAIL_ADDRESS);
        customer.setInvoiceFaxNo(UPDATED_INVOICE_FAX_NO);
        customer.setStatementEmailAddress(UPDATED_STATEMENT_EMAIL_ADDRESS);
        customer.setStatementFaxNo(UPDATED_STATEMENT_FAX_NO);
        customer.setIsPenaltyIssued(UPDATED_IS_PENALTY_ISSUED);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        restCustomerMockMvc.perform(put("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
                .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customers.get(customers.size() - 1);
        assertThat(testCustomer.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomer.getTradingName()).isEqualTo(UPDATED_TRADING_NAME);
        assertThat(testCustomer.getAbn()).isEqualTo(UPDATED_ABN);
        assertThat(testCustomer.getDeliveryInstruction()).isEqualTo(UPDATED_DELIVERY_INSTRUCTION);
        assertThat(testCustomer.getWebUrl()).isEqualTo(UPDATED_WEB_URL);
        assertThat(testCustomer.getIsOnHold()).isEqualTo(UPDATED_IS_ON_HOLD);
        assertThat(testCustomer.getIsOrderNoRequired()).isEqualTo(UPDATED_IS_ORDER_NO_REQUIRED);
        assertThat(testCustomer.getIsBlacklisted()).isEqualTo(UPDATED_IS_BLACKLISTED);
        assertThat(testCustomer.getIsBackorderAllowed()).isEqualTo(UPDATED_IS_BACKORDER_ALLOWED);
        assertThat(testCustomer.getIsArchived()).isEqualTo(UPDATED_IS_ARCHIVED);
        assertThat(testCustomer.getIsHeadOfficeAccount()).isEqualTo(UPDATED_IS_HEAD_OFFICE_ACCOUNT);
        assertThat(testCustomer.getAgeingMethod()).isEqualTo(UPDATED_AGEING_METHOD);
        assertThat(testCustomer.getIsWebaccessAllowed()).isEqualTo(UPDATED_IS_WEBACCESS_ALLOWED);
        assertThat(testCustomer.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testCustomer.getBsb()).isEqualTo(UPDATED_BSB);
        assertThat(testCustomer.getBankAccountNo()).isEqualTo(UPDATED_BANK_ACCOUNT_NO);
        assertThat(testCustomer.getBankSuburb()).isEqualTo(UPDATED_BANK_SUBURB);
        assertThat(testCustomer.getBankAccountName()).isEqualTo(UPDATED_BANK_ACCOUNT_NAME);
        assertThat(testCustomer.getIsCreditCardBillingActive()).isEqualTo(UPDATED_IS_CREDIT_CARD_BILLING_ACTIVE);
        assertThat(testCustomer.getCreditCardNo()).isEqualTo(UPDATED_CREDIT_CARD_NO);
        assertThat(testCustomer.getCreditCardHolderName()).isEqualTo(UPDATED_CREDIT_CARD_HOLDER_NAME);
        assertThat(testCustomer.getCreditCardExpiryDate()).isEqualTo(UPDATED_CREDIT_CARD_EXPIRY_DATE);
        assertThat(testCustomer.getCreditCardType()).isEqualTo(UPDATED_CREDIT_CARD_TYPE);
        assertThat(testCustomer.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testCustomer.getCreditAmount()).isEqualTo(UPDATED_CREDIT_AMOUNT);
        assertThat(testCustomer.getTerms()).isEqualTo(UPDATED_TERMS);
        assertThat(testCustomer.getSettlementTerms()).isEqualTo(UPDATED_SETTLEMENT_TERMS);
        assertThat(testCustomer.getInvoiceDeliveryMethod()).isEqualTo(UPDATED_INVOICE_DELIVERY_METHOD);
        assertThat(testCustomer.getStatementDeliveryMethod()).isEqualTo(UPDATED_STATEMENT_DELIVERY_METHOD);
        assertThat(testCustomer.getInvoiceEmailAddress()).isEqualTo(UPDATED_INVOICE_EMAIL_ADDRESS);
        assertThat(testCustomer.getInvoiceFaxNo()).isEqualTo(UPDATED_INVOICE_FAX_NO);
        assertThat(testCustomer.getStatementEmailAddress()).isEqualTo(UPDATED_STATEMENT_EMAIL_ADDRESS);
        assertThat(testCustomer.getStatementFaxNo()).isEqualTo(UPDATED_STATEMENT_FAX_NO);
        assertThat(testCustomer.getIsPenaltyIssued()).isEqualTo(UPDATED_IS_PENALTY_ISSUED);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

		int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Get the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
