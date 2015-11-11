package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.PurchaseOrderLineItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PurchaseOrderLineItem entity.
 */
public interface PurchaseOrderLineItemRepository extends JpaRepository<PurchaseOrderLineItem,Long> {

}
