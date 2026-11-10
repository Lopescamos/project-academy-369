package com.olimpiadas369.backend.registro.interfaces.rest.resources;

import com.olimpiadas369.backend.registro.domain.model.valueobjects.*;
import jakarta.validation.constraints.NotNull;

public record CreateBloqueResource(@NotNull Long sedeId, @NotNull Curso curso, @NotNull Nivel nivel, @NotNull Grado grado, Long tutorAsignadoId) {}
