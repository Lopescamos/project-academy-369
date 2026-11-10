package com.olimpiadas369.backend.imagenes.domain.repositories;

import com.olimpiadas369.backend.imagenes.domain.model.aggregates.Imagen;

import java.util.List;
import java.util.Optional;

public interface ImagenRepository {
    Optional<Imagen> findById(Long id);
    List<Imagen> findByLoteId(Long loteId);
    Imagen save(Imagen imagen);
}
