package com.olimpiadas369.backend.identidad.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record AssignUsuarioToSedeResource(@NotNull Long sedeId) {}
