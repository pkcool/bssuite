package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.CustomerDiscountRule;
import com.enginemobi.bssuite.repository.CustomerDiscountRuleRepository;
import com.enginemobi.bssuite.repository.search.CustomerDiscountRuleSearchRepository;
import com.enginemobi.bssuite.web.rest.dto.CustomerDiscountRuleDTO;
import com.enginemobi.bssuite.web.rest.mapper.CustomerDiscountRuleMapper;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CustomerDiscountRuleResource REST controller.
 *
 * @see CustomerDiscountRuleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CustomerDiscountRuleResourceIntTest {

    private static final String DEFAULT_PRICE_GROUP_CODE = "AAAAA";
    private static final String UPDATED_PRICE_GROUP_CODE = "BBBBB";

    private static final Integer DEFAULT_RULE_NO = 1;
    private static final Integer UPDATED_RULE_NO = 2;

    private static final Boolean DEFAULT_IS_APPLIED_GLOBALLY = false;
    private static final Boolean UPDATED_IS_APPLIED_GLOBALLY = true;

    private static final Boolean DEFAULT_IS_APPLIED_ON_SPECIAL_ITEMS_ONLY = false;
    private static final Boolean UPDATED_IS_APPLIED_ON_SPECIAL_ITEMS_ONLY = true;
    private static final String DEFAULT_CUSTOMER_CODE = "AAAAA";
    private static final String UPDATED_CUSTOMER_CODE = "BBBBB";
    private static final String DEFAULT_CUSTOMER_CATEGORY_CODE = "AAAAA";
    private static final String UPDATED_CUSTOMER_CATEGORY_CODE = "BBBBB";
    private static final String DEFAULT_FROM_PRODUCT_CODE = "AAAAA";
    private static final String UPDATED_FROM_PRODUCT_CODE = "BBBBB";
    private static final String DEFAULT_TO_PRODUCT_CODE = "AAAAA";
    private static final String UPDATED_TO_PRODUCT_CODE = "BBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_QTY_BREAK = 1;
    private static final Integer UPDATED_QTY_BREAK = 2;
    private static final String DEFAULT_FROM_SUPPLIER_CODE = "AAAAA";
    private static final String UPDATED_FROM_SUPPLIER_CODE = "BBBBB";
    private static final String DEFAULT_TO_SUPPLIER_CODE = "AAAAA";
    private static final String UPDATED_TO_SUPPLIER_CODE = "BBBBB";
    private static final String DEFAULT_FROM_STOCK_GROUP_CODE = "AAAAA";
    private static final String UPDATED_FROM_STOCK_GROUP_CODE = "BBBBB";
    private static final String DEFAULT_TO_STOCK_GROUP_CODE = "AAAAA";
    private static final String UPDATED_TO_STOCK_GROUP_CODE = "BBBBB";
    private static final String DEFAULT_TAX_CODE = "AAAAA";
    private static final String UPDATED_TAX_CODE = "BBBBB";

    private static final Boolean DEFAULT_IS_APPLIED_WHEN_TAX_EXEMPT = false;
    private static final Boolean UPDATED_IS_APPLIED_WHEN_TAX_EXEMPT = true;
    private static final String DEFAULT_STORE_CODE = "AAAAA";
    private static final String UPDATED_STORE_CODE = "BBBBB";
    private static final String DEFAULT_DISCOUNT_NAME = "AAAAA";
    private static final String UPDATED_DISCOUNT_NAME = "BBBBB";
    private static final String DEFAULT_STOCK_FAMILY_CODE = "AAAAA";
    private static final String UPDATED_STOCK_FAMILY_CODE = "BBBBB";

    private static final BigDecimal DEFAULT_LIST_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LIST_PRICE = new BigDecimal(2);
    private static final String DEFAULT_DISCOUNT_FORMULA = "AAAAA";
    private static final String UPDATED_DISCOUNT_FORMULA = "BBBBB";

    @Inject
    private CustomerDiscountRuleRepository customerDiscountRuleRepository;

    @Inject
    private CustomerDiscountRuleMapper customerDiscountRuleMapper;

    @Inject
    private CustomerDiscountRuleSearchRepository customerDiscountRuleSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCustomerDiscountRuleMockMvc;

    private CustomerDiscountRule customerDiscountRule;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerDiscountRuleResource customerDiscountRuleResource = new CustomerDiscountRuleResource();
        ReflectionTestUtils.setField(customerDiscountRuleResource, "customerDiscountRuleRepository", customerDiscountRuleRepository);
        ReflectionTestUtils.setField(customerDiscountRuleResource, "customerDiscountRuleMapper", customerDiscountRuleMapper);
        ReflectionTestUtils.setField(customerDiscountRuleResource, "customerDiscountRuleSearchRepository", customerDiscountRuleSearchRepository);
        this.restCustomerDiscountRuleMockMvc = MockMvcBuilders.standaloneSetup(customerDiscountRuleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        customerDiscountRule = new CustomerDiscountRule();
        customerDiscountRule.setPriceGroupCode(DEFAULT_PRICE_GROUP_CODE);
        customerDiscountRule.setRuleNo(DEFAULT_RULE_NO);
        customerDiscountRule.setIsAppliedGlobally(DEFAULT_IS_APPLIED_GLOBALLY);
        customerDiscountRule.setIsAppliedOnSpecialItemsOnly(DEFAULT_IS_APPLIED_ON_SPECIAL_ITEMS_ONLY);
        customerDiscountRule.setCustomerCode(DEFAULT_CUSTOMER_CODE);
        customerDiscountRule.setCustomerCategoryCode(DEFAULT_CUSTOMER_CATEGORY_CODE);
        customerDiscountRule.setFromProductCode(DEFAULT_FROM_PRODUCT_CODE);
        customerDiscountRule.setToProductCode(DEFAULT_TO_PRODUCT_CODE);
        customerDiscountRule.setStartDate(DEFAULT_START_DATE);
        customerDiscountRule.setEndDate(DEFAULT_END_DATE);
        customerDiscountRule.setQtyBreak(DEFAULT_QTY_BREAK);
        customerDiscountRule.setFromSupplierCode(DEFAULT_FROM_SUPPLIER_CODE);
        customerDiscountRule.setToSupplierCode(DEFAULT_TO_SUPPLIER_CODE);
        customerDiscountRule.setFromStockGroupCode(DEFAULT_FROM_STOCK_GROUP_CODE);
        customerDiscountRule.setToStockGroupCode(DEFAULT_TO_STOCK_GROUP_CODE);
        customerDiscountRule.setTaxCode(DEFAULT_TAX_CODE);
        customerDiscountRule.setIsAppliedWhenTaxExempt(DEFAULT_IS_APPLIED_WHEN_TAX_EXEMPT);
        customerDiscountRule.setStoreCode(DEFAULT_STORE_CODE);
        customerDiscountRule.setDiscountName(DEFAULT_DISCOUNT_NAME);
        customerDiscountRule.setStockFamilyCode(DEFAULT_STOCK_FAMILY_CODE);
        customerDiscountRule.setListPrice(DEFAULT_LIST_PRICE);
        customerDiscountRule.setDiscountFormula(DEFAULT_DISCOUNT_FORMULA);
    }

    @Test
    @Transactional
    public void createCustomerDiscountRule() throws Exception {
        int databaseSizeBeforeCreate = customerDiscountRuleRepository.findAll().size();

        // Create the CustomerDiscountRule
        CustomerDiscountRuleDTO customerDiscountRuleDTO = customerDiscountRuleMapper.customerDiscountRuleToCustomerDiscountRuleDTO(customerDiscountRule);

        restCustomerDiscountRuleMockMvc.perform(post("/api/customerDiscountRules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customerDiscountRuleDTO)))
                .andExpect(status().isCreated());

        // Validate the CustomerDiscountRule in the database
        List<CustomerDiscountRule> customerDiscountRules = customerDiscountRuleRepository.findAll();
        assertThat(customerDiscountRules).hasSize(databaseSizeBeforeCreate + 1);
        CustomerDiscountRule testCustomerDiscountRule = customerDiscountRules.get(customerDiscountRules.size() - 1);
        assertThat(testCustomerDiscountRule.getPriceGroupCode()).isEqualTo(DEFAULT_PRICE_GROUP_CODE);
        assertThat(testCustomerDiscountRule.getRuleNo()).isEqualTo(DEFAULT_RULE_NO);
        assertThat(testCustomerDiscountRule.getIsAppliedGlobally()).isEqualTo(DEFAULT_IS_APPLIED_GLOBALLY);
        assertThat(testCustomerDiscountRule.getIsAppliedOnSpecialItemsOnly()).isEqualTo(DEFAULT_IS_APPLIED_ON_SPECIAL_ITEMS_ONLY);
        assertThat(testCustomerDiscountRule.getCustomerCode()).isEqualTo(DEFAULT_CUSTOMER_CODE);
        assertThat(testCustomerDiscountRule.getCustomerCategoryCode()).isEqualTo(DEFAULT_CUSTOMER_CATEGORY_CODE);
        assertThat(testCustomerDiscountRule.getFromProductCode()).isEqualTo(DEFAULT_FROM_PRODUCT_CODE);
        assertThat(testCustomerDiscountRule.getToProductCode()).isEqualTo(DEFAULT_TO_PRODUCT_CODE);
        assertThat(testCustomerDiscountRule.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testCustomerDiscountRule.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testCustomerDiscountRule.getQtyBreak()).isEqualTo(DEFAULT_QTY_BREAK);
        assertThat(testCustomerDiscountRule.getFromSupplierCode()).isEqualTo(DEFAULT_FROM_SUPPLIER_CODE);
        assertThat(testCustomerDiscountRule.getToSupplierCode()).isEqualTo(DEFAULT_TO_SUPPLIER_CODE);
        assertThat(testCustomerDiscountRule.getFromStockGroupCode()).isEqualTo(DEFAULT_FROM_STOCK_GROUP_CODE);
        assertThat(testCustomerDiscountRule.getToStockGroupCode()).isEqualTo(DEFAULT_TO_STOCK_GROUP_CODE);
        assertThat(testCustomerDiscountRule.getTaxCode()).isEqualTo(DEFAULT_TAX_CODE);
        assertThat(testCustomerDiscountRule.getIsAppliedWhenTaxExempt()).isEqualTo(DEFAULT_IS_APPLIED_WHEN_TAX_EXEMPT);
        assertThat(testCustomerDiscountRule.getStoreCode()).isEqualTo(DEFAULT_STORE_CODE);
        assertThat(testCustomerDiscountRule.getDiscountName()).isEqualTo(DEFAULT_DISCOUNT_NAME);
        assertThat(testCustomerDiscountRule.getStockFamilyCode()).isEqualTo(DEFAULT_STOCK_FAMILY_CODE);
        assertThat(testCustomerDiscountRule.getListPrice()).isEqualTo(DEFAULT_LIST_PRICE);
        assertThat(testCustomerDiscountRule.getDiscountFormula()).isEqualTo(DEFAULT_DISCOUNT_FORMULA);
    }

    @Test
    @Transactional
    public void getAllCustomerDiscountRules() throws Exception {
        // Initialize the database
        customerDiscountRuleRepository.saveAndFlush(customerDiscountRule);

        // Get all the customerDiscountRules
        restCustomerDiscountRuleMockMvc.perform(get("/api/customerDiscountRules"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(customerDiscountRule.getId().intValue())))
                .andExpect(jsonPath("$.[*].priceGroupCode").value(hasItem(DEFAULT_PRICE_GROUP_CODE.toString())))
                .andExpect(jsonPath("$.[*].ruleNo").value(hasItem(DEFAULT_RULE_NO)))
                .andExpect(jsonPath("$.[*].isAppliedGlobally").value(hasItem(DEFAULT_IS_APPLIED_GLOBALLY.booleanValue())))
                .andExpect(jsonPath("$.[*].isAppliedOnSpecialItemsOnly").value(hasItem(DEFAULT_IS_APPLIED_ON_SPECIAL_ITEMS_ONLY.booleanValue())))
                .andExpect(jsonPath("$.[*].customerCode").value(hasItem(DEFAULT_CUSTOMER_CODE.toString())))
                .andExpect(jsonPath("$.[*].customerCategoryCode").value(hasItem(DEFAULT_CUSTOMER_CATEGORY_CODE.toString())))
                .andExpect(jsonPath("$.[*].fromProductCode").value(hasItem(DEFAULT_FROM_PRODUCT_CODE.toString())))
                .andExpect(jsonPath("$.[*].toProductCode").value(hasItem(DEFAULT_TO_PRODUCT_CODE.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].qtyBreak").value(hasItem(DEFAULT_QTY_BREAK)))
                .andExpect(jsonPath("$.[*].fromSupplierCode").value(hasItem(DEFAULT_FROM_SUPPLIER_CODE.toString())))
                .andExpect(jsonPath("$.[*].toSupplierCode").value(hasItem(DEFAULT_TO_SUPPLIER_CODE.toString())))
                .andExpect(jsonPath("$.[*].fromStockGroupCode").value(hasItem(DEFAULT_FROM_STOCK_GROUP_CODE.toString())))
                .andExpect(jsonPath("$.[*].toStockGroupCode").value(hasItem(DEFAULT_TO_STOCK_GROUP_CODE.toString())))
                .andExpect(jsonPath("$.[*].taxCode").value(hasItem(DEFAULT_TAX_CODE.toString())))
                .andExpect(jsonPath("$.[*].isAppliedWhenTaxExempt").value(hasItem(DEFAULT_IS_APPLIED_WHEN_TAX_EXEMPT.booleanValue())))
                .andExpect(jsonPath("$.[*].storeCode").value(hasItem(DEFAULT_STORE_CODE.toString())))
                .andExpect(jsonPath("$.[*].discountName").value(hasItem(DEFAULT_DISCOUNT_NAME.toString())))
                .andExpect(jsonPath("$.[*].stockFamilyCode").value(hasItem(DEFAULT_STOCK_FAMILY_CODE.toString())))
                .andExpect(jsonPath("$.[*].listPrice").value(hasItem(DEFAULT_LIST_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].discountFormula").value(hasItem(DEFAULT_DISCOUNT_FORMULA.toString())));
    }

    @Test
    @Transactional
    public void getCustomerDiscountRule() throws Exception {
        // Initialize the database
        customerDiscountRuleRepository.saveAndFlush(customerDiscountRule);

        // Get the customerDiscountRule
        restCustomerDiscountRuleMockMvc.perform(get("/api/customerDiscountRules/{id}", customerDiscountRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(customerDiscountRule.getId().intValue()))
            .andExpect(jsonPath("$.priceGroupCode").value(DEFAULT_PRICE_GROUP_CODE.toString()))
            .andExpect(jsonPath("$.ruleNo").value(DEFAULT_RULE_NO))
            .andExpect(jsonPath("$.isAppliedGlobally").value(DEFAULT_IS_APPLIED_GLOBALLY.booleanValue()))
            .andExpect(jsonPath("$.isAppliedOnSpecialItemsOnly").value(DEFAULT_IS_APPLIED_ON_SPECIAL_ITEMS_ONLY.booleanValue()))
            .andExpect(jsonPath("$.customerCode").value(DEFAULT_CUSTOMER_CODE.toString()))
            .andExpect(jsonPath("$.customerCategoryCode").value(DEFAULT_CUSTOMER_CATEGORY_CODE.toString()))
            .andExpect(jsonPath("$.fromProductCode").value(DEFAULT_FROM_PRODUCT_CODE.toString()))
            .andExpect(jsonPath("$.toProductCode").value(DEFAULT_TO_PRODUCT_CODE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.qtyBreak").value(DEFAULT_QTY_BREAK))
            .andExpect(jsonPath("$.fromSupplierCode").value(DEFAULT_FROM_SUPPLIER_CODE.toString()))
            .andExpect(jsonPath("$.toSupplierCode").value(DEFAULT_TO_SUPPLIER_CODE.toString()))
            .andExpect(jsonPath("$.fromStockGroupCode").value(DEFAULT_FROM_STOCK_GROUP_CODE.toString()))
            .andExpect(jsonPath("$.toStockGroupCode").value(DEFAULT_TO_STOCK_GROUP_CODE.toString()))
            .andExpect(jsonPath("$.taxCode").value(DEFAULT_TAX_CODE.toString()))
            .andExpect(jsonPath("$.isAppliedWhenTaxExempt").value(DEFAULT_IS_APPLIED_WHEN_TAX_EXEMPT.booleanValue()))
            .andExpect(jsonPath("$.storeCode").value(DEFAULT_STORE_CODE.toString()))
            .andExpect(jsonPath("$.discountName").value(DEFAULT_DISCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.stockFamilyCode").value(DEFAULT_STOCK_FAMILY_CODE.toString()))
            .andExpect(jsonPath("$.listPrice").value(DEFAULT_LIST_PRICE.intValue()))
            .andExpect(jsonPath("$.discountFormula").value(DEFAULT_DISCOUNT_FORMULA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerDiscountRule() throws Exception {
        // Get the customerDiscountRule
        restCustomerDiscountRuleMockMvc.perform(get("/api/customerDiscountRules/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerDiscountRule() throws Exception {
        // Initialize the database
        customerDiscountRuleRepository.saveAndFlush(customerDiscountRule);

		int databaseSizeBeforeUpdate = customerDiscountRuleRepository.findAll().size();

        // Update the customerDiscountRule
        customerDiscountRule.setPriceGroupCode(UPDATED_PRICE_GROUP_CODE);
        customerDiscountRule.setRuleNo(UPDATED_RULE_NO);
        customerDiscountRule.setIsAppliedGlobally(UPDATED_IS_APPLIED_GLOBALLY);
        customerDiscountRule.setIsAppliedOnSpecialItemsOnly(UPDATED_IS_APPLIED_ON_SPECIAL_ITEMS_ONLY);
        customerDiscountRule.setCustomerCode(UPDATED_CUSTOMER_CODE);
        customerDiscountRule.setCustomerCategoryCode(UPDATED_CUSTOMER_CATEGORY_CODE);
        customerDiscountRule.setFromProductCode(UPDATED_FROM_PRODUCT_CODE);
        customerDiscountRule.setToProductCode(UPDATED_TO_PRODUCT_CODE);
        customerDiscountRule.setStartDate(UPDATED_START_DATE);
        customerDiscountRule.setEndDate(UPDATED_END_DATE);
        customerDiscountRule.setQtyBreak(UPDATED_QTY_BREAK);
        customerDiscountRule.setFromSupplierCode(UPDATED_FROM_SUPPLIER_CODE);
        customerDiscountRule.setToSupplierCode(UPDATED_TO_SUPPLIER_CODE);
        customerDiscountRule.setFromStockGroupCode(UPDATED_FROM_STOCK_GROUP_CODE);
        customerDiscountRule.setToStockGroupCode(UPDATED_TO_STOCK_GROUP_CODE);
        customerDiscountRule.setTaxCode(UPDATED_TAX_CODE);
        customerDiscountRule.setIsAppliedWhenTaxExempt(UPDATED_IS_APPLIED_WHEN_TAX_EXEMPT);
        customerDiscountRule.setStoreCode(UPDATED_STORE_CODE);
        customerDiscountRule.setDiscountName(UPDATED_DISCOUNT_NAME);
        customerDiscountRule.setStockFamilyCode(UPDATED_STOCK_FAMILY_CODE);
        customerDiscountRule.setListPrice(UPDATED_LIST_PRICE);
        customerDiscountRule.setDiscountFormula(UPDATED_DISCOUNT_FORMULA);
        CustomerDiscountRuleDTO customerDiscountRuleDTO = customerDiscountRuleMapper.customerDiscountRuleToCustomerDiscountRuleDTO(customerDiscountRule);

        restCustomerDiscountRuleMockMvc.perform(put("/api/customerDiscountRules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customerDiscountRuleDTO)))
                .andExpect(status().isOk());

        // Validate the CustomerDiscountRule in the database
        List<CustomerDiscountRule> customerDiscountRules = customerDiscountRuleRepository.findAll();
        assertThat(customerDiscountRules).hasSize(databaseSizeBeforeUpdate);
        CustomerDiscountRule testCustomerDiscountRule = customerDiscountRules.get(customerDiscountRules.size() - 1);
        assertThat(testCustomerDiscountRule.getPriceGroupCode()).isEqualTo(UPDATED_PRICE_GROUP_CODE);
        assertThat(testCustomerDiscountRule.getRuleNo()).isEqualTo(UPDATED_RULE_NO);
        assertThat(testCustomerDiscountRule.getIsAppliedGlobally()).isEqualTo(UPDATED_IS_APPLIED_GLOBALLY);
        assertThat(testCustomerDiscountRule.getIsAppliedOnSpecialItemsOnly()).isEqualTo(UPDATED_IS_APPLIED_ON_SPECIAL_ITEMS_ONLY);
        assertThat(testCustomerDiscountRule.getCustomerCode()).isEqualTo(UPDATED_CUSTOMER_CODE);
        assertThat(testCustomerDiscountRule.getCustomerCategoryCode()).isEqualTo(UPDATED_CUSTOMER_CATEGORY_CODE);
        assertThat(testCustomerDiscountRule.getFromProductCode()).isEqualTo(UPDATED_FROM_PRODUCT_CODE);
        assertThat(testCustomerDiscountRule.getToProductCode()).isEqualTo(UPDATED_TO_PRODUCT_CODE);
        assertThat(testCustomerDiscountRule.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testCustomerDiscountRule.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testCustomerDiscountRule.getQtyBreak()).isEqualTo(UPDATED_QTY_BREAK);
        assertThat(testCustomerDiscountRule.getFromSupplierCode()).isEqualTo(UPDATED_FROM_SUPPLIER_CODE);
        assertThat(testCustomerDiscountRule.getToSupplierCode()).isEqualTo(UPDATED_TO_SUPPLIER_CODE);
        assertThat(testCustomerDiscountRule.getFromStockGroupCode()).isEqualTo(UPDATED_FROM_STOCK_GROUP_CODE);
        assertThat(testCustomerDiscountRule.getToStockGroupCode()).isEqualTo(UPDATED_TO_STOCK_GROUP_CODE);
        assertThat(testCustomerDiscountRule.getTaxCode()).isEqualTo(UPDATED_TAX_CODE);
        assertThat(testCustomerDiscountRule.getIsAppliedWhenTaxExempt()).isEqualTo(UPDATED_IS_APPLIED_WHEN_TAX_EXEMPT);
        assertThat(testCustomerDiscountRule.getStoreCode()).isEqualTo(UPDATED_STORE_CODE);
        assertThat(testCustomerDiscountRule.getDiscountName()).isEqualTo(UPDATED_DISCOUNT_NAME);
        assertThat(testCustomerDiscountRule.getStockFamilyCode()).isEqualTo(UPDATED_STOCK_FAMILY_CODE);
        assertThat(testCustomerDiscountRule.getListPrice()).isEqualTo(UPDATED_LIST_PRICE);
        assertThat(testCustomerDiscountRule.getDiscountFormula()).isEqualTo(UPDATED_DISCOUNT_FORMULA);
    }

    @Test
    @Transactional
    public void deleteCustomerDiscountRule() throws Exception {
        // Initialize the database
        customerDiscountRuleRepository.saveAndFlush(customerDiscountRule);

		int databaseSizeBeforeDelete = customerDiscountRuleRepository.findAll().size();

        // Get the customerDiscountRule
        restCustomerDiscountRuleMockMvc.perform(delete("/api/customerDiscountRules/{id}", customerDiscountRule.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerDiscountRule> customerDiscountRules = customerDiscountRuleRepository.findAll();
        assertThat(customerDiscountRules).hasSize(databaseSizeBeforeDelete - 1);
    }
}
