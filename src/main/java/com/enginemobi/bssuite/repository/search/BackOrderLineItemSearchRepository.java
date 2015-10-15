package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.BackOrderLineItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the BackOrderLineItem entity.
 */
public interface BackOrderLineItemSearchRepository extends ElasticsearchRepository<BackOrderLineItem, Long> {
}
