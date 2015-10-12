package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.Staff;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Staff entity.
 */
public interface StaffSearchRepository extends ElasticsearchRepository<Staff, Long> {
}
