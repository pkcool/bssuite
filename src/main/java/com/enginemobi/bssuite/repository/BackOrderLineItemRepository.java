package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.BackOrderLineItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BackOrderLineItem entity.
 */
public interface BackOrderLineItemRepository extends JpaRepository<BackOrderLineItem,Long> {

}
