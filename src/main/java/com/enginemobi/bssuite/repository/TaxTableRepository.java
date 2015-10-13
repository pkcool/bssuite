package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.TaxTable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TaxTable entity.
 */
public interface TaxTableRepository extends JpaRepository<TaxTable,Long> {

}
