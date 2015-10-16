package com.enginemobi.bssuite.web.rest.dto;

import org.joda.time.LocalDate;
import org.joda.time.DateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.enginemobi.bssuite.domain.enumeration.PurchaseOrderStatus;

/**
 * A DTO for the PurchaseOrder entity.
 */
public class PurchaseOrderDTO implements Serializable {

    private Long id;

    private String orderNo;


    private PurchaseOrderStatus status;


    private DateTime createdDate;


    private String ref;


    private LocalDate expectedDeliveryDate;


    private Boolean isTaxable;


    private Boolean isLocked;


    private String comment;


    private BigDecimal totalTaxAmount;


    private BigDecimal totalCost;


    private String taxExemptionCode;


    private Boolean isSuspended;


    private Long supplierId;

    private Long supplierContactId;

    private Long supplierDeliveryContactId;

    private Long storeId;

    private Long salesOrderId;

    private Long updatedByStaffId;

    private Long createdById;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public PurchaseOrderStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseOrderStatus status) {
        this.status = status;
    }


    public DateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = createdDate;
    }


    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }


    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }


    public Boolean getIsTaxable() {
        return isTaxable;
    }

    public void setIsTaxable(Boolean isTaxable) {
        this.isTaxable = isTaxable;
    }


    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public BigDecimal getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }


    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }


    public String getTaxExemptionCode() {
        return taxExemptionCode;
    }

    public void setTaxExemptionCode(String taxExemptionCode) {
        this.taxExemptionCode = taxExemptionCode;
    }


    public Boolean getIsSuspended() {
        return isSuspended;
    }

    public void setIsSuspended(Boolean isSuspended) {
        this.isSuspended = isSuspended;
    }


    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long SupplierId) {
        this.supplierId = SupplierId;
    }

    public Long getSupplierContactId() {
        return supplierContactId;
    }

    public void setSupplierContactId(Long contactId) {
        this.supplierContactId = contactId;
    }

    public Long getSupplierDeliveryContactId() {
        return supplierDeliveryContactId;
    }

    public void setSupplierDeliveryContactId(Long contactId) {
        this.supplierDeliveryContactId = contactId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public Long getUpdatedByStaffId() {
        return updatedByStaffId;
    }

    public void setUpdatedByStaffId(Long staffId) {
        this.updatedByStaffId = staffId;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long staffId) {
        this.createdById = staffId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PurchaseOrderDTO purchaseOrderDTO = (PurchaseOrderDTO) o;

        if ( ! Objects.equals(id, purchaseOrderDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PurchaseOrderDTO{" +
                "id=" + id +
                ", orderNo='" + orderNo + "'" +
                ", status='" + status + "'" +
                ", createdDate='" + createdDate + "'" +
                ", ref='" + ref + "'" +
                ", expectedDeliveryDate='" + expectedDeliveryDate + "'" +
                ", isTaxable='" + isTaxable + "'" +
                ", isLocked='" + isLocked + "'" +
                ", comment='" + comment + "'" +
                ", totalTaxAmount='" + totalTaxAmount + "'" +
                ", totalCost='" + totalCost + "'" +
                ", taxExemptionCode='" + taxExemptionCode + "'" +
                ", isSuspended='" + isSuspended + "'" +
                '}';
    }
}
