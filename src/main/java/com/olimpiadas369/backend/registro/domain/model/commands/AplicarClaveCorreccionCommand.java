package com.olimpiadas369.backend.registro.domain.model.commands;

import com.olimpiadas369.backend.registro.domain.model.valueobjects.TipoCorreccionClave;

public record AplicarClaveCorreccionCommand(
        Long claveRespuestasId,
        TipoCorreccionClave tipo,
        Short numeroPregunta,
        String opcionAnterior,
        String opcionNueva,
        String motivo) {}
