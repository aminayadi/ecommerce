package com.ecommerce.research.web.rest;

import com.ecommerce.research.repository.ResearchRepository;
import com.ecommerce.research.service.ResearchService;
import com.ecommerce.research.service.dto.ResearchDTO;
import com.ecommerce.research.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.ecommerce.research.domain.Research}.
 */
@RestController
@RequestMapping("/api")
public class ResearchResource {

    private final Logger log = LoggerFactory.getLogger(ResearchResource.class);

    private static final String ENTITY_NAME = "researchdbResearch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResearchService researchService;

    private final ResearchRepository researchRepository;

    public ResearchResource(ResearchService researchService, ResearchRepository researchRepository) {
        this.researchService = researchService;
        this.researchRepository = researchRepository;
    }

    /**
     * {@code POST  /research} : Create a new research.
     *
     * @param researchDTO the researchDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new researchDTO, or with status {@code 400 (Bad Request)} if the research has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/research")
    public ResponseEntity<ResearchDTO> createResearch(@RequestBody ResearchDTO researchDTO) throws URISyntaxException {
        log.debug("REST request to save Research : {}", researchDTO);
        if (researchDTO.getId() != null) {
            throw new BadRequestAlertException("A new research cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResearchDTO result = researchService.save(researchDTO);
        return ResponseEntity
            .created(new URI("/api/research/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /research/:id} : Updates an existing research.
     *
     * @param id the id of the researchDTO to save.
     * @param researchDTO the researchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated researchDTO,
     * or with status {@code 400 (Bad Request)} if the researchDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the researchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/research/{id}")
    public ResponseEntity<ResearchDTO> updateResearch(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ResearchDTO researchDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Research : {}, {}", id, researchDTO);
        if (researchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, researchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!researchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResearchDTO result = researchService.update(researchDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, researchDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /research/:id} : Partial updates given fields of an existing research, field will ignore if it is null
     *
     * @param id the id of the researchDTO to save.
     * @param researchDTO the researchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated researchDTO,
     * or with status {@code 400 (Bad Request)} if the researchDTO is not valid,
     * or with status {@code 404 (Not Found)} if the researchDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the researchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/research/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResearchDTO> partialUpdateResearch(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ResearchDTO researchDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Research partially : {}, {}", id, researchDTO);
        if (researchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, researchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!researchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResearchDTO> result = researchService.partialUpdate(researchDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, researchDTO.getId())
        );
    }

    /**
     * {@code GET  /research} : get all the research.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of research in body.
     */
    @GetMapping("/research")
    public List<ResearchDTO> getAllResearch() {
        log.debug("REST request to get all Research");
        return researchService.findAll();
    }

    /**
     * {@code GET  /research/:id} : get the "id" research.
     *
     * @param id the id of the researchDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the researchDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/research/{id}")
    public ResponseEntity<ResearchDTO> getResearch(@PathVariable String id) {
        log.debug("REST request to get Research : {}", id);
        Optional<ResearchDTO> researchDTO = researchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(researchDTO);
    }

    /**
     * {@code DELETE  /research/:id} : delete the "id" research.
     *
     * @param id the id of the researchDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/research/{id}")
    public ResponseEntity<Void> deleteResearch(@PathVariable String id) {
        log.debug("REST request to delete Research : {}", id);
        researchService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
