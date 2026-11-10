package com.olimpiadas369.backend.audit.domain.repositories;

import com.olimpiadas369.backend.audit.domain.model.aggregates.AuditoriaAccion;

import java.util.List;

public interface AuditoriaAccionRepository {
    AuditoriaAccion save(AuditoriaAccion a);
    List<AuditoriaAccion> findByEntidadAndEntidadId(String entidad, Long entidadId);
    List<AuditoriaAccion> findByUsuarioId(Long usuarioId);
}
