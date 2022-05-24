package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Taller;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Taller entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TallerRepository extends MongoRepository<Taller, String> {}
