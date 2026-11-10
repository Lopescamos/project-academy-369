package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.assemblers;

import com.olimpiadas369.backend.registro.domain.model.aggregates.ClaveCorreccion;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities.ClaveCorreccionPersistenceEntity;

public final class ClaveCorreccionPersistenceAssembler {
    private ClaveCorreccionPersistenceAssembler() {}
    public static ClaveCorreccion toDomainFromPersistence(ClaveCorreccionPersistenceEntity e) {
        if (e == null) return null;
        ClaveCorreccion c = new ClaveCorreccion();
        c.setId(e.getId()); c.setClaveRespuestasId(e.getClaveRespuestasId()); c.setTipo(e.getTipo());
        c.setNumeroPregunta(e.getNumeroPregunta()); c.setOpcionAnterior(e.getOpcionAnterior()); c.setOpcionNueva(e.getOpcionNueva());
        c.setMotivo(e.getMotivo()); c.setAplicadoPorId(e.getAplicadoPorId()); c.setFichasRecalculadas(e.getFichasRecalculadas());
        return c;
    }
    public static ClaveCorreccionPersistenceEntity toPersistenceFromDomain(ClaveCorreccion c) {
        if (c == null) return null;
        ClaveCorreccionPersistenceEntity e = new ClaveCorreccionPersistenceEntity();
        if (c.getId() != null) e.setId(c.getId());
        e.setClaveRespuestasId(c.getClaveRespuestasId()); e.setTipo(c.getTipo());
        e.setNumeroPregunta(c.getNumeroPregunta()); e.setOpcionAnterior(c.getOpcionAnterior()); e.setOpcionNueva(c.getOpcionNueva());
        e.setMotivo(c.getMotivo()); e.setAplicadoPorId(c.getAplicadoPorId()); e.setFichasRecalculadas(c.getFichasRecalculadas());
        return e;
    }
}
