package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.Quote;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Quote entity.
 */
public interface QuoteSearchRepository extends ElasticsearchRepository<Quote, Long> {
}
