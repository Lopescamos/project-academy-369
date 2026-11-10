package com.olimpiadas369.backend.imagenes.application.acl;

import com.olimpiadas369.backend.imagenes.application.queryservices.ImagenQueryService;
import com.olimpiadas369.backend.imagenes.domain.model.queries.GetImagenByIdQuery;
import com.olimpiadas369.backend.imagenes.interfaces.acl.ImagenesContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ImagenesContextFacadeImpl implements ImagenesContextFacade {
    private final ImagenQueryService queries;
    public ImagenesContextFacadeImpl(ImagenQueryService queries) { this.queries = queries; }
    @Override public String fetchStoragePathCensurada(Long id) {
        return queries.handle(new GetImagenByIdQuery(id)).map(i -> i.getStoragePathCensurada()).orElse(null);
    }
    @Override public String fetchSignedUrlCensurada(Long id) { return queries.generarSignedUrl(id, true); }
}
