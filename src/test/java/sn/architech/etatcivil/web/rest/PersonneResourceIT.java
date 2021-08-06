package sn.architech.etatcivil.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import sn.architech.etatcivil.domain.Personne;
import sn.architech.etatcivil.domain.enumeration.Sexe;
import sn.architech.etatcivil.repository.PersonneRepository;
import sn.architech.etatcivil.repository.search.PersonneSearchRepository;

/**
 * Integration tests for the {@link PersonneResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PersonneResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FATHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBBBBBBB";

    private static final Sexe DEFAULT_SEXE = Sexe.MASCULIN;
    private static final Sexe UPDATED_SEXE = Sexe.FEMININ;

    private static final String DEFAULT_MOTHER_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHER_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOTHER_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHER_LAST_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_OF_BIRTHDAY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_BIRTHDAY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HOUR_OF_BITHDAY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HOUR_OF_BITHDAY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_NUMBER_REGISTER = 1L;
    private static final Long UPDATED_NUMBER_REGISTER = 2L;

    private static final String ENTITY_API_URL = "/api/personnes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/personnes";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonneRepository personneRepository;

    /**
     * This repository is mocked in the sn.architech.etatcivil.repository.search test package.
     *
     * @see sn.architech.etatcivil.repository.search.PersonneSearchRepositoryMockConfiguration
     */
    @Autowired
    private PersonneSearchRepository mockPersonneSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonneMockMvc;

    private Personne personne;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personne createEntity(EntityManager em) {
        Personne personne = new Personne()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .fatherName(DEFAULT_FATHER_NAME)
            .sexe(DEFAULT_SEXE)
            .motherFirstName(DEFAULT_MOTHER_FIRST_NAME)
            .motherLastName(DEFAULT_MOTHER_LAST_NAME)
            .dateOfBirthday(DEFAULT_DATE_OF_BIRTHDAY)
            .hourOfBithday(DEFAULT_HOUR_OF_BITHDAY)
            .numberRegister(DEFAULT_NUMBER_REGISTER);
        return personne;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personne createUpdatedEntity(EntityManager em) {
        Personne personne = new Personne()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .fatherName(UPDATED_FATHER_NAME)
            .sexe(UPDATED_SEXE)
            .motherFirstName(UPDATED_MOTHER_FIRST_NAME)
            .motherLastName(UPDATED_MOTHER_LAST_NAME)
            .dateOfBirthday(UPDATED_DATE_OF_BIRTHDAY)
            .hourOfBithday(UPDATED_HOUR_OF_BITHDAY)
            .numberRegister(UPDATED_NUMBER_REGISTER);
        return personne;
    }

    @BeforeEach
    public void initTest() {
        personne = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonne() throws Exception {
        int databaseSizeBeforeCreate = personneRepository.findAll().size();
        // Create the Personne
        restPersonneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isCreated());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeCreate + 1);
        Personne testPersonne = personneList.get(personneList.size() - 1);
        assertThat(testPersonne.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPersonne.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPersonne.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testPersonne.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testPersonne.getMotherFirstName()).isEqualTo(DEFAULT_MOTHER_FIRST_NAME);
        assertThat(testPersonne.getMotherLastName()).isEqualTo(DEFAULT_MOTHER_LAST_NAME);
        assertThat(testPersonne.getDateOfBirthday()).isEqualTo(DEFAULT_DATE_OF_BIRTHDAY);
        assertThat(testPersonne.getHourOfBithday()).isEqualTo(DEFAULT_HOUR_OF_BITHDAY);
        assertThat(testPersonne.getNumberRegister()).isEqualTo(DEFAULT_NUMBER_REGISTER);

        // Validate the Personne in Elasticsearch
        verify(mockPersonneSearchRepository, times(1)).save(testPersonne);
    }

    @Test
    @Transactional
    void createPersonneWithExistingId() throws Exception {
        // Create the Personne with an existing ID
        personne.setId(1L);

        int databaseSizeBeforeCreate = personneRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isBadRequest());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeCreate);

        // Validate the Personne in Elasticsearch
        verify(mockPersonneSearchRepository, times(0)).save(personne);
    }

    @Test
    @Transactional
    void getAllPersonnes() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        // Get all the personneList
        restPersonneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personne.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].motherFirstName").value(hasItem(DEFAULT_MOTHER_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].motherLastName").value(hasItem(DEFAULT_MOTHER_LAST_NAME)))
            .andExpect(jsonPath("$.[*].dateOfBirthday").value(hasItem(DEFAULT_DATE_OF_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].hourOfBithday").value(hasItem(DEFAULT_HOUR_OF_BITHDAY.toString())))
            .andExpect(jsonPath("$.[*].numberRegister").value(hasItem(DEFAULT_NUMBER_REGISTER.intValue())));
    }

    @Test
    @Transactional
    void getPersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        // Get the personne
        restPersonneMockMvc
            .perform(get(ENTITY_API_URL_ID, personne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personne.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.motherFirstName").value(DEFAULT_MOTHER_FIRST_NAME))
            .andExpect(jsonPath("$.motherLastName").value(DEFAULT_MOTHER_LAST_NAME))
            .andExpect(jsonPath("$.dateOfBirthday").value(DEFAULT_DATE_OF_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.hourOfBithday").value(DEFAULT_HOUR_OF_BITHDAY.toString()))
            .andExpect(jsonPath("$.numberRegister").value(DEFAULT_NUMBER_REGISTER.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPersonne() throws Exception {
        // Get the personne
        restPersonneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        int databaseSizeBeforeUpdate = personneRepository.findAll().size();

        // Update the personne
        Personne updatedPersonne = personneRepository.findById(personne.getId()).get();
        // Disconnect from session so that the updates on updatedPersonne are not directly saved in db
        em.detach(updatedPersonne);
        updatedPersonne
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .fatherName(UPDATED_FATHER_NAME)
            .sexe(UPDATED_SEXE)
            .motherFirstName(UPDATED_MOTHER_FIRST_NAME)
            .motherLastName(UPDATED_MOTHER_LAST_NAME)
            .dateOfBirthday(UPDATED_DATE_OF_BIRTHDAY)
            .hourOfBithday(UPDATED_HOUR_OF_BITHDAY)
            .numberRegister(UPDATED_NUMBER_REGISTER);

        restPersonneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPersonne.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPersonne))
            )
            .andExpect(status().isOk());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
        Personne testPersonne = personneList.get(personneList.size() - 1);
        assertThat(testPersonne.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPersonne.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPersonne.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testPersonne.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testPersonne.getMotherFirstName()).isEqualTo(UPDATED_MOTHER_FIRST_NAME);
        assertThat(testPersonne.getMotherLastName()).isEqualTo(UPDATED_MOTHER_LAST_NAME);
        assertThat(testPersonne.getDateOfBirthday()).isEqualTo(UPDATED_DATE_OF_BIRTHDAY);
        assertThat(testPersonne.getHourOfBithday()).isEqualTo(UPDATED_HOUR_OF_BITHDAY);
        assertThat(testPersonne.getNumberRegister()).isEqualTo(UPDATED_NUMBER_REGISTER);

        // Validate the Personne in Elasticsearch
        verify(mockPersonneSearchRepository).save(testPersonne);
    }

    @Test
    @Transactional
    void putNonExistingPersonne() throws Exception {
        int databaseSizeBeforeUpdate = personneRepository.findAll().size();
        personne.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personne.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personne))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Personne in Elasticsearch
        verify(mockPersonneSearchRepository, times(0)).save(personne);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonne() throws Exception {
        int databaseSizeBeforeUpdate = personneRepository.findAll().size();
        personne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personne))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Personne in Elasticsearch
        verify(mockPersonneSearchRepository, times(0)).save(personne);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonne() throws Exception {
        int databaseSizeBeforeUpdate = personneRepository.findAll().size();
        personne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonneMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Personne in Elasticsearch
        verify(mockPersonneSearchRepository, times(0)).save(personne);
    }

    @Test
    @Transactional
    void partialUpdatePersonneWithPatch() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        int databaseSizeBeforeUpdate = personneRepository.findAll().size();

        // Update the personne using partial update
        Personne partialUpdatedPersonne = new Personne();
        partialUpdatedPersonne.setId(personne.getId());

        partialUpdatedPersonne
            .lastName(UPDATED_LAST_NAME)
            .sexe(UPDATED_SEXE)
            .dateOfBirthday(UPDATED_DATE_OF_BIRTHDAY)
            .hourOfBithday(UPDATED_HOUR_OF_BITHDAY);

        restPersonneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonne.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonne))
            )
            .andExpect(status().isOk());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
        Personne testPersonne = personneList.get(personneList.size() - 1);
        assertThat(testPersonne.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPersonne.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPersonne.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testPersonne.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testPersonne.getMotherFirstName()).isEqualTo(DEFAULT_MOTHER_FIRST_NAME);
        assertThat(testPersonne.getMotherLastName()).isEqualTo(DEFAULT_MOTHER_LAST_NAME);
        assertThat(testPersonne.getDateOfBirthday()).isEqualTo(UPDATED_DATE_OF_BIRTHDAY);
        assertThat(testPersonne.getHourOfBithday()).isEqualTo(UPDATED_HOUR_OF_BITHDAY);
        assertThat(testPersonne.getNumberRegister()).isEqualTo(DEFAULT_NUMBER_REGISTER);
    }

    @Test
    @Transactional
    void fullUpdatePersonneWithPatch() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        int databaseSizeBeforeUpdate = personneRepository.findAll().size();

        // Update the personne using partial update
        Personne partialUpdatedPersonne = new Personne();
        partialUpdatedPersonne.setId(personne.getId());

        partialUpdatedPersonne
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .fatherName(UPDATED_FATHER_NAME)
            .sexe(UPDATED_SEXE)
            .motherFirstName(UPDATED_MOTHER_FIRST_NAME)
            .motherLastName(UPDATED_MOTHER_LAST_NAME)
            .dateOfBirthday(UPDATED_DATE_OF_BIRTHDAY)
            .hourOfBithday(UPDATED_HOUR_OF_BITHDAY)
            .numberRegister(UPDATED_NUMBER_REGISTER);

        restPersonneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonne.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonne))
            )
            .andExpect(status().isOk());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
        Personne testPersonne = personneList.get(personneList.size() - 1);
        assertThat(testPersonne.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPersonne.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPersonne.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testPersonne.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testPersonne.getMotherFirstName()).isEqualTo(UPDATED_MOTHER_FIRST_NAME);
        assertThat(testPersonne.getMotherLastName()).isEqualTo(UPDATED_MOTHER_LAST_NAME);
        assertThat(testPersonne.getDateOfBirthday()).isEqualTo(UPDATED_DATE_OF_BIRTHDAY);
        assertThat(testPersonne.getHourOfBithday()).isEqualTo(UPDATED_HOUR_OF_BITHDAY);
        assertThat(testPersonne.getNumberRegister()).isEqualTo(UPDATED_NUMBER_REGISTER);
    }

    @Test
    @Transactional
    void patchNonExistingPersonne() throws Exception {
        int databaseSizeBeforeUpdate = personneRepository.findAll().size();
        personne.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personne.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personne))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Personne in Elasticsearch
        verify(mockPersonneSearchRepository, times(0)).save(personne);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonne() throws Exception {
        int databaseSizeBeforeUpdate = personneRepository.findAll().size();
        personne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personne))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Personne in Elasticsearch
        verify(mockPersonneSearchRepository, times(0)).save(personne);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonne() throws Exception {
        int databaseSizeBeforeUpdate = personneRepository.findAll().size();
        personne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonneMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Personne in Elasticsearch
        verify(mockPersonneSearchRepository, times(0)).save(personne);
    }

    @Test
    @Transactional
    void deletePersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        int databaseSizeBeforeDelete = personneRepository.findAll().size();

        // Delete the personne
        restPersonneMockMvc
            .perform(delete(ENTITY_API_URL_ID, personne.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Personne in Elasticsearch
        verify(mockPersonneSearchRepository, times(1)).deleteById(personne.getId());
    }

    @Test
    @Transactional
    void searchPersonne() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        personneRepository.saveAndFlush(personne);
        when(mockPersonneSearchRepository.search(queryStringQuery("id:" + personne.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(personne), PageRequest.of(0, 1), 1));

        // Search the personne
        restPersonneMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + personne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personne.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].motherFirstName").value(hasItem(DEFAULT_MOTHER_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].motherLastName").value(hasItem(DEFAULT_MOTHER_LAST_NAME)))
            .andExpect(jsonPath("$.[*].dateOfBirthday").value(hasItem(DEFAULT_DATE_OF_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].hourOfBithday").value(hasItem(DEFAULT_HOUR_OF_BITHDAY.toString())))
            .andExpect(jsonPath("$.[*].numberRegister").value(hasItem(DEFAULT_NUMBER_REGISTER.intValue())));
    }
}
