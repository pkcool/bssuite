package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.ProductRelationCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ProductRelationCategory entity.
 */
public interface ProductRelationCategorySearchRepository extends ElasticsearchRepository<ProductRelationCategory, Long> {
}
