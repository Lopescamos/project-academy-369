package com.olimpiadas369.backend.registro.domain.model.commands;

import com.olimpiadas369.backend.registro.domain.model.entities.ClaveRespuestaDetalle;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.*;

import java.util.List;

public record CreateClaveRespuestasCommand(Curso curso, Nivel nivel, Grado grado, List<ClaveRespuestaDetalle> detalles) {}
