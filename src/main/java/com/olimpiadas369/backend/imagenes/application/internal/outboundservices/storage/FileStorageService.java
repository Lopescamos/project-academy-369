package com.olimpiadas369.backend.imagenes.application.internal.outboundservices.storage;

/** Puerto de almacenamiento de archivos (fotos originales y censuradas). */
public interface FileStorageService {
    /** Sube bytes y devuelve el path en el storage (clave). */
    String upload(String path, byte[] content, String contentType);
    /** Signed URL de corta duración para que el cliente la lea sin exponer el bucket. */
    String signedReadUrl(String path, int expirationSeconds);
    void delete(String path);
}
