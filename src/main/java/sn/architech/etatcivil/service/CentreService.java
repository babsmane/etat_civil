package sn.architech.etatcivil.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.architech.etatcivil.domain.Centre;

/**
 * Service Interface for managing {@link Centre}.
 */
public interface CentreService {
    /**
     * Save a centre.
     *
     * @param centre the entity to save.
     * @return the persisted entity.
     */
    Centre save(Centre centre);

    /**
     * Partially updates a centre.
     *
     * @param centre the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Centre> partialUpdate(Centre centre);

    /**
     * Get all the centres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Centre> findAll(Pageable pageable);

    /**
     * Get the "id" centre.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Centre> findOne(Long id);

    /**
     * Delete the "id" centre.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the centre corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Centre> search(String query, Pageable pageable);
}
