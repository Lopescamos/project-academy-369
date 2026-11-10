package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.repositories;

import com.olimpiadas369.backend.registro.domain.model.valueobjects.EstadoRegistro;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities.RegistroPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroPersistenceRepository extends JpaRepository<RegistroPersistenceEntity, Long> {
    List<RegistroPersistenceEntity> findByBloqueId(Long bloqueId);
    List<RegistroPersistenceEntity> findByBloqueIdAndEstado(Long bloqueId, EstadoRegistro estado);
}
