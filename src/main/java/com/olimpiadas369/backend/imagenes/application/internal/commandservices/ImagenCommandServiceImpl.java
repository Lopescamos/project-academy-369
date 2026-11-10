package com.olimpiadas369.backend.imagenes.application.internal.commandservices;

import com.olimpiadas369.backend.imagenes.application.commandservices.ImagenCommandService;
import com.olimpiadas369.backend.imagenes.application.internal.outboundservices.acl.ExternalRegistroService;
import com.olimpiadas369.backend.imagenes.application.internal.outboundservices.storage.FileStorageService;
import com.olimpiadas369.backend.imagenes.domain.model.aggregates.Imagen;
import com.olimpiadas369.backend.imagenes.domain.model.commands.ReplaceImagenCommand;
import com.olimpiadas369.backend.imagenes.domain.model.commands.UploadImagenCommand;
import com.olimpiadas369.backend.imagenes.domain.model.events.ImagenSubidaEvent;
import com.olimpiadas369.backend.imagenes.domain.repositories.ImagenRepository;
import com.olimpiadas369.backend.imagenes.domain.services.CensorService;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.HexFormat;

@Service
public class ImagenCommandServiceImpl implements ImagenCommandService {

    private final ImagenRepository imagenes;
    private final FileStorageService storage;
    private final CensorService censor;
    private final ExternalRegistroService registro;

    public ImagenCommandServiceImpl(ImagenRepository imagenes, FileStorageService storage,
                                    CensorService censor, ExternalRegistroService registro) {
        this.imagenes = imagenes; this.storage = storage; this.censor = censor; this.registro = registro;
    }

    @Override
    public Result<Long, ApplicationError> handle(UploadImagenCommand c) {
        if (!registro.loteExiste(c.loteId()))
            return Result.failure(ApplicationError.notFound("Lote", c.loteId().toString()));
        try {
            String hash = sha256(c.contenido());
            // Sube original
            String pathOriginal = "lotes/%d/original/%d_%s".formatted(c.loteId(), System.currentTimeMillis(), hash.substring(0, 8));
            storage.upload(pathOriginal, c.contenido(), c.contentType());
            // Censura y sube censurada
            byte[] censurada = censor.censurar(c.contenido(), c.contentType());
            String pathCensurada = pathOriginal + "_censurada";
            storage.upload(pathCensurada, censurada, c.contentType());

            Imagen img = new Imagen(c.loteId(), c.ordenEnLote(), pathOriginal, (long) c.contenido().length, hash);
            img.registrarCensurada(pathCensurada);
            Imagen saved = imagenes.save(img);
            return Result.success(saved.getId());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("upload-imagen", e.getMessage()));
        }
    }

    @Override
    public Result<Long, ApplicationError> handle(ReplaceImagenCommand c) {
        var orig = imagenes.findById(c.imagenOriginalId());
        if (orig.isEmpty()) return Result.failure(ApplicationError.notFound("Imagen", c.imagenOriginalId().toString()));
        try {
            Imagen o = orig.get();
            String hash = sha256(c.contenido());
            String pathOriginal = "lotes/%d/original/%d_%s_r".formatted(o.getLoteId(), System.currentTimeMillis(), hash.substring(0, 8));
            storage.upload(pathOriginal, c.contenido(), c.contentType());
            byte[] censurada = censor.censurar(c.contenido(), c.contentType());
            String pathCensurada = pathOriginal + "_censurada";
            storage.upload(pathCensurada, censurada, c.contentType());

            Imagen nueva = new Imagen(o.getLoteId(), o.getOrdenEnLote(), pathOriginal, (long) c.contenido().length, hash);
            nueva.registrarCensurada(pathCensurada);
            nueva.marcarComoReemplazoDe(o.getId());
            return Result.success(imagenes.save(nueva).getId());
        } catch (Exception e) { return Result.failure(ApplicationError.unexpected("replace-imagen", e.getMessage())); }
    }

    private static String sha256(byte[] b) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return HexFormat.of().formatHex(md.digest(b));
    }
}
