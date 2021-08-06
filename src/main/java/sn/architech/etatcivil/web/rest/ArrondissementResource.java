package sn.architech.etatcivil.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sn.architech.etatcivil.domain.Arrondissement;
import sn.architech.etatcivil.repository.ArrondissementRepository;
import sn.architech.etatcivil.service.ArrondissementService;
import sn.architech.etatcivil.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.architech.etatcivil.domain.Arrondissement}.
 */
@RestController
@RequestMapping("/api")
public class ArrondissementResource {

    private final Logger log = LoggerFactory.getLogger(ArrondissementResource.class);

    private static final String ENTITY_NAME = "arrondissement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArrondissementService arrondissementService;

    private final ArrondissementRepository arrondissementRepository;

    public ArrondissementResource(ArrondissementService arrondissementService, ArrondissementRepository arrondissementRepository) {
        this.arrondissementService = arrondissementService;
        this.arrondissementRepository = arrondissementRepository;
    }

    /**
     * {@code POST  /arrondissements} : Create a new arrondissement.
     *
     * @param arrondissement the arrondissement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new arrondissement, or with status {@code 400 (Bad Request)} if the arrondissement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/arrondissements")
    public ResponseEntity<Arrondissement> createArrondissement(@Valid @RequestBody Arrondissement arrondissement)
        throws URISyntaxException {
        log.debug("REST request to save Arrondissement : {}", arrondissement);
        if (arrondissement.getId() != null) {
            throw new BadRequestAlertException("A new arrondissement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Arrondissement result = arrondissementService.save(arrondissement);
        return ResponseEntity
            .created(new URI("/api/arrondissements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /arrondissements/:id} : Updates an existing arrondissement.
     *
     * @param id the id of the arrondissement to save.
     * @param arrondissement the arrondissement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arrondissement,
     * or with status {@code 400 (Bad Request)} if the arrondissement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the arrondissement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/arrondissements/{id}")
    public ResponseEntity<Arrondissement> updateArrondissement(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Arrondissement arrondissement
    ) throws URISyntaxException {
        log.debug("REST request to update Arrondissement : {}, {}", id, arrondissement);
        if (arrondissement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, arrondissement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!arrondissementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Arrondissement result = arrondissementService.save(arrondissement);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, arrondissement.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /arrondissements/:id} : Partial updates given fields of an existing arrondissement, field will ignore if it is null
     *
     * @param id the id of the arrondissement to save.
     * @param arrondissement the arrondissement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arrondissement,
     * or with status {@code 400 (Bad Request)} if the arrondissement is not valid,
     * or with status {@code 404 (Not Found)} if the arrondissement is not found,
     * or with status {@code 500 (Internal Server Error)} if the arrondissement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/arrondissements/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Arrondissement> partialUpdateArrondissement(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Arrondissement arrondissement
    ) throws URISyntaxException {
        log.debug("REST request to partial update Arrondissement partially : {}, {}", id, arrondissement);
        if (arrondissement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, arrondissement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!arrondissementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Arrondissement> result = arrondissementService.partialUpdate(arrondissement);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, arrondissement.getId().toString())
        );
    }

    /**
     * {@code GET  /arrondissements} : get all the arrondissements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of arrondissements in body.
     */
    @GetMapping("/arrondissements")
    public ResponseEntity<List<Arrondissement>> getAllArrondissements(Pageable pageable) {
        log.debug("REST request to get a page of Arrondissements");
        Page<Arrondissement> page = arrondissementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /arrondissements/:id} : get the "id" arrondissement.
     *
     * @param id the id of the arrondissement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the arrondissement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/arrondissements/{id}")
    public ResponseEntity<Arrondissement> getArrondissement(@PathVariable Long id) {
        log.debug("REST request to get Arrondissement : {}", id);
        Optional<Arrondissement> arrondissement = arrondissementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(arrondissement);
    }

    /**
     * {@code DELETE  /arrondissements/:id} : delete the "id" arrondissement.
     *
     * @param id the id of the arrondissement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/arrondissements/{id}")
    public ResponseEntity<Void> deleteArrondissement(@PathVariable Long id) {
        log.debug("REST request to delete Arrondissement : {}", id);
        arrondissementService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/arrondissements?query=:query} : search for the arrondissement corresponding
     * to the query.
     *
     * @param query the query of the arrondissement search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/arrondissements")
    public ResponseEntity<List<Arrondissement>> searchArrondissements(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Arrondissements for query {}", query);
        Page<Arrondissement> page = arrondissementService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
