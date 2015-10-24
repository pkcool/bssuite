package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.CustomerCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomerCategory entity.
 */
public interface CustomerCategoryRepository extends JpaRepository<CustomerCategory,Long> {

}
