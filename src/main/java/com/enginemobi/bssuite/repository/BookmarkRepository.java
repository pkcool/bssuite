package com.enginemobi.bssuite.repository;

import com.enginemobi.bssuite.domain.Bookmark;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Bookmark entity.
 */
public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {

}
