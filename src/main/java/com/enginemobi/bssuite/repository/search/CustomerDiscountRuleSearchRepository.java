package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.CustomerDiscountRule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CustomerDiscountRule entity.
 */
public interface CustomerDiscountRuleSearchRepository extends ElasticsearchRepository<CustomerDiscountRule, Long> {
}
