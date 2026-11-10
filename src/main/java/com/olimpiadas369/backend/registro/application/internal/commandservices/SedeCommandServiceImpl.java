package com.olimpiadas369.backend.registro.application.internal.commandservices;

import com.olimpiadas369.backend.registro.application.commandservices.SedeCommandService;
import com.olimpiadas369.backend.registro.domain.model.aggregates.Sede;
import com.olimpiadas369.backend.registro.domain.model.commands.CreateSedeCommand;
import com.olimpiadas369.backend.registro.domain.model.commands.UpdateSedeCommand;
import com.olimpiadas369.backend.registro.domain.repositories.SedeRepository;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class SedeCommandServiceImpl implements SedeCommandService {
    private final SedeRepository repo;
    public SedeCommandServiceImpl(SedeRepository repo) { this.repo = repo; }

    @Override public Result<Long, ApplicationError> handle(CreateSedeCommand c) {
        try { return Result.success(repo.save(new Sede(c)).getId()); }
        catch (Exception e) { return Result.failure(ApplicationError.unexpected("create-sede", e.getMessage())); }
    }
    @Override public Result<Long, ApplicationError> handle(UpdateSedeCommand c) {
        var exist = repo.findById(c.sedeId());
        if (exist.isEmpty()) return Result.failure(ApplicationError.notFound("Sede", c.sedeId().toString()));
        try { Sede s = exist.get(); s.update(c.nombre(), c.direccion(), c.fechaEvento()); return Result.success(repo.save(s).getId()); }
        catch (Exception e) { return Result.failure(ApplicationError.unexpected("update-sede", e.getMessage())); }
    }
}
