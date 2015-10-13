package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.StockGroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the StockGroup entity.
 */
public interface StockGroupSearchRepository extends ElasticsearchRepository<StockGroup, Long> {
}
