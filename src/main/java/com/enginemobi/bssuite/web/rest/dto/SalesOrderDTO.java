package com.enginemobi.bssuite.web.rest.dto;

import org.joda.time.LocalDate;
import org.joda.time.DateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.enginemobi.bssuite.domain.enumeration.SalesOrderStatus;

/**
 * A DTO for the SalesOrder entity.
 */
public class SalesOrderDTO implements Serializable {

    private Long id;

    private String orderNo;


    private SalesOrderStatus status;


    private DateTime txnDate;


    private LocalDate forwardDate;


    private LocalDate requiredDate;


    private String customerOrderNo;


    private String ourRef;


    private BigDecimal freight;


    private BigDecimal handlingCharge;


    private BigDecimal charge2;


    private Boolean isTaxable;


    private Boolean isLocked;


    private BigDecimal adjustTax;


    private BigDecimal adjustTaxExempt;


    private BigDecimal prepayment;


    private String prepaymentNo;


    private String comment;


    private BigDecimal totalTaxAmount;


    private BigDecimal totalSellPrice;


    private BigDecimal totalCost;


    private Boolean isSuspended;


    private Long customerId;

    private Long contactId;

    private Long deliveryContactId;

    private Long storeId;

    private Long carrierId;

    private Long salespersonId;

    private Long promotionId;

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


    public SalesOrderStatus getStatus() {
        return status;
    }

    public void setStatus(SalesOrderStatus status) {
        this.status = status;
    }


    public DateTime getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(DateTime txnDate) {
        this.txnDate = txnDate;
    }


    public LocalDate getForwardDate() {
        return forwardDate;
    }

    public void setForwardDate(LocalDate forwardDate) {
        this.forwardDate = forwardDate;
    }


    public LocalDate getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(LocalDate requiredDate) {
        this.requiredDate = requiredDate;
    }


    public String getCustomerOrderNo() {
        return customerOrderNo;
    }

    public void setCustomerOrderNo(String customerOrderNo) {
        this.customerOrderNo = customerOrderNo;
    }


    public String getOurRef() {
        return ourRef;
    }

    public void setOurRef(String ourRef) {
        this.ourRef = ourRef;
    }


    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }


    public BigDecimal getHandlingCharge() {
        return handlingCharge;
    }

    public void setHandlingCharge(BigDecimal handlingCharge) {
        this.handlingCharge = handlingCharge;
    }


    public BigDecimal getCharge2() {
        return charge2;
    }

    public void setCharge2(BigDecimal charge2) {
        this.charge2 = charge2;
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


    public BigDecimal getAdjustTax() {
        return adjustTax;
    }

    public void setAdjustTax(BigDecimal adjustTax) {
        this.adjustTax = adjustTax;
    }


    public BigDecimal getAdjustTaxExempt() {
        return adjustTaxExempt;
    }

    public void setAdjustTaxExempt(BigDecimal adjustTaxExempt) {
        this.adjustTaxExempt = adjustTaxExempt;
    }


    public BigDecimal getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(BigDecimal prepayment) {
        this.prepayment = prepayment;
    }


    public String getPrepaymentNo() {
        return prepaymentNo;
    }

    public void setPrepaymentNo(String prepaymentNo) {
        this.prepaymentNo = prepaymentNo;
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


    public BigDecimal getTotalSellPrice() {
        return totalSellPrice;
    }

    public void setTotalSellPrice(BigDecimal totalSellPrice) {
        this.totalSellPrice = totalSellPrice;
    }


    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }


    public Boolean getIsSuspended() {
        return isSuspended;
    }

    public void setIsSuspended(Boolean isSuspended) {
        this.isSuspended = isSuspended;
    }


    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getDeliveryContactId() {
        return deliveryContactId;
    }

    public void setDeliveryContactId(Long contactId) {
        this.deliveryContactId = contactId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(Long carrierId) {
        this.carrierId = carrierId;
    }

    public Long getSalespersonId() {
        return salespersonId;
    }

    public void setSalespersonId(Long staffId) {
        this.salespersonId = staffId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
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

        SalesOrderDTO salesOrderDTO = (SalesOrderDTO) o;

        if ( ! Objects.equals(id, salesOrderDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SalesOrderDTO{" +
                "id=" + id +
                ", orderNo='" + orderNo + "'" +
                ", status='" + status + "'" +
                ", txnDate='" + txnDate + "'" +
                ", forwardDate='" + forwardDate + "'" +
                ", requiredDate='" + requiredDate + "'" +
                ", customerOrderNo='" + customerOrderNo + "'" +
                ", ourRef='" + ourRef + "'" +
                ", freight='" + freight + "'" +
                ", handlingCharge='" + handlingCharge + "'" +
                ", charge2='" + charge2 + "'" +
                ", isTaxable='" + isTaxable + "'" +
                ", isLocked='" + isLocked + "'" +
                ", adjustTax='" + adjustTax + "'" +
                ", adjustTaxExempt='" + adjustTaxExempt + "'" +
                ", prepayment='" + prepayment + "'" +
                ", prepaymentNo='" + prepaymentNo + "'" +
                ", comment='" + comment + "'" +
                ", totalTaxAmount='" + totalTaxAmount + "'" +
                ", totalSellPrice='" + totalSellPrice + "'" +
                ", totalCost='" + totalCost + "'" +
                ", isSuspended='" + isSuspended + "'" +
                '}';
    }
}
