package sn.architech.etatcivil.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import sn.architech.etatcivil.IntegrationTest;
import sn.architech.etatcivil.domain.Arrondissement;
import sn.architech.etatcivil.repository.ArrondissementRepository;
import sn.architech.etatcivil.repository.search.ArrondissementSearchRepository;

/**
 * Integration tests for the {@link ArrondissementResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ArrondissementResourceIT {

    private static final Integer DEFAULT_ARRONDISSEMENT_CODE = 1;
    private static final Integer UPDATED_ARRONDISSEMENT_CODE = 2;

    private static final String DEFAULT_ARRONDISSEMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ARRONDISSEMENT_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/arrondissements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/arrondissements";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArrondissementRepository arrondissementRepository;

    /**
     * This repository is mocked in the sn.architech.etatcivil.repository.search test package.
     *
     * @see sn.architech.etatcivil.repository.search.ArrondissementSearchRepositoryMockConfiguration
     */
    @Autowired
    private ArrondissementSearchRepository mockArrondissementSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArrondissementMockMvc;

    private Arrondissement arrondissement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arrondissement createEntity(EntityManager em) {
        Arrondissement arrondissement = new Arrondissement()
            .arrondissementCode(DEFAULT_ARRONDISSEMENT_CODE)
            .arrondissementName(DEFAULT_ARRONDISSEMENT_NAME);
        return arrondissement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arrondissement createUpdatedEntity(EntityManager em) {
        Arrondissement arrondissement = new Arrondissement()
            .arrondissementCode(UPDATED_ARRONDISSEMENT_CODE)
            .arrondissementName(UPDATED_ARRONDISSEMENT_NAME);
        return arrondissement;
    }

    @BeforeEach
    public void initTest() {
        arrondissement = createEntity(em);
    }

    @Test
    @Transactional
    void createArrondissement() throws Exception {
        int databaseSizeBeforeCreate = arrondissementRepository.findAll().size();
        // Create the Arrondissement
        restArrondissementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(arrondissement))
            )
            .andExpect(status().isCreated());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeCreate + 1);
        Arrondissement testArrondissement = arrondissementList.get(arrondissementList.size() - 1);
        assertThat(testArrondissement.getArrondissementCode()).isEqualTo(DEFAULT_ARRONDISSEMENT_CODE);
        assertThat(testArrondissement.getArrondissementName()).isEqualTo(DEFAULT_ARRONDISSEMENT_NAME);

        // Validate the Arrondissement in Elasticsearch
        verify(mockArrondissementSearchRepository, times(1)).save(testArrondissement);
    }

    @Test
    @Transactional
    void createArrondissementWithExistingId() throws Exception {
        // Create the Arrondissement with an existing ID
        arrondissement.setId(1L);

        int databaseSizeBeforeCreate = arrondissementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArrondissementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(arrondissement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeCreate);

        // Validate the Arrondissement in Elasticsearch
        verify(mockArrondissementSearchRepository, times(0)).save(arrondissement);
    }

    @Test
    @Transactional
    void checkArrondissementNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = arrondissementRepository.findAll().size();
        // set the field null
        arrondissement.setArrondissementName(null);

        // Create the Arrondissement, which fails.

        restArrondissementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(arrondissement))
            )
            .andExpect(status().isBadRequest());

        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArrondissements() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        // Get all the arrondissementList
        restArrondissementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arrondissement.getId().intValue())))
            .andExpect(jsonPath("$.[*].arrondissementCode").value(hasItem(DEFAULT_ARRONDISSEMENT_CODE)))
            .andExpect(jsonPath("$.[*].arrondissementName").value(hasItem(DEFAULT_ARRONDISSEMENT_NAME)));
    }

    @Test
    @Transactional
    void getArrondissement() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        // Get the arrondissement
        restArrondissementMockMvc
            .perform(get(ENTITY_API_URL_ID, arrondissement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(arrondissement.getId().intValue()))
            .andExpect(jsonPath("$.arrondissementCode").value(DEFAULT_ARRONDISSEMENT_CODE))
            .andExpect(jsonPath("$.arrondissementName").value(DEFAULT_ARRONDISSEMENT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingArrondissement() throws Exception {
        // Get the arrondissement
        restArrondissementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewArrondissement() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        int databaseSizeBeforeUpdate = arrondissementRepository.findAll().size();

        // Update the arrondissement
        Arrondissement updatedArrondissement = arrondissementRepository.findById(arrondissement.getId()).get();
        // Disconnect from session so that the updates on updatedArrondissement are not directly saved in db
        em.detach(updatedArrondissement);
        updatedArrondissement.arrondissementCode(UPDATED_ARRONDISSEMENT_CODE).arrondissementName(UPDATED_ARRONDISSEMENT_NAME);

        restArrondissementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedArrondissement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedArrondissement))
            )
            .andExpect(status().isOk());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeUpdate);
        Arrondissement testArrondissement = arrondissementList.get(arrondissementList.size() - 1);
        assertThat(testArrondissement.getArrondissementCode()).isEqualTo(UPDATED_ARRONDISSEMENT_CODE);
        assertThat(testArrondissement.getArrondissementName()).isEqualTo(UPDATED_ARRONDISSEMENT_NAME);

        // Validate the Arrondissement in Elasticsearch
        verify(mockArrondissementSearchRepository).save(testArrondissement);
    }

    @Test
    @Transactional
    void putNonExistingArrondissement() throws Exception {
        int databaseSizeBeforeUpdate = arrondissementRepository.findAll().size();
        arrondissement.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArrondissementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, arrondissement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arrondissement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Arrondissement in Elasticsearch
        verify(mockArrondissementSearchRepository, times(0)).save(arrondissement);
    }

    @Test
    @Transactional
    void putWithIdMismatchArrondissement() throws Exception {
        int databaseSizeBeforeUpdate = arrondissementRepository.findAll().size();
        arrondissement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArrondissementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arrondissement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Arrondissement in Elasticsearch
        verify(mockArrondissementSearchRepository, times(0)).save(arrondissement);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArrondissement() throws Exception {
        int databaseSizeBeforeUpdate = arrondissementRepository.findAll().size();
        arrondissement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArrondissementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(arrondissement)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Arrondissement in Elasticsearch
        verify(mockArrondissementSearchRepository, times(0)).save(arrondissement);
    }

    @Test
    @Transactional
    void partialUpdateArrondissementWithPatch() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        int databaseSizeBeforeUpdate = arrondissementRepository.findAll().size();

        // Update the arrondissement using partial update
        Arrondissement partialUpdatedArrondissement = new Arrondissement();
        partialUpdatedArrondissement.setId(arrondissement.getId());

        partialUpdatedArrondissement.arrondissementName(UPDATED_ARRONDISSEMENT_NAME);

        restArrondissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArrondissement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArrondissement))
            )
            .andExpect(status().isOk());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeUpdate);
        Arrondissement testArrondissement = arrondissementList.get(arrondissementList.size() - 1);
        assertThat(testArrondissement.getArrondissementCode()).isEqualTo(DEFAULT_ARRONDISSEMENT_CODE);
        assertThat(testArrondissement.getArrondissementName()).isEqualTo(UPDATED_ARRONDISSEMENT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateArrondissementWithPatch() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        int databaseSizeBeforeUpdate = arrondissementRepository.findAll().size();

        // Update the arrondissement using partial update
        Arrondissement partialUpdatedArrondissement = new Arrondissement();
        partialUpdatedArrondissement.setId(arrondissement.getId());

        partialUpdatedArrondissement.arrondissementCode(UPDATED_ARRONDISSEMENT_CODE).arrondissementName(UPDATED_ARRONDISSEMENT_NAME);

        restArrondissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArrondissement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArrondissement))
            )
            .andExpect(status().isOk());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeUpdate);
        Arrondissement testArrondissement = arrondissementList.get(arrondissementList.size() - 1);
        assertThat(testArrondissement.getArrondissementCode()).isEqualTo(UPDATED_ARRONDISSEMENT_CODE);
        assertThat(testArrondissement.getArrondissementName()).isEqualTo(UPDATED_ARRONDISSEMENT_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingArrondissement() throws Exception {
        int databaseSizeBeforeUpdate = arrondissementRepository.findAll().size();
        arrondissement.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArrondissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, arrondissement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(arrondissement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Arrondissement in Elasticsearch
        verify(mockArrondissementSearchRepository, times(0)).save(arrondissement);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArrondissement() throws Exception {
        int databaseSizeBeforeUpdate = arrondissementRepository.findAll().size();
        arrondissement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArrondissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(arrondissement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Arrondissement in Elasticsearch
        verify(mockArrondissementSearchRepository, times(0)).save(arrondissement);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArrondissement() throws Exception {
        int databaseSizeBeforeUpdate = arrondissementRepository.findAll().size();
        arrondissement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArrondissementMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(arrondissement))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Arrondissement in Elasticsearch
        verify(mockArrondissementSearchRepository, times(0)).save(arrondissement);
    }

    @Test
    @Transactional
    void deleteArrondissement() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        int databaseSizeBeforeDelete = arrondissementRepository.findAll().size();

        // Delete the arrondissement
        restArrondissementMockMvc
            .perform(delete(ENTITY_API_URL_ID, arrondissement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Arrondissement in Elasticsearch
        verify(mockArrondissementSearchRepository, times(1)).deleteById(arrondissement.getId());
    }

    @Test
    @Transactional
    void searchArrondissement() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);
        when(mockArrondissementSearchRepository.search(queryStringQuery("id:" + arrondissement.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(arrondissement), PageRequest.of(0, 1), 1));

        // Search the arrondissement
        restArrondissementMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + arrondissement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arrondissement.getId().intValue())))
            .andExpect(jsonPath("$.[*].arrondissementCode").value(hasItem(DEFAULT_ARRONDISSEMENT_CODE)))
            .andExpect(jsonPath("$.[*].arrondissementName").value(hasItem(DEFAULT_ARRONDISSEMENT_NAME)));
    }
}
