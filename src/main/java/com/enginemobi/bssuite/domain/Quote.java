package com.enginemobi.bssuite.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.enginemobi.bssuite.domain.enumeration.QuoteStatus;

/**
 * A Quote.
 */
@Entity
@Table(name = "quote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "quote")
public class Quote implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "quote_no")
    private String quoteNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private QuoteStatus status;

    @Column(name = "quote_date")
    private ZonedDateTime quoteDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "followup_date")
    private LocalDate followupDate;

    @Column(name = "reference")
    private String reference;

    @Column(name = "our_ref")
    private String ourRef;

    @Column(name = "freight", precision=10, scale=2)
    private BigDecimal freight;

    @Column(name = "reason_for_loss")
    private String reasonForLoss;

    @Column(name = "is_taxable")
    private Boolean isTaxable;

    @Column(name = "tax_exemption_code")
    private String taxExemptionCode;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "adjust_tax", precision=10, scale=2)
    private BigDecimal adjustTax;

    @Column(name = "adjust_tax_exempt", precision=10, scale=2)
    private BigDecimal adjustTaxExempt;

    @Column(name = "comment")
    private String comment;

    @Column(name = "total_tax_amount", precision=10, scale=2)
    private BigDecimal totalTaxAmount;

    @Column(name = "total_sell_price", precision=10, scale=2)
    private BigDecimal totalSellPrice;

    @Column(name = "total_cost", precision=10, scale=2)
    private BigDecimal totalCost;

    @Column(name = "is_suspended")
    private Boolean isSuspended;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @ManyToOne
    @JoinColumn(name = "delivery_contact_id")
    private Contact deliveryContact;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "quote_by_staff_id")
    private Staff quoteByStaff;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private Staff createdBy;

    @OneToMany(mappedBy = "quote")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuoteLineItem> quoteLineItemss = new HashSet<>();

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

    public ZonedDateTime getQuoteDate() {
        return quoteDate;
    }

    public void setQuoteDate(ZonedDateTime quoteDate) {
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Contact getDeliveryContact() {
        return deliveryContact;
    }

    public void setDeliveryContact(Contact contact) {
        this.deliveryContact = contact;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Staff getQuoteByStaff() {
        return quoteByStaff;
    }

    public void setQuoteByStaff(Staff staff) {
        this.quoteByStaff = staff;
    }

    public Staff getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Staff staff) {
        this.createdBy = staff;
    }

    public Set<QuoteLineItem> getQuoteLineItemss() {
        return quoteLineItemss;
    }

    public void setQuoteLineItemss(Set<QuoteLineItem> quoteLineItems) {
        this.quoteLineItemss = quoteLineItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quote quote = (Quote) o;
        return Objects.equals(id, quote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Quote{" +
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
