package com.olimpiadas369.backend.registro.application.acl;

import com.olimpiadas369.backend.registro.domain.repositories.LoteRepository;
import com.olimpiadas369.backend.registro.domain.repositories.RegistroRepository;
import com.olimpiadas369.backend.registro.interfaces.acl.RegistroContextFacade;
import org.springframework.stereotype.Service;

@Service
public class RegistroContextFacadeImpl implements RegistroContextFacade {

    private final LoteRepository lotes;
    private final RegistroRepository registros;

    public RegistroContextFacadeImpl(LoteRepository lotes, RegistroRepository registros) {
        this.lotes = lotes; this.registros = registros;
    }

    @Override public long fetchLoteIdIfExists(Long id) {
        return lotes.findById(id).map(l -> l.getId()).orElse(0L);
    }
    @Override public long fetchBloqueIdFromRegistroConfirmado(Long id) {
        return registros.findById(id).filter(r -> r.estaConfirmado()).map(r -> r.getBloqueId()).orElse(0L);
    }
    @Override public boolean loteExiste(Long id) { return fetchLoteIdIfExists(id) != 0L; }
    @Override public boolean registroExiste(Long id) { return registros.findById(id).isPresent(); }
}
