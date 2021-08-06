package sn.architech.etatcivil.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import sn.architech.etatcivil.domain.Personne;

/**
 * Spring Data Elasticsearch repository for the {@link Personne} entity.
 */
public interface PersonneSearchRepository extends ElasticsearchRepository<Personne, Long> {}
