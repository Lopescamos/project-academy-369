package com.olimpiadas369.backend.identidad.infrastructure.persistence.jpa.repositories;

import com.olimpiadas369.backend.identidad.infrastructure.persistence.jpa.entities.UsuarioSedePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioSedePersistenceRepository extends JpaRepository<UsuarioSedePersistenceEntity, Long> {
    List<UsuarioSedePersistenceEntity> findByUsuarioId(Long usuarioId);
    List<UsuarioSedePersistenceEntity> findBySedeId(Long sedeId);
    Optional<UsuarioSedePersistenceEntity> findByUsuarioIdAndSedeId(Long usuarioId, Long sedeId);
    boolean existsByUsuarioIdAndSedeId(Long usuarioId, Long sedeId);
}
