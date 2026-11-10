package com.olimpiadas369.backend.vision.domain.services;

/**
 * Puerto para extraer la ficha de una imagen usando un modelo multimodal.
 * La extracción es "el corazón del aggregate Registro" — por eso va en domain/services
 * aunque por debajo llame a Gemini/Claude/etc.
 */
public interface VisionProvider {
    /** Devuelve el resultado estructurado (JSON) de procesar la imagen por signed URL. */
    VisionResult extraerDesdeUrl(String imagenSignedUrl);
}
