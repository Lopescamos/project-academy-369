package com.olimpiadas369.backend.imagenes.infrastructure.persistence.jpa.assemblers;

import com.olimpiadas369.backend.imagenes.domain.model.aggregates.Imagen;
import com.olimpiadas369.backend.imagenes.infrastructure.persistence.jpa.entities.ImagenPersistenceEntity;

public final class ImagenPersistenceAssembler {
    private ImagenPersistenceAssembler() {}
    public static Imagen toDomainFromPersistence(ImagenPersistenceEntity e) {
        if (e == null) return null;
        Imagen i = new Imagen();
        i.setId(e.getId()); i.setLoteId(e.getLoteId()); i.setOrdenEnLote(e.getOrdenEnLote());
        i.setStoragePath(e.getStoragePath()); i.setStoragePathCensurada(e.getStoragePathCensurada());
        i.setBytes(e.getBytes()); i.setHashSha256(e.getHashSha256()); i.setReemplazaImagenId(e.getReemplazaImagenId());
        return i;
    }
    public static ImagenPersistenceEntity toPersistenceFromDomain(Imagen i) {
        if (i == null) return null;
        ImagenPersistenceEntity e = new ImagenPersistenceEntity();
        if (i.getId() != null) e.setId(i.getId());
        e.setLoteId(i.getLoteId()); e.setOrdenEnLote(i.getOrdenEnLote());
        e.setStoragePath(i.getStoragePath()); e.setStoragePathCensurada(i.getStoragePathCensurada());
        e.setBytes(i.getBytes()); e.setHashSha256(i.getHashSha256()); e.setReemplazaImagenId(i.getReemplazaImagenId());
        return e;
    }
}
