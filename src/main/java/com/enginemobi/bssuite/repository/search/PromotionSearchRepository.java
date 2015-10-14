package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.Promotion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Promotion entity.
 */
public interface PromotionSearchRepository extends ElasticsearchRepository<Promotion, Long> {
}
