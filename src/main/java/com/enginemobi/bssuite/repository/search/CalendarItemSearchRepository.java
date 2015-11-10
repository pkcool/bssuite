package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.CalendarItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CalendarItem entity.
 */
public interface CalendarItemSearchRepository extends ElasticsearchRepository<CalendarItem, Long> {
}
