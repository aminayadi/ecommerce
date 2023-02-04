package com.ecommerce.photomanager.service;

import com.ecommerce.photomanager.domain.File;
import com.ecommerce.photomanager.repository.FileRepository;
import com.ecommerce.photomanager.service.dto.FileDTO;
import com.ecommerce.photomanager.service.dto.FolderDTO;
import com.ecommerce.photomanager.service.mapper.FileMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link File}.
 */
@Service
public class FileService {

    private final Logger log = LoggerFactory.getLogger(FileService.class);

    private final FileRepository fileRepository;

    private final FileMapper fileMapper;

    public FileService(FileRepository fileRepository, FileMapper fileMapper) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
    }

    /**
     * Save a file.
     *
     * @param fileDTO the entity to save.
     * @return the persisted entity.
     */
    public FileDTO save(FileDTO fileDTO) {
        log.debug("Request to save File : {}", fileDTO);
        File file = fileMapper.toEntity(fileDTO);
        file = fileRepository.save(file);
        return fileMapper.toDto(file);
    }

    /**
     * Update a file.
     *
     * @param fileDTO the entity to save.
     * @return the persisted entity.
     */
    public FileDTO update(FileDTO fileDTO) {
        log.debug("Request to update File : {}", fileDTO);
        File file = fileMapper.toEntity(fileDTO);
        file = fileRepository.save(file);
        return fileMapper.toDto(file);
    }

    /**
     * Partially update a file.
     *
     * @param fileDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FileDTO> partialUpdate(FileDTO fileDTO) {
        log.debug("Request to partially update File : {}", fileDTO);

        return fileRepository
            .findById(fileDTO.getId())
            .map(existingFile -> {
                fileMapper.partialUpdate(existingFile, fileDTO);

                return existingFile;
            })
            .map(fileRepository::save)
            .map(fileMapper::toDto);
    }

    /**
     * Get all the files.
     *
     * @return the list of entities.
     */
    public List<FileDTO> findAll() {
        log.debug("Request to get all Files");
        return fileRepository.findAll().stream().map(fileMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the folders by idproduct.
     *
     * @return the list of entities.
     */
    public List<FileDTO> findAllByIdProduct(String idproduct) {
        log.debug("Request to get all FileDTOs by idproduct");
        return fileRepository.findByIdproduct(idproduct).stream().map(fileMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one file by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<FileDTO> findOne(String id) {
        log.debug("Request to get File : {}", id);
        return fileRepository.findById(id).map(fileMapper::toDto);
    }

    /**
     * Delete the file by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete File : {}", id);
        fileRepository.deleteById(id);
    }
}
