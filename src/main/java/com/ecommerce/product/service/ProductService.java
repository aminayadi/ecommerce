package com.ecommerce.product.service;

import com.ecommerce.product.client.CategoryFeignClient;
import com.ecommerce.product.client.ClientFeignClient;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.dto.CategoryDTO;
import com.ecommerce.product.service.dto.ClientDTO;
import com.ecommerce.product.service.dto.ProductDTO;
import com.ecommerce.product.service.mapper.ProductMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Product}.
 */
@Service
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;
    private final CategoryFeignClient categoryFeignClient;
    private final ClientFeignClient clientFeignClient;

   /* public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }*/
    public ProductService(ProductRepository productRepository, ProductMapper productMapper, 
    		ClientFeignClient clientFeignClient, CategoryFeignClient categoryFeignClient) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryFeignClient = categoryFeignClient;
        this.clientFeignClient = clientFeignClient;
    }

    /**
     * Save a product.
     *
     * @param productDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    /**
     * Update a product.
     *
     * @param productDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductDTO update(ProductDTO productDTO) {
        log.debug("Request to update Product : {}", productDTO);
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    /**
     * Partially update a product.
     *
     * @param productDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductDTO> partialUpdate(ProductDTO productDTO) {
        log.debug("Request to partially update Product : {}", productDTO);

        return productRepository
            .findById(productDTO.getId())
            .map(existingProduct -> {
                productMapper.partialUpdate(existingProduct, productDTO);

                return existingProduct;
            })
            .map(productRepository::save)
            .map(productMapper::toDto);
    }

    /**
     * Get all the products.
     *
     * @return the list of entities.
     */
    public List<ProductDTO> findAll() {
        log.debug("Request to get all Products");
        return productRepository.findAll().stream().map(productMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one product by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ProductDTO> findOne(String id) {
        /*log.debug("Request to get Product : {}", id);
        return productRepository.findById(id).map(productMapper::toDto);
        */
    	
    	log.debug("Request to get Product : {}", id);
        // return productRepository.findById(id).map(productMapper::toDto);
         Optional<ProductDTO> pDTO = productRepository.findById(id).map(productMapper::toDto);

         ResponseEntity<CategoryDTO> catDTO = categoryFeignClient.getCategory(pDTO.get().getIdcategory());
         pDTO.get().setCategoryDTO(catDTO.getBody());
         
         ResponseEntity<ClientDTO> clientDTO = clientFeignClient.getClient(pDTO.get().getIduser());
         pDTO.get().setClientDTO(clientDTO.getBody());
        
         
         return pDTO;
    }

    /**
     * Delete the product by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
    }
}