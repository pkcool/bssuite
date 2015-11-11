package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.Carrier;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Carrier entity.
 */
public interface CarrierRepository extends JpaRepository<Carrier,Long> {

}
