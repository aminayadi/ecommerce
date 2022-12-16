package com.ecommerce.category.service;

import com.ecommerce.category.domain.Fields;
import com.ecommerce.category.repository.FieldsRepository;
import com.ecommerce.category.service.dto.FieldsDTO;
import com.ecommerce.category.service.mapper.FieldsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Fields}.
 */
@Service
public class FieldsService {

    private final Logger log = LoggerFactory.getLogger(FieldsService.class);

    private final FieldsRepository fieldsRepository;

    private final FieldsMapper fieldsMapper;

    public FieldsService(FieldsRepository fieldsRepository, FieldsMapper fieldsMapper) {
        this.fieldsRepository = fieldsRepository;
        this.fieldsMapper = fieldsMapper;
    }

    /**
     * Save a fields.
     *
     * @param fieldsDTO the entity to save.
     * @return the persisted entity.
     */
    public FieldsDTO save(FieldsDTO fieldsDTO) {
        log.debug("Request to save Fields : {}", fieldsDTO);
        Fields fields = fieldsMapper.toEntity(fieldsDTO);
        fields = fieldsRepository.save(fields);
        return fieldsMapper.toDto(fields);
    }

    /**
     * Save a list of fields.
     *
     * @param fieldsDTO the entity to save.
     * @return the persisted entity.
     */
    public int saveList(List<FieldsDTO>  fieldsDTOList) {
        log.debug("Request to save list of Fields : {}", fieldsDTOList);
        int nbSaved = 0;
        
        for (nbSaved=0; nbSaved<fieldsDTOList.size(); nbSaved++)
        {
	        Fields fields = fieldsMapper.toEntity(fieldsDTOList.get(nbSaved));
	        fields = fieldsRepository.save(fields);
        }
        
        return nbSaved;
    }
    
    /**
     * Update a fields.
     *
     * @param fieldsDTO the entity to save.
     * @return the persisted entity.
     */
    public FieldsDTO update(FieldsDTO fieldsDTO) {
        log.debug("Request to update Fields : {}", fieldsDTO);
        Fields fields = fieldsMapper.toEntity(fieldsDTO);
        fields = fieldsRepository.save(fields);
        return fieldsMapper.toDto(fields);
    }

    /**
     * Partially update a fields.
     *
     * @param fieldsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FieldsDTO> partialUpdate(FieldsDTO fieldsDTO) {
        log.debug("Request to partially update Fields : {}", fieldsDTO);

        return fieldsRepository
            .findById(fieldsDTO.getId())
            .map(existingFields -> {
                fieldsMapper.partialUpdate(existingFields, fieldsDTO);

                return existingFields;
            })
            .map(fieldsRepository::save)
            .map(fieldsMapper::toDto);
    }

    /**
     * Get all the fields.
     *
     * @return the list of entities.
     */
    public List<FieldsDTO> findAll() {
        log.debug("Request to get all Fields");
        return fieldsRepository.findAll().stream().map(fieldsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the fields with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<FieldsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return fieldsRepository.findAllWithEagerRelationships(pageable).map(fieldsMapper::toDto);
    }

    /**
     * Get one fields by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<FieldsDTO> findOne(String id) {
        log.debug("Request to get Fields : {}", id);
        return fieldsRepository.findOneWithEagerRelationships(id).map(fieldsMapper::toDto);
    }

    /**
     * Delete the fields by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Fields : {}", id);
        fieldsRepository.deleteById(id);
    }
}
