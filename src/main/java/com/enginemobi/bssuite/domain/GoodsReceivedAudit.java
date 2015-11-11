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

import com.enginemobi.bssuite.domain.enumeration.GoodsReceiptType;

/**
 * A GoodsReceivedAudit.
 */
@Entity
@Table(name = "goods_received_audit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "goodsreceivedaudit")
public class GoodsReceivedAudit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "received_on")
    private ZonedDateTime receivedOn;

    @Column(name = "txn_number")
    private String txnNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_receipt")
    private GoodsReceiptType typeReceipt;

    @Column(name = "qty_received")
    private Double qtyReceived;

    @ManyToOne
    private Staff receivedBy;

    @ManyToOne
    private Supplier supplier;

    @ManyToOne
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(ZonedDateTime receivedOn) {
        this.receivedOn = receivedOn;
    }

    public String getTxnNumber() {
        return txnNumber;
    }

    public void setTxnNumber(String txnNumber) {
        this.txnNumber = txnNumber;
    }

    public GoodsReceiptType getTypeReceipt() {
        return typeReceipt;
    }

    public void setTypeReceipt(GoodsReceiptType typeReceipt) {
        this.typeReceipt = typeReceipt;
    }

    public Double getQtyReceived() {
        return qtyReceived;
    }

    public void setQtyReceived(Double qtyReceived) {
        this.qtyReceived = qtyReceived;
    }

    public Staff getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(Staff staff) {
        this.receivedBy = staff;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
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

        GoodsReceivedAudit goodsReceivedAudit = (GoodsReceivedAudit) o;

        if ( ! Objects.equals(id, goodsReceivedAudit.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GoodsReceivedAudit{" +
            "id=" + id +
            ", receivedOn='" + receivedOn + "'" +
            ", txnNumber='" + txnNumber + "'" +
            ", typeReceipt='" + typeReceipt + "'" +
            ", qtyReceived='" + qtyReceived + "'" +
            '}';
    }
}
