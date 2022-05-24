package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Taller;
import com.mycompany.myapp.repository.TallerRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link TallerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TallerResourceIT {

    private static final String DEFAULT_MARCA = "AAAAAAAAAA";
    private static final String UPDATED_MARCA = "BBBBBBBBBB";

    private static final String DEFAULT_MODELO = "AAAAAAAAAA";
    private static final String UPDATED_MODELO = "BBBBBBBBBB";

    private static final String DEFAULT_MATRICULA = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULA = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_NUM_SERIE = "BBBBBBBBBB";

    private static final String DEFAULT_GENERACION = "AAAAAAAAAA";
    private static final String UPDATED_GENERACION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tallers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TallerRepository tallerRepository;

    @Autowired
    private MockMvc restTallerMockMvc;

    private Taller taller;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Taller createEntity() {
        Taller taller = new Taller()
            .marca(DEFAULT_MARCA)
            .modelo(DEFAULT_MODELO)
            .matricula(DEFAULT_MATRICULA)
            .color(DEFAULT_COLOR)
            .numSerie(DEFAULT_NUM_SERIE)
            .generacion(DEFAULT_GENERACION);
        return taller;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Taller createUpdatedEntity() {
        Taller taller = new Taller()
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .matricula(UPDATED_MATRICULA)
            .color(UPDATED_COLOR)
            .numSerie(UPDATED_NUM_SERIE)
            .generacion(UPDATED_GENERACION);
        return taller;
    }

    @BeforeEach
    public void initTest() {
        tallerRepository.deleteAll();
        taller = createEntity();
    }

    @Test
    void createTaller() throws Exception {
        int databaseSizeBeforeCreate = tallerRepository.findAll().size();
        // Create the Taller
        restTallerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taller)))
            .andExpect(status().isCreated());

        // Validate the Taller in the database
        List<Taller> tallerList = tallerRepository.findAll();
        assertThat(tallerList).hasSize(databaseSizeBeforeCreate + 1);
        Taller testTaller = tallerList.get(tallerList.size() - 1);
        assertThat(testTaller.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testTaller.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testTaller.getMatricula()).isEqualTo(DEFAULT_MATRICULA);
        assertThat(testTaller.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testTaller.getNumSerie()).isEqualTo(DEFAULT_NUM_SERIE);
        assertThat(testTaller.getGeneracion()).isEqualTo(DEFAULT_GENERACION);
    }

    @Test
    void createTallerWithExistingId() throws Exception {
        // Create the Taller with an existing ID
        taller.setId("existing_id");

        int databaseSizeBeforeCreate = tallerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTallerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taller)))
            .andExpect(status().isBadRequest());

        // Validate the Taller in the database
        List<Taller> tallerList = tallerRepository.findAll();
        assertThat(tallerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllTallers() throws Exception {
        // Initialize the database
        tallerRepository.save(taller);

        // Get all the tallerList
        restTallerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taller.getId())))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA)))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO)))
            .andExpect(jsonPath("$.[*].matricula").value(hasItem(DEFAULT_MATRICULA)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].numSerie").value(hasItem(DEFAULT_NUM_SERIE)))
            .andExpect(jsonPath("$.[*].generacion").value(hasItem(DEFAULT_GENERACION)));
    }

    @Test
    void getTaller() throws Exception {
        // Initialize the database
        tallerRepository.save(taller);

        // Get the taller
        restTallerMockMvc
            .perform(get(ENTITY_API_URL_ID, taller.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taller.getId()))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO))
            .andExpect(jsonPath("$.matricula").value(DEFAULT_MATRICULA))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.numSerie").value(DEFAULT_NUM_SERIE))
            .andExpect(jsonPath("$.generacion").value(DEFAULT_GENERACION));
    }

    @Test
    void getNonExistingTaller() throws Exception {
        // Get the taller
        restTallerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewTaller() throws Exception {
        // Initialize the database
        tallerRepository.save(taller);

        int databaseSizeBeforeUpdate = tallerRepository.findAll().size();

        // Update the taller
        Taller updatedTaller = tallerRepository.findById(taller.getId()).get();
        updatedTaller
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .matricula(UPDATED_MATRICULA)
            .color(UPDATED_COLOR)
            .numSerie(UPDATED_NUM_SERIE)
            .generacion(UPDATED_GENERACION);

        restTallerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTaller.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTaller))
            )
            .andExpect(status().isOk());

        // Validate the Taller in the database
        List<Taller> tallerList = tallerRepository.findAll();
        assertThat(tallerList).hasSize(databaseSizeBeforeUpdate);
        Taller testTaller = tallerList.get(tallerList.size() - 1);
        assertThat(testTaller.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testTaller.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testTaller.getMatricula()).isEqualTo(UPDATED_MATRICULA);
        assertThat(testTaller.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testTaller.getNumSerie()).isEqualTo(UPDATED_NUM_SERIE);
        assertThat(testTaller.getGeneracion()).isEqualTo(UPDATED_GENERACION);
    }

    @Test
    void putNonExistingTaller() throws Exception {
        int databaseSizeBeforeUpdate = tallerRepository.findAll().size();
        taller.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTallerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taller.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taller))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taller in the database
        List<Taller> tallerList = tallerRepository.findAll();
        assertThat(tallerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTaller() throws Exception {
        int databaseSizeBeforeUpdate = tallerRepository.findAll().size();
        taller.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTallerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taller))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taller in the database
        List<Taller> tallerList = tallerRepository.findAll();
        assertThat(tallerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTaller() throws Exception {
        int databaseSizeBeforeUpdate = tallerRepository.findAll().size();
        taller.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTallerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taller)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Taller in the database
        List<Taller> tallerList = tallerRepository.findAll();
        assertThat(tallerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTallerWithPatch() throws Exception {
        // Initialize the database
        tallerRepository.save(taller);

        int databaseSizeBeforeUpdate = tallerRepository.findAll().size();

        // Update the taller using partial update
        Taller partialUpdatedTaller = new Taller();
        partialUpdatedTaller.setId(taller.getId());

        partialUpdatedTaller.marca(UPDATED_MARCA).numSerie(UPDATED_NUM_SERIE);

        restTallerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaller.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaller))
            )
            .andExpect(status().isOk());

        // Validate the Taller in the database
        List<Taller> tallerList = tallerRepository.findAll();
        assertThat(tallerList).hasSize(databaseSizeBeforeUpdate);
        Taller testTaller = tallerList.get(tallerList.size() - 1);
        assertThat(testTaller.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testTaller.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testTaller.getMatricula()).isEqualTo(DEFAULT_MATRICULA);
        assertThat(testTaller.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testTaller.getNumSerie()).isEqualTo(UPDATED_NUM_SERIE);
        assertThat(testTaller.getGeneracion()).isEqualTo(DEFAULT_GENERACION);
    }

    @Test
    void fullUpdateTallerWithPatch() throws Exception {
        // Initialize the database
        tallerRepository.save(taller);

        int databaseSizeBeforeUpdate = tallerRepository.findAll().size();

        // Update the taller using partial update
        Taller partialUpdatedTaller = new Taller();
        partialUpdatedTaller.setId(taller.getId());

        partialUpdatedTaller
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .matricula(UPDATED_MATRICULA)
            .color(UPDATED_COLOR)
            .numSerie(UPDATED_NUM_SERIE)
            .generacion(UPDATED_GENERACION);

        restTallerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaller.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaller))
            )
            .andExpect(status().isOk());

        // Validate the Taller in the database
        List<Taller> tallerList = tallerRepository.findAll();
        assertThat(tallerList).hasSize(databaseSizeBeforeUpdate);
        Taller testTaller = tallerList.get(tallerList.size() - 1);
        assertThat(testTaller.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testTaller.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testTaller.getMatricula()).isEqualTo(UPDATED_MATRICULA);
        assertThat(testTaller.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testTaller.getNumSerie()).isEqualTo(UPDATED_NUM_SERIE);
        assertThat(testTaller.getGeneracion()).isEqualTo(UPDATED_GENERACION);
    }

    @Test
    void patchNonExistingTaller() throws Exception {
        int databaseSizeBeforeUpdate = tallerRepository.findAll().size();
        taller.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTallerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taller.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taller))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taller in the database
        List<Taller> tallerList = tallerRepository.findAll();
        assertThat(tallerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTaller() throws Exception {
        int databaseSizeBeforeUpdate = tallerRepository.findAll().size();
        taller.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTallerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taller))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taller in the database
        List<Taller> tallerList = tallerRepository.findAll();
        assertThat(tallerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTaller() throws Exception {
        int databaseSizeBeforeUpdate = tallerRepository.findAll().size();
        taller.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTallerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(taller)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Taller in the database
        List<Taller> tallerList = tallerRepository.findAll();
        assertThat(tallerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTaller() throws Exception {
        // Initialize the database
        tallerRepository.save(taller);

        int databaseSizeBeforeDelete = tallerRepository.findAll().size();

        // Delete the taller
        restTallerMockMvc
            .perform(delete(ENTITY_API_URL_ID, taller.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Taller> tallerList = tallerRepository.findAll();
        assertThat(tallerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
