package com.olimpiadas369.backend.audit.infrastructure.persistence.jpa.adapters;

import com.olimpiadas369.backend.audit.domain.model.aggregates.AuditoriaAccion;
import com.olimpiadas369.backend.audit.domain.repositories.AuditoriaAccionRepository;
import com.olimpiadas369.backend.audit.infrastructure.persistence.jpa.assemblers.AuditoriaAccionPersistenceAssembler;
import com.olimpiadas369.backend.audit.infrastructure.persistence.jpa.repositories.AuditoriaAccionPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuditoriaAccionRepositoryImpl implements AuditoriaAccionRepository {
    private final AuditoriaAccionPersistenceRepository jpa;
    public AuditoriaAccionRepositoryImpl(AuditoriaAccionPersistenceRepository jpa) { this.jpa = jpa; }
    @Override public AuditoriaAccion save(AuditoriaAccion a) {
        return AuditoriaAccionPersistenceAssembler.toDomainFromPersistence(
                jpa.save(AuditoriaAccionPersistenceAssembler.toPersistenceFromDomain(a)));
    }
    @Override public List<AuditoriaAccion> findByEntidadAndEntidadId(String e, Long id) {
        return jpa.findByEntidadAndEntidadId(e, id).stream()
                .map(AuditoriaAccionPersistenceAssembler::toDomainFromPersistence).toList();
    }
    @Override public List<AuditoriaAccion> findByUsuarioId(Long id) {
        return jpa.findByUsuarioId(id).stream()
                .map(AuditoriaAccionPersistenceAssembler::toDomainFromPersistence).toList();
    }
}
