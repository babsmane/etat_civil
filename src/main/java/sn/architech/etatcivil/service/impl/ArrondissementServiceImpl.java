package sn.architech.etatcivil.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.architech.etatcivil.domain.Arrondissement;
import sn.architech.etatcivil.repository.ArrondissementRepository;
import sn.architech.etatcivil.repository.search.ArrondissementSearchRepository;
import sn.architech.etatcivil.service.ArrondissementService;

/**
 * Service Implementation for managing {@link Arrondissement}.
 */
@Service
@Transactional
public class ArrondissementServiceImpl implements ArrondissementService {

    private final Logger log = LoggerFactory.getLogger(ArrondissementServiceImpl.class);

    private final ArrondissementRepository arrondissementRepository;

    private final ArrondissementSearchRepository arrondissementSearchRepository;

    public ArrondissementServiceImpl(
        ArrondissementRepository arrondissementRepository,
        ArrondissementSearchRepository arrondissementSearchRepository
    ) {
        this.arrondissementRepository = arrondissementRepository;
        this.arrondissementSearchRepository = arrondissementSearchRepository;
    }

    @Override
    public Arrondissement save(Arrondissement arrondissement) {
        log.debug("Request to save Arrondissement : {}", arrondissement);
        Arrondissement result = arrondissementRepository.save(arrondissement);
        arrondissementSearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<Arrondissement> partialUpdate(Arrondissement arrondissement) {
        log.debug("Request to partially update Arrondissement : {}", arrondissement);

        return arrondissementRepository
            .findById(arrondissement.getId())
            .map(
                existingArrondissement -> {
                    if (arrondissement.getArrondissementCode() != null) {
                        existingArrondissement.setArrondissementCode(arrondissement.getArrondissementCode());
                    }
                    if (arrondissement.getArrondissementName() != null) {
                        existingArrondissement.setArrondissementName(arrondissement.getArrondissementName());
                    }

                    return existingArrondissement;
                }
            )
            .map(arrondissementRepository::save)
            .map(
                savedArrondissement -> {
                    arrondissementSearchRepository.save(savedArrondissement);

                    return savedArrondissement;
                }
            );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Arrondissement> findAll(Pageable pageable) {
        log.debug("Request to get all Arrondissements");
        return arrondissementRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Arrondissement> findOne(Long id) {
        log.debug("Request to get Arrondissement : {}", id);
        return arrondissementRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Arrondissement : {}", id);
        arrondissementRepository.deleteById(id);
        arrondissementSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Arrondissement> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Arrondissements for query {}", query);
        return arrondissementSearchRepository.search(queryStringQuery(query), pageable);
    }
}
