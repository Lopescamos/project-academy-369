package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.assemblers;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Colegio;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities.ColegioPersistenceEntity;

public final class ColegioPersistenceAssembler {
    private ColegioPersistenceAssembler() {}
    public static Colegio toDomainFromPersistence(ColegioPersistenceEntity e) {
        if (e == null) return null;
        Colegio c = new Colegio(); c.setId(e.getId()); c.setNombre(e.getNombre()); c.setNombreNormalizado(e.getNombreNormalizado());
        return c;
    }
    public static ColegioPersistenceEntity toPersistenceFromDomain(Colegio c) {
        if (c == null) return null;
        ColegioPersistenceEntity e = new ColegioPersistenceEntity();
        if (c.getId() != null) e.setId(c.getId());
        e.setNombre(c.getNombre()); e.setNombreNormalizado(c.getNombreNormalizado());
        return e;
    }
}
