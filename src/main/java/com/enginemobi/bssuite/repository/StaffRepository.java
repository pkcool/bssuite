package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.Staff;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Staff entity.
 */
public interface StaffRepository extends JpaRepository<Staff,Long> {

}
