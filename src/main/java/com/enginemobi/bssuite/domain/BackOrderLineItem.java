package com.enginemobi.bssuite.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.enginemobi.bssuite.domain.util.CustomDateTimeDeserializer;
import com.enginemobi.bssuite.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
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
@Document(indexName="backorderlineitem")
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
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "allocated_date")
    private DateTime allocatedDate;
    
    @Column(name = "comment")
    private String comment;
    
    @Column(name = "is_picked")
    private Boolean isPicked;
    
    @Column(name = "is_marked")
    private Boolean isMarked;

    @ManyToOne
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

    public DateTime getAllocatedDate() {
        return allocatedDate;
    }

    public void setAllocatedDate(DateTime allocatedDate) {
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

        if ( ! Objects.equals(id, backOrderLineItem.id)) return false;

        return true;
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
