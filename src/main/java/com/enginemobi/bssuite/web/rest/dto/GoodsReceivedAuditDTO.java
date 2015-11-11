package com.enginemobi.bssuite.web.rest.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.enginemobi.bssuite.domain.enumeration.GoodsReceiptType;

/**
 * A DTO for the GoodsReceivedAudit entity.
 */
public class GoodsReceivedAuditDTO implements Serializable {

    private Long id;

    private ZonedDateTime receivedOn;

    private String txnNumber;

    private GoodsReceiptType typeReceipt;

    private Double qtyReceived;

    private Long receivedById;
    private Long supplierId;
    private Long purchaseOrderId;
    private Long productId;
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

    public Long getReceivedById() {
        return receivedById;
    }

    public void setReceivedById(Long staffId) {
        this.receivedById = staffId;
    }
    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
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

        GoodsReceivedAuditDTO goodsReceivedAuditDTO = (GoodsReceivedAuditDTO) o;

        if ( ! Objects.equals(id, goodsReceivedAuditDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GoodsReceivedAuditDTO{" +
            "id=" + id +
            ", receivedOn='" + receivedOn + "'" +
            ", txnNumber='" + txnNumber + "'" +
            ", typeReceipt='" + typeReceipt + "'" +
            ", qtyReceived='" + qtyReceived + "'" +
            '}';
    }
}
