package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.Product;
import com.enginemobi.bssuite.repository.ProductRepository;
import com.enginemobi.bssuite.repository.search.ProductSearchRepository;
import com.enginemobi.bssuite.web.rest.dto.ProductDTO;
import com.enginemobi.bssuite.web.rest.mapper.ProductMapper;

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
 * Test class for the ProductResource REST controller.
 *
 * @see ProductResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProductResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_LONG_DESCRIPTION = "AAAAA";
    private static final String UPDATED_LONG_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_ALTERNATE_CODE = "AAAAA";
    private static final String UPDATED_ALTERNATE_CODE = "BBBBB";
    private static final String DEFAULT_BIN = "AAAAA";
    private static final String UPDATED_BIN = "BBBBB";

    private static final Boolean DEFAULT_IS_ON_SPECIAL = false;
    private static final Boolean UPDATED_IS_ON_SPECIAL = true;

    private static final Boolean DEFAULT_IS_ON_HOLD = false;
    private static final Boolean UPDATED_IS_ON_HOLD = true;

    private static final Boolean DEFAULT_IS_IN_PRICELIST_REPORTS = false;
    private static final Boolean UPDATED_IS_IN_PRICELIST_REPORTS = true;

    private static final Double DEFAULT_QTY_ON_ORDER = 1D;
    private static final Double UPDATED_QTY_ON_ORDER = 2D;

    private static final Double DEFAULT_QTY_STOCK_ON_HOLD = 1D;
    private static final Double UPDATED_QTY_STOCK_ON_HOLD = 2D;

    private static final Double DEFAULT_QTY_BACKORDERED = 1D;
    private static final Double UPDATED_QTY_BACKORDERED = 2D;

    private static final Double DEFAULT_QTY_ALLOCATED = 1D;
    private static final Double UPDATED_QTY_ALLOCATED = 2D;

    private static final Double DEFAULT_QTY_BACKORDER_HOLD = 1D;
    private static final Double UPDATED_QTY_BACKORDER_HOLD = 2D;

    private static final Double DEFAULT_QTY_CONSIGNED = 1D;
    private static final Double UPDATED_QTY_CONSIGNED = 2D;

    private static final Double DEFAULT_QTY_WAREHOUSE_RECEIVED = 1D;
    private static final Double UPDATED_QTY_WAREHOUSE_RECEIVED = 2D;

    private static final Double DEFAULT_QTY_STOCKTAKE_VARIANCE = 1D;
    private static final Double UPDATED_QTY_STOCKTAKE_VARIANCE = 2D;

    private static final Double DEFAULT_QTY_TRANSIT_IN = 1D;
    private static final Double UPDATED_QTY_TRANSIT_IN = 2D;

    private static final Double DEFAULT_QTY_TRANSIT_OUT = 1D;
    private static final Double UPDATED_QTY_TRANSIT_OUT = 2D;

    private static final BigDecimal DEFAULT_COST = new BigDecimal(0);
    private static final BigDecimal UPDATED_COST = new BigDecimal(1);

    private static final BigDecimal DEFAULT_WHOLESALE_LIST_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_WHOLESALE_LIST_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LIST_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LIST_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TRADE_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TRADE_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BOX_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_BOX_COST = new BigDecimal(2);
    private static final String DEFAULT_UNIT_MEASURE = "AAAAA";
    private static final String UPDATED_UNIT_MEASURE = "BBBBB";
    private static final String DEFAULT_BOX_MEASURE = "AAAAA";
    private static final String UPDATED_BOX_MEASURE = "BBBBB";

    private static final BigDecimal DEFAULT_BOX_CONVERSION_FACTOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_BOX_CONVERSION_FACTOR = new BigDecimal(2);

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    private static final Double DEFAULT_VOLUMN = 1D;
    private static final Double UPDATED_VOLUMN = 2D;

    private static final Integer DEFAULT_SERVICE_COVER = 1;
    private static final Integer UPDATED_SERVICE_COVER = 2;

    private static final Double DEFAULT_QTY_FLOOR_LEVEL = 1D;
    private static final Double UPDATED_QTY_FLOOR_LEVEL = 2D;

    private static final Double DEFAULT_QTY_REORDER_LEVEL = 1D;
    private static final Double UPDATED_QTY_REORDER_LEVEL = 2D;

    private static final Double DEFAULT_QTY_OVERSTOCK_LEVEL = 1D;
    private static final Double UPDATED_QTY_OVERSTOCK_LEVEL = 2D;

    private static final Boolean DEFAULT_IS_COMMENT = false;
    private static final Boolean UPDATED_IS_COMMENT = true;

    private static final Boolean DEFAULT_IS_DIMINISHING = false;
    private static final Boolean UPDATED_IS_DIMINISHING = true;

    private static final Boolean DEFAULT_IS_NON_TAX_EXEPTABLE = false;
    private static final Boolean UPDATED_IS_NON_TAX_EXEPTABLE = true;

    private static final Integer DEFAULT_LEAD_TIME = 1;
    private static final Integer UPDATED_LEAD_TIME = 2;

    private static final Integer DEFAULT_PURCHASE_UNIT = 1;
    private static final Integer UPDATED_PURCHASE_UNIT = 2;

    private static final Double DEFAULT_EST_MONTHLY_SALES = 1D;
    private static final Double UPDATED_EST_MONTHLY_SALES = 2D;

    private static final LocalDate DEFAULT_DATE_FIRST_SALE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIRST_SALE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_LAST_SALE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_LAST_SALE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIRST_ORDER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIRST_ORDER = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_LAST_DELIVERY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_LAST_DELIVERY = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_NEXT_DELIVERY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NEXT_DELIVERY = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_LAST_TRANSFER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_LAST_TRANSFER = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_LAST_ORDER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_LAST_ORDER = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_LAST_STOCKTAKE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_LAST_STOCKTAKE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_ARCHIVED = false;
    private static final Boolean UPDATED_IS_ARCHIVED = true;
    private static final String DEFAULT_CLASS_CODE = "AAAAA";
    private static final String UPDATED_CLASS_CODE = "BBBBB";

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ProductMapper productMapper;

    @Inject
    private ProductSearchRepository productSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProductMockMvc;

    private Product product;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductResource productResource = new ProductResource();
        ReflectionTestUtils.setField(productResource, "productRepository", productRepository);
        ReflectionTestUtils.setField(productResource, "productMapper", productMapper);
        ReflectionTestUtils.setField(productResource, "productSearchRepository", productSearchRepository);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        product = new Product();
        product.setCode(DEFAULT_CODE);
        product.setName(DEFAULT_NAME);
        product.setDescription(DEFAULT_DESCRIPTION);
        product.setLongDescription(DEFAULT_LONG_DESCRIPTION);
        product.setAlternateCode(DEFAULT_ALTERNATE_CODE);
        product.setBin(DEFAULT_BIN);
        product.setIsOnSpecial(DEFAULT_IS_ON_SPECIAL);
        product.setIsOnHold(DEFAULT_IS_ON_HOLD);
        product.setIsInPricelistReports(DEFAULT_IS_IN_PRICELIST_REPORTS);
        product.setQtyOnOrder(DEFAULT_QTY_ON_ORDER);
        product.setQtyStockOnHold(DEFAULT_QTY_STOCK_ON_HOLD);
        product.setQtyBackordered(DEFAULT_QTY_BACKORDERED);
        product.setQtyAllocated(DEFAULT_QTY_ALLOCATED);
        product.setQtyBackorderHold(DEFAULT_QTY_BACKORDER_HOLD);
        product.setQtyConsigned(DEFAULT_QTY_CONSIGNED);
        product.setQtyWarehouseReceived(DEFAULT_QTY_WAREHOUSE_RECEIVED);
        product.setQtyStocktakeVariance(DEFAULT_QTY_STOCKTAKE_VARIANCE);
        product.setQtyTransitIn(DEFAULT_QTY_TRANSIT_IN);
        product.setQtyTransitOut(DEFAULT_QTY_TRANSIT_OUT);
        product.setCost(DEFAULT_COST);
        product.setWholesaleListPrice(DEFAULT_WHOLESALE_LIST_PRICE);
        product.setListPrice(DEFAULT_LIST_PRICE);
        product.setTradePrice(DEFAULT_TRADE_PRICE);
        product.setBoxCost(DEFAULT_BOX_COST);
        product.setUnitMeasure(DEFAULT_UNIT_MEASURE);
        product.setBoxMeasure(DEFAULT_BOX_MEASURE);
        product.setBoxConversionFactor(DEFAULT_BOX_CONVERSION_FACTOR);
        product.setWeight(DEFAULT_WEIGHT);
        product.setVolumn(DEFAULT_VOLUMN);
        product.setServiceCover(DEFAULT_SERVICE_COVER);
        product.setQtyFloorLevel(DEFAULT_QTY_FLOOR_LEVEL);
        product.setQtyReorderLevel(DEFAULT_QTY_REORDER_LEVEL);
        product.setQtyOverstockLevel(DEFAULT_QTY_OVERSTOCK_LEVEL);
        product.setIsComment(DEFAULT_IS_COMMENT);
        product.setIsDiminishing(DEFAULT_IS_DIMINISHING);
        product.setIsNonTaxExeptable(DEFAULT_IS_NON_TAX_EXEPTABLE);
        product.setLeadTime(DEFAULT_LEAD_TIME);
        product.setPurchaseUnit(DEFAULT_PURCHASE_UNIT);
        product.setEstMonthlySales(DEFAULT_EST_MONTHLY_SALES);
        product.setDateFirstSale(DEFAULT_DATE_FIRST_SALE);
        product.setDateLastSale(DEFAULT_DATE_LAST_SALE);
        product.setDateFirstOrder(DEFAULT_DATE_FIRST_ORDER);
        product.setDateCreated(DEFAULT_DATE_CREATED);
        product.setDateLastDelivery(DEFAULT_DATE_LAST_DELIVERY);
        product.setDateNextDelivery(DEFAULT_DATE_NEXT_DELIVERY);
        product.setDateLastTransfer(DEFAULT_DATE_LAST_TRANSFER);
        product.setDateLastOrder(DEFAULT_DATE_LAST_ORDER);
        product.setDateLastStocktake(DEFAULT_DATE_LAST_STOCKTAKE);
        product.setIsArchived(DEFAULT_IS_ARCHIVED);
        product.setClassCode(DEFAULT_CLASS_CODE);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        restProductMockMvc.perform(post("/api/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productDTO)))
                .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = products.get(products.size() - 1);
        assertThat(testProduct.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProduct.getLongDescription()).isEqualTo(DEFAULT_LONG_DESCRIPTION);
        assertThat(testProduct.getAlternateCode()).isEqualTo(DEFAULT_ALTERNATE_CODE);
        assertThat(testProduct.getBin()).isEqualTo(DEFAULT_BIN);
        assertThat(testProduct.getIsOnSpecial()).isEqualTo(DEFAULT_IS_ON_SPECIAL);
        assertThat(testProduct.getIsOnHold()).isEqualTo(DEFAULT_IS_ON_HOLD);
        assertThat(testProduct.getIsInPricelistReports()).isEqualTo(DEFAULT_IS_IN_PRICELIST_REPORTS);
        assertThat(testProduct.getQtyOnOrder()).isEqualTo(DEFAULT_QTY_ON_ORDER);
        assertThat(testProduct.getQtyStockOnHold()).isEqualTo(DEFAULT_QTY_STOCK_ON_HOLD);
        assertThat(testProduct.getQtyBackordered()).isEqualTo(DEFAULT_QTY_BACKORDERED);
        assertThat(testProduct.getQtyAllocated()).isEqualTo(DEFAULT_QTY_ALLOCATED);
        assertThat(testProduct.getQtyBackorderHold()).isEqualTo(DEFAULT_QTY_BACKORDER_HOLD);
        assertThat(testProduct.getQtyConsigned()).isEqualTo(DEFAULT_QTY_CONSIGNED);
        assertThat(testProduct.getQtyWarehouseReceived()).isEqualTo(DEFAULT_QTY_WAREHOUSE_RECEIVED);
        assertThat(testProduct.getQtyStocktakeVariance()).isEqualTo(DEFAULT_QTY_STOCKTAKE_VARIANCE);
        assertThat(testProduct.getQtyTransitIn()).isEqualTo(DEFAULT_QTY_TRANSIT_IN);
        assertThat(testProduct.getQtyTransitOut()).isEqualTo(DEFAULT_QTY_TRANSIT_OUT);
        assertThat(testProduct.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testProduct.getWholesaleListPrice()).isEqualTo(DEFAULT_WHOLESALE_LIST_PRICE);
        assertThat(testProduct.getListPrice()).isEqualTo(DEFAULT_LIST_PRICE);
        assertThat(testProduct.getTradePrice()).isEqualTo(DEFAULT_TRADE_PRICE);
        assertThat(testProduct.getBoxCost()).isEqualTo(DEFAULT_BOX_COST);
        assertThat(testProduct.getUnitMeasure()).isEqualTo(DEFAULT_UNIT_MEASURE);
        assertThat(testProduct.getBoxMeasure()).isEqualTo(DEFAULT_BOX_MEASURE);
        assertThat(testProduct.getBoxConversionFactor()).isEqualTo(DEFAULT_BOX_CONVERSION_FACTOR);
        assertThat(testProduct.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testProduct.getVolumn()).isEqualTo(DEFAULT_VOLUMN);
        assertThat(testProduct.getServiceCover()).isEqualTo(DEFAULT_SERVICE_COVER);
        assertThat(testProduct.getQtyFloorLevel()).isEqualTo(DEFAULT_QTY_FLOOR_LEVEL);
        assertThat(testProduct.getQtyReorderLevel()).isEqualTo(DEFAULT_QTY_REORDER_LEVEL);
        assertThat(testProduct.getQtyOverstockLevel()).isEqualTo(DEFAULT_QTY_OVERSTOCK_LEVEL);
        assertThat(testProduct.getIsComment()).isEqualTo(DEFAULT_IS_COMMENT);
        assertThat(testProduct.getIsDiminishing()).isEqualTo(DEFAULT_IS_DIMINISHING);
        assertThat(testProduct.getIsNonTaxExeptable()).isEqualTo(DEFAULT_IS_NON_TAX_EXEPTABLE);
        assertThat(testProduct.getLeadTime()).isEqualTo(DEFAULT_LEAD_TIME);
        assertThat(testProduct.getPurchaseUnit()).isEqualTo(DEFAULT_PURCHASE_UNIT);
        assertThat(testProduct.getEstMonthlySales()).isEqualTo(DEFAULT_EST_MONTHLY_SALES);
        assertThat(testProduct.getDateFirstSale()).isEqualTo(DEFAULT_DATE_FIRST_SALE);
        assertThat(testProduct.getDateLastSale()).isEqualTo(DEFAULT_DATE_LAST_SALE);
        assertThat(testProduct.getDateFirstOrder()).isEqualTo(DEFAULT_DATE_FIRST_ORDER);
        assertThat(testProduct.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testProduct.getDateLastDelivery()).isEqualTo(DEFAULT_DATE_LAST_DELIVERY);
        assertThat(testProduct.getDateNextDelivery()).isEqualTo(DEFAULT_DATE_NEXT_DELIVERY);
        assertThat(testProduct.getDateLastTransfer()).isEqualTo(DEFAULT_DATE_LAST_TRANSFER);
        assertThat(testProduct.getDateLastOrder()).isEqualTo(DEFAULT_DATE_LAST_ORDER);
        assertThat(testProduct.getDateLastStocktake()).isEqualTo(DEFAULT_DATE_LAST_STOCKTAKE);
        assertThat(testProduct.getIsArchived()).isEqualTo(DEFAULT_IS_ARCHIVED);
        assertThat(testProduct.getClassCode()).isEqualTo(DEFAULT_CLASS_CODE);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setCode(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        restProductMockMvc.perform(post("/api/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productDTO)))
                .andExpect(status().isBadRequest());

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the products
        restProductMockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].alternateCode").value(hasItem(DEFAULT_ALTERNATE_CODE.toString())))
                .andExpect(jsonPath("$.[*].bin").value(hasItem(DEFAULT_BIN.toString())))
                .andExpect(jsonPath("$.[*].isOnSpecial").value(hasItem(DEFAULT_IS_ON_SPECIAL.booleanValue())))
                .andExpect(jsonPath("$.[*].isOnHold").value(hasItem(DEFAULT_IS_ON_HOLD.booleanValue())))
                .andExpect(jsonPath("$.[*].isInPricelistReports").value(hasItem(DEFAULT_IS_IN_PRICELIST_REPORTS.booleanValue())))
                .andExpect(jsonPath("$.[*].qtyOnOrder").value(hasItem(DEFAULT_QTY_ON_ORDER.doubleValue())))
                .andExpect(jsonPath("$.[*].qtyStockOnHold").value(hasItem(DEFAULT_QTY_STOCK_ON_HOLD.doubleValue())))
                .andExpect(jsonPath("$.[*].qtyBackordered").value(hasItem(DEFAULT_QTY_BACKORDERED.doubleValue())))
                .andExpect(jsonPath("$.[*].qtyAllocated").value(hasItem(DEFAULT_QTY_ALLOCATED.doubleValue())))
                .andExpect(jsonPath("$.[*].qtyBackorderHold").value(hasItem(DEFAULT_QTY_BACKORDER_HOLD.doubleValue())))
                .andExpect(jsonPath("$.[*].qtyConsigned").value(hasItem(DEFAULT_QTY_CONSIGNED.doubleValue())))
                .andExpect(jsonPath("$.[*].qtyWarehouseReceived").value(hasItem(DEFAULT_QTY_WAREHOUSE_RECEIVED.doubleValue())))
                .andExpect(jsonPath("$.[*].qtyStocktakeVariance").value(hasItem(DEFAULT_QTY_STOCKTAKE_VARIANCE.doubleValue())))
                .andExpect(jsonPath("$.[*].qtyTransitIn").value(hasItem(DEFAULT_QTY_TRANSIT_IN.doubleValue())))
                .andExpect(jsonPath("$.[*].qtyTransitOut").value(hasItem(DEFAULT_QTY_TRANSIT_OUT.doubleValue())))
                .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.intValue())))
                .andExpect(jsonPath("$.[*].wholesaleListPrice").value(hasItem(DEFAULT_WHOLESALE_LIST_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].listPrice").value(hasItem(DEFAULT_LIST_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].tradePrice").value(hasItem(DEFAULT_TRADE_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].boxCost").value(hasItem(DEFAULT_BOX_COST.intValue())))
                .andExpect(jsonPath("$.[*].unitMeasure").value(hasItem(DEFAULT_UNIT_MEASURE.toString())))
                .andExpect(jsonPath("$.[*].boxMeasure").value(hasItem(DEFAULT_BOX_MEASURE.toString())))
                .andExpect(jsonPath("$.[*].boxConversionFactor").value(hasItem(DEFAULT_BOX_CONVERSION_FACTOR.intValue())))
                .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
                .andExpect(jsonPath("$.[*].volumn").value(hasItem(DEFAULT_VOLUMN.doubleValue())))
                .andExpect(jsonPath("$.[*].serviceCover").value(hasItem(DEFAULT_SERVICE_COVER)))
                .andExpect(jsonPath("$.[*].qtyFloorLevel").value(hasItem(DEFAULT_QTY_FLOOR_LEVEL.doubleValue())))
                .andExpect(jsonPath("$.[*].qtyReorderLevel").value(hasItem(DEFAULT_QTY_REORDER_LEVEL.doubleValue())))
                .andExpect(jsonPath("$.[*].qtyOverstockLevel").value(hasItem(DEFAULT_QTY_OVERSTOCK_LEVEL.doubleValue())))
                .andExpect(jsonPath("$.[*].isComment").value(hasItem(DEFAULT_IS_COMMENT.booleanValue())))
                .andExpect(jsonPath("$.[*].isDiminishing").value(hasItem(DEFAULT_IS_DIMINISHING.booleanValue())))
                .andExpect(jsonPath("$.[*].isNonTaxExeptable").value(hasItem(DEFAULT_IS_NON_TAX_EXEPTABLE.booleanValue())))
                .andExpect(jsonPath("$.[*].leadTime").value(hasItem(DEFAULT_LEAD_TIME)))
                .andExpect(jsonPath("$.[*].purchaseUnit").value(hasItem(DEFAULT_PURCHASE_UNIT)))
                .andExpect(jsonPath("$.[*].estMonthlySales").value(hasItem(DEFAULT_EST_MONTHLY_SALES.doubleValue())))
                .andExpect(jsonPath("$.[*].dateFirstSale").value(hasItem(DEFAULT_DATE_FIRST_SALE.toString())))
                .andExpect(jsonPath("$.[*].dateLastSale").value(hasItem(DEFAULT_DATE_LAST_SALE.toString())))
                .andExpect(jsonPath("$.[*].dateFirstOrder").value(hasItem(DEFAULT_DATE_FIRST_ORDER.toString())))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
                .andExpect(jsonPath("$.[*].dateLastDelivery").value(hasItem(DEFAULT_DATE_LAST_DELIVERY.toString())))
                .andExpect(jsonPath("$.[*].dateNextDelivery").value(hasItem(DEFAULT_DATE_NEXT_DELIVERY.toString())))
                .andExpect(jsonPath("$.[*].dateLastTransfer").value(hasItem(DEFAULT_DATE_LAST_TRANSFER.toString())))
                .andExpect(jsonPath("$.[*].dateLastOrder").value(hasItem(DEFAULT_DATE_LAST_ORDER.toString())))
                .andExpect(jsonPath("$.[*].dateLastStocktake").value(hasItem(DEFAULT_DATE_LAST_STOCKTAKE.toString())))
                .andExpect(jsonPath("$.[*].isArchived").value(hasItem(DEFAULT_IS_ARCHIVED.booleanValue())))
                .andExpect(jsonPath("$.[*].classCode").value(hasItem(DEFAULT_CLASS_CODE.toString())));
    }

    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.longDescription").value(DEFAULT_LONG_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.alternateCode").value(DEFAULT_ALTERNATE_CODE.toString()))
            .andExpect(jsonPath("$.bin").value(DEFAULT_BIN.toString()))
            .andExpect(jsonPath("$.isOnSpecial").value(DEFAULT_IS_ON_SPECIAL.booleanValue()))
            .andExpect(jsonPath("$.isOnHold").value(DEFAULT_IS_ON_HOLD.booleanValue()))
            .andExpect(jsonPath("$.isInPricelistReports").value(DEFAULT_IS_IN_PRICELIST_REPORTS.booleanValue()))
            .andExpect(jsonPath("$.qtyOnOrder").value(DEFAULT_QTY_ON_ORDER.doubleValue()))
            .andExpect(jsonPath("$.qtyStockOnHold").value(DEFAULT_QTY_STOCK_ON_HOLD.doubleValue()))
            .andExpect(jsonPath("$.qtyBackordered").value(DEFAULT_QTY_BACKORDERED.doubleValue()))
            .andExpect(jsonPath("$.qtyAllocated").value(DEFAULT_QTY_ALLOCATED.doubleValue()))
            .andExpect(jsonPath("$.qtyBackorderHold").value(DEFAULT_QTY_BACKORDER_HOLD.doubleValue()))
            .andExpect(jsonPath("$.qtyConsigned").value(DEFAULT_QTY_CONSIGNED.doubleValue()))
            .andExpect(jsonPath("$.qtyWarehouseReceived").value(DEFAULT_QTY_WAREHOUSE_RECEIVED.doubleValue()))
            .andExpect(jsonPath("$.qtyStocktakeVariance").value(DEFAULT_QTY_STOCKTAKE_VARIANCE.doubleValue()))
            .andExpect(jsonPath("$.qtyTransitIn").value(DEFAULT_QTY_TRANSIT_IN.doubleValue()))
            .andExpect(jsonPath("$.qtyTransitOut").value(DEFAULT_QTY_TRANSIT_OUT.doubleValue()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.intValue()))
            .andExpect(jsonPath("$.wholesaleListPrice").value(DEFAULT_WHOLESALE_LIST_PRICE.intValue()))
            .andExpect(jsonPath("$.listPrice").value(DEFAULT_LIST_PRICE.intValue()))
            .andExpect(jsonPath("$.tradePrice").value(DEFAULT_TRADE_PRICE.intValue()))
            .andExpect(jsonPath("$.boxCost").value(DEFAULT_BOX_COST.intValue()))
            .andExpect(jsonPath("$.unitMeasure").value(DEFAULT_UNIT_MEASURE.toString()))
            .andExpect(jsonPath("$.boxMeasure").value(DEFAULT_BOX_MEASURE.toString()))
            .andExpect(jsonPath("$.boxConversionFactor").value(DEFAULT_BOX_CONVERSION_FACTOR.intValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.volumn").value(DEFAULT_VOLUMN.doubleValue()))
            .andExpect(jsonPath("$.serviceCover").value(DEFAULT_SERVICE_COVER))
            .andExpect(jsonPath("$.qtyFloorLevel").value(DEFAULT_QTY_FLOOR_LEVEL.doubleValue()))
            .andExpect(jsonPath("$.qtyReorderLevel").value(DEFAULT_QTY_REORDER_LEVEL.doubleValue()))
            .andExpect(jsonPath("$.qtyOverstockLevel").value(DEFAULT_QTY_OVERSTOCK_LEVEL.doubleValue()))
            .andExpect(jsonPath("$.isComment").value(DEFAULT_IS_COMMENT.booleanValue()))
            .andExpect(jsonPath("$.isDiminishing").value(DEFAULT_IS_DIMINISHING.booleanValue()))
            .andExpect(jsonPath("$.isNonTaxExeptable").value(DEFAULT_IS_NON_TAX_EXEPTABLE.booleanValue()))
            .andExpect(jsonPath("$.leadTime").value(DEFAULT_LEAD_TIME))
            .andExpect(jsonPath("$.purchaseUnit").value(DEFAULT_PURCHASE_UNIT))
            .andExpect(jsonPath("$.estMonthlySales").value(DEFAULT_EST_MONTHLY_SALES.doubleValue()))
            .andExpect(jsonPath("$.dateFirstSale").value(DEFAULT_DATE_FIRST_SALE.toString()))
            .andExpect(jsonPath("$.dateLastSale").value(DEFAULT_DATE_LAST_SALE.toString()))
            .andExpect(jsonPath("$.dateFirstOrder").value(DEFAULT_DATE_FIRST_ORDER.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateLastDelivery").value(DEFAULT_DATE_LAST_DELIVERY.toString()))
            .andExpect(jsonPath("$.dateNextDelivery").value(DEFAULT_DATE_NEXT_DELIVERY.toString()))
            .andExpect(jsonPath("$.dateLastTransfer").value(DEFAULT_DATE_LAST_TRANSFER.toString()))
            .andExpect(jsonPath("$.dateLastOrder").value(DEFAULT_DATE_LAST_ORDER.toString()))
            .andExpect(jsonPath("$.dateLastStocktake").value(DEFAULT_DATE_LAST_STOCKTAKE.toString()))
            .andExpect(jsonPath("$.isArchived").value(DEFAULT_IS_ARCHIVED.booleanValue()))
            .andExpect(jsonPath("$.classCode").value(DEFAULT_CLASS_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

		int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        product.setCode(UPDATED_CODE);
        product.setName(UPDATED_NAME);
        product.setDescription(UPDATED_DESCRIPTION);
        product.setLongDescription(UPDATED_LONG_DESCRIPTION);
        product.setAlternateCode(UPDATED_ALTERNATE_CODE);
        product.setBin(UPDATED_BIN);
        product.setIsOnSpecial(UPDATED_IS_ON_SPECIAL);
        product.setIsOnHold(UPDATED_IS_ON_HOLD);
        product.setIsInPricelistReports(UPDATED_IS_IN_PRICELIST_REPORTS);
        product.setQtyOnOrder(UPDATED_QTY_ON_ORDER);
        product.setQtyStockOnHold(UPDATED_QTY_STOCK_ON_HOLD);
        product.setQtyBackordered(UPDATED_QTY_BACKORDERED);
        product.setQtyAllocated(UPDATED_QTY_ALLOCATED);
        product.setQtyBackorderHold(UPDATED_QTY_BACKORDER_HOLD);
        product.setQtyConsigned(UPDATED_QTY_CONSIGNED);
        product.setQtyWarehouseReceived(UPDATED_QTY_WAREHOUSE_RECEIVED);
        product.setQtyStocktakeVariance(UPDATED_QTY_STOCKTAKE_VARIANCE);
        product.setQtyTransitIn(UPDATED_QTY_TRANSIT_IN);
        product.setQtyTransitOut(UPDATED_QTY_TRANSIT_OUT);
        product.setCost(UPDATED_COST);
        product.setWholesaleListPrice(UPDATED_WHOLESALE_LIST_PRICE);
        product.setListPrice(UPDATED_LIST_PRICE);
        product.setTradePrice(UPDATED_TRADE_PRICE);
        product.setBoxCost(UPDATED_BOX_COST);
        product.setUnitMeasure(UPDATED_UNIT_MEASURE);
        product.setBoxMeasure(UPDATED_BOX_MEASURE);
        product.setBoxConversionFactor(UPDATED_BOX_CONVERSION_FACTOR);
        product.setWeight(UPDATED_WEIGHT);
        product.setVolumn(UPDATED_VOLUMN);
        product.setServiceCover(UPDATED_SERVICE_COVER);
        product.setQtyFloorLevel(UPDATED_QTY_FLOOR_LEVEL);
        product.setQtyReorderLevel(UPDATED_QTY_REORDER_LEVEL);
        product.setQtyOverstockLevel(UPDATED_QTY_OVERSTOCK_LEVEL);
        product.setIsComment(UPDATED_IS_COMMENT);
        product.setIsDiminishing(UPDATED_IS_DIMINISHING);
        product.setIsNonTaxExeptable(UPDATED_IS_NON_TAX_EXEPTABLE);
        product.setLeadTime(UPDATED_LEAD_TIME);
        product.setPurchaseUnit(UPDATED_PURCHASE_UNIT);
        product.setEstMonthlySales(UPDATED_EST_MONTHLY_SALES);
        product.setDateFirstSale(UPDATED_DATE_FIRST_SALE);
        product.setDateLastSale(UPDATED_DATE_LAST_SALE);
        product.setDateFirstOrder(UPDATED_DATE_FIRST_ORDER);
        product.setDateCreated(UPDATED_DATE_CREATED);
        product.setDateLastDelivery(UPDATED_DATE_LAST_DELIVERY);
        product.setDateNextDelivery(UPDATED_DATE_NEXT_DELIVERY);
        product.setDateLastTransfer(UPDATED_DATE_LAST_TRANSFER);
        product.setDateLastOrder(UPDATED_DATE_LAST_ORDER);
        product.setDateLastStocktake(UPDATED_DATE_LAST_STOCKTAKE);
        product.setIsArchived(UPDATED_IS_ARCHIVED);
        product.setClassCode(UPDATED_CLASS_CODE);
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        restProductMockMvc.perform(put("/api/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productDTO)))
                .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = products.get(products.size() - 1);
        assertThat(testProduct.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduct.getLongDescription()).isEqualTo(UPDATED_LONG_DESCRIPTION);
        assertThat(testProduct.getAlternateCode()).isEqualTo(UPDATED_ALTERNATE_CODE);
        assertThat(testProduct.getBin()).isEqualTo(UPDATED_BIN);
        assertThat(testProduct.getIsOnSpecial()).isEqualTo(UPDATED_IS_ON_SPECIAL);
        assertThat(testProduct.getIsOnHold()).isEqualTo(UPDATED_IS_ON_HOLD);
        assertThat(testProduct.getIsInPricelistReports()).isEqualTo(UPDATED_IS_IN_PRICELIST_REPORTS);
        assertThat(testProduct.getQtyOnOrder()).isEqualTo(UPDATED_QTY_ON_ORDER);
        assertThat(testProduct.getQtyStockOnHold()).isEqualTo(UPDATED_QTY_STOCK_ON_HOLD);
        assertThat(testProduct.getQtyBackordered()).isEqualTo(UPDATED_QTY_BACKORDERED);
        assertThat(testProduct.getQtyAllocated()).isEqualTo(UPDATED_QTY_ALLOCATED);
        assertThat(testProduct.getQtyBackorderHold()).isEqualTo(UPDATED_QTY_BACKORDER_HOLD);
        assertThat(testProduct.getQtyConsigned()).isEqualTo(UPDATED_QTY_CONSIGNED);
        assertThat(testProduct.getQtyWarehouseReceived()).isEqualTo(UPDATED_QTY_WAREHOUSE_RECEIVED);
        assertThat(testProduct.getQtyStocktakeVariance()).isEqualTo(UPDATED_QTY_STOCKTAKE_VARIANCE);
        assertThat(testProduct.getQtyTransitIn()).isEqualTo(UPDATED_QTY_TRANSIT_IN);
        assertThat(testProduct.getQtyTransitOut()).isEqualTo(UPDATED_QTY_TRANSIT_OUT);
        assertThat(testProduct.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testProduct.getWholesaleListPrice()).isEqualTo(UPDATED_WHOLESALE_LIST_PRICE);
        assertThat(testProduct.getListPrice()).isEqualTo(UPDATED_LIST_PRICE);
        assertThat(testProduct.getTradePrice()).isEqualTo(UPDATED_TRADE_PRICE);
        assertThat(testProduct.getBoxCost()).isEqualTo(UPDATED_BOX_COST);
        assertThat(testProduct.getUnitMeasure()).isEqualTo(UPDATED_UNIT_MEASURE);
        assertThat(testProduct.getBoxMeasure()).isEqualTo(UPDATED_BOX_MEASURE);
        assertThat(testProduct.getBoxConversionFactor()).isEqualTo(UPDATED_BOX_CONVERSION_FACTOR);
        assertThat(testProduct.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testProduct.getVolumn()).isEqualTo(UPDATED_VOLUMN);
        assertThat(testProduct.getServiceCover()).isEqualTo(UPDATED_SERVICE_COVER);
        assertThat(testProduct.getQtyFloorLevel()).isEqualTo(UPDATED_QTY_FLOOR_LEVEL);
        assertThat(testProduct.getQtyReorderLevel()).isEqualTo(UPDATED_QTY_REORDER_LEVEL);
        assertThat(testProduct.getQtyOverstockLevel()).isEqualTo(UPDATED_QTY_OVERSTOCK_LEVEL);
        assertThat(testProduct.getIsComment()).isEqualTo(UPDATED_IS_COMMENT);
        assertThat(testProduct.getIsDiminishing()).isEqualTo(UPDATED_IS_DIMINISHING);
        assertThat(testProduct.getIsNonTaxExeptable()).isEqualTo(UPDATED_IS_NON_TAX_EXEPTABLE);
        assertThat(testProduct.getLeadTime()).isEqualTo(UPDATED_LEAD_TIME);
        assertThat(testProduct.getPurchaseUnit()).isEqualTo(UPDATED_PURCHASE_UNIT);
        assertThat(testProduct.getEstMonthlySales()).isEqualTo(UPDATED_EST_MONTHLY_SALES);
        assertThat(testProduct.getDateFirstSale()).isEqualTo(UPDATED_DATE_FIRST_SALE);
        assertThat(testProduct.getDateLastSale()).isEqualTo(UPDATED_DATE_LAST_SALE);
        assertThat(testProduct.getDateFirstOrder()).isEqualTo(UPDATED_DATE_FIRST_ORDER);
        assertThat(testProduct.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testProduct.getDateLastDelivery()).isEqualTo(UPDATED_DATE_LAST_DELIVERY);
        assertThat(testProduct.getDateNextDelivery()).isEqualTo(UPDATED_DATE_NEXT_DELIVERY);
        assertThat(testProduct.getDateLastTransfer()).isEqualTo(UPDATED_DATE_LAST_TRANSFER);
        assertThat(testProduct.getDateLastOrder()).isEqualTo(UPDATED_DATE_LAST_ORDER);
        assertThat(testProduct.getDateLastStocktake()).isEqualTo(UPDATED_DATE_LAST_STOCKTAKE);
        assertThat(testProduct.getIsArchived()).isEqualTo(UPDATED_IS_ARCHIVED);
        assertThat(testProduct.getClassCode()).isEqualTo(UPDATED_CLASS_CODE);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

		int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Get the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(databaseSizeBeforeDelete - 1);
    }
}
