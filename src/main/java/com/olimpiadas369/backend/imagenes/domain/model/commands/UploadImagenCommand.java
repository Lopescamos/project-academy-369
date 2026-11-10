package com.olimpiadas369.backend.imagenes.domain.model.commands;

public record UploadImagenCommand(Long loteId, Integer ordenEnLote, byte[] contenido, String contentType) {}
