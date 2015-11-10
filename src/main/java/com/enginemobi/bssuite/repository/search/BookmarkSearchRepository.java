package com.enginemobi.bssuite.repository.search;

import com.enginemobi.bssuite.domain.Bookmark;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Bookmark entity.
 */
public interface BookmarkSearchRepository extends ElasticsearchRepository<Bookmark, Long> {
}
