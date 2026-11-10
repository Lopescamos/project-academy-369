package com.olimpiadas369.backend.registro.application.internal.commandservices;

import com.olimpiadas369.backend.registro.application.commandservices.BloqueCommandService;
import com.olimpiadas369.backend.registro.application.internal.outboundservices.acl.ExternalIdentidadService;
import com.olimpiadas369.backend.registro.domain.model.aggregates.Bloque;
import com.olimpiadas369.backend.registro.domain.model.commands.CompletarBloqueCommand;
import com.olimpiadas369.backend.registro.domain.model.commands.CreateBloqueCommand;
import com.olimpiadas369.backend.registro.domain.repositories.BloqueRepository;
import com.olimpiadas369.backend.registro.domain.repositories.ClaveRespuestasRepository;
import com.olimpiadas369.backend.registro.domain.repositories.SedeRepository;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class BloqueCommandServiceImpl implements BloqueCommandService {

    private final BloqueRepository bloques;
    private final SedeRepository sedes;
    private final ClaveRespuestasRepository claves;
    private final ExternalIdentidadService identidad;

    public BloqueCommandServiceImpl(BloqueRepository bloques, SedeRepository sedes,
                                    ClaveRespuestasRepository claves, ExternalIdentidadService identidad) {
        this.bloques = bloques; this.sedes = sedes; this.claves = claves; this.identidad = identidad;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateBloqueCommand c) {
        if (sedes.findById(c.sedeId()).isEmpty())
            return Result.failure(ApplicationError.notFound("Sede", c.sedeId().toString()));
        if (bloques.existsBySedeAndScope(c.sedeId(), c.curso(), c.nivel(), c.grado()))
            return Result.failure(ApplicationError.conflict("Bloque", "A bloque already exists for that sede+scope"));
        // Prerrequisito: la clave debe existir para el scope (salvo INICIAL, que no tiene clave)
        if (c.nivel() != com.olimpiadas369.backend.registro.domain.model.valueobjects.Nivel.INICIAL
                && !claves.existsByScope(c.curso(), c.nivel(), c.grado()))
            return Result.failure(ApplicationError.businessRuleViolation("Bloque",
                    "No answer key exists for scope (curso=%s, nivel=%s, grado=%s)".formatted(c.curso(), c.nivel(), c.grado())));
        if (c.tutorAsignadoId() != null && !identidad.usuarioExisteYActivo(c.tutorAsignadoId()))
            return Result.failure(ApplicationError.notFound("Usuario", c.tutorAsignadoId().toString()));
        try { return Result.success(bloques.save(new Bloque(c)).getId()); }
        catch (Exception e) { return Result.failure(ApplicationError.unexpected("create-bloque", e.getMessage())); }
    }

    @Override
    public Result<Long, ApplicationError> handle(CompletarBloqueCommand c) {
        var b = bloques.findById(c.bloqueId());
        if (b.isEmpty()) return Result.failure(ApplicationError.notFound("Bloque", c.bloqueId().toString()));
        try { Bloque bb = b.get(); bb.marcarCompletado(c.usuarioId()); return Result.success(bloques.save(bb).getId()); }
        catch (Exception e) { return Result.failure(ApplicationError.unexpected("completar-bloque", e.getMessage())); }
    }

    @Override
    public Result<Long, ApplicationError> seleccionarBloque(Long bloqueId) {
        var b = bloques.findById(bloqueId);
        if (b.isEmpty()) return Result.failure(ApplicationError.notFound("Bloque", bloqueId.toString()));
        try { Bloque bb = b.get(); bb.bloquearPorSeleccion(); return Result.success(bloques.save(bb).getId()); }
        catch (Exception e) { return Result.failure(ApplicationError.unexpected("seleccionar-bloque", e.getMessage())); }
    }
}
