package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.RelatedProduct;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RelatedProduct entity.
 */
public interface RelatedProductRepository extends JpaRepository<RelatedProduct,Long> {

}
