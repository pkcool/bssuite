package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.Promotion;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Promotion entity.
 */
public interface PromotionRepository extends JpaRepository<Promotion,Long> {

}
