package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.TxnActivityAudit;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TxnActivityAudit entity.
 */
public interface TxnActivityAuditRepository extends JpaRepository<TxnActivityAudit,Long> {

}
