package com.ecommerce.category.service;

import com.ecommerce.category.domain.Category;
import com.ecommerce.category.repository.CategoryRepository;
import com.ecommerce.category.service.dto.CategoryDTO;
import com.ecommerce.category.service.dto.FieldsDTO;
import com.ecommerce.category.service.mapper.CategoryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Category}.
 */
@Service
public class CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;
    
    private final FieldsService fieldsService;

    public CategoryService(FieldsService fieldsService, CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.fieldsService = fieldsService;
    }

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save.
     * @return the persisted entity.
     */
    public CategoryDTO save(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
        
        if(category.getMother()!=null)
        	category.setParent(category.getMother().getName());

        List<FieldsDTO> lfields = categoryDTO.getFields();
        
        category = categoryRepository.save(category);
      
        fieldsService.saveList(lfields, category);
        
        return categoryMapper.toDto(category);
    }

    /**
     * Update a category.
     *
     * @param categoryDTO the entity to save.
     * @return the persisted entity.
     */
    public CategoryDTO update(CategoryDTO categoryDTO) {
        log.debug("Request to update Category : {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    /**
     * Partially update a category.
     *
     * @param categoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CategoryDTO> partialUpdate(CategoryDTO categoryDTO) {
        log.debug("Request to partially update Category : {}", categoryDTO);

        return categoryRepository
            .findById(categoryDTO.getId())
            .map(existingCategory -> {
                categoryMapper.partialUpdate(existingCategory, categoryDTO);

                return existingCategory;
            })
            .map(categoryRepository::save)
            .map(categoryMapper::toDto);
    }

    /**
     * Get all the categories.
     *
     * @return the list of entities.
     */
    public List<CategoryDTO> findAll() {
        log.debug("Request to get all Categories");
        List<CategoryDTO> listCategoryDTO =  categoryRepository.findAll().stream().map(categoryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    
        for (int i=0; i<listCategoryDTO.size(); i++)
        {
        	listCategoryDTO.get(i).setFields(fieldsService.findAllByCategory(listCategoryDTO.get(i)));
        	log.debug("i : --------",i,"      listCategoryDTO ; ",listCategoryDTO);
        }
    
        return listCategoryDTO ;
    }

    /**
     * Get one category by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<CategoryDTO> findOne(String id) {
        log.debug("Request to get Category : {}", id);
        return categoryRepository.findById(id).map(categoryMapper::toDto);
    }

    /**
     * Delete the category by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.deleteById(id);
    }
}
