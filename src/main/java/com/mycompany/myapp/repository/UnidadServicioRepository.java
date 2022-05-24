package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UnidadServicio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the UnidadServicio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadServicioRepository extends MongoRepository<UnidadServicio, String> {}
