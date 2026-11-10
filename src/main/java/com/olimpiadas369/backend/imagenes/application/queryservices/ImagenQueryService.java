package com.olimpiadas369.backend.imagenes.application.queryservices;

import com.olimpiadas369.backend.imagenes.domain.model.aggregates.Imagen;
import com.olimpiadas369.backend.imagenes.domain.model.queries.GetImagenByIdQuery;
import com.olimpiadas369.backend.imagenes.domain.model.queries.GetImagenesByLoteQuery;

import java.util.List;
import java.util.Optional;

public interface ImagenQueryService {
    Optional<Imagen> handle(GetImagenByIdQuery q);
    List<Imagen> handle(GetImagenesByLoteQuery q);
    String generarSignedUrl(Long imagenId, boolean censurada);
}
