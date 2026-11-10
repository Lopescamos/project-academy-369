package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.repositories;

import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities.ClaveCorreccionPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaveCorreccionPersistenceRepository extends JpaRepository<ClaveCorreccionPersistenceEntity, Long> {
    List<ClaveCorreccionPersistenceEntity> findByClaveRespuestasId(Long claveRespuestasId);
}
