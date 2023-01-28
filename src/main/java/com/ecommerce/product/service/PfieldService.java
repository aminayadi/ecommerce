package com.ecommerce.product.service;

import com.ecommerce.product.domain.Pfield;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.repository.PfieldRepository;
import com.ecommerce.product.service.dto.PfieldDTO;
import com.ecommerce.product.service.mapper.PfieldMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Pfield}.
 */
@Service
public class PfieldService {

    private final Logger log = LoggerFactory.getLogger(PfieldService.class);

    private final PfieldRepository pfieldRepository;

    private final PfieldMapper pfieldMapper;

    public PfieldService(PfieldRepository pfieldRepository, PfieldMapper pfieldMapper) {
        this.pfieldRepository = pfieldRepository;
        this.pfieldMapper = pfieldMapper;
    }

    /**
     * Save a pfield.
     *
     * @param pfieldDTO the entity to save.
     * @return the persisted entity.
     */
    public PfieldDTO save(PfieldDTO pfieldDTO) {
        log.debug("Request to save Pfield : {}", pfieldDTO);
        Pfield pfield = pfieldMapper.toEntity(pfieldDTO);
        pfield = pfieldRepository.save(pfield);
        return pfieldMapper.toDto(pfield);
    }

    /**
     * Update a pfield.
     *
     * @param pfieldDTO the entity to save.
     * @return the persisted entity.
     */
    public PfieldDTO update(PfieldDTO pfieldDTO) {
        log.debug("Request to update Pfield : {}", pfieldDTO);
        Pfield pfield = pfieldMapper.toEntity(pfieldDTO);
        pfield = pfieldRepository.save(pfield);
        return pfieldMapper.toDto(pfield);
    }

    /**
     * Partially update a pfield.
     *
     * @param pfieldDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PfieldDTO> partialUpdate(PfieldDTO pfieldDTO) {
        log.debug("Request to partially update Pfield : {}", pfieldDTO);

        return pfieldRepository
            .findById(pfieldDTO.getId())
            .map(existingPfield -> {
                pfieldMapper.partialUpdate(existingPfield, pfieldDTO);

                return existingPfield;
            })
            .map(pfieldRepository::save)
            .map(pfieldMapper::toDto);
    }

    /**
     * Get all the pfields.
     *
     * @return the list of entities.
     */
    public List<PfieldDTO> findAll() {
        log.debug("Request to get all Pfields");
        return pfieldRepository.findAll().stream().map(pfieldMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the pfields.
     *
     * @return the list of entities.
     */
    public List<PfieldDTO> findAllByProduct(Product product) {
        log.debug("Request to get all Pfields");
        return pfieldRepository.findAllByProduct(product);
    }    
    
    
    
    /**
     * Get one pfield by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<PfieldDTO> findOne(String id) {
        log.debug("Request to get Pfield : {}", id);
        return pfieldRepository.findById(id).map(pfieldMapper::toDto);
    }

    /**
     * Delete the pfield by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Pfield : {}", id);
        pfieldRepository.deleteById(id);
    }
}
