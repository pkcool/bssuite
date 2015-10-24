package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.PriceScale;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PriceScale entity.
 */
public interface PriceScaleRepository extends JpaRepository<PriceScale,Long> {

}
