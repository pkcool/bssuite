package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Product entity.
 */
public interface ProductSearchRepository extends ElasticsearchRepository<Product, Long> {
}
