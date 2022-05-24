package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Taller;
import com.mycompany.myapp.repository.TallerRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Taller}.
 */
@RestController
@RequestMapping("/api")
public class TallerResource {

    private final Logger log = LoggerFactory.getLogger(TallerResource.class);

    private static final String ENTITY_NAME = "taller";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TallerRepository tallerRepository;

    public TallerResource(TallerRepository tallerRepository) {
        this.tallerRepository = tallerRepository;
    }

    /**
     * {@code POST  /tallers} : Create a new taller.
     *
     * @param taller the taller to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taller, or with status {@code 400 (Bad Request)} if the taller has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tallers")
    public ResponseEntity<Taller> createTaller(@RequestBody Taller taller) throws URISyntaxException {
        log.debug("REST request to save Taller : {}", taller);
        if (taller.getId() != null) {
            throw new BadRequestAlertException("A new taller cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Taller result = tallerRepository.save(taller);
        return ResponseEntity
            .created(new URI("/api/tallers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /tallers/:id} : Updates an existing taller.
     *
     * @param id the id of the taller to save.
     * @param taller the taller to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taller,
     * or with status {@code 400 (Bad Request)} if the taller is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taller couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tallers/{id}")
    public ResponseEntity<Taller> updateTaller(@PathVariable(value = "id", required = false) final String id, @RequestBody Taller taller)
        throws URISyntaxException {
        log.debug("REST request to update Taller : {}, {}", id, taller);
        if (taller.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taller.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tallerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Taller result = tallerRepository.save(taller);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taller.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /tallers/:id} : Partial updates given fields of an existing taller, field will ignore if it is null
     *
     * @param id the id of the taller to save.
     * @param taller the taller to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taller,
     * or with status {@code 400 (Bad Request)} if the taller is not valid,
     * or with status {@code 404 (Not Found)} if the taller is not found,
     * or with status {@code 500 (Internal Server Error)} if the taller couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tallers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Taller> partialUpdateTaller(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Taller taller
    ) throws URISyntaxException {
        log.debug("REST request to partial update Taller partially : {}, {}", id, taller);
        if (taller.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taller.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tallerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Taller> result = tallerRepository
            .findById(taller.getId())
            .map(existingTaller -> {
                if (taller.getMarca() != null) {
                    existingTaller.setMarca(taller.getMarca());
                }
                if (taller.getModelo() != null) {
                    existingTaller.setModelo(taller.getModelo());
                }
                if (taller.getMatricula() != null) {
                    existingTaller.setMatricula(taller.getMatricula());
                }
                if (taller.getColor() != null) {
                    existingTaller.setColor(taller.getColor());
                }
                if (taller.getNumSerie() != null) {
                    existingTaller.setNumSerie(taller.getNumSerie());
                }
                if (taller.getGeneracion() != null) {
                    existingTaller.setGeneracion(taller.getGeneracion());
                }

                return existingTaller;
            })
            .map(tallerRepository::save);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taller.getId()));
    }

    /**
     * {@code GET  /tallers} : get all the tallers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tallers in body.
     */
    @GetMapping("/tallers")
    public List<Taller> getAllTallers() {
        log.debug("REST request to get all Tallers");
        return tallerRepository.findAll();
    }

    /**
     * {@code GET  /tallers/:id} : get the "id" taller.
     *
     * @param id the id of the taller to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taller, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tallers/{id}")
    public ResponseEntity<Taller> getTaller(@PathVariable String id) {
        log.debug("REST request to get Taller : {}", id);
        Optional<Taller> taller = tallerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taller);
    }

    /**
     * {@code DELETE  /tallers/:id} : delete the "id" taller.
     *
     * @param id the id of the taller to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tallers/{id}")
    public ResponseEntity<Void> deleteTaller(@PathVariable String id) {
        log.debug("REST request to delete Taller : {}", id);
        tallerRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
