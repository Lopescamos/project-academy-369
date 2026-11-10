package com.olimpiadas369.backend.imagenes.interfaces.acl;

/** Cara pública del BC Imágenes para que Visión acceda a la foto censurada por id. */
public interface ImagenesContextFacade {
    /** Devuelve la ruta en el storage de la imagen CENSURADA; null si no existe. */
    String fetchStoragePathCensurada(Long imagenId);
    /** Signed URL de corta duración, lista para que Visión la pase al proveedor. */
    String fetchSignedUrlCensurada(Long imagenId);
}
