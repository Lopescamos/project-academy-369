package com.olimpiadas369.backend.identidad.interfaces.acl;

/** Cara pública de Identidad para que otros BCs puedan consultar datos mínimos sin acoplarse. */
public interface IdentidadContextFacade {
    /** Devuelve 0L si el usuario no existe (se traduce a Optional en el consumidor). */
    long fetchUsuarioIdIfActive(Long usuarioId);

    /** true si el usuario existe y está activo. */
    boolean existsAndActive(Long usuarioId);
}
