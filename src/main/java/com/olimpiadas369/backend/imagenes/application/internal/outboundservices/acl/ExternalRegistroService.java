package com.olimpiadas369.backend.imagenes.application.internal.outboundservices.acl;

import com.olimpiadas369.backend.registro.interfaces.acl.RegistroContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalRegistroService {
    private final RegistroContextFacade registro;
    public ExternalRegistroService(RegistroContextFacade registro) { this.registro = registro; }
    public Optional<Long> fetchLoteId(Long loteId) {
        long id = registro.fetchLoteIdIfExists(loteId);
        return id == 0L ? Optional.empty() : Optional.of(id);
    }
    public boolean loteExiste(Long loteId) { return registro.loteExiste(loteId); }
}
