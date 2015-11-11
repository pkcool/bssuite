package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.StockFamily;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StockFamily entity.
 */
public interface StockFamilyRepository extends JpaRepository<StockFamily,Long> {

}
