package com.olimpiadas369.backend.registro.interfaces.rest.resources;

import com.olimpiadas369.backend.registro.domain.model.valueobjects.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateClaveRespuestasResource(
        @NotNull Curso curso, @NotNull Nivel nivel, @NotNull Grado grado,
        @NotEmpty List<DetalleResource> detalles) {
    public record DetalleResource(@NotNull Short numeroPregunta, @NotNull String opcionCorrecta) {}
}
