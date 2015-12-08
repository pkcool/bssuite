package com.enginemobi.bssuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A StockGroup.
 */
@Entity
@Table(name = "stock_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "stockgroup")
public class StockGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "is_diminishing")
    private Boolean isDiminishing;

    @Column(name = "lowest_margin")
    private Double lowestMargin;

    @Column(name = "highest_margin")
    private Double highestMargin;

    @Column(name = "is_discount_allowed")
    private Boolean isDiscountAllowed;

    @Column(name = "comment")
    private String comment;

    @Column(name = "is_archived")
    private Boolean isArchived;

    @ManyToOne
    @JoinColumn(name = "stock_family_id")
    private StockFamily stockFamily;

    @ManyToOne
    @JoinColumn(name = "price_scale_id")
    private PriceScale priceScale;

    @ManyToOne
    @JoinColumn(name = "tax_rate_id")
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

    public Boolean getIsDiminishing() {
        return isDiminishing;
    }

    public void setIsDiminishing(Boolean isDiminishing) {
        this.isDiminishing = isDiminishing;
    }

    public Double getLowestMargin() {
        return lowestMargin;
    }

    public void setLowestMargin(Double lowestMargin) {
        this.lowestMargin = lowestMargin;
    }

    public Double getHighestMargin() {
        return highestMargin;
    }

    public void setHighestMargin(Double highestMargin) {
        this.highestMargin = highestMargin;
    }

    public Boolean getIsDiscountAllowed() {
        return isDiscountAllowed;
    }

    public void setIsDiscountAllowed(Boolean isDiscountAllowed) {
        this.isDiscountAllowed = isDiscountAllowed;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public StockFamily getStockFamily() {
        return stockFamily;
    }

    public void setStockFamily(StockFamily stockFamily) {
        this.stockFamily = stockFamily;
    }

    public PriceScale getPriceScale() {
        return priceScale;
    }

    public void setPriceScale(PriceScale priceScale) {
        this.priceScale = priceScale;
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
        StockGroup stockGroup = (StockGroup) o;
        return Objects.equals(id, stockGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StockGroup{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", isDiminishing='" + isDiminishing + "'" +
            ", lowestMargin='" + lowestMargin + "'" +
            ", highestMargin='" + highestMargin + "'" +
            ", isDiscountAllowed='" + isDiscountAllowed + "'" +
            ", comment='" + comment + "'" +
            ", isArchived='" + isArchived + "'" +
            '}';
    }
}
