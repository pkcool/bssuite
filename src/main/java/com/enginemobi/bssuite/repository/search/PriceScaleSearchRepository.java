package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.PriceScale;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PriceScale entity.
 */
public interface PriceScaleSearchRepository extends ElasticsearchRepository<PriceScale, Long> {
}
