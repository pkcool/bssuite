package com.enginemobi.bssuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "alternate_code")
    private String alternateCode;

    @Column(name = "bin")
    private String bin;

    @Column(name = "is_on_special")
    private Boolean isOnSpecial;

    @Column(name = "is_on_hold")
    private Boolean isOnHold;

    @Column(name = "is_in_pricelist_reports")
    private Boolean isInPricelistReports;

    @Column(name = "qty_on_order")
    private Double qtyOnOrder;

    @Column(name = "qty_stock_on_hold")
    private Double qtyStockOnHold;

    @Column(name = "qty_backordered")
    private Double qtyBackordered;

    @Column(name = "qty_allocated")
    private Double qtyAllocated;

    @Column(name = "qty_backorder_hold")
    private Double qtyBackorderHold;

    @Column(name = "qty_consigned")
    private Double qtyConsigned;

    @Column(name = "qty_warehouse_received")
    private Double qtyWarehouseReceived;

    @Column(name = "qty_stocktake_variance")
    private Double qtyStocktakeVariance;

    @Column(name = "qty_transit_in")
    private Double qtyTransitIn;

    @Column(name = "qty_transit_out")
    private Double qtyTransitOut;

    @Min(value = 0)
    @Column(name = "cost", precision=10, scale=2)
    private BigDecimal cost;

    @Column(name = "wholesale_list_price", precision=10, scale=2)
    private BigDecimal wholesaleListPrice;

    @Column(name = "list_price", precision=10, scale=2)
    private BigDecimal listPrice;

    @Column(name = "trade_price", precision=10, scale=2)
    private BigDecimal tradePrice;

    @Column(name = "box_cost", precision=10, scale=2)
    private BigDecimal boxCost;

    @Column(name = "unit_measure")
    private String unitMeasure;

    @Column(name = "box_measure")
    private String boxMeasure;

    @Column(name = "box_conversion_factor", precision=10, scale=2)
    private BigDecimal boxConversionFactor;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "volumn")
    private Double volumn;

    @Column(name = "service_cover")
    private Integer serviceCover;

    @Column(name = "qty_floor_level")
    private Double qtyFloorLevel;

    @Column(name = "qty_reorder_level")
    private Double qtyReorderLevel;

    @Column(name = "qty_overstock_level")
    private Double qtyOverstockLevel;

    @Column(name = "is_comment")
    private Boolean isComment;

    @Column(name = "is_diminishing")
    private Boolean isDiminishing;

    @Column(name = "is_non_tax_exeptable")
    private Boolean isNonTaxExeptable;

    @Column(name = "lead_time")
    private Integer leadTime;

    @Column(name = "purchase_unit")
    private Integer purchaseUnit;

    @Column(name = "est_monthly_sales")
    private Double estMonthlySales;

    @Column(name = "date_first_sale")
    private LocalDate dateFirstSale;

    @Column(name = "date_last_sale")
    private LocalDate dateLastSale;

    @Column(name = "date_first_order")
    private LocalDate dateFirstOrder;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_last_delivery")
    private LocalDate dateLastDelivery;

    @Column(name = "date_next_delivery")
    private LocalDate dateNextDelivery;

    @Column(name = "date_last_transfer")
    private LocalDate dateLastTransfer;

    @Column(name = "date_last_order")
    private LocalDate dateLastOrder;

    @Column(name = "date_last_stocktake")
    private LocalDate dateLastStocktake;

    @Column(name = "is_archived")
    private Boolean isArchived;

    @Column(name = "class_code")
    private String classCode;

    @ManyToOne
    private StockGroup stockGroup;

    @ManyToOne
    private Supplier supplier;

    @ManyToOne
    private Store store;

    @ManyToOne
    private TaxTable taxRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getAlternateCode() {
        return alternateCode;
    }

    public void setAlternateCode(String alternateCode) {
        this.alternateCode = alternateCode;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public Boolean getIsOnSpecial() {
        return isOnSpecial;
    }

    public void setIsOnSpecial(Boolean isOnSpecial) {
        this.isOnSpecial = isOnSpecial;
    }

    public Boolean getIsOnHold() {
        return isOnHold;
    }

    public void setIsOnHold(Boolean isOnHold) {
        this.isOnHold = isOnHold;
    }

    public Boolean getIsInPricelistReports() {
        return isInPricelistReports;
    }

    public void setIsInPricelistReports(Boolean isInPricelistReports) {
        this.isInPricelistReports = isInPricelistReports;
    }

    public Double getQtyOnOrder() {
        return qtyOnOrder;
    }

    public void setQtyOnOrder(Double qtyOnOrder) {
        this.qtyOnOrder = qtyOnOrder;
    }

    public Double getQtyStockOnHold() {
        return qtyStockOnHold;
    }

    public void setQtyStockOnHold(Double qtyStockOnHold) {
        this.qtyStockOnHold = qtyStockOnHold;
    }

    public Double getQtyBackordered() {
        return qtyBackordered;
    }

    public void setQtyBackordered(Double qtyBackordered) {
        this.qtyBackordered = qtyBackordered;
    }

    public Double getQtyAllocated() {
        return qtyAllocated;
    }

    public void setQtyAllocated(Double qtyAllocated) {
        this.qtyAllocated = qtyAllocated;
    }

    public Double getQtyBackorderHold() {
        return qtyBackorderHold;
    }

    public void setQtyBackorderHold(Double qtyBackorderHold) {
        this.qtyBackorderHold = qtyBackorderHold;
    }

    public Double getQtyConsigned() {
        return qtyConsigned;
    }

    public void setQtyConsigned(Double qtyConsigned) {
        this.qtyConsigned = qtyConsigned;
    }

    public Double getQtyWarehouseReceived() {
        return qtyWarehouseReceived;
    }

    public void setQtyWarehouseReceived(Double qtyWarehouseReceived) {
        this.qtyWarehouseReceived = qtyWarehouseReceived;
    }

    public Double getQtyStocktakeVariance() {
        return qtyStocktakeVariance;
    }

    public void setQtyStocktakeVariance(Double qtyStocktakeVariance) {
        this.qtyStocktakeVariance = qtyStocktakeVariance;
    }

    public Double getQtyTransitIn() {
        return qtyTransitIn;
    }

    public void setQtyTransitIn(Double qtyTransitIn) {
        this.qtyTransitIn = qtyTransitIn;
    }

    public Double getQtyTransitOut() {
        return qtyTransitOut;
    }

    public void setQtyTransitOut(Double qtyTransitOut) {
        this.qtyTransitOut = qtyTransitOut;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getWholesaleListPrice() {
        return wholesaleListPrice;
    }

    public void setWholesaleListPrice(BigDecimal wholesaleListPrice) {
        this.wholesaleListPrice = wholesaleListPrice;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
    }

    public BigDecimal getBoxCost() {
        return boxCost;
    }

    public void setBoxCost(BigDecimal boxCost) {
        this.boxCost = boxCost;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public String getBoxMeasure() {
        return boxMeasure;
    }

    public void setBoxMeasure(String boxMeasure) {
        this.boxMeasure = boxMeasure;
    }

    public BigDecimal getBoxConversionFactor() {
        return boxConversionFactor;
    }

    public void setBoxConversionFactor(BigDecimal boxConversionFactor) {
        this.boxConversionFactor = boxConversionFactor;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolumn() {
        return volumn;
    }

    public void setVolumn(Double volumn) {
        this.volumn = volumn;
    }

    public Integer getServiceCover() {
        return serviceCover;
    }

    public void setServiceCover(Integer serviceCover) {
        this.serviceCover = serviceCover;
    }

    public Double getQtyFloorLevel() {
        return qtyFloorLevel;
    }

    public void setQtyFloorLevel(Double qtyFloorLevel) {
        this.qtyFloorLevel = qtyFloorLevel;
    }

    public Double getQtyReorderLevel() {
        return qtyReorderLevel;
    }

    public void setQtyReorderLevel(Double qtyReorderLevel) {
        this.qtyReorderLevel = qtyReorderLevel;
    }

    public Double getQtyOverstockLevel() {
        return qtyOverstockLevel;
    }

    public void setQtyOverstockLevel(Double qtyOverstockLevel) {
        this.qtyOverstockLevel = qtyOverstockLevel;
    }

    public Boolean getIsComment() {
        return isComment;
    }

    public void setIsComment(Boolean isComment) {
        this.isComment = isComment;
    }

    public Boolean getIsDiminishing() {
        return isDiminishing;
    }

    public void setIsDiminishing(Boolean isDiminishing) {
        this.isDiminishing = isDiminishing;
    }

    public Boolean getIsNonTaxExeptable() {
        return isNonTaxExeptable;
    }

    public void setIsNonTaxExeptable(Boolean isNonTaxExeptable) {
        this.isNonTaxExeptable = isNonTaxExeptable;
    }

    public Integer getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }

    public Integer getPurchaseUnit() {
        return purchaseUnit;
    }

    public void setPurchaseUnit(Integer purchaseUnit) {
        this.purchaseUnit = purchaseUnit;
    }

    public Double getEstMonthlySales() {
        return estMonthlySales;
    }

    public void setEstMonthlySales(Double estMonthlySales) {
        this.estMonthlySales = estMonthlySales;
    }

    public LocalDate getDateFirstSale() {
        return dateFirstSale;
    }

    public void setDateFirstSale(LocalDate dateFirstSale) {
        this.dateFirstSale = dateFirstSale;
    }

    public LocalDate getDateLastSale() {
        return dateLastSale;
    }

    public void setDateLastSale(LocalDate dateLastSale) {
        this.dateLastSale = dateLastSale;
    }

    public LocalDate getDateFirstOrder() {
        return dateFirstOrder;
    }

    public void setDateFirstOrder(LocalDate dateFirstOrder) {
        this.dateFirstOrder = dateFirstOrder;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateLastDelivery() {
        return dateLastDelivery;
    }

    public void setDateLastDelivery(LocalDate dateLastDelivery) {
        this.dateLastDelivery = dateLastDelivery;
    }

    public LocalDate getDateNextDelivery() {
        return dateNextDelivery;
    }

    public void setDateNextDelivery(LocalDate dateNextDelivery) {
        this.dateNextDelivery = dateNextDelivery;
    }

    public LocalDate getDateLastTransfer() {
        return dateLastTransfer;
    }

    public void setDateLastTransfer(LocalDate dateLastTransfer) {
        this.dateLastTransfer = dateLastTransfer;
    }

    public LocalDate getDateLastOrder() {
        return dateLastOrder;
    }

    public void setDateLastOrder(LocalDate dateLastOrder) {
        this.dateLastOrder = dateLastOrder;
    }

    public LocalDate getDateLastStocktake() {
        return dateLastStocktake;
    }

    public void setDateLastStocktake(LocalDate dateLastStocktake) {
        this.dateLastStocktake = dateLastStocktake;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public StockGroup getStockGroup() {
        return stockGroup;
    }

    public void setStockGroup(StockGroup stockGroup) {
        this.stockGroup = stockGroup;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public TaxTable getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(TaxTable taxTable) {
        this.taxRate = taxTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product product = (Product) o;

        if ( ! Objects.equals(id, product.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", longDescription='" + longDescription + "'" +
            ", alternateCode='" + alternateCode + "'" +
            ", bin='" + bin + "'" +
            ", isOnSpecial='" + isOnSpecial + "'" +
            ", isOnHold='" + isOnHold + "'" +
            ", isInPricelistReports='" + isInPricelistReports + "'" +
            ", qtyOnOrder='" + qtyOnOrder + "'" +
            ", qtyStockOnHold='" + qtyStockOnHold + "'" +
            ", qtyBackordered='" + qtyBackordered + "'" +
            ", qtyAllocated='" + qtyAllocated + "'" +
            ", qtyBackorderHold='" + qtyBackorderHold + "'" +
            ", qtyConsigned='" + qtyConsigned + "'" +
            ", qtyWarehouseReceived='" + qtyWarehouseReceived + "'" +
            ", qtyStocktakeVariance='" + qtyStocktakeVariance + "'" +
            ", qtyTransitIn='" + qtyTransitIn + "'" +
            ", qtyTransitOut='" + qtyTransitOut + "'" +
            ", cost='" + cost + "'" +
            ", wholesaleListPrice='" + wholesaleListPrice + "'" +
            ", listPrice='" + listPrice + "'" +
            ", tradePrice='" + tradePrice + "'" +
            ", boxCost='" + boxCost + "'" +
            ", unitMeasure='" + unitMeasure + "'" +
            ", boxMeasure='" + boxMeasure + "'" +
            ", boxConversionFactor='" + boxConversionFactor + "'" +
            ", weight='" + weight + "'" +
            ", volumn='" + volumn + "'" +
            ", serviceCover='" + serviceCover + "'" +
            ", qtyFloorLevel='" + qtyFloorLevel + "'" +
            ", qtyReorderLevel='" + qtyReorderLevel + "'" +
            ", qtyOverstockLevel='" + qtyOverstockLevel + "'" +
            ", isComment='" + isComment + "'" +
            ", isDiminishing='" + isDiminishing + "'" +
            ", isNonTaxExeptable='" + isNonTaxExeptable + "'" +
            ", leadTime='" + leadTime + "'" +
            ", purchaseUnit='" + purchaseUnit + "'" +
            ", estMonthlySales='" + estMonthlySales + "'" +
            ", dateFirstSale='" + dateFirstSale + "'" +
            ", dateLastSale='" + dateLastSale + "'" +
            ", dateFirstOrder='" + dateFirstOrder + "'" +
            ", dateCreated='" + dateCreated + "'" +
            ", dateLastDelivery='" + dateLastDelivery + "'" +
            ", dateNextDelivery='" + dateNextDelivery + "'" +
            ", dateLastTransfer='" + dateLastTransfer + "'" +
            ", dateLastOrder='" + dateLastOrder + "'" +
            ", dateLastStocktake='" + dateLastStocktake + "'" +
            ", isArchived='" + isArchived + "'" +
            ", classCode='" + classCode + "'" +
            '}';
    }
}
