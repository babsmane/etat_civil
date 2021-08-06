package sn.architech.etatcivil.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.architech.etatcivil.domain.Arrondissement;

/**
 * Service Interface for managing {@link Arrondissement}.
 */
public interface ArrondissementService {
    /**
     * Save a arrondissement.
     *
     * @param arrondissement the entity to save.
     * @return the persisted entity.
     */
    Arrondissement save(Arrondissement arrondissement);

    /**
     * Partially updates a arrondissement.
     *
     * @param arrondissement the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Arrondissement> partialUpdate(Arrondissement arrondissement);

    /**
     * Get all the arrondissements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Arrondissement> findAll(Pageable pageable);

    /**
     * Get the "id" arrondissement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Arrondissement> findOne(Long id);

    /**
     * Delete the "id" arrondissement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the arrondissement corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Arrondissement> search(String query, Pageable pageable);
}
