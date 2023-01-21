package com.ecommerce.product.service;

import com.ecommerce.product.domain.Pfield;
import com.ecommerce.product.domain.Photo;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.enumeration.etype;
import com.ecommerce.product.repository.PfieldRepository;
import com.ecommerce.product.repository.PhotoRepository;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.security.SecurityUtils;
import com.ecommerce.product.service.dto.PfieldDTO;
import com.ecommerce.product.service.dto.PhotoDTO;
import com.ecommerce.product.service.dto.ProductDTO;
import com.ecommerce.product.service.mapper.PfieldMapper;
import com.ecommerce.product.service.mapper.PhotoMapper;
import com.ecommerce.product.service.mapper.ProductMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Product}.
 */
@Service
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final PfieldRepository pfieldRepository;

    private final PfieldMapper pfieldMapper;    
    
    private final PhotoRepository photoRepository;

    private final PhotoMapper photoMapper;      
    
    public ProductService(
    		ProductRepository productRepository, 
    		ProductMapper productMapper,
    		PfieldRepository pfieldRepository,
    		PfieldMapper pfieldMapper,
    		PhotoRepository photoRepository,
    		PhotoMapper photoMapper    		
    	) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.pfieldRepository = pfieldRepository;
        this.pfieldMapper = pfieldMapper;  
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;          
    }

    /**
     * Save a product.
     *
     * @param productDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        
        //Set UserID
        try {
            String idUser = SecurityUtils.getCurrentUserLogin().get();
            productDTO.setIduser(idUser);
            log.debug("idUser : ", idUser);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        
        for(int i=0; i<productDTO.getPfields().size(); i++) {
        	
        	PfieldDTO pfieldDTO = productDTO.getPfields().get(i);
        	pfieldDTO.setType(etype.STRING);
        	log.debug("Request to save Pfield : {}", pfieldDTO);
            Pfield pfield = pfieldMapper.toEntity(pfieldDTO);
            pfield = pfieldRepository.save(pfield);        	
        	
        }

        ProductDTO pMtoDTO = productMapper.toDto(product) ;
        
        for(int i=0; i<productDTO.getLphotos().size(); i++) {
        	
        	PhotoDTO photoDTO = productDTO.getLphotos().get(i);
        	photoDTO.setProduct(pMtoDTO);
       
            Photo photo = photoMapper.toEntity(photoDTO);
            photo = photoRepository.save(photo);        	
        	
        }        
        
        return pMtoDTO;
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
        log.debug("Request to get Product : {}", id);
        return productRepository.findById(id).map(productMapper::toDto);
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
