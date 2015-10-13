package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.TaxTable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TaxTable entity.
 */
public interface TaxTableSearchRepository extends ElasticsearchRepository<TaxTable, Long> {
}
