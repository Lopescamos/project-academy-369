package com.olimpiadas369.backend.registro.application.internal.outboundservices.acl;

import com.olimpiadas369.backend.identidad.interfaces.acl.IdentidadContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** Wrapper del lado consumidor: traduce los primitivos del facade a Optional/value objects locales. */
@Service
public class ExternalIdentidadService {

    private final IdentidadContextFacade identidad;

    public ExternalIdentidadService(IdentidadContextFacade identidad) { this.identidad = identidad; }

    public Optional<Long> fetchUsuarioActivoId(Long usuarioId) {
        long id = identidad.fetchUsuarioIdIfActive(usuarioId);
        return id == 0L ? Optional.empty() : Optional.of(id);
    }

    public boolean usuarioExisteYActivo(Long usuarioId) { return identidad.existsAndActive(usuarioId); }
}
