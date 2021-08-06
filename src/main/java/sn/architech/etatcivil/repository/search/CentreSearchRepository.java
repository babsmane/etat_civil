package sn.architech.etatcivil.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import sn.architech.etatcivil.domain.Centre;

/**
 * Spring Data Elasticsearch repository for the {@link Centre} entity.
 */
public interface CentreSearchRepository extends ElasticsearchRepository<Centre, Long> {}
