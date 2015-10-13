package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.StockFamily;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the StockFamily entity.
 */
public interface StockFamilySearchRepository extends ElasticsearchRepository<StockFamily, Long> {
}
