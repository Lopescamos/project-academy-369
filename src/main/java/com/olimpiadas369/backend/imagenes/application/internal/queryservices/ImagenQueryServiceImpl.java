package com.olimpiadas369.backend.imagenes.application.internal.queryservices;

import com.olimpiadas369.backend.imagenes.application.internal.outboundservices.storage.FileStorageService;
import com.olimpiadas369.backend.imagenes.application.queryservices.ImagenQueryService;
import com.olimpiadas369.backend.imagenes.domain.model.aggregates.Imagen;
import com.olimpiadas369.backend.imagenes.domain.model.queries.GetImagenByIdQuery;
import com.olimpiadas369.backend.imagenes.domain.model.queries.GetImagenesByLoteQuery;
import com.olimpiadas369.backend.imagenes.domain.repositories.ImagenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImagenQueryServiceImpl implements ImagenQueryService {
    private static final int SIGNED_URL_TTL = 300; // 5 min

    private final ImagenRepository imagenes;
    private final FileStorageService storage;
    public ImagenQueryServiceImpl(ImagenRepository imagenes, FileStorageService storage) {
        this.imagenes = imagenes; this.storage = storage;
    }
    @Override public Optional<Imagen> handle(GetImagenByIdQuery q) { return imagenes.findById(q.imagenId()); }
    @Override public List<Imagen> handle(GetImagenesByLoteQuery q) { return imagenes.findByLoteId(q.loteId()); }
    @Override public String generarSignedUrl(Long imagenId, boolean censurada) {
        return imagenes.findById(imagenId)
                .map(i -> censurada ? i.getStoragePathCensurada() : i.getStoragePath())
                .map(p -> storage.signedReadUrl(p, SIGNED_URL_TTL))
                .orElse(null);
    }
}
