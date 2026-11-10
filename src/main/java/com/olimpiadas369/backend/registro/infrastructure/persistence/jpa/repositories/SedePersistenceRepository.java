package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.repositories;

import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities.SedePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SedePersistenceRepository extends JpaRepository<SedePersistenceEntity, Long> {}
