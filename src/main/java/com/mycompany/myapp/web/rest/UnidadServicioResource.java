package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.UnidadServicio;
import com.mycompany.myapp.repository.UnidadServicioRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.UnidadServicio}.
 */
@RestController
@RequestMapping("/api")
public class UnidadServicioResource {

    private final Logger log = LoggerFactory.getLogger(UnidadServicioResource.class);

    private static final String ENTITY_NAME = "unidadServicio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnidadServicioRepository unidadServicioRepository;

    public UnidadServicioResource(UnidadServicioRepository unidadServicioRepository) {
        this.unidadServicioRepository = unidadServicioRepository;
    }

    /**
     * {@code POST  /unidad-servicios} : Create a new unidadServicio.
     *
     * @param unidadServicio the unidadServicio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unidadServicio, or with status {@code 400 (Bad Request)} if the unidadServicio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unidad-servicios")
    public ResponseEntity<UnidadServicio> createUnidadServicio(@RequestBody UnidadServicio unidadServicio) throws URISyntaxException {
        log.debug("REST request to save UnidadServicio : {}", unidadServicio);
        if (unidadServicio.getId() != null) {
            throw new BadRequestAlertException("A new unidadServicio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnidadServicio result = unidadServicioRepository.save(unidadServicio);
        return ResponseEntity
            .created(new URI("/api/unidad-servicios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /unidad-servicios/:id} : Updates an existing unidadServicio.
     *
     * @param id the id of the unidadServicio to save.
     * @param unidadServicio the unidadServicio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadServicio,
     * or with status {@code 400 (Bad Request)} if the unidadServicio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unidadServicio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unidad-servicios/{id}")
    public ResponseEntity<UnidadServicio> updateUnidadServicio(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody UnidadServicio unidadServicio
    ) throws URISyntaxException {
        log.debug("REST request to update UnidadServicio : {}, {}", id, unidadServicio);
        if (unidadServicio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unidadServicio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unidadServicioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UnidadServicio result = unidadServicioRepository.save(unidadServicio);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unidadServicio.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /unidad-servicios/:id} : Partial updates given fields of an existing unidadServicio, field will ignore if it is null
     *
     * @param id the id of the unidadServicio to save.
     * @param unidadServicio the unidadServicio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadServicio,
     * or with status {@code 400 (Bad Request)} if the unidadServicio is not valid,
     * or with status {@code 404 (Not Found)} if the unidadServicio is not found,
     * or with status {@code 500 (Internal Server Error)} if the unidadServicio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/unidad-servicios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UnidadServicio> partialUpdateUnidadServicio(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody UnidadServicio unidadServicio
    ) throws URISyntaxException {
        log.debug("REST request to partial update UnidadServicio partially : {}, {}", id, unidadServicio);
        if (unidadServicio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unidadServicio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unidadServicioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UnidadServicio> result = unidadServicioRepository
            .findById(unidadServicio.getId())
            .map(existingUnidadServicio -> {
                if (unidadServicio.getFecha() != null) {
                    existingUnidadServicio.setFecha(unidadServicio.getFecha());
                }

                return existingUnidadServicio;
            })
            .map(unidadServicioRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unidadServicio.getId())
        );
    }

    /**
     * {@code GET  /unidad-servicios} : get all the unidadServicios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unidadServicios in body.
     */
    @GetMapping("/unidad-servicios")
    public List<UnidadServicio> getAllUnidadServicios() {
        log.debug("REST request to get all UnidadServicios");
        return unidadServicioRepository.findAll();
    }

    /**
     * {@code GET  /unidad-servicios/:id} : get the "id" unidadServicio.
     *
     * @param id the id of the unidadServicio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unidadServicio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unidad-servicios/{id}")
    public ResponseEntity<UnidadServicio> getUnidadServicio(@PathVariable String id) {
        log.debug("REST request to get UnidadServicio : {}", id);
        Optional<UnidadServicio> unidadServicio = unidadServicioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(unidadServicio);
    }

    /**
     * {@code DELETE  /unidad-servicios/:id} : delete the "id" unidadServicio.
     *
     * @param id the id of the unidadServicio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unidad-servicios/{id}")
    public ResponseEntity<Void> deleteUnidadServicio(@PathVariable String id) {
        log.debug("REST request to delete UnidadServicio : {}", id);
        unidadServicioRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
