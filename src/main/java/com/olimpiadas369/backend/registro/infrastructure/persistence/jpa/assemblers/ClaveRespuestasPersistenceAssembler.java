package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.assemblers;

import com.olimpiadas369.backend.registro.domain.model.aggregates.ClaveRespuestas;
import com.olimpiadas369.backend.registro.domain.model.entities.ClaveRespuestaDetalle;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities.ClaveRespuestaDetallePersistenceEntity;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities.ClaveRespuestasPersistenceEntity;

import java.util.List;

public final class ClaveRespuestasPersistenceAssembler {
    private ClaveRespuestasPersistenceAssembler() {}

    public static ClaveRespuestas toDomainFromPersistence(ClaveRespuestasPersistenceEntity e) {
        if (e == null) return null;
        ClaveRespuestas c = new ClaveRespuestas();
        c.setId(e.getId()); c.setCurso(e.getCurso()); c.setNivel(e.getNivel()); c.setGrado(e.getGrado());
        c.setCreadoPorId(e.getCreadoPorId()); c.setVersion(e.getVersion());
        c.setDetalles(e.getDetalles().stream().map(d -> {
            ClaveRespuestaDetalle dd = new ClaveRespuestaDetalle();
            dd.setId(d.getId()); dd.setNumeroPregunta(d.getNumeroPregunta()); dd.setOpcionCorrecta(d.getOpcionCorrecta());
            return dd;
        }).toList());
        return c;
    }

    public static ClaveRespuestasPersistenceEntity toPersistenceFromDomain(ClaveRespuestas c) {
        if (c == null) return null;
        ClaveRespuestasPersistenceEntity e = new ClaveRespuestasPersistenceEntity();
        if (c.getId() != null) e.setId(c.getId());
        e.setCurso(c.getCurso()); e.setNivel(c.getNivel()); e.setGrado(c.getGrado());
        e.setCreadoPorId(c.getCreadoPorId()); e.setVersion(c.getVersion() != null ? c.getVersion() : 1);
        List<ClaveRespuestaDetallePersistenceEntity> ds = c.getDetalles() == null ? List.of() :
                c.getDetalles().stream().map(d -> {
                    ClaveRespuestaDetallePersistenceEntity de = new ClaveRespuestaDetallePersistenceEntity();
                    if (d.getId() != null) de.setId(d.getId());
                    if (c.getId() != null) de.setClaveRespuestasId(c.getId());
                    de.setNumeroPregunta(d.getNumeroPregunta()); de.setOpcionCorrecta(d.getOpcionCorrecta());
                    return de;
                }).toList();
        e.setDetalles(new java.util.ArrayList<>(ds));
        return e;
    }
}
