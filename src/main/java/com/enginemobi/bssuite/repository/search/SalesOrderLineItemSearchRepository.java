package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.SalesOrderLineItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SalesOrderLineItem entity.
 */
public interface SalesOrderLineItemSearchRepository extends ElasticsearchRepository<SalesOrderLineItem, Long> {
}
