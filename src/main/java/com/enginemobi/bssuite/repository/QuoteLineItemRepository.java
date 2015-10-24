package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.QuoteLineItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the QuoteLineItem entity.
 */
public interface QuoteLineItemRepository extends JpaRepository<QuoteLineItem,Long> {

}
