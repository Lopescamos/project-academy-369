package com.olimpiadas369.backend.imagenes.domain.model.commands;

public record ReplaceImagenCommand(Long imagenOriginalId, byte[] contenido, String contentType) {}
