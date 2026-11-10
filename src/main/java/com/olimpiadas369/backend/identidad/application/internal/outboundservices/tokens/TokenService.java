package com.olimpiadas369.backend.identidad.application.internal.outboundservices.tokens;

import com.olimpiadas369.backend.identidad.domain.model.valueobjects.Rol;

import java.util.Optional;

/**
 * Emite y valida tokens de sesión. Puerto de aplicación: cómo se identifique
 * una sesión (JWT, token opaco...) no es parte del negocio, es cómo se transporta.
 */
public interface TokenService {
    String issue(Long usuarioId, String correo, Rol rol);
    Optional<AuthenticatedUser> verify(String token);
}
