package com.olimpiadas369.backend.imagenes.domain.services;

/**
 * Censor por recorte de coordenadas fijas (DNI/celular) ANTES de enviar a Visión.
 * Es regla de negocio: el DNI nunca se persiste ni se envía al proveedor externo.
 */
public interface CensorService {
    /** Devuelve los bytes de la imagen ya censurada, en el mismo formato de entrada. */
    byte[] censurar(byte[] original, String contentType);
}
