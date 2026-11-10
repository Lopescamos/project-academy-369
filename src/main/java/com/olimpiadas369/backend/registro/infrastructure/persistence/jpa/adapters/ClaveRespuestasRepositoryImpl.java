package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.adapters;

import com.olimpiadas369.backend.registro.domain.model.aggregates.ClaveRespuestas;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.*;
import com.olimpiadas369.backend.registro.domain.repositories.ClaveRespuestasRepository;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.assemblers.ClaveRespuestasPersistenceAssembler;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.repositories.ClaveRespuestasPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ClaveRespuestasRepositoryImpl implements ClaveRespuestasRepository {
    private final ClaveRespuestasPersistenceRepository jpa;
    public ClaveRespuestasRepositoryImpl(ClaveRespuestasPersistenceRepository jpa) { this.jpa = jpa; }
    @Override public Optional<ClaveRespuestas> findById(Long id) { return jpa.findById(id).map(ClaveRespuestasPersistenceAssembler::toDomainFromPersistence); }
    @Override public Optional<ClaveRespuestas> findByScope(Curso c, Nivel n, Grado g) { return jpa.findByCursoAndNivelAndGrado(c, n, g).map(ClaveRespuestasPersistenceAssembler::toDomainFromPersistence); }
    @Override public boolean existsByScope(Curso c, Nivel n, Grado g) { return jpa.existsByCursoAndNivelAndGrado(c, n, g); }
    @Override public ClaveRespuestas save(ClaveRespuestas c) { return ClaveRespuestasPersistenceAssembler.toDomainFromPersistence(jpa.save(ClaveRespuestasPersistenceAssembler.toPersistenceFromDomain(c))); }
}
