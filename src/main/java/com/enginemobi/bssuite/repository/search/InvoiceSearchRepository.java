package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.Invoice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Invoice entity.
 */
public interface InvoiceSearchRepository extends ElasticsearchRepository<Invoice, Long> {
}
