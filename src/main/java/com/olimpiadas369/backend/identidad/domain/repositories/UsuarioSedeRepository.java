package com.olimpiadas369.backend.identidad.domain.repositories;

import com.olimpiadas369.backend.identidad.domain.model.aggregates.UsuarioSede;

import java.util.List;
import java.util.Optional;

public interface UsuarioSedeRepository {
    List<UsuarioSede> findByUsuarioId(Long usuarioId);
    List<UsuarioSede> findBySedeId(Long sedeId);
    Optional<UsuarioSede> findByUsuarioIdAndSedeId(Long usuarioId, Long sedeId);
    boolean existsByUsuarioIdAndSedeId(Long usuarioId, Long sedeId);
    UsuarioSede save(UsuarioSede usuarioSede);
}
