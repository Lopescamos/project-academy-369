package com.olimpiadas369.backend.audit.infrastructure.persistence.jpa.assemblers;

import com.olimpiadas369.backend.audit.domain.model.aggregates.AuditoriaAccion;
import com.olimpiadas369.backend.audit.infrastructure.persistence.jpa.entities.AuditoriaAccionPersistenceEntity;

public final class AuditoriaAccionPersistenceAssembler {
    private AuditoriaAccionPersistenceAssembler() {}
    public static AuditoriaAccion toDomainFromPersistence(AuditoriaAccionPersistenceEntity e) {
        if (e == null) return null;
        AuditoriaAccion a = new AuditoriaAccion();
        a.setId(e.getId()); a.setUsuarioId(e.getUsuarioId()); a.setAccion(e.getAccion());
        a.setEntidad(e.getEntidad()); a.setEntidadId(e.getEntidadId());
        a.setDetalleJson(e.getDetalleJson()); a.setCreatedAt(e.getCreatedAt());
        return a;
    }
    public static AuditoriaAccionPersistenceEntity toPersistenceFromDomain(AuditoriaAccion a) {
        if (a == null) return null;
        AuditoriaAccionPersistenceEntity e = new AuditoriaAccionPersistenceEntity();
        if (a.getId() != null) e.setId(a.getId());
        e.setUsuarioId(a.getUsuarioId()); e.setAccion(a.getAccion());
        e.setEntidad(a.getEntidad()); e.setEntidadId(a.getEntidadId());
        e.setDetalleJson(a.getDetalleJson());
        e.setCreatedAt(a.getCreatedAt() != null ? a.getCreatedAt() : java.time.Instant.now());
        return e;
    }
}
