package sn.architech.etatcivil.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.architech.etatcivil.domain.Personne;
import sn.architech.etatcivil.repository.PersonneRepository;
import sn.architech.etatcivil.repository.search.PersonneSearchRepository;
import sn.architech.etatcivil.service.PersonneService;

/**
 * Service Implementation for managing {@link Personne}.
 */
@Service
@Transactional
public class PersonneServiceImpl implements PersonneService {

    private final Logger log = LoggerFactory.getLogger(PersonneServiceImpl.class);

    private final PersonneRepository personneRepository;

    private final PersonneSearchRepository personneSearchRepository;

    public PersonneServiceImpl(PersonneRepository personneRepository, PersonneSearchRepository personneSearchRepository) {
        this.personneRepository = personneRepository;
        this.personneSearchRepository = personneSearchRepository;
    }

    @Override
    public Personne save(Personne personne) {
        log.debug("Request to save Personne : {}", personne);
        Personne result = personneRepository.save(personne);
        personneSearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<Personne> partialUpdate(Personne personne) {
        log.debug("Request to partially update Personne : {}", personne);

        return personneRepository
            .findById(personne.getId())
            .map(
                existingPersonne -> {
                    if (personne.getFirstName() != null) {
                        existingPersonne.setFirstName(personne.getFirstName());
                    }
                    if (personne.getLastName() != null) {
                        existingPersonne.setLastName(personne.getLastName());
                    }
                    if (personne.getFatherName() != null) {
                        existingPersonne.setFatherName(personne.getFatherName());
                    }
                    if (personne.getSexe() != null) {
                        existingPersonne.setSexe(personne.getSexe());
                    }
                    if (personne.getMotherFirstName() != null) {
                        existingPersonne.setMotherFirstName(personne.getMotherFirstName());
                    }
                    if (personne.getMotherLastName() != null) {
                        existingPersonne.setMotherLastName(personne.getMotherLastName());
                    }
                    if (personne.getDateOfBirthday() != null) {
                        existingPersonne.setDateOfBirthday(personne.getDateOfBirthday());
                    }
                    if (personne.getHourOfBithday() != null) {
                        existingPersonne.setHourOfBithday(personne.getHourOfBithday());
                    }
                    if (personne.getNumberRegister() != null) {
                        existingPersonne.setNumberRegister(personne.getNumberRegister());
                    }

                    return existingPersonne;
                }
            )
            .map(personneRepository::save)
            .map(
                savedPersonne -> {
                    personneSearchRepository.save(savedPersonne);

                    return savedPersonne;
                }
            );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Personne> findAll(Pageable pageable) {
        log.debug("Request to get all Personnes");
        return personneRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Personne> findOne(Long id) {
        log.debug("Request to get Personne : {}", id);
        return personneRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Personne : {}", id);
        personneRepository.deleteById(id);
        personneSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Personne> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Personnes for query {}", query);
        return personneSearchRepository.search(queryStringQuery(query), pageable);
    }
}
