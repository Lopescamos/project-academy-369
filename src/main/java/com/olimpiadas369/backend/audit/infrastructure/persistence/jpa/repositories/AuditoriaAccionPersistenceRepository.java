package com.olimpiadas369.backend.audit.infrastructure.persistence.jpa.repositories;

import com.olimpiadas369.backend.audit.infrastructure.persistence.jpa.entities.AuditoriaAccionPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditoriaAccionPersistenceRepository extends JpaRepository<AuditoriaAccionPersistenceEntity, Long> {
    List<AuditoriaAccionPersistenceEntity> findByEntidadAndEntidadId(String entidad, Long entidadId);
    List<AuditoriaAccionPersistenceEntity> findByUsuarioId(Long usuarioId);
}
