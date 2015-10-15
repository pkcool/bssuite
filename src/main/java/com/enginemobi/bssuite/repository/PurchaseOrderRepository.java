package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PurchaseOrder entity.
 */
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder,Long> {

}
