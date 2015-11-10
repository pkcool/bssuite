package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.GoodsReceivedAudit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the GoodsReceivedAudit entity.
 */
public interface GoodsReceivedAuditSearchRepository extends ElasticsearchRepository<GoodsReceivedAudit, Long> {
}
