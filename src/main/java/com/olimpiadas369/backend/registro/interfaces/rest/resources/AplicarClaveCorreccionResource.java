package com.olimpiadas369.backend.registro.interfaces.rest.resources;

import com.olimpiadas369.backend.registro.domain.model.valueobjects.TipoCorreccionClave;
import jakarta.validation.constraints.NotNull;

public record AplicarClaveCorreccionResource(
        @NotNull TipoCorreccionClave tipo,
        @NotNull Short numeroPregunta,
        String opcionAnterior,
        String opcionNueva,
        String motivo) {}
