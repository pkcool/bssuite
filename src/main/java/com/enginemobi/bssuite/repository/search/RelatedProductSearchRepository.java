package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.RelatedProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RelatedProduct entity.
 */
public interface RelatedProductSearchRepository extends ElasticsearchRepository<RelatedProduct, Long> {
}
