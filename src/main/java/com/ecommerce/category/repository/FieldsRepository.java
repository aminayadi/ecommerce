package com.ecommerce.category.repository;

import com.ecommerce.category.domain.Fields;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Fields entity.
 */
@Repository
public interface FieldsRepository extends MongoRepository<Fields, String> {
    @Query("{}")
    Page<Fields> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Fields> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Fields> findOneWithEagerRelationships(String id);
    
    
    @Query("{'mother.id' : :#{#id}}")
    List<Fields> findAllByCategory(String id);
}
