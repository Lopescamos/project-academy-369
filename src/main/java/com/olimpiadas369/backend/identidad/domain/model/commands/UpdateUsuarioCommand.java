package com.olimpiadas369.backend.identidad.domain.model.commands;

import com.olimpiadas369.backend.identidad.domain.model.valueobjects.Rol;

public record UpdateUsuarioCommand(Long usuarioId, String nombreCompleto, Rol rol) {}
