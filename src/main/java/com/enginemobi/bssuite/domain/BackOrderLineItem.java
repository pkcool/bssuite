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

/**
 * A BackOrderLineItem.
 */
@Entity
@Table(name = "back_order_line_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "backorderlineitem")
public class BackOrderLineItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "is_ready_to_release")
    private Boolean isReadyToRelease;

    @Column(name = "qty_allocated")
    private Double qtyAllocated;

    @Column(name = "is_marked_for_auto_purchase_ordering")
    private Boolean isMarkedForAutoPurchaseOrdering;

    @Column(name = "is_on_hold")
    private Boolean isOnHold;

    @Column(name = "allocated_date")
    private ZonedDateTime allocatedDate;

    @Column(name = "comment")
    private String comment;

    @Column(name = "is_picked")
    private Boolean isPicked;

    @Column(name = "is_marked")
    private Boolean isMarked;

    @ManyToOne
    @JoinColumn(name = "sales_order_line_item_id")
    private SalesOrderLineItem salesOrderLineItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsReadyToRelease() {
        return isReadyToRelease;
    }

    public void setIsReadyToRelease(Boolean isReadyToRelease) {
        this.isReadyToRelease = isReadyToRelease;
    }

    public Double getQtyAllocated() {
        return qtyAllocated;
    }

    public void setQtyAllocated(Double qtyAllocated) {
        this.qtyAllocated = qtyAllocated;
    }

    public Boolean getIsMarkedForAutoPurchaseOrdering() {
        return isMarkedForAutoPurchaseOrdering;
    }

    public void setIsMarkedForAutoPurchaseOrdering(Boolean isMarkedForAutoPurchaseOrdering) {
        this.isMarkedForAutoPurchaseOrdering = isMarkedForAutoPurchaseOrdering;
    }

    public Boolean getIsOnHold() {
        return isOnHold;
    }

    public void setIsOnHold(Boolean isOnHold) {
        this.isOnHold = isOnHold;
    }

    public ZonedDateTime getAllocatedDate() {
        return allocatedDate;
    }

    public void setAllocatedDate(ZonedDateTime allocatedDate) {
        this.allocatedDate = allocatedDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getIsPicked() {
        return isPicked;
    }

    public void setIsPicked(Boolean isPicked) {
        this.isPicked = isPicked;
    }

    public Boolean getIsMarked() {
        return isMarked;
    }

    public void setIsMarked(Boolean isMarked) {
        this.isMarked = isMarked;
    }

    public SalesOrderLineItem getSalesOrderLineItem() {
        return salesOrderLineItem;
    }

    public void setSalesOrderLineItem(SalesOrderLineItem salesOrderLineItem) {
        this.salesOrderLineItem = salesOrderLineItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BackOrderLineItem backOrderLineItem = (BackOrderLineItem) o;
        return Objects.equals(id, backOrderLineItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BackOrderLineItem{" +
            "id=" + id +
            ", isReadyToRelease='" + isReadyToRelease + "'" +
            ", qtyAllocated='" + qtyAllocated + "'" +
            ", isMarkedForAutoPurchaseOrdering='" + isMarkedForAutoPurchaseOrdering + "'" +
            ", isOnHold='" + isOnHold + "'" +
            ", allocatedDate='" + allocatedDate + "'" +
            ", comment='" + comment + "'" +
            ", isPicked='" + isPicked + "'" +
            ", isMarked='" + isMarked + "'" +
            '}';
    }
}
