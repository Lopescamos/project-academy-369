package com.olimpiadas369.backend.registro.application.internal.commandservices;

import com.olimpiadas369.backend.registro.application.commandservices.ColegioCommandService;
import com.olimpiadas369.backend.registro.domain.model.aggregates.Colegio;
import com.olimpiadas369.backend.registro.domain.model.commands.CreateColegioCommand;
import com.olimpiadas369.backend.registro.domain.repositories.ColegioRepository;
import com.olimpiadas369.backend.registro.domain.services.Normalizador;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class ColegioCommandServiceImpl implements ColegioCommandService {
    private final ColegioRepository repo;
    public ColegioCommandServiceImpl(ColegioRepository repo) { this.repo = repo; }
    @Override public Result<Long, ApplicationError> handle(CreateColegioCommand c) {
        String norm = Normalizador.normalizar(c.nombre());
        if (repo.findByNombreNormalizado(norm).isPresent())
            return Result.failure(ApplicationError.conflict("Colegio", "A school with normalized name '%s' already exists".formatted(norm)));
        try { return Result.success(repo.save(new Colegio(c.nombre())).getId()); }
        catch (Exception e) { return Result.failure(ApplicationError.unexpected("create-colegio", e.getMessage())); }
    }
}
