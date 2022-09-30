package com.ecommerce.request.service;

import com.ecommerce.request.client.CategoryFeignClient;
import com.ecommerce.request.client.ClientFeignClient;
import com.ecommerce.request.client.ProductFeignClient;
import com.ecommerce.request.domain.Request;
import com.ecommerce.request.repository.RequestRepository;
import com.ecommerce.request.service.dto.CategoryDTO;
import com.ecommerce.request.service.dto.ClientDTO;
import com.ecommerce.request.service.dto.ProductDTO;
import com.ecommerce.request.service.dto.RequestDTO;
import com.ecommerce.request.service.mapper.RequestMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Request}.
 */
@Service
public class RequestService {

    private final Logger log = LoggerFactory.getLogger(RequestService.class);

    private final RequestRepository requestRepository;

    private final RequestMapper requestMapper;
    private final CategoryFeignClient categoryFeignClient;
    private final ClientFeignClient clientFeignClient;
    private final ProductFeignClient productFeignClient;

    public RequestService(RequestRepository requestRepository, RequestMapper requestMapper, ClientFeignClient clientFeignClient, CategoryFeignClient categoryFeignClient, ProductFeignClient productFeignClient) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
        this.categoryFeignClient = categoryFeignClient;
        this.clientFeignClient = clientFeignClient;
        this.productFeignClient= productFeignClient;
    }

    /**
     * Save a request.
     *
     * @param requestDTO the entity to save.
     * @return the persisted entity.
     */
    public RequestDTO save(RequestDTO requestDTO) {
        log.debug("Request to save Request : {}", requestDTO);
        Request request = requestMapper.toEntity(requestDTO);
        request = requestRepository.save(request);
        return requestMapper.toDto(request);
    }

    /**
     * Update a request.
     *
     * @param requestDTO the entity to save.
     * @return the persisted entity.
     */
    public RequestDTO update(RequestDTO requestDTO) {
        log.debug("Request to update Request : {}", requestDTO);
        Request request = requestMapper.toEntity(requestDTO);
        request = requestRepository.save(request);
        return requestMapper.toDto(request);
    }

    /**
     * Partially update a request.
     *
     * @param requestDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RequestDTO> partialUpdate(RequestDTO requestDTO) {
        log.debug("Request to partially update Request : {}", requestDTO);

        return requestRepository
            .findById(requestDTO.getId())
            .map(existingRequest -> {
                requestMapper.partialUpdate(existingRequest, requestDTO);

                return existingRequest;
            })
            .map(requestRepository::save)
            .map(requestMapper::toDto);
    }

    /**
     * Get all the requests.
     *
     * @return the list of entities.
     */
    public List<RequestDTO> findAll() {
        log.debug("Request to get all Requests");
        return requestRepository.findAll().stream().map(requestMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one request by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<RequestDTO> findOne(String id) {
    	
    	/*
        log.debug("Request to get Request : {}", id);
        return requestRepository.findById(id).map(requestMapper::toDto);
        */
    	
    	log.debug("Request to get request : {}", id);
        // return productRepository.findById(id).map(productMapper::toDto);
         Optional<RequestDTO> rDTO = requestRepository.findById(id).map(requestMapper::toDto);

         ResponseEntity<CategoryDTO> catDTO = categoryFeignClient.getCategory(rDTO.get().getIdcategory());
         rDTO.get().setCategoryDTO(catDTO.getBody());
         
         ResponseEntity<ClientDTO> clientDTO = clientFeignClient.getClient(rDTO.get().getIduser());
         rDTO.get().setClientDTO(clientDTO.getBody());
         
         ResponseEntity<ProductDTO> productDTO = productFeignClient.getProduct(rDTO.get().getIdproduct());
         rDTO.get().setProductDTO(productDTO.getBody());
         
         return rDTO;
    }

    /**
     * Delete the request by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Request : {}", id);
        requestRepository.deleteById(id);
    }
}
