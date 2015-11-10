package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.ProductActivityAudit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ProductActivityAudit entity.
 */
public interface ProductActivityAuditSearchRepository extends ElasticsearchRepository<ProductActivityAudit, Long> {
}
