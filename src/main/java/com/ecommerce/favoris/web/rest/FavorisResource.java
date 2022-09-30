package com.ecommerce.favoris.web.rest;

import com.ecommerce.favoris.repository.FavorisRepository;
import com.ecommerce.favoris.service.FavorisService;
import com.ecommerce.favoris.service.dto.FavorisDTO;
import com.ecommerce.favoris.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.ecommerce.favoris.domain.Favoris}.
 */
@RestController
@RequestMapping("/api")
public class FavorisResource {

    private final Logger log = LoggerFactory.getLogger(FavorisResource.class);

    private static final String ENTITY_NAME = "favorisdbFavoris";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FavorisService favorisService;

    private final FavorisRepository favorisRepository;

    public FavorisResource(FavorisService favorisService, FavorisRepository favorisRepository) {
        this.favorisService = favorisService;
        this.favorisRepository = favorisRepository;
    }

    /**
     * {@code POST  /favorises} : Create a new favoris.
     *
     * @param favorisDTO the favorisDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new favorisDTO, or with status {@code 400 (Bad Request)} if the favoris has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/favorises")
    public ResponseEntity<FavorisDTO> createFavoris(@RequestBody FavorisDTO favorisDTO) throws URISyntaxException {
        log.debug("REST request to save Favoris : {}", favorisDTO);
        if (favorisDTO.getId() != null) {
            throw new BadRequestAlertException("A new favoris cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FavorisDTO result = favorisService.save(favorisDTO);
        return ResponseEntity
            .created(new URI("/api/favorises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /favorises/:id} : Updates an existing favoris.
     *
     * @param id the id of the favorisDTO to save.
     * @param favorisDTO the favorisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated favorisDTO,
     * or with status {@code 400 (Bad Request)} if the favorisDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the favorisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/favorises/{id}")
    public ResponseEntity<FavorisDTO> updateFavoris(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody FavorisDTO favorisDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Favoris : {}, {}", id, favorisDTO);
        if (favorisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, favorisDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!favorisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FavorisDTO result = favorisService.update(favorisDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, favorisDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /favorises/:id} : Partial updates given fields of an existing favoris, field will ignore if it is null
     *
     * @param id the id of the favorisDTO to save.
     * @param favorisDTO the favorisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated favorisDTO,
     * or with status {@code 400 (Bad Request)} if the favorisDTO is not valid,
     * or with status {@code 404 (Not Found)} if the favorisDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the favorisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/favorises/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FavorisDTO> partialUpdateFavoris(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody FavorisDTO favorisDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Favoris partially : {}, {}", id, favorisDTO);
        if (favorisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, favorisDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!favorisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FavorisDTO> result = favorisService.partialUpdate(favorisDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, favorisDTO.getId())
        );
    }

    /**
     * {@code GET  /favorises} : get all the favorises.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of favorises in body.
     */
    @GetMapping("/favorises")
    public List<FavorisDTO> getAllFavorises() {
        log.debug("REST request to get all Favorises");
        return favorisService.findAll();
    }

    /**
     * {@code GET  /favorises/:id} : get the "id" favoris.
     *
     * @param id the id of the favorisDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the favorisDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/favorises/{id}")
    public ResponseEntity<FavorisDTO> getFavoris(@PathVariable String id) {
        log.debug("REST request to get Favoris : {}", id);
        Optional<FavorisDTO> favorisDTO = favorisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(favorisDTO);
    }

    /**
     * {@code DELETE  /favorises/:id} : delete the "id" favoris.
     *
     * @param id the id of the favorisDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/favorises/{id}")
    public ResponseEntity<Void> deleteFavoris(@PathVariable String id) {
        log.debug("REST request to delete Favoris : {}", id);
        favorisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
