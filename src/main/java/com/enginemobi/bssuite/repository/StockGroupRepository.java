package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.StockGroup;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StockGroup entity.
 */
public interface StockGroupRepository extends JpaRepository<StockGroup,Long> {

}
