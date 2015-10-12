package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.CustomerCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CustomerCategory entity.
 */
public interface CustomerCategorySearchRepository extends ElasticsearchRepository<CustomerCategory, Long> {
}
