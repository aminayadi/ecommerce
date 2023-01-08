package com.ecommerce.product.repository;

import com.ecommerce.product.domain.Pfield;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Pfield entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PfieldRepository extends MongoRepository<Pfield, String> {}
