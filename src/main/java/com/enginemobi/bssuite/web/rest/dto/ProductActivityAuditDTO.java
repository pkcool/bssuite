package com.enginemobi.bssuite.web.rest.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.enginemobi.bssuite.domain.enumeration.ProductActivityType;

/**
 * A DTO for the ProductActivityAudit entity.
 */
public class ProductActivityAuditDTO implements Serializable {

    private Long id;

    private ZonedDateTime createdOn;

    private String txnNumber;

    private ProductActivityType activityType;

    private Double qtyTxn;

    private Double qtyAdjusted;

    private Double qtyStockOnHold;

    private String lineNo;

    private String txnAccountCode;

    private Long createdById;
    private Long productId;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getTxnNumber() {
        return txnNumber;
    }

    public void setTxnNumber(String txnNumber) {
        this.txnNumber = txnNumber;
    }

    public ProductActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ProductActivityType activityType) {
        this.activityType = activityType;
    }

    public Double getQtyTxn() {
        return qtyTxn;
    }

    public void setQtyTxn(Double qtyTxn) {
        this.qtyTxn = qtyTxn;
    }

    public Double getQtyAdjusted() {
        return qtyAdjusted;
    }

    public void setQtyAdjusted(Double qtyAdjusted) {
        this.qtyAdjusted = qtyAdjusted;
    }

    public Double getQtyStockOnHold() {
        return qtyStockOnHold;
    }

    public void setQtyStockOnHold(Double qtyStockOnHold) {
        this.qtyStockOnHold = qtyStockOnHold;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getTxnAccountCode() {
        return txnAccountCode;
    }

    public void setTxnAccountCode(String txnAccountCode) {
        this.txnAccountCode = txnAccountCode;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long staffId) {
        this.createdById = staffId;
    }
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductActivityAuditDTO productActivityAuditDTO = (ProductActivityAuditDTO) o;

        if ( ! Objects.equals(id, productActivityAuditDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductActivityAuditDTO{" +
            "id=" + id +
            ", createdOn='" + createdOn + "'" +
            ", txnNumber='" + txnNumber + "'" +
            ", activityType='" + activityType + "'" +
            ", qtyTxn='" + qtyTxn + "'" +
            ", qtyAdjusted='" + qtyAdjusted + "'" +
            ", qtyStockOnHold='" + qtyStockOnHold + "'" +
            ", lineNo='" + lineNo + "'" +
            ", txnAccountCode='" + txnAccountCode + "'" +
            '}';
    }
}
