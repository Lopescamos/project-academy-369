package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.repositories;

import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities.ColegioPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColegioPersistenceRepository extends JpaRepository<ColegioPersistenceEntity, Long> {
    Optional<ColegioPersistenceEntity> findByNombreNormalizado(String nombreNormalizado);
}
