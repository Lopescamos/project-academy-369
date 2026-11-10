package com.olimpiadas369.backend.registro.domain.repositories;

import com.olimpiadas369.backend.registro.domain.model.aggregates.ClaveCorreccion;

import java.util.List;

public interface ClaveCorreccionRepository {
    ClaveCorreccion save(ClaveCorreccion cc);
    List<ClaveCorreccion> findByClaveRespuestasId(Long claveRespuestasId);
}
