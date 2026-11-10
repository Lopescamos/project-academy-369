package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.assemblers;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Bloque;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities.BloquePersistenceEntity;

public final class BloquePersistenceAssembler {
    private BloquePersistenceAssembler() {}
    public static Bloque toDomainFromPersistence(BloquePersistenceEntity e) {
        if (e == null) return null;
        Bloque b = new Bloque();
        b.setId(e.getId()); b.setSedeId(e.getSedeId()); b.setCurso(e.getCurso()); b.setNivel(e.getNivel()); b.setGrado(e.getGrado());
        b.setTutorAsignadoId(e.getTutorAsignadoId()); b.setBloqueadoDesde(e.getBloqueadoDesde());
        b.setCompletado(e.getCompletado()); b.setCompletadoEn(e.getCompletadoEn()); b.setCompletadoPorId(e.getCompletadoPorId());
        return b;
    }
    public static BloquePersistenceEntity toPersistenceFromDomain(Bloque b) {
        if (b == null) return null;
        BloquePersistenceEntity e = new BloquePersistenceEntity();
        if (b.getId() != null) e.setId(b.getId());
        e.setSedeId(b.getSedeId()); e.setCurso(b.getCurso()); e.setNivel(b.getNivel()); e.setGrado(b.getGrado());
        e.setTutorAsignadoId(b.getTutorAsignadoId()); e.setBloqueadoDesde(b.getBloqueadoDesde());
        e.setCompletado(b.getCompletado() != null ? b.getCompletado() : false);
        e.setCompletadoEn(b.getCompletadoEn()); e.setCompletadoPorId(b.getCompletadoPorId());
        return e;
    }
}
