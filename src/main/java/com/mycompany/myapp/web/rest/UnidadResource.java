package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Unidad;
import com.mycompany.myapp.repository.UnidadRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Unidad}.
 */
@RestController
@RequestMapping("/api")
public class UnidadResource {

    private final Logger log = LoggerFactory.getLogger(UnidadResource.class);

    private static final String ENTITY_NAME = "unidad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnidadRepository unidadRepository;

    public UnidadResource(UnidadRepository unidadRepository) {
        this.unidadRepository = unidadRepository;
    }

    /**
     * {@code POST  /unidads} : Create a new unidad.
     *
     * @param unidad the unidad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unidad, or with status {@code 400 (Bad Request)} if the unidad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unidads")
    public ResponseEntity<Unidad> createUnidad(@RequestBody Unidad unidad) throws URISyntaxException {
        log.debug("REST request to save Unidad : {}", unidad);
        if (unidad.getId() != null) {
            throw new BadRequestAlertException("A new unidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Unidad result = unidadRepository.save(unidad);
        return ResponseEntity
            .created(new URI("/api/unidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /unidads/:id} : Updates an existing unidad.
     *
     * @param id the id of the unidad to save.
     * @param unidad the unidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidad,
     * or with status {@code 400 (Bad Request)} if the unidad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unidads/{id}")
    public ResponseEntity<Unidad> updateUnidad(@PathVariable(value = "id", required = false) final String id, @RequestBody Unidad unidad)
        throws URISyntaxException {
        log.debug("REST request to update Unidad : {}, {}", id, unidad);
        if (unidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unidad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unidadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Unidad result = unidadRepository.save(unidad);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unidad.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /unidads/:id} : Partial updates given fields of an existing unidad, field will ignore if it is null
     *
     * @param id the id of the unidad to save.
     * @param unidad the unidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidad,
     * or with status {@code 400 (Bad Request)} if the unidad is not valid,
     * or with status {@code 404 (Not Found)} if the unidad is not found,
     * or with status {@code 500 (Internal Server Error)} if the unidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/unidads/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Unidad> partialUpdateUnidad(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Unidad unidad
    ) throws URISyntaxException {
        log.debug("REST request to partial update Unidad partially : {}, {}", id, unidad);
        if (unidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unidad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unidadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Unidad> result = unidadRepository
            .findById(unidad.getId())
            .map(existingUnidad -> {
                if (unidad.getMarca() != null) {
                    existingUnidad.setMarca(unidad.getMarca());
                }
                if (unidad.getModelo() != null) {
                    existingUnidad.setModelo(unidad.getModelo());
                }
                if (unidad.getMatricula() != null) {
                    existingUnidad.setMatricula(unidad.getMatricula());
                }
                if (unidad.getColor() != null) {
                    existingUnidad.setColor(unidad.getColor());
                }
                if (unidad.getNumSerie() != null) {
                    existingUnidad.setNumSerie(unidad.getNumSerie());
                }
                if (unidad.getGeneracion() != null) {
                    existingUnidad.setGeneracion(unidad.getGeneracion());
                }

                return existingUnidad;
            })
            .map(unidadRepository::save);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unidad.getId()));
    }

    /**
     * {@code GET  /unidads} : get all the unidads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unidads in body.
     */
    @GetMapping("/unidads")
    public List<Unidad> getAllUnidads() {
        log.debug("REST request to get all Unidads");
        return unidadRepository.findAll();
    }

    /**
     * {@code GET  /unidads/:id} : get the "id" unidad.
     *
     * @param id the id of the unidad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unidad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unidads/{id}")
    public ResponseEntity<Unidad> getUnidad(@PathVariable String id) {
        log.debug("REST request to get Unidad : {}", id);
        Optional<Unidad> unidad = unidadRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(unidad);
    }

    /**
     * {@code DELETE  /unidads/:id} : delete the "id" unidad.
     *
     * @param id the id of the unidad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unidads/{id}")
    public ResponseEntity<Void> deleteUnidad(@PathVariable String id) {
        log.debug("REST request to delete Unidad : {}", id);
        unidadRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
