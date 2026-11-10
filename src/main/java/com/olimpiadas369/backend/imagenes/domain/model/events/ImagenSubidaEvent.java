package com.olimpiadas369.backend.imagenes.domain.model.events;

public record ImagenSubidaEvent(Long imagenId, Long loteId, String storagePathCensurada) {}
