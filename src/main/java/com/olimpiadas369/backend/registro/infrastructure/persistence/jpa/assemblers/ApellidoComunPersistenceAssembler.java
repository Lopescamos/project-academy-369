package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.assemblers;

import com.olimpiadas369.backend.registro.domain.model.aggregates.ApellidoComun;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities.ApellidoComunPersistenceEntity;

public final class ApellidoComunPersistenceAssembler {
    private ApellidoComunPersistenceAssembler() {}
    public static ApellidoComun toDomainFromPersistence(ApellidoComunPersistenceEntity e) {
        if (e == null) return null;
        ApellidoComun a = new ApellidoComun();
        a.setId(e.getId()); a.setApellido(e.getApellido()); a.setApellidoNormalizado(e.getApellidoNormalizado());
        return a;
    }
    public static ApellidoComunPersistenceEntity toPersistenceFromDomain(ApellidoComun a) {
        if (a == null) return null;
        ApellidoComunPersistenceEntity e = new ApellidoComunPersistenceEntity();
        if (a.getId() != null) e.setId(a.getId());
        e.setApellido(a.getApellido()); e.setApellidoNormalizado(a.getApellidoNormalizado());
        return e;
    }
}
