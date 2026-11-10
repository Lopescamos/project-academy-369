package com.olimpiadas369.backend.registro.interfaces.rest.resources;

import com.olimpiadas369.backend.registro.domain.model.valueobjects.*;

import java.time.Instant;

public record BloqueResource(Long id, Long sedeId, Curso curso, Nivel nivel, Grado grado, Long tutorAsignadoId,
                             Instant bloqueadoDesde, Boolean completado) {}
