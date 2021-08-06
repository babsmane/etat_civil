package sn.architech.etatcivil.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import sn.architech.etatcivil.domain.Commune;

/**
 * Spring Data Elasticsearch repository for the {@link Commune} entity.
 */
public interface CommuneSearchRepository extends ElasticsearchRepository<Commune, Long> {}
