package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.Supplier;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Supplier entity.
 */
public interface SupplierSearchRepository extends ElasticsearchRepository<Supplier, Long> {
}
