package com.enginemobi.bssuite.web.rest.dto;

import org.joda.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


/**
 * A DTO for the SupplierDiscountRule entity.
 */
public class SupplierDiscountRuleDTO implements Serializable {

    private Long id;

    @NotNull
    private String supplierCode;


    private Integer ruleNo;


    private Boolean isAppliedToSales;


    private Boolean isAppliedOnSpecialItemsOnly;


    private String customerCode;


    private String customerCategoryCode;


    private String productCode;


    private LocalDate startDate;


    private LocalDate endDate;


    private Integer qtyBreak;


    private String fromStockGroupCode;


    private String toStockGroupCode;


    private String taxCode;


    private Boolean isAppliedWhenTaxExempt;


    private String storeCode;


    private String discountName;


    private String stockFamilyCode;


    private BigDecimal cost;


    private String discountFormula;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }


    public Integer getRuleNo() {
        return ruleNo;
    }

    public void setRuleNo(Integer ruleNo) {
        this.ruleNo = ruleNo;
    }


    public Boolean getIsAppliedToSales() {
        return isAppliedToSales;
    }

    public void setIsAppliedToSales(Boolean isAppliedToSales) {
        this.isAppliedToSales = isAppliedToSales;
    }


    public Boolean getIsAppliedOnSpecialItemsOnly() {
        return isAppliedOnSpecialItemsOnly;
    }

    public void setIsAppliedOnSpecialItemsOnly(Boolean isAppliedOnSpecialItemsOnly) {
        this.isAppliedOnSpecialItemsOnly = isAppliedOnSpecialItemsOnly;
    }


    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }


    public String getCustomerCategoryCode() {
        return customerCategoryCode;
    }

    public void setCustomerCategoryCode(String customerCategoryCode) {
        this.customerCategoryCode = customerCategoryCode;
    }


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }


    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    public Integer getQtyBreak() {
        return qtyBreak;
    }

    public void setQtyBreak(Integer qtyBreak) {
        this.qtyBreak = qtyBreak;
    }


    public String getFromStockGroupCode() {
        return fromStockGroupCode;
    }

    public void setFromStockGroupCode(String fromStockGroupCode) {
        this.fromStockGroupCode = fromStockGroupCode;
    }


    public String getToStockGroupCode() {
        return toStockGroupCode;
    }

    public void setToStockGroupCode(String toStockGroupCode) {
        this.toStockGroupCode = toStockGroupCode;
    }


    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }


    public Boolean getIsAppliedWhenTaxExempt() {
        return isAppliedWhenTaxExempt;
    }

    public void setIsAppliedWhenTaxExempt(Boolean isAppliedWhenTaxExempt) {
        this.isAppliedWhenTaxExempt = isAppliedWhenTaxExempt;
    }


    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }


    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }


    public String getStockFamilyCode() {
        return stockFamilyCode;
    }

    public void setStockFamilyCode(String stockFamilyCode) {
        this.stockFamilyCode = stockFamilyCode;
    }


    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }


    public String getDiscountFormula() {
        return discountFormula;
    }

    public void setDiscountFormula(String discountFormula) {
        this.discountFormula = discountFormula;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SupplierDiscountRuleDTO supplierDiscountRuleDTO = (SupplierDiscountRuleDTO) o;

        if ( ! Objects.equals(id, supplierDiscountRuleDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SupplierDiscountRuleDTO{" +
                "id=" + id +
                ", supplierCode='" + supplierCode + "'" +
                ", ruleNo='" + ruleNo + "'" +
                ", isAppliedToSales='" + isAppliedToSales + "'" +
                ", isAppliedOnSpecialItemsOnly='" + isAppliedOnSpecialItemsOnly + "'" +
                ", customerCode='" + customerCode + "'" +
                ", customerCategoryCode='" + customerCategoryCode + "'" +
                ", productCode='" + productCode + "'" +
                ", startDate='" + startDate + "'" +
                ", endDate='" + endDate + "'" +
                ", qtyBreak='" + qtyBreak + "'" +
                ", fromStockGroupCode='" + fromStockGroupCode + "'" +
                ", toStockGroupCode='" + toStockGroupCode + "'" +
                ", taxCode='" + taxCode + "'" +
                ", isAppliedWhenTaxExempt='" + isAppliedWhenTaxExempt + "'" +
                ", storeCode='" + storeCode + "'" +
                ", discountName='" + discountName + "'" +
                ", stockFamilyCode='" + stockFamilyCode + "'" +
                ", cost='" + cost + "'" +
                ", discountFormula='" + discountFormula + "'" +
                '}';
    }
}
