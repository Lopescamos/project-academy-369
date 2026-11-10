package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.adapters;

import com.olimpiadas369.backend.registro.domain.model.aggregates.ClaveCorreccion;
import com.olimpiadas369.backend.registro.domain.repositories.ClaveCorreccionRepository;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.assemblers.ClaveCorreccionPersistenceAssembler;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.repositories.ClaveCorreccionPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClaveCorreccionRepositoryImpl implements ClaveCorreccionRepository {
    private final ClaveCorreccionPersistenceRepository jpa;
    public ClaveCorreccionRepositoryImpl(ClaveCorreccionPersistenceRepository jpa) { this.jpa = jpa; }
    @Override public ClaveCorreccion save(ClaveCorreccion cc) { return ClaveCorreccionPersistenceAssembler.toDomainFromPersistence(jpa.save(ClaveCorreccionPersistenceAssembler.toPersistenceFromDomain(cc))); }
    @Override public List<ClaveCorreccion> findByClaveRespuestasId(Long id) { return jpa.findByClaveRespuestasId(id).stream().map(ClaveCorreccionPersistenceAssembler::toDomainFromPersistence).toList(); }
}
