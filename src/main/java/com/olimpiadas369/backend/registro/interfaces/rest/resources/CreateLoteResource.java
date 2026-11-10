package com.olimpiadas369.backend.registro.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record CreateLoteResource(@NotNull Long bloqueId, @NotNull Long tutorId, Integer totalFotosEsperadas) {}
