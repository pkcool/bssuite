package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.SalesOrderLineItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SalesOrderLineItem entity.
 */
public interface SalesOrderLineItemRepository extends JpaRepository<SalesOrderLineItem,Long> {

}
