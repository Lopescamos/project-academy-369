package com.olimpiadas369.backend.vision.application.internal.outboundservices.acl;

import com.olimpiadas369.backend.imagenes.interfaces.acl.ImagenesContextFacade;
import com.olimpiadas369.backend.registro.interfaces.acl.RegistroContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** Wrappers hacia Imágenes (lectura de signed URL) y Registro (confirmar lote existe). */
@Service
public class VisionExternalServices {

    private final ImagenesContextFacade imagenes;
    private final RegistroContextFacade registro;

    public VisionExternalServices(ImagenesContextFacade imagenes, RegistroContextFacade registro) {
        this.imagenes = imagenes; this.registro = registro;
    }

    public Optional<String> fetchSignedUrlCensurada(Long imagenId) {
        return Optional.ofNullable(imagenes.fetchSignedUrlCensurada(imagenId));
    }
    public boolean registroExiste(Long registroId) { return registro.registroExiste(registroId); }
}
