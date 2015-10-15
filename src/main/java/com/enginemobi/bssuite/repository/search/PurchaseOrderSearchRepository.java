package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.PurchaseOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PurchaseOrder entity.
 */
public interface PurchaseOrderSearchRepository extends ElasticsearchRepository<PurchaseOrder, Long> {
}
