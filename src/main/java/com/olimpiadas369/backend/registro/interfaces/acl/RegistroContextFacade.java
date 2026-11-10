package com.olimpiadas369.backend.registro.interfaces.acl;

/** Cara pública del BC Registro para que otros BCs (Imágenes, Visión, Exportación) consulten lo mínimo. */
public interface RegistroContextFacade {
    /** 0L si no existe. */
    long fetchLoteIdIfExists(Long loteId);
    /** 0L si no existe o no está confirmado. */
    long fetchBloqueIdFromRegistroConfirmado(Long registroId);
    boolean loteExiste(Long loteId);
    boolean registroExiste(Long registroId);
}
