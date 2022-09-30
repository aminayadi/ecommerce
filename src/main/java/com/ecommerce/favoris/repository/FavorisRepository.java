package com.ecommerce.favoris.repository;

import com.ecommerce.favoris.domain.Favoris;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Favoris entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FavorisRepository extends MongoRepository<Favoris, String> {}
