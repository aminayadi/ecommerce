package com.ecommerce.research.repository;

import com.ecommerce.research.domain.Research;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Research entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResearchRepository extends MongoRepository<Research, String> {}
