package com.olimpiadas369.backend.identidad.application.internal.outboundservices.tokens;

import com.olimpiadas369.backend.identidad.domain.model.valueobjects.Rol;

/** DTO plano que viaja junto al puerto {@link TokenService}. */
public record AuthenticatedUser(Long usuarioId, String correo, Rol rol) {}
