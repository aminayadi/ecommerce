package com.ecommerce.product.web.rest;

import com.ecommerce.product.repository.PfieldRepository;
import com.ecommerce.product.service.PfieldService;
import com.ecommerce.product.service.dto.PfieldDTO;
import com.ecommerce.product.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ecommerce.product.domain.Pfield}.
 */
@RestController
@RequestMapping("/api")
public class PfieldResource {

    private final Logger log = LoggerFactory.getLogger(PfieldResource.class);

    private static final String ENTITY_NAME = "productdbPfield";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PfieldService pfieldService;

    private final PfieldRepository pfieldRepository;

    public PfieldResource(PfieldService pfieldService, PfieldRepository pfieldRepository) {
        this.pfieldService = pfieldService;
        this.pfieldRepository = pfieldRepository;
    }

    /**
     * {@code POST  /pfields} : Create a new pfield.
     *
     * @param pfieldDTO the pfieldDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pfieldDTO, or with status {@code 400 (Bad Request)} if the pfield has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pfields")
    public ResponseEntity<PfieldDTO> createPfield(@Valid @RequestBody PfieldDTO pfieldDTO) throws URISyntaxException {
        log.debug("REST request to save Pfield : {}", pfieldDTO);
        if (pfieldDTO.getId() != null) {
            throw new BadRequestAlertException("A new pfield cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PfieldDTO result = pfieldService.save(pfieldDTO);
        return ResponseEntity
            .created(new URI("/api/pfields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /pfields/:id} : Updates an existing pfield.
     *
     * @param id the id of the pfieldDTO to save.
     * @param pfieldDTO the pfieldDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pfieldDTO,
     * or with status {@code 400 (Bad Request)} if the pfieldDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pfieldDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pfields/{id}")
    public ResponseEntity<PfieldDTO> updatePfield(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody PfieldDTO pfieldDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Pfield : {}, {}", id, pfieldDTO);
        if (pfieldDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pfieldDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pfieldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PfieldDTO result = pfieldService.update(pfieldDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pfieldDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /pfields/:id} : Partial updates given fields of an existing pfield, field will ignore if it is null
     *
     * @param id the id of the pfieldDTO to save.
     * @param pfieldDTO the pfieldDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pfieldDTO,
     * or with status {@code 400 (Bad Request)} if the pfieldDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pfieldDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pfieldDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pfields/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PfieldDTO> partialUpdatePfield(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody PfieldDTO pfieldDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pfield partially : {}, {}", id, pfieldDTO);
        if (pfieldDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pfieldDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pfieldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PfieldDTO> result = pfieldService.partialUpdate(pfieldDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pfieldDTO.getId())
        );
    }

    /**
     * {@code GET  /pfields} : get all the pfields.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pfields in body.
     */
    @GetMapping("/pfields")
    public List<PfieldDTO> getAllPfields() {
        log.debug("REST request to get all Pfields");
        return pfieldService.findAll();
    }

    /**
     * {@code GET  /pfields/:id} : get the "id" pfield.
     *
     * @param id the id of the pfieldDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pfieldDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pfields/{id}")
    public ResponseEntity<PfieldDTO> getPfield(@PathVariable String id) {
        log.debug("REST request to get Pfield : {}", id);
        Optional<PfieldDTO> pfieldDTO = pfieldService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pfieldDTO);
    }

    /**
     * {@code DELETE  /pfields/:id} : delete the "id" pfield.
     *
     * @param id the id of the pfieldDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pfields/{id}")
    public ResponseEntity<Void> deletePfield(@PathVariable String id) {
        log.debug("REST request to delete Pfield : {}", id);
        pfieldService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
