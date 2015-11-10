package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.ProductActivityAudit;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductActivityAudit entity.
 */
public interface ProductActivityAuditRepository extends JpaRepository<ProductActivityAudit,Long> {

}
