package com.ecommerce.photomanager.service;

import com.ecommerce.photomanager.domain.Folder;
import com.ecommerce.photomanager.repository.FolderRepository;
import com.ecommerce.photomanager.service.dto.FolderDTO;
import com.ecommerce.photomanager.service.mapper.FolderMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Folder}.
 */
@Service
public class FolderService {

    private final Logger log = LoggerFactory.getLogger(FolderService.class);

    private final FolderRepository folderRepository;

    private final FolderMapper folderMapper;

    public FolderService(FolderRepository folderRepository, FolderMapper folderMapper) {
        this.folderRepository = folderRepository;
        this.folderMapper = folderMapper;
    }

    /**
     * Save a folder.
     *
     * @param folderDTO the entity to save.
     * @return the persisted entity.
     */
    public FolderDTO save(FolderDTO folderDTO) {
        log.debug("Request to save Folder : {}", folderDTO);
        Folder folder = folderMapper.toEntity(folderDTO);
        folder = folderRepository.save(folder);
        return folderMapper.toDto(folder);
    }

    /**
     * Update a folder.
     *
     * @param folderDTO the entity to save.
     * @return the persisted entity.
     */
    public FolderDTO update(FolderDTO folderDTO) {
        log.debug("Request to update Folder : {}", folderDTO);
        Folder folder = folderMapper.toEntity(folderDTO);
        folder = folderRepository.save(folder);
        return folderMapper.toDto(folder);
    }

    /**
     * Partially update a folder.
     *
     * @param folderDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FolderDTO> partialUpdate(FolderDTO folderDTO) {
        log.debug("Request to partially update Folder : {}", folderDTO);

        return folderRepository
            .findById(folderDTO.getId())
            .map(existingFolder -> {
                folderMapper.partialUpdate(existingFolder, folderDTO);

                return existingFolder;
            })
            .map(folderRepository::save)
            .map(folderMapper::toDto);
    }

    /**
     * Get all the folders.
     *
     * @return the list of entities.
     */
    public List<FolderDTO> findAll() {
        log.debug("Request to get all Folders");
        return folderRepository.findAll().stream().map(folderMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one folder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<FolderDTO> findOne(String id) {
        log.debug("Request to get Folder : {}", id);
        return folderRepository.findById(id).map(folderMapper::toDto);
    }

    /**
     * Delete the folder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Folder : {}", id);
        folderRepository.deleteById(id);
    }
}
