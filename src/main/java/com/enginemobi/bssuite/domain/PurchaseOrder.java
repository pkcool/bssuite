package com.enginemobi.bssuite.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.enginemobi.bssuite.domain.util.CustomLocalDateSerializer;
import com.enginemobi.bssuite.domain.util.ISO8601LocalDateDeserializer;
import com.enginemobi.bssuite.domain.util.CustomDateTimeDeserializer;
import com.enginemobi.bssuite.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.DateTime;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.enginemobi.bssuite.domain.enumeration.PurchaseOrderStatus;

/**
 * A PurchaseOrder.
 */
@Entity
@Table(name = "purchase_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName="purchaseorder")
public class PurchaseOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @Column(name = "order_no")
    private String orderNo;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PurchaseOrderStatus status;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "created_date")
    private DateTime createdDate;
    
    @Column(name = "ref")
    private String ref;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "expected_delivery_date")
    private LocalDate expectedDeliveryDate;
    
    @Column(name = "is_taxable")
    private Boolean isTaxable;
    
    @Column(name = "is_locked")
    private Boolean isLocked;
    
    @Column(name = "comment")
    private String comment;
    
    @Column(name = "total_tax_amount", precision=10, scale=2)
    private BigDecimal totalTaxAmount;
    
    @Column(name = "total_cost", precision=10, scale=2)
    private BigDecimal totalCost;
    
    @Column(name = "tax_exemption_code")
    private String taxExemptionCode;
    
    @Column(name = "is_suspended")
    private Boolean isSuspended;

    @ManyToOne
    private Supplier supplier;

    @ManyToOne
    private Contact supplierContact;

    @ManyToOne
    private Contact supplierDeliveryContact;

    @ManyToOne
    private Store store;

    @ManyToOne
    private SalesOrder salesOrder;

    @ManyToOne
    private Staff updatedByStaff;

    @ManyToOne
    private Staff createdBy;

    @OneToMany(mappedBy = "purchaseOrder")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PurchaseOrderLineItem> purchaseOrderLineItemss = new HashSet<>();

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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier Supplier) {
        this.supplier = Supplier;
    }

    public Contact getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(Contact contact) {
        this.supplierContact = contact;
    }

    public Contact getSupplierDeliveryContact() {
        return supplierDeliveryContact;
    }

    public void setSupplierDeliveryContact(Contact contact) {
        this.supplierDeliveryContact = contact;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public SalesOrder getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }

    public Staff getUpdatedByStaff() {
        return updatedByStaff;
    }

    public void setUpdatedByStaff(Staff staff) {
        this.updatedByStaff = staff;
    }

    public Staff getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Staff staff) {
        this.createdBy = staff;
    }

    public Set<PurchaseOrderLineItem> getPurchaseOrderLineItemss() {
        return purchaseOrderLineItemss;
    }

    public void setPurchaseOrderLineItemss(Set<PurchaseOrderLineItem> purchaseOrderLineItems) {
        this.purchaseOrderLineItemss = purchaseOrderLineItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PurchaseOrder purchaseOrder = (PurchaseOrder) o;

        if ( ! Objects.equals(id, purchaseOrder.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
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
