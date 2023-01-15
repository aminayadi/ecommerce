package com.ecommerce.product.service;

import com.ecommerce.product.domain.Photo;
import com.ecommerce.product.repository.PhotoRepository;
import com.ecommerce.product.service.dto.PhotoDTO;
import com.ecommerce.product.service.mapper.PhotoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Photo}.
 */
@Service
public class PhotoService {

    private final Logger log = LoggerFactory.getLogger(PhotoService.class);

    private final PhotoRepository photoRepository;

    private final PhotoMapper photoMapper;

    public PhotoService(PhotoRepository photoRepository, PhotoMapper photoMapper) {
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
    }

    /**
     * Save a photo.
     *
     * @param photoDTO the entity to save.
     * @return the persisted entity.
     */
    public PhotoDTO save(PhotoDTO photoDTO) {
        log.debug("Request to save Photo : {}", photoDTO);
        Photo photo = photoMapper.toEntity(photoDTO);
        photo = photoRepository.save(photo);
        return photoMapper.toDto(photo);
    }

    /**
     * Update a photo.
     *
     * @param photoDTO the entity to save.
     * @return the persisted entity.
     */
    public PhotoDTO update(PhotoDTO photoDTO) {
        log.debug("Request to update Photo : {}", photoDTO);
        Photo photo = photoMapper.toEntity(photoDTO);
        photo = photoRepository.save(photo);
        return photoMapper.toDto(photo);
    }

    /**
     * Partially update a photo.
     *
     * @param photoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PhotoDTO> partialUpdate(PhotoDTO photoDTO) {
        log.debug("Request to partially update Photo : {}", photoDTO);

        return photoRepository
            .findById(photoDTO.getId())
            .map(existingPhoto -> {
                photoMapper.partialUpdate(existingPhoto, photoDTO);

                return existingPhoto;
            })
            .map(photoRepository::save)
            .map(photoMapper::toDto);
    }

    /**
     * Get all the photos.
     *
     * @return the list of entities.
     */
    public List<PhotoDTO> findAll() {
        log.debug("Request to get all Photos");
        return photoRepository.findAll().stream().map(photoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one photo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<PhotoDTO> findOne(String id) {
        log.debug("Request to get Photo : {}", id);
        return photoRepository.findById(id).map(photoMapper::toDto);
    }

    /**
     * Delete the photo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Photo : {}", id);
        photoRepository.deleteById(id);
    }
}
