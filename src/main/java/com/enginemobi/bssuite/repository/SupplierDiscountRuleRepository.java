package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.SupplierDiscountRule;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SupplierDiscountRule entity.
 */
public interface SupplierDiscountRuleRepository extends JpaRepository<SupplierDiscountRule,Long> {

}
