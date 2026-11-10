package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.assemblers;

import com.olimpiadas369.backend.registro.domain.model.aggregates.RespuestaAlumno;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities.RespuestaAlumnoPersistenceEntity;

public final class RespuestaAlumnoPersistenceAssembler {
    private RespuestaAlumnoPersistenceAssembler() {}
    public static RespuestaAlumno toDomainFromPersistence(RespuestaAlumnoPersistenceEntity e) {
        if (e == null) return null;
        RespuestaAlumno r = new RespuestaAlumno();
        r.setId(e.getId()); r.setRegistroId(e.getRegistroId()); r.setNumeroPregunta(e.getNumeroPregunta());
        r.setOpcionMarcada(e.getOpcionMarcada()); r.setEsCorrecta(e.getEsCorrecta());
        r.setEstadosOpcionesJson(e.getEstadosOpcionesJson()); r.setRevisadaPorHumano(e.getRevisadaPorHumano());
        return r;
    }
    public static RespuestaAlumnoPersistenceEntity toPersistenceFromDomain(RespuestaAlumno r) {
        if (r == null) return null;
        RespuestaAlumnoPersistenceEntity e = new RespuestaAlumnoPersistenceEntity();
        if (r.getId() != null) e.setId(r.getId());
        e.setRegistroId(r.getRegistroId()); e.setNumeroPregunta(r.getNumeroPregunta());
        e.setOpcionMarcada(r.getOpcionMarcada()); e.setEsCorrecta(r.getEsCorrecta());
        e.setEstadosOpcionesJson(r.getEstadosOpcionesJson());
        e.setRevisadaPorHumano(r.getRevisadaPorHumano() != null ? r.getRevisadaPorHumano() : false);
        return e;
    }
}
