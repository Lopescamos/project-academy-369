package com.olimpiadas369.backend.identidad.infrastructure.persistence.jpa.assemblers;

import com.olimpiadas369.backend.identidad.domain.model.aggregates.UsuarioSede;
import com.olimpiadas369.backend.identidad.infrastructure.persistence.jpa.entities.UsuarioSedePersistenceEntity;

public final class UsuarioSedePersistenceAssembler {

    private UsuarioSedePersistenceAssembler() {}

    public static UsuarioSede toDomainFromPersistence(UsuarioSedePersistenceEntity e) {
        if (e == null) return null;
        UsuarioSede us = new UsuarioSede();
        us.setId(e.getId());
        us.setUsuarioId(e.getUsuarioId());
        us.setSedeId(e.getSedeId());
        us.setFechaAsignacion(e.getFechaAsignacion());
        us.setActivoEnSede(e.getActivoEnSede());
        return us;
    }

    public static UsuarioSedePersistenceEntity toPersistenceFromDomain(UsuarioSede us) {
        if (us == null) return null;
        UsuarioSedePersistenceEntity e = new UsuarioSedePersistenceEntity();
        if (us.getId() != null) e.setId(us.getId());
        e.setUsuarioId(us.getUsuarioId());
        e.setSedeId(us.getSedeId());
        e.setFechaAsignacion(us.getFechaAsignacion());
        e.setActivoEnSede(us.getActivoEnSede());
        return e;
    }
}
