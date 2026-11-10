package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.repositories;

import com.olimpiadas369.backend.registro.domain.model.valueobjects.*;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities.ClaveRespuestasPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClaveRespuestasPersistenceRepository extends JpaRepository<ClaveRespuestasPersistenceEntity, Long> {
    Optional<ClaveRespuestasPersistenceEntity> findByCursoAndNivelAndGrado(Curso curso, Nivel nivel, Grado grado);
    boolean existsByCursoAndNivelAndGrado(Curso curso, Nivel nivel, Grado grado);
}
