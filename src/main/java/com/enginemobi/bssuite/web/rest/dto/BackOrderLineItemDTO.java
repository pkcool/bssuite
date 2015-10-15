package com.enginemobi.bssuite.web.rest.dto;

import org.joda.time.DateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the BackOrderLineItem entity.
 */
public class BackOrderLineItemDTO implements Serializable {

    private Long id;

    private Boolean isReadyToRelease;


    private Double qtyAllocated;


    private Boolean isMarkedForAutoPurchaseOrdering;


    private Boolean isOnHold;


    private DateTime allocatedDate;


    private String comment;


    private Boolean isPicked;


    private Boolean isMarked;


    private Long salesOrderLineItemId;

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


    public Long getSalesOrderLineItemId() {
        return salesOrderLineItemId;
    }

    public void setSalesOrderLineItemId(Long salesOrderLineItemId) {
        this.salesOrderLineItemId = salesOrderLineItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BackOrderLineItemDTO backOrderLineItemDTO = (BackOrderLineItemDTO) o;

        if ( ! Objects.equals(id, backOrderLineItemDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BackOrderLineItemDTO{" +
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
