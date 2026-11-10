package com.olimpiadas369.backend.registro.application.internal.commandservices;

import com.olimpiadas369.backend.registro.application.commandservices.LoteCommandService;
import com.olimpiadas369.backend.registro.application.internal.outboundservices.acl.ExternalIdentidadService;
import com.olimpiadas369.backend.registro.domain.model.aggregates.Lote;
import com.olimpiadas369.backend.registro.domain.model.commands.CreateLoteCommand;
import com.olimpiadas369.backend.registro.domain.repositories.BloqueRepository;
import com.olimpiadas369.backend.registro.domain.repositories.LoteRepository;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class LoteCommandServiceImpl implements LoteCommandService {

    private final LoteRepository lotes;
    private final BloqueRepository bloques;
    private final ExternalIdentidadService identidad;

    public LoteCommandServiceImpl(LoteRepository lotes, BloqueRepository bloques, ExternalIdentidadService identidad) {
        this.lotes = lotes; this.bloques = bloques; this.identidad = identidad;
    }

    @Override public Result<Long, ApplicationError> handle(CreateLoteCommand c) {
        if (bloques.findById(c.bloqueId()).isEmpty())
            return Result.failure(ApplicationError.notFound("Bloque", c.bloqueId().toString()));
        if (!identidad.usuarioExisteYActivo(c.tutorId()))
            return Result.failure(ApplicationError.notFound("Usuario", c.tutorId().toString()));
        try { return Result.success(lotes.save(new Lote(c)).getId()); }
        catch (Exception e) { return Result.failure(ApplicationError.unexpected("create-lote", e.getMessage())); }
    }
}
