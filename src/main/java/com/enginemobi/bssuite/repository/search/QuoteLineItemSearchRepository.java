package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.QuoteLineItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the QuoteLineItem entity.
 */
public interface QuoteLineItemSearchRepository extends ElasticsearchRepository<QuoteLineItem, Long> {
}
