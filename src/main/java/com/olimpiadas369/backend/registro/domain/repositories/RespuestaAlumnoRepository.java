package com.olimpiadas369.backend.registro.domain.repositories;

import com.olimpiadas369.backend.registro.domain.model.aggregates.RespuestaAlumno;

import java.util.List;

public interface RespuestaAlumnoRepository {
    List<RespuestaAlumno> findByRegistroId(Long registroId);
    RespuestaAlumno save(RespuestaAlumno r);
    void saveAll(List<RespuestaAlumno> rs);
    void deleteByRegistroId(Long registroId);
}
