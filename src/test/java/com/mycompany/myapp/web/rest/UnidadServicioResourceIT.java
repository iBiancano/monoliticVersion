package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.UnidadServicio;
import com.mycompany.myapp.repository.UnidadServicioRepository;
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
 * Integration tests for the {@link UnidadServicioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UnidadServicioResourceIT {

    private static final String DEFAULT_FECHA = "AAAAAAAAAA";
    private static final String UPDATED_FECHA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/unidad-servicios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private UnidadServicioRepository unidadServicioRepository;

    @Autowired
    private MockMvc restUnidadServicioMockMvc;

    private UnidadServicio unidadServicio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadServicio createEntity() {
        UnidadServicio unidadServicio = new UnidadServicio().fecha(DEFAULT_FECHA);
        return unidadServicio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadServicio createUpdatedEntity() {
        UnidadServicio unidadServicio = new UnidadServicio().fecha(UPDATED_FECHA);
        return unidadServicio;
    }

    @BeforeEach
    public void initTest() {
        unidadServicioRepository.deleteAll();
        unidadServicio = createEntity();
    }

    @Test
    void createUnidadServicio() throws Exception {
        int databaseSizeBeforeCreate = unidadServicioRepository.findAll().size();
        // Create the UnidadServicio
        restUnidadServicioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidadServicio))
            )
            .andExpect(status().isCreated());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadServicio testUnidadServicio = unidadServicioList.get(unidadServicioList.size() - 1);
        assertThat(testUnidadServicio.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    void createUnidadServicioWithExistingId() throws Exception {
        // Create the UnidadServicio with an existing ID
        unidadServicio.setId("existing_id");

        int databaseSizeBeforeCreate = unidadServicioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadServicioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidadServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllUnidadServicios() throws Exception {
        // Initialize the database
        unidadServicioRepository.save(unidadServicio);

        // Get all the unidadServicioList
        restUnidadServicioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadServicio.getId())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA)));
    }

    @Test
    void getUnidadServicio() throws Exception {
        // Initialize the database
        unidadServicioRepository.save(unidadServicio);

        // Get the unidadServicio
        restUnidadServicioMockMvc
            .perform(get(ENTITY_API_URL_ID, unidadServicio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unidadServicio.getId()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA));
    }

    @Test
    void getNonExistingUnidadServicio() throws Exception {
        // Get the unidadServicio
        restUnidadServicioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewUnidadServicio() throws Exception {
        // Initialize the database
        unidadServicioRepository.save(unidadServicio);

        int databaseSizeBeforeUpdate = unidadServicioRepository.findAll().size();

        // Update the unidadServicio
        UnidadServicio updatedUnidadServicio = unidadServicioRepository.findById(unidadServicio.getId()).get();
        updatedUnidadServicio.fecha(UPDATED_FECHA);

        restUnidadServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUnidadServicio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUnidadServicio))
            )
            .andExpect(status().isOk());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeUpdate);
        UnidadServicio testUnidadServicio = unidadServicioList.get(unidadServicioList.size() - 1);
        assertThat(testUnidadServicio.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    void putNonExistingUnidadServicio() throws Exception {
        int databaseSizeBeforeUpdate = unidadServicioRepository.findAll().size();
        unidadServicio.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, unidadServicio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unidadServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchUnidadServicio() throws Exception {
        int databaseSizeBeforeUpdate = unidadServicioRepository.findAll().size();
        unidadServicio.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unidadServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamUnidadServicio() throws Exception {
        int databaseSizeBeforeUpdate = unidadServicioRepository.findAll().size();
        unidadServicio.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadServicioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidadServicio)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateUnidadServicioWithPatch() throws Exception {
        // Initialize the database
        unidadServicioRepository.save(unidadServicio);

        int databaseSizeBeforeUpdate = unidadServicioRepository.findAll().size();

        // Update the unidadServicio using partial update
        UnidadServicio partialUpdatedUnidadServicio = new UnidadServicio();
        partialUpdatedUnidadServicio.setId(unidadServicio.getId());

        restUnidadServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnidadServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnidadServicio))
            )
            .andExpect(status().isOk());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeUpdate);
        UnidadServicio testUnidadServicio = unidadServicioList.get(unidadServicioList.size() - 1);
        assertThat(testUnidadServicio.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    void fullUpdateUnidadServicioWithPatch() throws Exception {
        // Initialize the database
        unidadServicioRepository.save(unidadServicio);

        int databaseSizeBeforeUpdate = unidadServicioRepository.findAll().size();

        // Update the unidadServicio using partial update
        UnidadServicio partialUpdatedUnidadServicio = new UnidadServicio();
        partialUpdatedUnidadServicio.setId(unidadServicio.getId());

        partialUpdatedUnidadServicio.fecha(UPDATED_FECHA);

        restUnidadServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnidadServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnidadServicio))
            )
            .andExpect(status().isOk());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeUpdate);
        UnidadServicio testUnidadServicio = unidadServicioList.get(unidadServicioList.size() - 1);
        assertThat(testUnidadServicio.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    void patchNonExistingUnidadServicio() throws Exception {
        int databaseSizeBeforeUpdate = unidadServicioRepository.findAll().size();
        unidadServicio.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, unidadServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unidadServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchUnidadServicio() throws Exception {
        int databaseSizeBeforeUpdate = unidadServicioRepository.findAll().size();
        unidadServicio.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unidadServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamUnidadServicio() throws Exception {
        int databaseSizeBeforeUpdate = unidadServicioRepository.findAll().size();
        unidadServicio.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadServicioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(unidadServicio))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteUnidadServicio() throws Exception {
        // Initialize the database
        unidadServicioRepository.save(unidadServicio);

        int databaseSizeBeforeDelete = unidadServicioRepository.findAll().size();

        // Delete the unidadServicio
        restUnidadServicioMockMvc
            .perform(delete(ENTITY_API_URL_ID, unidadServicio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
