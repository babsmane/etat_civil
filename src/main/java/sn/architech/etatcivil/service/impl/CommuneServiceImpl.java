package sn.architech.etatcivil.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.architech.etatcivil.domain.Commune;
import sn.architech.etatcivil.repository.CommuneRepository;
import sn.architech.etatcivil.repository.search.CommuneSearchRepository;
import sn.architech.etatcivil.service.CommuneService;

/**
 * Service Implementation for managing {@link Commune}.
 */
@Service
@Transactional
public class CommuneServiceImpl implements CommuneService {

    private final Logger log = LoggerFactory.getLogger(CommuneServiceImpl.class);

    private final CommuneRepository communeRepository;

    private final CommuneSearchRepository communeSearchRepository;

    public CommuneServiceImpl(CommuneRepository communeRepository, CommuneSearchRepository communeSearchRepository) {
        this.communeRepository = communeRepository;
        this.communeSearchRepository = communeSearchRepository;
    }

    @Override
    public Commune save(Commune commune) {
        log.debug("Request to save Commune : {}", commune);
        Commune result = communeRepository.save(commune);
        communeSearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<Commune> partialUpdate(Commune commune) {
        log.debug("Request to partially update Commune : {}", commune);

        return communeRepository
            .findById(commune.getId())
            .map(
                existingCommune -> {
                    if (commune.getCommuneCode() != null) {
                        existingCommune.setCommuneCode(commune.getCommuneCode());
                    }
                    if (commune.getCommuneName() != null) {
                        existingCommune.setCommuneName(commune.getCommuneName());
                    }

                    return existingCommune;
                }
            )
            .map(communeRepository::save)
            .map(
                savedCommune -> {
                    communeSearchRepository.save(savedCommune);

                    return savedCommune;
                }
            );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Commune> findAll(Pageable pageable) {
        log.debug("Request to get all Communes");
        return communeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Commune> findOne(Long id) {
        log.debug("Request to get Commune : {}", id);
        return communeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Commune : {}", id);
        communeRepository.deleteById(id);
        communeSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Commune> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Communes for query {}", query);
        return communeSearchRepository.search(queryStringQuery(query), pageable);
    }
}
