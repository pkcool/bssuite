package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.Store;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Store entity.
 */
public interface StoreRepository extends JpaRepository<Store,Long> {

}
