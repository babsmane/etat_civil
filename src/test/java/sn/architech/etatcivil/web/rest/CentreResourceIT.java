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
import sn.architech.etatcivil.domain.Centre;
import sn.architech.etatcivil.repository.CentreRepository;
import sn.architech.etatcivil.repository.search.CentreSearchRepository;

/**
 * Integration tests for the {@link CentreResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CentreResourceIT {

    private static final String DEFAULT_CENTRE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CENTRE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CENTRE_CHIEF = "AAAAAAAAAA";
    private static final String UPDATED_CENTRE_CHIEF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/centres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/centres";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CentreRepository centreRepository;

    /**
     * This repository is mocked in the sn.architech.etatcivil.repository.search test package.
     *
     * @see sn.architech.etatcivil.repository.search.CentreSearchRepositoryMockConfiguration
     */
    @Autowired
    private CentreSearchRepository mockCentreSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCentreMockMvc;

    private Centre centre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Centre createEntity(EntityManager em) {
        Centre centre = new Centre().centreName(DEFAULT_CENTRE_NAME).centreChief(DEFAULT_CENTRE_CHIEF);
        return centre;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Centre createUpdatedEntity(EntityManager em) {
        Centre centre = new Centre().centreName(UPDATED_CENTRE_NAME).centreChief(UPDATED_CENTRE_CHIEF);
        return centre;
    }

    @BeforeEach
    public void initTest() {
        centre = createEntity(em);
    }

    @Test
    @Transactional
    void createCentre() throws Exception {
        int databaseSizeBeforeCreate = centreRepository.findAll().size();
        // Create the Centre
        restCentreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(centre)))
            .andExpect(status().isCreated());

        // Validate the Centre in the database
        List<Centre> centreList = centreRepository.findAll();
        assertThat(centreList).hasSize(databaseSizeBeforeCreate + 1);
        Centre testCentre = centreList.get(centreList.size() - 1);
        assertThat(testCentre.getCentreName()).isEqualTo(DEFAULT_CENTRE_NAME);
        assertThat(testCentre.getCentreChief()).isEqualTo(DEFAULT_CENTRE_CHIEF);

        // Validate the Centre in Elasticsearch
        verify(mockCentreSearchRepository, times(1)).save(testCentre);
    }

    @Test
    @Transactional
    void createCentreWithExistingId() throws Exception {
        // Create the Centre with an existing ID
        centre.setId(1L);

        int databaseSizeBeforeCreate = centreRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCentreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(centre)))
            .andExpect(status().isBadRequest());

        // Validate the Centre in the database
        List<Centre> centreList = centreRepository.findAll();
        assertThat(centreList).hasSize(databaseSizeBeforeCreate);

        // Validate the Centre in Elasticsearch
        verify(mockCentreSearchRepository, times(0)).save(centre);
    }

    @Test
    @Transactional
    void getAllCentres() throws Exception {
        // Initialize the database
        centreRepository.saveAndFlush(centre);

        // Get all the centreList
        restCentreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centre.getId().intValue())))
            .andExpect(jsonPath("$.[*].centreName").value(hasItem(DEFAULT_CENTRE_NAME)))
            .andExpect(jsonPath("$.[*].centreChief").value(hasItem(DEFAULT_CENTRE_CHIEF)));
    }

    @Test
    @Transactional
    void getCentre() throws Exception {
        // Initialize the database
        centreRepository.saveAndFlush(centre);

        // Get the centre
        restCentreMockMvc
            .perform(get(ENTITY_API_URL_ID, centre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(centre.getId().intValue()))
            .andExpect(jsonPath("$.centreName").value(DEFAULT_CENTRE_NAME))
            .andExpect(jsonPath("$.centreChief").value(DEFAULT_CENTRE_CHIEF));
    }

    @Test
    @Transactional
    void getNonExistingCentre() throws Exception {
        // Get the centre
        restCentreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCentre() throws Exception {
        // Initialize the database
        centreRepository.saveAndFlush(centre);

        int databaseSizeBeforeUpdate = centreRepository.findAll().size();

        // Update the centre
        Centre updatedCentre = centreRepository.findById(centre.getId()).get();
        // Disconnect from session so that the updates on updatedCentre are not directly saved in db
        em.detach(updatedCentre);
        updatedCentre.centreName(UPDATED_CENTRE_NAME).centreChief(UPDATED_CENTRE_CHIEF);

        restCentreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCentre.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCentre))
            )
            .andExpect(status().isOk());

        // Validate the Centre in the database
        List<Centre> centreList = centreRepository.findAll();
        assertThat(centreList).hasSize(databaseSizeBeforeUpdate);
        Centre testCentre = centreList.get(centreList.size() - 1);
        assertThat(testCentre.getCentreName()).isEqualTo(UPDATED_CENTRE_NAME);
        assertThat(testCentre.getCentreChief()).isEqualTo(UPDATED_CENTRE_CHIEF);

        // Validate the Centre in Elasticsearch
        verify(mockCentreSearchRepository).save(testCentre);
    }

    @Test
    @Transactional
    void putNonExistingCentre() throws Exception {
        int databaseSizeBeforeUpdate = centreRepository.findAll().size();
        centre.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, centre.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(centre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Centre in the database
        List<Centre> centreList = centreRepository.findAll();
        assertThat(centreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Centre in Elasticsearch
        verify(mockCentreSearchRepository, times(0)).save(centre);
    }

    @Test
    @Transactional
    void putWithIdMismatchCentre() throws Exception {
        int databaseSizeBeforeUpdate = centreRepository.findAll().size();
        centre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(centre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Centre in the database
        List<Centre> centreList = centreRepository.findAll();
        assertThat(centreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Centre in Elasticsearch
        verify(mockCentreSearchRepository, times(0)).save(centre);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCentre() throws Exception {
        int databaseSizeBeforeUpdate = centreRepository.findAll().size();
        centre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentreMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(centre)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Centre in the database
        List<Centre> centreList = centreRepository.findAll();
        assertThat(centreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Centre in Elasticsearch
        verify(mockCentreSearchRepository, times(0)).save(centre);
    }

    @Test
    @Transactional
    void partialUpdateCentreWithPatch() throws Exception {
        // Initialize the database
        centreRepository.saveAndFlush(centre);

        int databaseSizeBeforeUpdate = centreRepository.findAll().size();

        // Update the centre using partial update
        Centre partialUpdatedCentre = new Centre();
        partialUpdatedCentre.setId(centre.getId());

        restCentreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCentre))
            )
            .andExpect(status().isOk());

        // Validate the Centre in the database
        List<Centre> centreList = centreRepository.findAll();
        assertThat(centreList).hasSize(databaseSizeBeforeUpdate);
        Centre testCentre = centreList.get(centreList.size() - 1);
        assertThat(testCentre.getCentreName()).isEqualTo(DEFAULT_CENTRE_NAME);
        assertThat(testCentre.getCentreChief()).isEqualTo(DEFAULT_CENTRE_CHIEF);
    }

    @Test
    @Transactional
    void fullUpdateCentreWithPatch() throws Exception {
        // Initialize the database
        centreRepository.saveAndFlush(centre);

        int databaseSizeBeforeUpdate = centreRepository.findAll().size();

        // Update the centre using partial update
        Centre partialUpdatedCentre = new Centre();
        partialUpdatedCentre.setId(centre.getId());

        partialUpdatedCentre.centreName(UPDATED_CENTRE_NAME).centreChief(UPDATED_CENTRE_CHIEF);

        restCentreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCentre))
            )
            .andExpect(status().isOk());

        // Validate the Centre in the database
        List<Centre> centreList = centreRepository.findAll();
        assertThat(centreList).hasSize(databaseSizeBeforeUpdate);
        Centre testCentre = centreList.get(centreList.size() - 1);
        assertThat(testCentre.getCentreName()).isEqualTo(UPDATED_CENTRE_NAME);
        assertThat(testCentre.getCentreChief()).isEqualTo(UPDATED_CENTRE_CHIEF);
    }

    @Test
    @Transactional
    void patchNonExistingCentre() throws Exception {
        int databaseSizeBeforeUpdate = centreRepository.findAll().size();
        centre.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, centre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(centre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Centre in the database
        List<Centre> centreList = centreRepository.findAll();
        assertThat(centreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Centre in Elasticsearch
        verify(mockCentreSearchRepository, times(0)).save(centre);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCentre() throws Exception {
        int databaseSizeBeforeUpdate = centreRepository.findAll().size();
        centre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(centre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Centre in the database
        List<Centre> centreList = centreRepository.findAll();
        assertThat(centreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Centre in Elasticsearch
        verify(mockCentreSearchRepository, times(0)).save(centre);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCentre() throws Exception {
        int databaseSizeBeforeUpdate = centreRepository.findAll().size();
        centre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentreMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(centre)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Centre in the database
        List<Centre> centreList = centreRepository.findAll();
        assertThat(centreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Centre in Elasticsearch
        verify(mockCentreSearchRepository, times(0)).save(centre);
    }

    @Test
    @Transactional
    void deleteCentre() throws Exception {
        // Initialize the database
        centreRepository.saveAndFlush(centre);

        int databaseSizeBeforeDelete = centreRepository.findAll().size();

        // Delete the centre
        restCentreMockMvc
            .perform(delete(ENTITY_API_URL_ID, centre.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Centre> centreList = centreRepository.findAll();
        assertThat(centreList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Centre in Elasticsearch
        verify(mockCentreSearchRepository, times(1)).deleteById(centre.getId());
    }

    @Test
    @Transactional
    void searchCentre() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        centreRepository.saveAndFlush(centre);
        when(mockCentreSearchRepository.search(queryStringQuery("id:" + centre.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(centre), PageRequest.of(0, 1), 1));

        // Search the centre
        restCentreMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + centre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centre.getId().intValue())))
            .andExpect(jsonPath("$.[*].centreName").value(hasItem(DEFAULT_CENTRE_NAME)))
            .andExpect(jsonPath("$.[*].centreChief").value(hasItem(DEFAULT_CENTRE_CHIEF)));
    }
}
