package com.olimpiadas369.backend.vision.domain.services;

import java.math.BigDecimal;
import java.util.List;

/** Resultado puro: datos que el servicio de aplicación convertirá en Registro + RespuestaAlumno. */
public record VisionResult(
        String nombre, String apellidos, String colegioTexto,
        String horaFinalizacion, BigDecimal confianzaPromedio,
        List<RespuestaExtraida> respuestas) {

    public record RespuestaExtraida(short numeroPregunta, String opcionMarcada, String estadosOpcionesJson) {}
}
