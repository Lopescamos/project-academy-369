package com.olimpiadas369.backend.registro.application.internal.commandservices;

import com.olimpiadas369.backend.registro.application.commandservices.ApellidoComunCommandService;
import com.olimpiadas369.backend.registro.domain.model.aggregates.ApellidoComun;
import com.olimpiadas369.backend.registro.domain.model.commands.CreateApellidoComunCommand;
import com.olimpiadas369.backend.registro.domain.repositories.ApellidoComunRepository;
import com.olimpiadas369.backend.registro.domain.services.Normalizador;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class ApellidoComunCommandServiceImpl implements ApellidoComunCommandService {
    private final ApellidoComunRepository repo;
    public ApellidoComunCommandServiceImpl(ApellidoComunRepository repo) { this.repo = repo; }
    @Override public Result<Long, ApplicationError> handle(CreateApellidoComunCommand c) {
        String norm = Normalizador.normalizar(c.apellido());
        if (repo.existsByNormalizado(norm))
            return Result.failure(ApplicationError.conflict("ApellidoComun", "Already exists"));
        try { return Result.success(repo.save(new ApellidoComun(c.apellido())).getId()); }
        catch (Exception e) { return Result.failure(ApplicationError.unexpected("create-apellido-comun", e.getMessage())); }
    }
}
