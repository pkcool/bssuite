package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.SupplierCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SupplierCategory entity.
 */
public interface SupplierCategorySearchRepository extends ElasticsearchRepository<SupplierCategory, Long> {
}
