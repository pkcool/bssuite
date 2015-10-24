package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.SupplierDiscountRule;
import com.enginemobi.bssuite.repository.SupplierDiscountRuleRepository;
import com.enginemobi.bssuite.repository.search.SupplierDiscountRuleSearchRepository;
import com.enginemobi.bssuite.web.rest.dto.SupplierDiscountRuleDTO;
import com.enginemobi.bssuite.web.rest.mapper.SupplierDiscountRuleMapper;

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
 * Test class for the SupplierDiscountRuleResource REST controller.
 *
 * @see SupplierDiscountRuleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SupplierDiscountRuleResourceTest {

    private static final String DEFAULT_SUPPLIER_CODE = "AAAAA";
    private static final String UPDATED_SUPPLIER_CODE = "BBBBB";

    private static final Integer DEFAULT_RULE_NO = 1;
    private static final Integer UPDATED_RULE_NO = 2;

    private static final Boolean DEFAULT_IS_APPLIED_TO_SALES = false;
    private static final Boolean UPDATED_IS_APPLIED_TO_SALES = true;

    private static final Boolean DEFAULT_IS_APPLIED_ON_SPECIAL_ITEMS_ONLY = false;
    private static final Boolean UPDATED_IS_APPLIED_ON_SPECIAL_ITEMS_ONLY = true;
    private static final String DEFAULT_CUSTOMER_CODE = "AAAAA";
    private static final String UPDATED_CUSTOMER_CODE = "BBBBB";
    private static final String DEFAULT_CUSTOMER_CATEGORY_CODE = "AAAAA";
    private static final String UPDATED_CUSTOMER_CATEGORY_CODE = "BBBBB";
    private static final String DEFAULT_PRODUCT_CODE = "AAAAA";
    private static final String UPDATED_PRODUCT_CODE = "BBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_QTY_BREAK = 1;
    private static final Integer UPDATED_QTY_BREAK = 2;
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

    private static final BigDecimal DEFAULT_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST = new BigDecimal(2);
    private static final String DEFAULT_DISCOUNT_FORMULA = "AAAAA";
    private static final String UPDATED_DISCOUNT_FORMULA = "BBBBB";

    @Inject
    private SupplierDiscountRuleRepository supplierDiscountRuleRepository;

    @Inject
    private SupplierDiscountRuleMapper supplierDiscountRuleMapper;

    @Inject
    private SupplierDiscountRuleSearchRepository supplierDiscountRuleSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSupplierDiscountRuleMockMvc;

    private SupplierDiscountRule supplierDiscountRule;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SupplierDiscountRuleResource supplierDiscountRuleResource = new SupplierDiscountRuleResource();
        ReflectionTestUtils.setField(supplierDiscountRuleResource, "supplierDiscountRuleRepository", supplierDiscountRuleRepository);
        ReflectionTestUtils.setField(supplierDiscountRuleResource, "supplierDiscountRuleMapper", supplierDiscountRuleMapper);
        ReflectionTestUtils.setField(supplierDiscountRuleResource, "supplierDiscountRuleSearchRepository", supplierDiscountRuleSearchRepository);
        this.restSupplierDiscountRuleMockMvc = MockMvcBuilders.standaloneSetup(supplierDiscountRuleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        supplierDiscountRule = new SupplierDiscountRule();
        supplierDiscountRule.setSupplierCode(DEFAULT_SUPPLIER_CODE);
        supplierDiscountRule.setRuleNo(DEFAULT_RULE_NO);
        supplierDiscountRule.setIsAppliedToSales(DEFAULT_IS_APPLIED_TO_SALES);
        supplierDiscountRule.setIsAppliedOnSpecialItemsOnly(DEFAULT_IS_APPLIED_ON_SPECIAL_ITEMS_ONLY);
        supplierDiscountRule.setCustomerCode(DEFAULT_CUSTOMER_CODE);
        supplierDiscountRule.setCustomerCategoryCode(DEFAULT_CUSTOMER_CATEGORY_CODE);
        supplierDiscountRule.setProductCode(DEFAULT_PRODUCT_CODE);
        supplierDiscountRule.setStartDate(DEFAULT_START_DATE);
        supplierDiscountRule.setEndDate(DEFAULT_END_DATE);
        supplierDiscountRule.setQtyBreak(DEFAULT_QTY_BREAK);
        supplierDiscountRule.setFromStockGroupCode(DEFAULT_FROM_STOCK_GROUP_CODE);
        supplierDiscountRule.setToStockGroupCode(DEFAULT_TO_STOCK_GROUP_CODE);
        supplierDiscountRule.setTaxCode(DEFAULT_TAX_CODE);
        supplierDiscountRule.setIsAppliedWhenTaxExempt(DEFAULT_IS_APPLIED_WHEN_TAX_EXEMPT);
        supplierDiscountRule.setStoreCode(DEFAULT_STORE_CODE);
        supplierDiscountRule.setDiscountName(DEFAULT_DISCOUNT_NAME);
        supplierDiscountRule.setStockFamilyCode(DEFAULT_STOCK_FAMILY_CODE);
        supplierDiscountRule.setCost(DEFAULT_COST);
        supplierDiscountRule.setDiscountFormula(DEFAULT_DISCOUNT_FORMULA);
    }

    @Test
    @Transactional
    public void createSupplierDiscountRule() throws Exception {
        int databaseSizeBeforeCreate = supplierDiscountRuleRepository.findAll().size();

        // Create the SupplierDiscountRule
        SupplierDiscountRuleDTO supplierDiscountRuleDTO = supplierDiscountRuleMapper.supplierDiscountRuleToSupplierDiscountRuleDTO(supplierDiscountRule);

        restSupplierDiscountRuleMockMvc.perform(post("/api/supplierDiscountRules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(supplierDiscountRuleDTO)))
                .andExpect(status().isCreated());

        // Validate the SupplierDiscountRule in the database
        List<SupplierDiscountRule> supplierDiscountRules = supplierDiscountRuleRepository.findAll();
        assertThat(supplierDiscountRules).hasSize(databaseSizeBeforeCreate + 1);
        SupplierDiscountRule testSupplierDiscountRule = supplierDiscountRules.get(supplierDiscountRules.size() - 1);
        assertThat(testSupplierDiscountRule.getSupplierCode()).isEqualTo(DEFAULT_SUPPLIER_CODE);
        assertThat(testSupplierDiscountRule.getRuleNo()).isEqualTo(DEFAULT_RULE_NO);
        assertThat(testSupplierDiscountRule.getIsAppliedToSales()).isEqualTo(DEFAULT_IS_APPLIED_TO_SALES);
        assertThat(testSupplierDiscountRule.getIsAppliedOnSpecialItemsOnly()).isEqualTo(DEFAULT_IS_APPLIED_ON_SPECIAL_ITEMS_ONLY);
        assertThat(testSupplierDiscountRule.getCustomerCode()).isEqualTo(DEFAULT_CUSTOMER_CODE);
        assertThat(testSupplierDiscountRule.getCustomerCategoryCode()).isEqualTo(DEFAULT_CUSTOMER_CATEGORY_CODE);
        assertThat(testSupplierDiscountRule.getProductCode()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testSupplierDiscountRule.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSupplierDiscountRule.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testSupplierDiscountRule.getQtyBreak()).isEqualTo(DEFAULT_QTY_BREAK);
        assertThat(testSupplierDiscountRule.getFromStockGroupCode()).isEqualTo(DEFAULT_FROM_STOCK_GROUP_CODE);
        assertThat(testSupplierDiscountRule.getToStockGroupCode()).isEqualTo(DEFAULT_TO_STOCK_GROUP_CODE);
        assertThat(testSupplierDiscountRule.getTaxCode()).isEqualTo(DEFAULT_TAX_CODE);
        assertThat(testSupplierDiscountRule.getIsAppliedWhenTaxExempt()).isEqualTo(DEFAULT_IS_APPLIED_WHEN_TAX_EXEMPT);
        assertThat(testSupplierDiscountRule.getStoreCode()).isEqualTo(DEFAULT_STORE_CODE);
        assertThat(testSupplierDiscountRule.getDiscountName()).isEqualTo(DEFAULT_DISCOUNT_NAME);
        assertThat(testSupplierDiscountRule.getStockFamilyCode()).isEqualTo(DEFAULT_STOCK_FAMILY_CODE);
        assertThat(testSupplierDiscountRule.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testSupplierDiscountRule.getDiscountFormula()).isEqualTo(DEFAULT_DISCOUNT_FORMULA);
    }

    @Test
    @Transactional
    public void checkSupplierCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierDiscountRuleRepository.findAll().size();
        // set the field null
        supplierDiscountRule.setSupplierCode(null);

        // Create the SupplierDiscountRule, which fails.
        SupplierDiscountRuleDTO supplierDiscountRuleDTO = supplierDiscountRuleMapper.supplierDiscountRuleToSupplierDiscountRuleDTO(supplierDiscountRule);

        restSupplierDiscountRuleMockMvc.perform(post("/api/supplierDiscountRules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(supplierDiscountRuleDTO)))
                .andExpect(status().isBadRequest());

        List<SupplierDiscountRule> supplierDiscountRules = supplierDiscountRuleRepository.findAll();
        assertThat(supplierDiscountRules).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplierDiscountRules() throws Exception {
        // Initialize the database
        supplierDiscountRuleRepository.saveAndFlush(supplierDiscountRule);

        // Get all the supplierDiscountRules
        restSupplierDiscountRuleMockMvc.perform(get("/api/supplierDiscountRules"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(supplierDiscountRule.getId().intValue())))
                .andExpect(jsonPath("$.[*].supplierCode").value(hasItem(DEFAULT_SUPPLIER_CODE.toString())))
                .andExpect(jsonPath("$.[*].ruleNo").value(hasItem(DEFAULT_RULE_NO)))
                .andExpect(jsonPath("$.[*].isAppliedToSales").value(hasItem(DEFAULT_IS_APPLIED_TO_SALES.booleanValue())))
                .andExpect(jsonPath("$.[*].isAppliedOnSpecialItemsOnly").value(hasItem(DEFAULT_IS_APPLIED_ON_SPECIAL_ITEMS_ONLY.booleanValue())))
                .andExpect(jsonPath("$.[*].customerCode").value(hasItem(DEFAULT_CUSTOMER_CODE.toString())))
                .andExpect(jsonPath("$.[*].customerCategoryCode").value(hasItem(DEFAULT_CUSTOMER_CATEGORY_CODE.toString())))
                .andExpect(jsonPath("$.[*].productCode").value(hasItem(DEFAULT_PRODUCT_CODE.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].qtyBreak").value(hasItem(DEFAULT_QTY_BREAK)))
                .andExpect(jsonPath("$.[*].fromStockGroupCode").value(hasItem(DEFAULT_FROM_STOCK_GROUP_CODE.toString())))
                .andExpect(jsonPath("$.[*].toStockGroupCode").value(hasItem(DEFAULT_TO_STOCK_GROUP_CODE.toString())))
                .andExpect(jsonPath("$.[*].taxCode").value(hasItem(DEFAULT_TAX_CODE.toString())))
                .andExpect(jsonPath("$.[*].isAppliedWhenTaxExempt").value(hasItem(DEFAULT_IS_APPLIED_WHEN_TAX_EXEMPT.booleanValue())))
                .andExpect(jsonPath("$.[*].storeCode").value(hasItem(DEFAULT_STORE_CODE.toString())))
                .andExpect(jsonPath("$.[*].discountName").value(hasItem(DEFAULT_DISCOUNT_NAME.toString())))
                .andExpect(jsonPath("$.[*].stockFamilyCode").value(hasItem(DEFAULT_STOCK_FAMILY_CODE.toString())))
                .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.intValue())))
                .andExpect(jsonPath("$.[*].discountFormula").value(hasItem(DEFAULT_DISCOUNT_FORMULA.toString())));
    }

    @Test
    @Transactional
    public void getSupplierDiscountRule() throws Exception {
        // Initialize the database
        supplierDiscountRuleRepository.saveAndFlush(supplierDiscountRule);

        // Get the supplierDiscountRule
        restSupplierDiscountRuleMockMvc.perform(get("/api/supplierDiscountRules/{id}", supplierDiscountRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(supplierDiscountRule.getId().intValue()))
            .andExpect(jsonPath("$.supplierCode").value(DEFAULT_SUPPLIER_CODE.toString()))
            .andExpect(jsonPath("$.ruleNo").value(DEFAULT_RULE_NO))
            .andExpect(jsonPath("$.isAppliedToSales").value(DEFAULT_IS_APPLIED_TO_SALES.booleanValue()))
            .andExpect(jsonPath("$.isAppliedOnSpecialItemsOnly").value(DEFAULT_IS_APPLIED_ON_SPECIAL_ITEMS_ONLY.booleanValue()))
            .andExpect(jsonPath("$.customerCode").value(DEFAULT_CUSTOMER_CODE.toString()))
            .andExpect(jsonPath("$.customerCategoryCode").value(DEFAULT_CUSTOMER_CATEGORY_CODE.toString()))
            .andExpect(jsonPath("$.productCode").value(DEFAULT_PRODUCT_CODE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.qtyBreak").value(DEFAULT_QTY_BREAK))
            .andExpect(jsonPath("$.fromStockGroupCode").value(DEFAULT_FROM_STOCK_GROUP_CODE.toString()))
            .andExpect(jsonPath("$.toStockGroupCode").value(DEFAULT_TO_STOCK_GROUP_CODE.toString()))
            .andExpect(jsonPath("$.taxCode").value(DEFAULT_TAX_CODE.toString()))
            .andExpect(jsonPath("$.isAppliedWhenTaxExempt").value(DEFAULT_IS_APPLIED_WHEN_TAX_EXEMPT.booleanValue()))
            .andExpect(jsonPath("$.storeCode").value(DEFAULT_STORE_CODE.toString()))
            .andExpect(jsonPath("$.discountName").value(DEFAULT_DISCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.stockFamilyCode").value(DEFAULT_STOCK_FAMILY_CODE.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.intValue()))
            .andExpect(jsonPath("$.discountFormula").value(DEFAULT_DISCOUNT_FORMULA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSupplierDiscountRule() throws Exception {
        // Get the supplierDiscountRule
        restSupplierDiscountRuleMockMvc.perform(get("/api/supplierDiscountRules/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplierDiscountRule() throws Exception {
        // Initialize the database
        supplierDiscountRuleRepository.saveAndFlush(supplierDiscountRule);

		int databaseSizeBeforeUpdate = supplierDiscountRuleRepository.findAll().size();

        // Update the supplierDiscountRule
        supplierDiscountRule.setSupplierCode(UPDATED_SUPPLIER_CODE);
        supplierDiscountRule.setRuleNo(UPDATED_RULE_NO);
        supplierDiscountRule.setIsAppliedToSales(UPDATED_IS_APPLIED_TO_SALES);
        supplierDiscountRule.setIsAppliedOnSpecialItemsOnly(UPDATED_IS_APPLIED_ON_SPECIAL_ITEMS_ONLY);
        supplierDiscountRule.setCustomerCode(UPDATED_CUSTOMER_CODE);
        supplierDiscountRule.setCustomerCategoryCode(UPDATED_CUSTOMER_CATEGORY_CODE);
        supplierDiscountRule.setProductCode(UPDATED_PRODUCT_CODE);
        supplierDiscountRule.setStartDate(UPDATED_START_DATE);
        supplierDiscountRule.setEndDate(UPDATED_END_DATE);
        supplierDiscountRule.setQtyBreak(UPDATED_QTY_BREAK);
        supplierDiscountRule.setFromStockGroupCode(UPDATED_FROM_STOCK_GROUP_CODE);
        supplierDiscountRule.setToStockGroupCode(UPDATED_TO_STOCK_GROUP_CODE);
        supplierDiscountRule.setTaxCode(UPDATED_TAX_CODE);
        supplierDiscountRule.setIsAppliedWhenTaxExempt(UPDATED_IS_APPLIED_WHEN_TAX_EXEMPT);
        supplierDiscountRule.setStoreCode(UPDATED_STORE_CODE);
        supplierDiscountRule.setDiscountName(UPDATED_DISCOUNT_NAME);
        supplierDiscountRule.setStockFamilyCode(UPDATED_STOCK_FAMILY_CODE);
        supplierDiscountRule.setCost(UPDATED_COST);
        supplierDiscountRule.setDiscountFormula(UPDATED_DISCOUNT_FORMULA);
        SupplierDiscountRuleDTO supplierDiscountRuleDTO = supplierDiscountRuleMapper.supplierDiscountRuleToSupplierDiscountRuleDTO(supplierDiscountRule);

        restSupplierDiscountRuleMockMvc.perform(put("/api/supplierDiscountRules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(supplierDiscountRuleDTO)))
                .andExpect(status().isOk());

        // Validate the SupplierDiscountRule in the database
        List<SupplierDiscountRule> supplierDiscountRules = supplierDiscountRuleRepository.findAll();
        assertThat(supplierDiscountRules).hasSize(databaseSizeBeforeUpdate);
        SupplierDiscountRule testSupplierDiscountRule = supplierDiscountRules.get(supplierDiscountRules.size() - 1);
        assertThat(testSupplierDiscountRule.getSupplierCode()).isEqualTo(UPDATED_SUPPLIER_CODE);
        assertThat(testSupplierDiscountRule.getRuleNo()).isEqualTo(UPDATED_RULE_NO);
        assertThat(testSupplierDiscountRule.getIsAppliedToSales()).isEqualTo(UPDATED_IS_APPLIED_TO_SALES);
        assertThat(testSupplierDiscountRule.getIsAppliedOnSpecialItemsOnly()).isEqualTo(UPDATED_IS_APPLIED_ON_SPECIAL_ITEMS_ONLY);
        assertThat(testSupplierDiscountRule.getCustomerCode()).isEqualTo(UPDATED_CUSTOMER_CODE);
        assertThat(testSupplierDiscountRule.getCustomerCategoryCode()).isEqualTo(UPDATED_CUSTOMER_CATEGORY_CODE);
        assertThat(testSupplierDiscountRule.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testSupplierDiscountRule.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSupplierDiscountRule.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSupplierDiscountRule.getQtyBreak()).isEqualTo(UPDATED_QTY_BREAK);
        assertThat(testSupplierDiscountRule.getFromStockGroupCode()).isEqualTo(UPDATED_FROM_STOCK_GROUP_CODE);
        assertThat(testSupplierDiscountRule.getToStockGroupCode()).isEqualTo(UPDATED_TO_STOCK_GROUP_CODE);
        assertThat(testSupplierDiscountRule.getTaxCode()).isEqualTo(UPDATED_TAX_CODE);
        assertThat(testSupplierDiscountRule.getIsAppliedWhenTaxExempt()).isEqualTo(UPDATED_IS_APPLIED_WHEN_TAX_EXEMPT);
        assertThat(testSupplierDiscountRule.getStoreCode()).isEqualTo(UPDATED_STORE_CODE);
        assertThat(testSupplierDiscountRule.getDiscountName()).isEqualTo(UPDATED_DISCOUNT_NAME);
        assertThat(testSupplierDiscountRule.getStockFamilyCode()).isEqualTo(UPDATED_STOCK_FAMILY_CODE);
        assertThat(testSupplierDiscountRule.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testSupplierDiscountRule.getDiscountFormula()).isEqualTo(UPDATED_DISCOUNT_FORMULA);
    }

    @Test
    @Transactional
    public void deleteSupplierDiscountRule() throws Exception {
        // Initialize the database
        supplierDiscountRuleRepository.saveAndFlush(supplierDiscountRule);

		int databaseSizeBeforeDelete = supplierDiscountRuleRepository.findAll().size();

        // Get the supplierDiscountRule
        restSupplierDiscountRuleMockMvc.perform(delete("/api/supplierDiscountRules/{id}", supplierDiscountRule.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SupplierDiscountRule> supplierDiscountRules = supplierDiscountRuleRepository.findAll();
        assertThat(supplierDiscountRules).hasSize(databaseSizeBeforeDelete - 1);
    }
}
