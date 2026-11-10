package com.olimpiadas369.backend.identidad.infrastructure.persistence.jpa.repositories;

import com.olimpiadas369.backend.identidad.domain.model.valueobjects.Correo;
import com.olimpiadas369.backend.identidad.infrastructure.persistence.jpa.entities.UsuarioPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioPersistenceRepository extends JpaRepository<UsuarioPersistenceEntity, Long> {
    Optional<UsuarioPersistenceEntity> findByCorreo(Correo correo);
    boolean existsByCorreo(Correo correo);
}
