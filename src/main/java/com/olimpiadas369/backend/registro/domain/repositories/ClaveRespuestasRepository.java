package com.olimpiadas369.backend.registro.domain.repositories;

import com.olimpiadas369.backend.registro.domain.model.aggregates.ClaveRespuestas;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.Curso;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.Grado;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.Nivel;

import java.util.Optional;

public interface ClaveRespuestasRepository {
    Optional<ClaveRespuestas> findById(Long id);
    Optional<ClaveRespuestas> findByScope(Curso curso, Nivel nivel, Grado grado);
    boolean existsByScope(Curso curso, Nivel nivel, Grado grado);
    ClaveRespuestas save(ClaveRespuestas clave);
}
