package com.ecommerce.research.service;

import com.ecommerce.research.domain.Research;
import com.ecommerce.research.repository.ResearchRepository;
import com.ecommerce.research.service.dto.ResearchDTO;
import com.ecommerce.research.service.mapper.ResearchMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Research}.
 */
@Service
public class ResearchService {

    private final Logger log = LoggerFactory.getLogger(ResearchService.class);

    private final ResearchRepository researchRepository;

    private final ResearchMapper researchMapper;

    public ResearchService(ResearchRepository researchRepository, ResearchMapper researchMapper) {
        this.researchRepository = researchRepository;
        this.researchMapper = researchMapper;
    }

    /**
     * Save a research.
     *
     * @param researchDTO the entity to save.
     * @return the persisted entity.
     */
    public ResearchDTO save(ResearchDTO researchDTO) {
        log.debug("Request to save Research : {}", researchDTO);
        Research research = researchMapper.toEntity(researchDTO);
        research = researchRepository.save(research);
        return researchMapper.toDto(research);
    }

    /**
     * Update a research.
     *
     * @param researchDTO the entity to save.
     * @return the persisted entity.
     */
    public ResearchDTO update(ResearchDTO researchDTO) {
        log.debug("Request to update Research : {}", researchDTO);
        Research research = researchMapper.toEntity(researchDTO);
        research = researchRepository.save(research);
        return researchMapper.toDto(research);
    }

    /**
     * Partially update a research.
     *
     * @param researchDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ResearchDTO> partialUpdate(ResearchDTO researchDTO) {
        log.debug("Request to partially update Research : {}", researchDTO);

        return researchRepository
            .findById(researchDTO.getId())
            .map(existingResearch -> {
                researchMapper.partialUpdate(existingResearch, researchDTO);

                return existingResearch;
            })
            .map(researchRepository::save)
            .map(researchMapper::toDto);
    }

    /**
     * Get all the research.
     *
     * @return the list of entities.
     */
    public List<ResearchDTO> findAll() {
        log.debug("Request to get all Research");
        return researchRepository.findAll().stream().map(researchMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one research by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ResearchDTO> findOne(String id) {
        log.debug("Request to get Research : {}", id);
        return researchRepository.findById(id).map(researchMapper::toDto);
    }

    /**
     * Delete the research by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Research : {}", id);
        researchRepository.deleteById(id);
    }
}
