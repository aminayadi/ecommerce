package com.ecommerce.product.repository;

import com.ecommerce.product.domain.Pfield;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.service.dto.PfieldDTO;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Pfield entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PfieldRepository extends MongoRepository<Pfield, String> {
	List<PfieldDTO>findAllByProduct(Product product);
	
}
