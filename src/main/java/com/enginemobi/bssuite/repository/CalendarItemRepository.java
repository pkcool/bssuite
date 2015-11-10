package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.CalendarItem;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CalendarItem entity.
 */
public interface CalendarItemRepository extends JpaRepository<CalendarItem,Long> {

}
