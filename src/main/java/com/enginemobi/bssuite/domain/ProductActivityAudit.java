package com.enginemobi.bssuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.ZonedDateTime;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.enginemobi.bssuite.domain.enumeration.ProductActivityType;

/**
 * A ProductActivityAudit.
 */
@Entity
@Table(name = "product_activity_audit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productactivityaudit")
public class ProductActivityAudit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "txn_number")
    private String txnNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type")
    private ProductActivityType activityType;

    @Column(name = "qty_txn")
    private Double qtyTxn;

    @Column(name = "qty_adjusted")
    private Double qtyAdjusted;

    @Column(name = "qty_stock_on_hold")
    private Double qtyStockOnHold;

    @Column(name = "line_no")
    private String lineNo;

    @Column(name = "txn_account_code")
    private String txnAccountCode;

    @ManyToOne
    private Staff createdBy;

    @ManyToOne
    private Product product;

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

    public Staff getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Staff staff) {
        this.createdBy = staff;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductActivityAudit productActivityAudit = (ProductActivityAudit) o;

        if ( ! Objects.equals(id, productActivityAudit.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductActivityAudit{" +
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
