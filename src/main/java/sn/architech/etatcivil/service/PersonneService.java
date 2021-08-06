package sn.architech.etatcivil.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.architech.etatcivil.domain.Personne;

/**
 * Service Interface for managing {@link Personne}.
 */
public interface PersonneService {
    /**
     * Save a personne.
     *
     * @param personne the entity to save.
     * @return the persisted entity.
     */
    Personne save(Personne personne);

    /**
     * Partially updates a personne.
     *
     * @param personne the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Personne> partialUpdate(Personne personne);

    /**
     * Get all the personnes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Personne> findAll(Pageable pageable);

    /**
     * Get the "id" personne.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Personne> findOne(Long id);

    /**
     * Delete the "id" personne.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the personne corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Personne> search(String query, Pageable pageable);
}
