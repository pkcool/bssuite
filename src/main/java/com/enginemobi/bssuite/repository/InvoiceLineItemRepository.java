package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.InvoiceLineItem;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InvoiceLineItem entity.
 */
public interface InvoiceLineItemRepository extends JpaRepository<InvoiceLineItem,Long> {

}
