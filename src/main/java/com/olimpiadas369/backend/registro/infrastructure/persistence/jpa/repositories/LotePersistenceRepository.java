package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.repositories;

import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities.LotePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LotePersistenceRepository extends JpaRepository<LotePersistenceEntity, Long> {
    List<LotePersistenceEntity> findByBloqueId(Long bloqueId);
}
