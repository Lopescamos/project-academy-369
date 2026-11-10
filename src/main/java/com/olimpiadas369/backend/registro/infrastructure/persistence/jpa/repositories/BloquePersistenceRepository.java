package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.repositories;

import com.olimpiadas369.backend.registro.domain.model.valueobjects.*;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities.BloquePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BloquePersistenceRepository extends JpaRepository<BloquePersistenceEntity, Long> {
    List<BloquePersistenceEntity> findBySedeId(Long sedeId);
    Optional<BloquePersistenceEntity> findBySedeIdAndCursoAndNivelAndGrado(Long sedeId, Curso curso, Nivel nivel, Grado grado);
    boolean existsBySedeIdAndCursoAndNivelAndGrado(Long sedeId, Curso curso, Nivel nivel, Grado grado);
}
