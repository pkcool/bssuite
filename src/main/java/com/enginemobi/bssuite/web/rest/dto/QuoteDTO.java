package com.enginemobi.bssuite.web.rest.dto;

import org.joda.time.LocalDate;
import org.joda.time.DateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.enginemobi.bssuite.domain.enumeration.QuoteStatus;

/**
 * A DTO for the Quote entity.
 */
public class QuoteDTO implements Serializable {

    private Long id;

    private String quoteNo;


    private QuoteStatus status;


    private DateTime quoteDate;


    private LocalDate expiryDate;


    private LocalDate followupDate;


    private String reference;


    private String ourRef;


    private BigDecimal freight;


    private String reasonForLoss;


    private Boolean isTaxable;


    private String taxExemptionCode;


    private Boolean isLocked;


    private BigDecimal adjustTax;


    private BigDecimal adjustTaxExempt;


    private String comment;


    private BigDecimal totalTaxAmount;


    private BigDecimal totalSellPrice;


    private BigDecimal totalCost;


    private Boolean isSuspended;


    private Long customerId;

    private Long contactId;

    private Long deliveryContactId;

    private Long storeId;

    private Long quoteByStaffId;

    private Long createdById;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuoteNo() {
        return quoteNo;
    }

    public void setQuoteNo(String quoteNo) {
        this.quoteNo = quoteNo;
    }


    public QuoteStatus getStatus() {
        return status;
    }

    public void setStatus(QuoteStatus status) {
        this.status = status;
    }


    public DateTime getQuoteDate() {
        return quoteDate;
    }

    public void setQuoteDate(DateTime quoteDate) {
        this.quoteDate = quoteDate;
    }


    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }


    public LocalDate getFollowupDate() {
        return followupDate;
    }

    public void setFollowupDate(LocalDate followupDate) {
        this.followupDate = followupDate;
    }


    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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


    public String getReasonForLoss() {
        return reasonForLoss;
    }

    public void setReasonForLoss(String reasonForLoss) {
        this.reasonForLoss = reasonForLoss;
    }


    public Boolean getIsTaxable() {
        return isTaxable;
    }

    public void setIsTaxable(Boolean isTaxable) {
        this.isTaxable = isTaxable;
    }


    public String getTaxExemptionCode() {
        return taxExemptionCode;
    }

    public void setTaxExemptionCode(String taxExemptionCode) {
        this.taxExemptionCode = taxExemptionCode;
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

    public Long getQuoteByStaffId() {
        return quoteByStaffId;
    }

    public void setQuoteByStaffId(Long staffId) {
        this.quoteByStaffId = staffId;
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

        QuoteDTO quoteDTO = (QuoteDTO) o;

        if ( ! Objects.equals(id, quoteDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "QuoteDTO{" +
                "id=" + id +
                ", quoteNo='" + quoteNo + "'" +
                ", status='" + status + "'" +
                ", quoteDate='" + quoteDate + "'" +
                ", expiryDate='" + expiryDate + "'" +
                ", followupDate='" + followupDate + "'" +
                ", reference='" + reference + "'" +
                ", ourRef='" + ourRef + "'" +
                ", freight='" + freight + "'" +
                ", reasonForLoss='" + reasonForLoss + "'" +
                ", isTaxable='" + isTaxable + "'" +
                ", taxExemptionCode='" + taxExemptionCode + "'" +
                ", isLocked='" + isLocked + "'" +
                ", adjustTax='" + adjustTax + "'" +
                ", adjustTaxExempt='" + adjustTaxExempt + "'" +
                ", comment='" + comment + "'" +
                ", totalTaxAmount='" + totalTaxAmount + "'" +
                ", totalSellPrice='" + totalSellPrice + "'" +
                ", totalCost='" + totalCost + "'" +
                ", isSuspended='" + isSuspended + "'" +
                '}';
    }
}
