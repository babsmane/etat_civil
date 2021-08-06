package sn.architech.etatcivil.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import sn.architech.etatcivil.domain.Arrondissement;

/**
 * Spring Data Elasticsearch repository for the {@link Arrondissement} entity.
 */
public interface ArrondissementSearchRepository extends ElasticsearchRepository<Arrondissement, Long> {}
