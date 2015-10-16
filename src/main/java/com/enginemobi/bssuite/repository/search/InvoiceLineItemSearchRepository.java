package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.InvoiceLineItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InvoiceLineItem entity.
 */
public interface InvoiceLineItemSearchRepository extends ElasticsearchRepository<InvoiceLineItem, Long> {
}
