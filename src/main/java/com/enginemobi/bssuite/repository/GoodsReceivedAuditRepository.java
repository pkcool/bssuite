package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.GoodsReceivedAudit;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GoodsReceivedAudit entity.
 */
public interface GoodsReceivedAuditRepository extends JpaRepository<GoodsReceivedAudit,Long> {

}
