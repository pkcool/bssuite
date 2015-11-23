package com.enginemobi.bssuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A SupplierDiscountRule.
 */
@Entity
@Table(name = "supplier_discount_rule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "supplierdiscountrule")
public class SupplierDiscountRule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "supplier_code", nullable = false)
    private String supplierCode;

    @Column(name = "rule_no")
    private Integer ruleNo;

    @Column(name = "is_applied_to_sales")
    private Boolean isAppliedToSales;

    @Column(name = "is_applied_on_special_items_only")
    private Boolean isAppliedOnSpecialItemsOnly;

    @Column(name = "customer_code")
    private String customerCode;

    @Column(name = "customer_category_code")
    private String customerCategoryCode;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "qty_break")
    private Integer qtyBreak;

    @Column(name = "from_stock_group_code")
    private String fromStockGroupCode;

    @Column(name = "to_stock_group_code")
    private String toStockGroupCode;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "is_applied_when_tax_exempt")
    private Boolean isAppliedWhenTaxExempt;

    @Column(name = "store_code")
    private String storeCode;

    @Column(name = "discount_name")
    private String discountName;

    @Column(name = "stock_family_code")
    private String stockFamilyCode;

    @Column(name = "cost", precision=10, scale=2)
    private BigDecimal cost;

    @Column(name = "discount_formula")
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
        SupplierDiscountRule supplierDiscountRule = (SupplierDiscountRule) o;
        return Objects.equals(id, supplierDiscountRule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SupplierDiscountRule{" +
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
