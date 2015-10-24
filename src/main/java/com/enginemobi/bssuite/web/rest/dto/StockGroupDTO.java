package com.enginemobi.bssuite.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the StockGroup entity.
 */
public class StockGroupDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2)
    private String code;

    private String name;

    private Boolean isDiminishing;

    private Double lowestMargin;

    private Double highestMargin;

    private Boolean isDiscountAllowed;

    private String comment;

    private Boolean isArchived;

    private Long stockFamilyId;
    private Long priceScaleId;
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

    public Long getStockFamilyId() {
        return stockFamilyId;
    }

    public void setStockFamilyId(Long stockFamilyId) {
        this.stockFamilyId = stockFamilyId;
    }
    public Long getPriceScaleId() {
        return priceScaleId;
    }

    public void setPriceScaleId(Long priceScaleId) {
        this.priceScaleId = priceScaleId;
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

        StockGroupDTO stockGroupDTO = (StockGroupDTO) o;

        if ( ! Objects.equals(id, stockGroupDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StockGroupDTO{" +
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
