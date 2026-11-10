package com.olimpiadas369.backend.registro.domain.repositories;

import com.olimpiadas369.backend.registro.domain.model.aggregates.ApellidoComun;

import java.util.List;
import java.util.Optional;

public interface ApellidoComunRepository {
    Optional<ApellidoComun> findById(Long id);
    List<ApellidoComun> findAll();
    boolean existsByNormalizado(String normalizado);
    ApellidoComun save(ApellidoComun ac);
}
