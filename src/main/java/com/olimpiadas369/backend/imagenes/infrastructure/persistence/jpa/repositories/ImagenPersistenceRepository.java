package com.olimpiadas369.backend.imagenes.infrastructure.persistence.jpa.repositories;

import com.olimpiadas369.backend.imagenes.infrastructure.persistence.jpa.entities.ImagenPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenPersistenceRepository extends JpaRepository<ImagenPersistenceEntity, Long> {
    List<ImagenPersistenceEntity> findByLoteIdOrderByOrdenEnLote(Long loteId);
}
