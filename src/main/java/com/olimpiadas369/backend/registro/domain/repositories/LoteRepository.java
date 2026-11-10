package com.olimpiadas369.backend.registro.domain.repositories;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Lote;

import java.util.List;
import java.util.Optional;

public interface LoteRepository {
    Optional<Lote> findById(Long id);
    List<Lote> findByBloqueId(Long bloqueId);
    Lote save(Lote lote);
}
