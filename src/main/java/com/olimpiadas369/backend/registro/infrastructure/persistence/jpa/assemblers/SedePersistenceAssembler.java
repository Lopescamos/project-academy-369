package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.assemblers;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Sede;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities.SedePersistenceEntity;

public final class SedePersistenceAssembler {
    private SedePersistenceAssembler() {}
    public static Sede toDomainFromPersistence(SedePersistenceEntity e) {
        if (e == null) return null;
        Sede s = new Sede();
        s.setId(e.getId()); s.setNombre(e.getNombre()); s.setDireccion(e.getDireccion());
        s.setFechaEvento(e.getFechaEvento()); s.setEstado(e.getEstado());
        return s;
    }
    public static SedePersistenceEntity toPersistenceFromDomain(Sede s) {
        if (s == null) return null;
        SedePersistenceEntity e = new SedePersistenceEntity();
        if (s.getId() != null) e.setId(s.getId());
        e.setNombre(s.getNombre()); e.setDireccion(s.getDireccion());
        e.setFechaEvento(s.getFechaEvento()); e.setEstado(s.getEstado());
        return e;
    }
}
