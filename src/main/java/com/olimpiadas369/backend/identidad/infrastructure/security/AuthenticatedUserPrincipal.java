package com.olimpiadas369.backend.identidad.infrastructure.security;

import com.olimpiadas369.backend.identidad.domain.model.valueobjects.Rol;

/** Principal del request autenticado; accesible con SecurityContextHolder. */
public record AuthenticatedUserPrincipal(Long usuarioId, String correo, Rol rol) {}
