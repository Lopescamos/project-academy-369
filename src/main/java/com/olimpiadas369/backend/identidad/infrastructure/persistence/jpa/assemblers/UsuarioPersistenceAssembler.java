package com.olimpiadas369.backend.identidad.infrastructure.persistence.jpa.assemblers;

import com.olimpiadas369.backend.identidad.domain.model.aggregates.Usuario;
import com.olimpiadas369.backend.identidad.infrastructure.persistence.jpa.entities.UsuarioPersistenceEntity;

public final class UsuarioPersistenceAssembler {

    private UsuarioPersistenceAssembler() {}

    public static Usuario toDomainFromPersistence(UsuarioPersistenceEntity e) {
        if (e == null) return null;
        Usuario u = new Usuario();
        u.setId(e.getId());
        u.setNombreCompleto(e.getNombreCompleto());
        u.setCorreo(e.getCorreo());
        u.setPasswordHash(e.getPasswordHash());
        u.setRol(e.getRol());
        u.setEstado(e.getEstado());
        u.setDesactivadoEn(e.getDesactivadoEn());
        return u;
    }

    public static UsuarioPersistenceEntity toPersistenceFromDomain(Usuario u) {
        if (u == null) return null;
        UsuarioPersistenceEntity e = new UsuarioPersistenceEntity();
        if (u.getId() != null) e.setId(u.getId());
        e.setNombreCompleto(u.getNombreCompleto());
        e.setCorreo(u.getCorreo());
        e.setPasswordHash(u.getPasswordHash());
        e.setRol(u.getRol());
        e.setEstado(u.getEstado());
        e.setDesactivadoEn(u.getDesactivadoEn());
        return e;
    }
}
