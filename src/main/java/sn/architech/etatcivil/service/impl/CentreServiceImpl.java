package sn.architech.etatcivil.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.architech.etatcivil.domain.Centre;
import sn.architech.etatcivil.repository.CentreRepository;
import sn.architech.etatcivil.repository.search.CentreSearchRepository;
import sn.architech.etatcivil.service.CentreService;

/**
 * Service Implementation for managing {@link Centre}.
 */
@Service
@Transactional
public class CentreServiceImpl implements CentreService {

    private final Logger log = LoggerFactory.getLogger(CentreServiceImpl.class);

    private final CentreRepository centreRepository;

    private final CentreSearchRepository centreSearchRepository;

    public CentreServiceImpl(CentreRepository centreRepository, CentreSearchRepository centreSearchRepository) {
        this.centreRepository = centreRepository;
        this.centreSearchRepository = centreSearchRepository;
    }

    @Override
    public Centre save(Centre centre) {
        log.debug("Request to save Centre : {}", centre);
        Centre result = centreRepository.save(centre);
        centreSearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<Centre> partialUpdate(Centre centre) {
        log.debug("Request to partially update Centre : {}", centre);

        return centreRepository
            .findById(centre.getId())
            .map(
                existingCentre -> {
                    if (centre.getCentreName() != null) {
                        existingCentre.setCentreName(centre.getCentreName());
                    }
                    if (centre.getCentreChief() != null) {
                        existingCentre.setCentreChief(centre.getCentreChief());
                    }

                    return existingCentre;
                }
            )
            .map(centreRepository::save)
            .map(
                savedCentre -> {
                    centreSearchRepository.save(savedCentre);

                    return savedCentre;
                }
            );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Centre> findAll(Pageable pageable) {
        log.debug("Request to get all Centres");
        return centreRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Centre> findOne(Long id) {
        log.debug("Request to get Centre : {}", id);
        return centreRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Centre : {}", id);
        centreRepository.deleteById(id);
        centreSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Centre> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Centres for query {}", query);
        return centreSearchRepository.search(queryStringQuery(query), pageable);
    }
}
