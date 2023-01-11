package com.ecommerce.photomanager.repository;

import com.ecommerce.photomanager.domain.Folder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Folder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FolderRepository extends MongoRepository<Folder, String> {}
