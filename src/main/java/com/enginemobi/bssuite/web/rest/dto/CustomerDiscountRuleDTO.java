package com.enginemobi.bssuite.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


/**
 * A DTO for the CustomerDiscountRule entity.
 */
public class CustomerDiscountRuleDTO implements Serializable {

    private Long id;

    private String priceGroupCode;

    private Integer ruleNo;

    private Boolean isAppliedGlobally;

    private Boolean isAppliedOnSpecialItemsOnly;

    private String customerCode;

    private String customerCategoryCode;

    private String fromProductCode;

    private String toProductCode;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer qtyBreak;

    private String fromSupplierCode;

    private String toSupplierCode;

    private String fromStockGroupCode;

    private String toStockGroupCode;

    private String taxCode;

    private Boolean isAppliedWhenTaxExempt;

    private String storeCode;

    private String discountName;

    private String stockFamilyCode;

    private BigDecimal listPrice;

    private String discountFormula;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPriceGroupCode() {
        return priceGroupCode;
    }

    public void setPriceGroupCode(String priceGroupCode) {
        this.priceGroupCode = priceGroupCode;
    }

    public Integer getRuleNo() {
        return ruleNo;
    }

    public void setRuleNo(Integer ruleNo) {
        this.ruleNo = ruleNo;
    }

    public Boolean getIsAppliedGlobally() {
        return isAppliedGlobally;
    }

    public void setIsAppliedGlobally(Boolean isAppliedGlobally) {
        this.isAppliedGlobally = isAppliedGlobally;
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

    public String getFromProductCode() {
        return fromProductCode;
    }

    public void setFromProductCode(String fromProductCode) {
        this.fromProductCode = fromProductCode;
    }

    public String getToProductCode() {
        return toProductCode;
    }

    public void setToProductCode(String toProductCode) {
        this.toProductCode = toProductCode;
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

    public String getFromSupplierCode() {
        return fromSupplierCode;
    }

    public void setFromSupplierCode(String fromSupplierCode) {
        this.fromSupplierCode = fromSupplierCode;
    }

    public String getToSupplierCode() {
        return toSupplierCode;
    }

    public void setToSupplierCode(String toSupplierCode) {
        this.toSupplierCode = toSupplierCode;
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

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
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

        CustomerDiscountRuleDTO customerDiscountRuleDTO = (CustomerDiscountRuleDTO) o;

        if ( ! Objects.equals(id, customerDiscountRuleDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CustomerDiscountRuleDTO{" +
            "id=" + id +
            ", priceGroupCode='" + priceGroupCode + "'" +
            ", ruleNo='" + ruleNo + "'" +
            ", isAppliedGlobally='" + isAppliedGlobally + "'" +
            ", isAppliedOnSpecialItemsOnly='" + isAppliedOnSpecialItemsOnly + "'" +
            ", customerCode='" + customerCode + "'" +
            ", customerCategoryCode='" + customerCategoryCode + "'" +
            ", fromProductCode='" + fromProductCode + "'" +
            ", toProductCode='" + toProductCode + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", qtyBreak='" + qtyBreak + "'" +
            ", fromSupplierCode='" + fromSupplierCode + "'" +
            ", toSupplierCode='" + toSupplierCode + "'" +
            ", fromStockGroupCode='" + fromStockGroupCode + "'" +
            ", toStockGroupCode='" + toStockGroupCode + "'" +
            ", taxCode='" + taxCode + "'" +
            ", isAppliedWhenTaxExempt='" + isAppliedWhenTaxExempt + "'" +
            ", storeCode='" + storeCode + "'" +
            ", discountName='" + discountName + "'" +
            ", stockFamilyCode='" + stockFamilyCode + "'" +
            ", listPrice='" + listPrice + "'" +
            ", discountFormula='" + discountFormula + "'" +
            '}';
    }
}
