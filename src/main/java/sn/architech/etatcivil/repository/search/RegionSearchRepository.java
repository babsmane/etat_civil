package sn.architech.etatcivil.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import sn.architech.etatcivil.domain.Region;

/**
 * Spring Data Elasticsearch repository for the {@link Region} entity.
 */
public interface RegionSearchRepository extends ElasticsearchRepository<Region, Long> {}
