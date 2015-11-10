package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.TxnActivityAudit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TxnActivityAudit entity.
 */
public interface TxnActivityAuditSearchRepository extends ElasticsearchRepository<TxnActivityAudit, Long> {
}
