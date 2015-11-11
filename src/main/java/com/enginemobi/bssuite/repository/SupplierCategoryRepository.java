package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.SupplierCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SupplierCategory entity.
 */
public interface SupplierCategoryRepository extends JpaRepository<SupplierCategory,Long> {

}
