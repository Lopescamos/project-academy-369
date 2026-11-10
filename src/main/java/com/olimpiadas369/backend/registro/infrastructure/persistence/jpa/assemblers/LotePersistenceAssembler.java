package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.assemblers;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Lote;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities.LotePersistenceEntity;

public final class LotePersistenceAssembler {
    private LotePersistenceAssembler() {}
    public static Lote toDomainFromPersistence(LotePersistenceEntity e) {
        if (e == null) return null;
        Lote l = new Lote();
        l.setId(e.getId()); l.setBloqueId(e.getBloqueId()); l.setTutorId(e.getTutorId()); l.setEstado(e.getEstado());
        l.setTotalFotosEsperadas(e.getTotalFotosEsperadas()); l.setTotalFotosRecibidas(e.getTotalFotosRecibidas());
        l.setUltimaFotoTomadaAt(e.getUltimaFotoTomadaAt());
        return l;
    }
    public static LotePersistenceEntity toPersistenceFromDomain(Lote l) {
        if (l == null) return null;
        LotePersistenceEntity e = new LotePersistenceEntity();
        if (l.getId() != null) e.setId(l.getId());
        e.setBloqueId(l.getBloqueId()); e.setTutorId(l.getTutorId()); e.setEstado(l.getEstado());
        e.setTotalFotosEsperadas(l.getTotalFotosEsperadas());
        e.setTotalFotosRecibidas(l.getTotalFotosRecibidas() != null ? l.getTotalFotosRecibidas() : 0);
        e.setUltimaFotoTomadaAt(l.getUltimaFotoTomadaAt());
        return e;
    }
}
