package com.olimpiadas369.backend.identidad.interfaces.rest.resources;

import com.olimpiadas369.backend.identidad.domain.model.valueobjects.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUsuarioResource(
        @NotBlank String nombreCompleto,
        @NotBlank @Email String correo,
        @NotNull Rol rol) {}
