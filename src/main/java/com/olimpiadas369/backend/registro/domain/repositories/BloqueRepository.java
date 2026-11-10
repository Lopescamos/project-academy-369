package com.olimpiadas369.backend.registro.domain.repositories;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Bloque;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.Curso;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.Grado;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.Nivel;

import java.util.List;
import java.util.Optional;

public interface BloqueRepository {
    Optional<Bloque> findById(Long id);
    List<Bloque> findBySedeId(Long sedeId);
    Optional<Bloque> findBySedeAndScope(Long sedeId, Curso curso, Nivel nivel, Grado grado);
    boolean existsBySedeAndScope(Long sedeId, Curso curso, Nivel nivel, Grado grado);
    Bloque save(Bloque bloque);
}
