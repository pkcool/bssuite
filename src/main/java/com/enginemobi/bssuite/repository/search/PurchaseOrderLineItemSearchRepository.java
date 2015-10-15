package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.PurchaseOrderLineItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PurchaseOrderLineItem entity.
 */
public interface PurchaseOrderLineItemSearchRepository extends ElasticsearchRepository<PurchaseOrderLineItem, Long> {
}
