package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.Quote;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Quote entity.
 */
public interface QuoteRepository extends JpaRepository<Quote,Long> {

}
