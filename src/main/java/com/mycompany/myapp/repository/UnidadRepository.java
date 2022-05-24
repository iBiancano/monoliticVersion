package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Unidad;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Unidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadRepository extends MongoRepository<Unidad, String> {}
