package com.olimpiadas369.backend.registro.domain.repositories;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Sede;

import java.util.List;
import java.util.Optional;

public interface SedeRepository {
    Optional<Sede> findById(Long id);
    List<Sede> findAll();
    Sede save(Sede sede);
}
