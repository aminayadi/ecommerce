package com.ecommerce.chatmanager.repository;

import com.ecommerce.chatmanager.domain.Msg;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Msg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MsgRepository extends MongoRepository<Msg, String> {}
