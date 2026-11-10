package com.olimpiadas369.backend.registro.domain.model.commands;

import com.olimpiadas369.backend.registro.domain.model.valueobjects.*;

public record CreateBloqueCommand(Long sedeId, Curso curso, Nivel nivel, Grado grado, Long tutorAsignadoId) {}
