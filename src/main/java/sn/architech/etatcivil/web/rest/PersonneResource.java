package sn.architech.etatcivil.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
import sn.architech.etatcivil.domain.Personne;
import sn.architech.etatcivil.repository.PersonneRepository;
import sn.architech.etatcivil.service.PersonneService;
import sn.architech.etatcivil.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.architech.etatcivil.domain.Personne}.
 */
@RestController
@RequestMapping("/api")
public class PersonneResource {

    private final Logger log = LoggerFactory.getLogger(PersonneResource.class);

    private static final String ENTITY_NAME = "personne";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonneService personneService;

    private final PersonneRepository personneRepository;

    public PersonneResource(PersonneService personneService, PersonneRepository personneRepository) {
        this.personneService = personneService;
        this.personneRepository = personneRepository;
    }

    /**
     * {@code POST  /personnes} : Create a new personne.
     *
     * @param personne the personne to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personne, or with status {@code 400 (Bad Request)} if the personne has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personnes")
    public ResponseEntity<Personne> createPersonne(@RequestBody Personne personne) throws URISyntaxException {
        log.debug("REST request to save Personne : {}", personne);
        if (personne.getId() != null) {
            throw new BadRequestAlertException("A new personne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Personne result = personneService.save(personne);
        return ResponseEntity
            .created(new URI("/api/personnes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personnes/:id} : Updates an existing personne.
     *
     * @param id the id of the personne to save.
     * @param personne the personne to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personne,
     * or with status {@code 400 (Bad Request)} if the personne is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personne couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personnes/{id}")
    public ResponseEntity<Personne> updatePersonne(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Personne personne
    ) throws URISyntaxException {
        log.debug("REST request to update Personne : {}, {}", id, personne);
        if (personne.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personne.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Personne result = personneService.save(personne);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personne.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /personnes/:id} : Partial updates given fields of an existing personne, field will ignore if it is null
     *
     * @param id the id of the personne to save.
     * @param personne the personne to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personne,
     * or with status {@code 400 (Bad Request)} if the personne is not valid,
     * or with status {@code 404 (Not Found)} if the personne is not found,
     * or with status {@code 500 (Internal Server Error)} if the personne couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/personnes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Personne> partialUpdatePersonne(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Personne personne
    ) throws URISyntaxException {
        log.debug("REST request to partial update Personne partially : {}, {}", id, personne);
        if (personne.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personne.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Personne> result = personneService.partialUpdate(personne);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personne.getId().toString())
        );
    }

    /**
     * {@code GET  /personnes} : get all the personnes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personnes in body.
     */
    @GetMapping("/personnes")
    public ResponseEntity<List<Personne>> getAllPersonnes(Pageable pageable) {
        log.debug("REST request to get a page of Personnes");
        Page<Personne> page = personneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /personnes/:id} : get the "id" personne.
     *
     * @param id the id of the personne to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personne, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personnes/{id}")
    public ResponseEntity<Personne> getPersonne(@PathVariable Long id) {
        log.debug("REST request to get Personne : {}", id);
        Optional<Personne> personne = personneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personne);
    }

    /**
     * {@code DELETE  /personnes/:id} : delete the "id" personne.
     *
     * @param id the id of the personne to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/personnes/{id}")
    public ResponseEntity<Void> deletePersonne(@PathVariable Long id) {
        log.debug("REST request to delete Personne : {}", id);
        personneService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/personnes?query=:query} : search for the personne corresponding
     * to the query.
     *
     * @param query the query of the personne search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/personnes")
    public ResponseEntity<List<Personne>> searchPersonnes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Personnes for query {}", query);
        Page<Personne> page = personneService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
