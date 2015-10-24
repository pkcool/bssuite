package com.enginemobi.bssuite.domain;

import com.enginemobi.bssuite.domain.enumeration.SalesOrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A SalesOrder.
 */
@Entity
@Table(name = "sales_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "salesorder")
public class SalesOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_no")
    private String orderNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SalesOrderStatus status;

    @Column(name = "txn_date")
    private ZonedDateTime txnDate;

    @Column(name = "forward_date")
    private LocalDate forwardDate;

    @Column(name = "required_date")
    private LocalDate requiredDate;

    @Column(name = "customer_order_no")
    private String customerOrderNo;

    @Column(name = "our_ref")
    private String ourRef;

    @Column(name = "freight", precision=10, scale=2)
    private BigDecimal freight;

    @Column(name = "handling_charge", precision=10, scale=2)
    private BigDecimal handlingCharge;

    @Column(name = "charge2", precision=10, scale=2)
    private BigDecimal charge2;

    @Column(name = "is_taxable")
    private Boolean isTaxable;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "adjust_tax", precision=10, scale=2)
    private BigDecimal adjustTax;

    @Column(name = "adjust_tax_exempt", precision=10, scale=2)
    private BigDecimal adjustTaxExempt;

    @Column(name = "prepayment", precision=10, scale=2)
    private BigDecimal prepayment;

    @Column(name = "prepayment_no")
    private String prepaymentNo;

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
    private Customer customer;

    @ManyToOne
    private Contact contact;

    @ManyToOne
    private Contact deliveryContact;

    @ManyToOne
    private Store store;

    @ManyToOne
    private Carrier carrier;

    @ManyToOne
    private Staff salesperson;

    @ManyToOne
    private Promotion promotion;

    @ManyToOne
    private Staff createdBy;

    @OneToMany(mappedBy = "salesOrder")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SalesOrderLineItem> salesOrderLineItems = new HashSet<>();

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

    public ZonedDateTime getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(ZonedDateTime txnDate) {
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

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public Staff getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(Staff staff) {
        this.salesperson = staff;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Staff getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Staff staff) {
        this.createdBy = staff;
    }

    public Set<SalesOrderLineItem> getSalesOrderLineItems() {
        return salesOrderLineItems;
    }

    public void setSalesOrderLineItems(Set<SalesOrderLineItem> salesOrderLineItems) {
        this.salesOrderLineItems = salesOrderLineItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SalesOrder salesOrder = (SalesOrder) o;

        if ( ! Objects.equals(id, salesOrder.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SalesOrder{" +
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
