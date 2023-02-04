package com.ecommerce.photomanager.repository;

import com.ecommerce.photomanager.domain.File;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the File entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileRepository extends MongoRepository<File, String> {
    List<File> findByIdproduct(String idproduct);
}