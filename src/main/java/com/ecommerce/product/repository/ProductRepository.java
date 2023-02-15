package com.ecommerce.product.repository;

import com.ecommerce.product.domain.Product;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByIduser(String id);	

}