package com.olimpiadas369.backend.registro.domain.repositories;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Colegio;

import java.util.List;
import java.util.Optional;

public interface ColegioRepository {
    Optional<Colegio> findById(Long id);
    Optional<Colegio> findByNombreNormalizado(String normalizado);
    List<Colegio> findAll();
    Colegio save(Colegio colegio);
}
