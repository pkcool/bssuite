package com.enginemobi.bssuite.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.enginemobi.bssuite.domain.util.CustomLocalDateSerializer;
import com.enginemobi.bssuite.domain.util.ISO8601LocalDateDeserializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A CustomerDiscountRule.
 */
@Entity
@Table(name = "customer_discount_rule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName="customerdiscountrule")
public class CustomerDiscountRule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @Column(name = "price_group_code")
    private String priceGroupCode;
    
    @Column(name = "rule_no")
    private Integer ruleNo;
    
    @Column(name = "is_applied_globally")
    private Boolean isAppliedGlobally;
    
    @Column(name = "is_applied_on_special_items_only")
    private Boolean isAppliedOnSpecialItemsOnly;
    
    @Column(name = "customer_code")
    private String customerCode;
    
    @Column(name = "customer_category_code")
    private String customerCategoryCode;
    
    @Column(name = "from_product_code")
    private String fromProductCode;
    
    @Column(name = "to_product_code")
    private String toProductCode;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "start_date")
    private LocalDate startDate;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @Column(name = "qty_break")
    private Integer qtyBreak;
    
    @Column(name = "from_supplier_code")
    private String fromSupplierCode;
    
    @Column(name = "to_supplier_code")
    private String toSupplierCode;
    
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
    
    @Column(name = "list_price", precision=10, scale=2)
    private BigDecimal listPrice;
    
    @Column(name = "discount_formula")
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

        CustomerDiscountRule customerDiscountRule = (CustomerDiscountRule) o;

        if ( ! Objects.equals(id, customerDiscountRule.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CustomerDiscountRule{" +
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
