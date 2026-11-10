package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.repositories;

import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities.RespuestaAlumnoPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespuestaAlumnoPersistenceRepository extends JpaRepository<RespuestaAlumnoPersistenceEntity, Long> {
    List<RespuestaAlumnoPersistenceEntity> findByRegistroId(Long registroId);
    void deleteByRegistroId(Long registroId);
}
