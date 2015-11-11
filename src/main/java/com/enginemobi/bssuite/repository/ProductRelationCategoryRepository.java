package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.ProductRelationCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductRelationCategory entity.
 */
public interface ProductRelationCategoryRepository extends JpaRepository<ProductRelationCategory,Long> {

}
