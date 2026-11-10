package com.olimpiadas369.backend.identidad.interfaces.rest.resources;

import com.olimpiadas369.backend.identidad.domain.model.valueobjects.EstadoUsuario;
import com.olimpiadas369.backend.identidad.domain.model.valueobjects.Rol;

import java.time.Instant;

public record UsuarioResource(
        Long id, String nombreCompleto, String correo, Rol rol,
        EstadoUsuario estado, Instant desactivadoEn) {}
