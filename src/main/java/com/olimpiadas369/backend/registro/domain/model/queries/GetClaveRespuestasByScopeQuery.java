package com.olimpiadas369.backend.registro.domain.model.queries;

import com.olimpiadas369.backend.registro.domain.model.valueobjects.*;

public record GetClaveRespuestasByScopeQuery(Curso curso, Nivel nivel, Grado grado) {}
