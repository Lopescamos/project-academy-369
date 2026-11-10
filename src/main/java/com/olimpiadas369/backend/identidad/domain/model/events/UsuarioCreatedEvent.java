package com.olimpiadas369.backend.identidad.domain.model.events;

import com.olimpiadas369.backend.identidad.domain.model.valueobjects.Rol;

public record UsuarioCreatedEvent(Long usuarioId, String correo, Rol rol) {}
