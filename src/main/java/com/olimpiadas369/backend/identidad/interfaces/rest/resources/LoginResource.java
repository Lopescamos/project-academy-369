package com.olimpiadas369.backend.identidad.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record LoginResource(@NotBlank String correo, @NotBlank String password) {}
