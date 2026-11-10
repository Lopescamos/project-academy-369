package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.repositories;

import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities.ApellidoComunPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApellidoComunPersistenceRepository extends JpaRepository<ApellidoComunPersistenceEntity, Long> {
    boolean existsByApellidoNormalizado(String apellidoNormalizado);
}
