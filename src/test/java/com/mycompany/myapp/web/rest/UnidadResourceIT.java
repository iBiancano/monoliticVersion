package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Unidad;
import com.mycompany.myapp.repository.UnidadRepository;
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
 * Integration tests for the {@link UnidadResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UnidadResourceIT {

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

    private static final String ENTITY_API_URL = "/api/unidads";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private UnidadRepository unidadRepository;

    @Autowired
    private MockMvc restUnidadMockMvc;

    private Unidad unidad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Unidad createEntity() {
        Unidad unidad = new Unidad()
            .marca(DEFAULT_MARCA)
            .modelo(DEFAULT_MODELO)
            .matricula(DEFAULT_MATRICULA)
            .color(DEFAULT_COLOR)
            .numSerie(DEFAULT_NUM_SERIE)
            .generacion(DEFAULT_GENERACION);
        return unidad;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Unidad createUpdatedEntity() {
        Unidad unidad = new Unidad()
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .matricula(UPDATED_MATRICULA)
            .color(UPDATED_COLOR)
            .numSerie(UPDATED_NUM_SERIE)
            .generacion(UPDATED_GENERACION);
        return unidad;
    }

    @BeforeEach
    public void initTest() {
        unidadRepository.deleteAll();
        unidad = createEntity();
    }

    @Test
    void createUnidad() throws Exception {
        int databaseSizeBeforeCreate = unidadRepository.findAll().size();
        // Create the Unidad
        restUnidadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidad)))
            .andExpect(status().isCreated());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeCreate + 1);
        Unidad testUnidad = unidadList.get(unidadList.size() - 1);
        assertThat(testUnidad.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testUnidad.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testUnidad.getMatricula()).isEqualTo(DEFAULT_MATRICULA);
        assertThat(testUnidad.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testUnidad.getNumSerie()).isEqualTo(DEFAULT_NUM_SERIE);
        assertThat(testUnidad.getGeneracion()).isEqualTo(DEFAULT_GENERACION);
    }

    @Test
    void createUnidadWithExistingId() throws Exception {
        // Create the Unidad with an existing ID
        unidad.setId("existing_id");

        int databaseSizeBeforeCreate = unidadRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidad)))
            .andExpect(status().isBadRequest());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllUnidads() throws Exception {
        // Initialize the database
        unidadRepository.save(unidad);

        // Get all the unidadList
        restUnidadMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidad.getId())))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA)))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO)))
            .andExpect(jsonPath("$.[*].matricula").value(hasItem(DEFAULT_MATRICULA)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].numSerie").value(hasItem(DEFAULT_NUM_SERIE)))
            .andExpect(jsonPath("$.[*].generacion").value(hasItem(DEFAULT_GENERACION)));
    }

    @Test
    void getUnidad() throws Exception {
        // Initialize the database
        unidadRepository.save(unidad);

        // Get the unidad
        restUnidadMockMvc
            .perform(get(ENTITY_API_URL_ID, unidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unidad.getId()))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO))
            .andExpect(jsonPath("$.matricula").value(DEFAULT_MATRICULA))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.numSerie").value(DEFAULT_NUM_SERIE))
            .andExpect(jsonPath("$.generacion").value(DEFAULT_GENERACION));
    }

    @Test
    void getNonExistingUnidad() throws Exception {
        // Get the unidad
        restUnidadMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewUnidad() throws Exception {
        // Initialize the database
        unidadRepository.save(unidad);

        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();

        // Update the unidad
        Unidad updatedUnidad = unidadRepository.findById(unidad.getId()).get();
        updatedUnidad
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .matricula(UPDATED_MATRICULA)
            .color(UPDATED_COLOR)
            .numSerie(UPDATED_NUM_SERIE)
            .generacion(UPDATED_GENERACION);

        restUnidadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUnidad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUnidad))
            )
            .andExpect(status().isOk());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate);
        Unidad testUnidad = unidadList.get(unidadList.size() - 1);
        assertThat(testUnidad.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testUnidad.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testUnidad.getMatricula()).isEqualTo(UPDATED_MATRICULA);
        assertThat(testUnidad.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testUnidad.getNumSerie()).isEqualTo(UPDATED_NUM_SERIE);
        assertThat(testUnidad.getGeneracion()).isEqualTo(UPDATED_GENERACION);
    }

    @Test
    void putNonExistingUnidad() throws Exception {
        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();
        unidad.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, unidad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchUnidad() throws Exception {
        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();
        unidad.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamUnidad() throws Exception {
        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();
        unidad.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidad)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateUnidadWithPatch() throws Exception {
        // Initialize the database
        unidadRepository.save(unidad);

        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();

        // Update the unidad using partial update
        Unidad partialUpdatedUnidad = new Unidad();
        partialUpdatedUnidad.setId(unidad.getId());

        partialUpdatedUnidad.modelo(UPDATED_MODELO).matricula(UPDATED_MATRICULA).numSerie(UPDATED_NUM_SERIE);

        restUnidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnidad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnidad))
            )
            .andExpect(status().isOk());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate);
        Unidad testUnidad = unidadList.get(unidadList.size() - 1);
        assertThat(testUnidad.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testUnidad.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testUnidad.getMatricula()).isEqualTo(UPDATED_MATRICULA);
        assertThat(testUnidad.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testUnidad.getNumSerie()).isEqualTo(UPDATED_NUM_SERIE);
        assertThat(testUnidad.getGeneracion()).isEqualTo(DEFAULT_GENERACION);
    }

    @Test
    void fullUpdateUnidadWithPatch() throws Exception {
        // Initialize the database
        unidadRepository.save(unidad);

        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();

        // Update the unidad using partial update
        Unidad partialUpdatedUnidad = new Unidad();
        partialUpdatedUnidad.setId(unidad.getId());

        partialUpdatedUnidad
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .matricula(UPDATED_MATRICULA)
            .color(UPDATED_COLOR)
            .numSerie(UPDATED_NUM_SERIE)
            .generacion(UPDATED_GENERACION);

        restUnidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnidad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnidad))
            )
            .andExpect(status().isOk());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate);
        Unidad testUnidad = unidadList.get(unidadList.size() - 1);
        assertThat(testUnidad.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testUnidad.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testUnidad.getMatricula()).isEqualTo(UPDATED_MATRICULA);
        assertThat(testUnidad.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testUnidad.getNumSerie()).isEqualTo(UPDATED_NUM_SERIE);
        assertThat(testUnidad.getGeneracion()).isEqualTo(UPDATED_GENERACION);
    }

    @Test
    void patchNonExistingUnidad() throws Exception {
        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();
        unidad.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, unidad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchUnidad() throws Exception {
        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();
        unidad.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamUnidad() throws Exception {
        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();
        unidad.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(unidad)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteUnidad() throws Exception {
        // Initialize the database
        unidadRepository.save(unidad);

        int databaseSizeBeforeDelete = unidadRepository.findAll().size();

        // Delete the unidad
        restUnidadMockMvc
            .perform(delete(ENTITY_API_URL_ID, unidad.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
