package com.olimpiadas369.backend.audit.application.internal.commandservices;

import com.olimpiadas369.backend.audit.domain.model.aggregates.AuditoriaAccion;
import com.olimpiadas369.backend.audit.domain.repositories.AuditoriaAccionRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaAccionService {
    private final AuditoriaAccionRepository repo;
    public AuditoriaAccionService(AuditoriaAccionRepository repo) { this.repo = repo; }
    public AuditoriaAccion registrar(Long usuarioId, String accion, String entidad, Long entidadId, String detalleJson) {
        return repo.save(new AuditoriaAccion(usuarioId, accion, entidad, entidadId, detalleJson));
    }
}
