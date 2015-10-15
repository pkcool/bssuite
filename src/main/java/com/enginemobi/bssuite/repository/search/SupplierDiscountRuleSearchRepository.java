package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.SupplierDiscountRule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SupplierDiscountRule entity.
 */
public interface SupplierDiscountRuleSearchRepository extends ElasticsearchRepository<SupplierDiscountRule, Long> {
}
