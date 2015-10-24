package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.SalesOrder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SalesOrder entity.
 */
public interface SalesOrderRepository extends JpaRepository<SalesOrder,Long> {

}
