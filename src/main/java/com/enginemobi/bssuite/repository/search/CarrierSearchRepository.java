package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.Carrier;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Carrier entity.
 */
public interface CarrierSearchRepository extends ElasticsearchRepository<Carrier, Long> {
}
