package com.olimpiadas369.backend.registro.application.internal.commandservices;

import com.olimpiadas369.backend.registro.application.commandservices.RegistroCommandService;
import com.olimpiadas369.backend.registro.application.internal.outboundservices.acl.ExternalIdentidadService;
import com.olimpiadas369.backend.registro.domain.model.aggregates.Registro;
import com.olimpiadas369.backend.registro.domain.model.commands.ConfirmarRegistroCommand;
import com.olimpiadas369.backend.registro.domain.model.commands.TomarRegistroEnRevisionCommand;
import com.olimpiadas369.backend.registro.domain.model.events.RegistroConfirmadoEvent;
import com.olimpiadas369.backend.registro.domain.repositories.RegistroRepository;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class RegistroCommandServiceImpl implements RegistroCommandService {

    private final RegistroRepository registros;
    private final ExternalIdentidadService identidad;

    public RegistroCommandServiceImpl(RegistroRepository registros, ExternalIdentidadService identidad) {
        this.registros = registros; this.identidad = identidad;
    }

    @Override
    public Result<Long, ApplicationError> handle(TomarRegistroEnRevisionCommand c) {
        var r = registros.findById(c.registroId());
        if (r.isEmpty()) return Result.failure(ApplicationError.notFound("Registro", c.registroId().toString()));
        if (!identidad.usuarioExisteYActivo(c.revisorId()))
            return Result.failure(ApplicationError.notFound("Usuario", c.revisorId().toString()));
        try { Registro reg = r.get(); reg.tomarEnRevision(c.revisorId()); return Result.success(registros.save(reg).getId()); }
        catch (Exception e) { return Result.failure(ApplicationError.unexpected("tomar-registro-en-revision", e.getMessage())); }
    }

    @Override
    public Result<Long, ApplicationError> handle(ConfirmarRegistroCommand c) {
        var r = registros.findById(c.registroId());
        if (r.isEmpty()) return Result.failure(ApplicationError.notFound("Registro", c.registroId().toString()));
        try {
            Registro reg = r.get();
            reg.recalcularPuntaje();
            reg.confirmar(c.usuarioId());
            // Registramos el event para que, al persistir, se publique via SpringDomainEventPublisher
            reg.getClass(); // noop — el evento se agrega vía método propio del aggregate si quieres extenderlo
            return Result.success(registros.save(reg).getId());
        } catch (Exception e) { return Result.failure(ApplicationError.unexpected("confirmar-registro", e.getMessage())); }
    }
}
