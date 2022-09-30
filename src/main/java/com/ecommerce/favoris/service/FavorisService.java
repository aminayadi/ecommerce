package com.ecommerce.favoris.service;

import com.ecommerce.favoris.domain.Favoris;
import com.ecommerce.favoris.repository.FavorisRepository;
import com.ecommerce.favoris.service.dto.FavorisDTO;
import com.ecommerce.favoris.service.mapper.FavorisMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Favoris}.
 */
@Service
public class FavorisService {

    private final Logger log = LoggerFactory.getLogger(FavorisService.class);

    private final FavorisRepository favorisRepository;

    private final FavorisMapper favorisMapper;

    public FavorisService(FavorisRepository favorisRepository, FavorisMapper favorisMapper) {
        this.favorisRepository = favorisRepository;
        this.favorisMapper = favorisMapper;
    }

    /**
     * Save a favoris.
     *
     * @param favorisDTO the entity to save.
     * @return the persisted entity.
     */
    public FavorisDTO save(FavorisDTO favorisDTO) {
        log.debug("Request to save Favoris : {}", favorisDTO);
        Favoris favoris = favorisMapper.toEntity(favorisDTO);
        favoris = favorisRepository.save(favoris);
        return favorisMapper.toDto(favoris);
    }

    /**
     * Update a favoris.
     *
     * @param favorisDTO the entity to save.
     * @return the persisted entity.
     */
    public FavorisDTO update(FavorisDTO favorisDTO) {
        log.debug("Request to update Favoris : {}", favorisDTO);
        Favoris favoris = favorisMapper.toEntity(favorisDTO);
        favoris = favorisRepository.save(favoris);
        return favorisMapper.toDto(favoris);
    }

    /**
     * Partially update a favoris.
     *
     * @param favorisDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FavorisDTO> partialUpdate(FavorisDTO favorisDTO) {
        log.debug("Request to partially update Favoris : {}", favorisDTO);

        return favorisRepository
            .findById(favorisDTO.getId())
            .map(existingFavoris -> {
                favorisMapper.partialUpdate(existingFavoris, favorisDTO);

                return existingFavoris;
            })
            .map(favorisRepository::save)
            .map(favorisMapper::toDto);
    }

    /**
     * Get all the favorises.
     *
     * @return the list of entities.
     */
    public List<FavorisDTO> findAll() {
        log.debug("Request to get all Favorises");
        return favorisRepository.findAll().stream().map(favorisMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one favoris by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<FavorisDTO> findOne(String id) {
        log.debug("Request to get Favoris : {}", id);
        return favorisRepository.findById(id).map(favorisMapper::toDto);
    }

    /**
     * Delete the favoris by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Favoris : {}", id);
        favorisRepository.deleteById(id);
    }
}
