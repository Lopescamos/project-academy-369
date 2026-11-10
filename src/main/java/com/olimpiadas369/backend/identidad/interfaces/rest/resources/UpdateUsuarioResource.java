package com.olimpiadas369.backend.identidad.interfaces.rest.resources;

import com.olimpiadas369.backend.identidad.domain.model.valueobjects.Rol;

public record UpdateUsuarioResource(String nombreCompleto, Rol rol) {}
