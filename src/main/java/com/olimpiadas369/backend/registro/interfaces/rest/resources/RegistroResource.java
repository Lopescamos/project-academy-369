package com.olimpiadas369.backend.registro.interfaces.rest.resources;

import com.olimpiadas369.backend.registro.domain.model.valueobjects.EstadoRegistro;

import java.math.BigDecimal;
import java.time.Instant;

public record RegistroResource(
        Long id, Long loteId, Long bloqueId, Long imagenId,
        String nombre, String apellidos, Long colegioId, String colegioTextoExtraido,
        Short correctas, Short incorrectas, Short anuladas, Short blancos, Integer puntaje,
        EstadoRegistro estado, Boolean tieneObservacion, BigDecimal confianzaPromedio,
        Long confirmadoPorId, Instant confirmadoAt) {}
