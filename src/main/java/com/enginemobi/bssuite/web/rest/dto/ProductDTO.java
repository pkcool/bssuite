package com.enginemobi.bssuite.web.rest.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Product entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    private String name;

    private String description;

    private String longDescription;

    private String alternateCode;

    private String bin;

    private Boolean isOnSpecial;

    private Boolean isOnHold;

    private Boolean isInPricelistReports;

    private Double qtyOnOrder;

    private Double qtyStockOnHold;

    private Double qtyBackordered;

    private Double qtyAllocated;

    private Double qtyBackorderHold;

    private Double qtyConsigned;

    private Double qtyWarehouseReceived;

    private Double qtyStocktakeVariance;

    private Double qtyTransitIn;

    private Double qtyTransitOut;

    @Min(value = 0)
    private BigDecimal cost;

    private BigDecimal wholesaleListPrice;

    private BigDecimal listPrice;

    private BigDecimal tradePrice;

    private BigDecimal boxCost;

    private String unitMeasure;

    private String boxMeasure;

    private BigDecimal boxConversionFactor;

    private Double weight;

    private Double volumn;

    private Integer serviceCover;

    private Double qtyFloorLevel;

    private Double qtyReorderLevel;

    private Double qtyOverstockLevel;

    private Boolean isComment;

    private Boolean isDiminishing;

    private Boolean isNonTaxExeptable;

    private Integer leadTime;

    private Integer purchaseUnit;

    private Double estMonthlySales;

    private LocalDate dateFirstSale;

    private LocalDate dateLastSale;

    private LocalDate dateFirstOrder;

    private LocalDate dateCreated;

    private LocalDate dateLastDelivery;

    private LocalDate dateNextDelivery;

    private LocalDate dateLastTransfer;

    private LocalDate dateLastOrder;

    private LocalDate dateLastStocktake;

    private Boolean isArchived;

    private String classCode;

    private Long stockGroupId;
    private Long supplierId;
    private Long storeId;
    private Long taxRateId;
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

    public Long getStockGroupId() {
        return stockGroupId;
    }

    public void setStockGroupId(Long stockGroupId) {
        this.stockGroupId = stockGroupId;
    }
    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
    public Long getTaxRateId() {
        return taxRateId;
    }

    public void setTaxRateId(Long taxTableId) {
        this.taxRateId = taxTableId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;

        if ( ! Objects.equals(id, productDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
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
