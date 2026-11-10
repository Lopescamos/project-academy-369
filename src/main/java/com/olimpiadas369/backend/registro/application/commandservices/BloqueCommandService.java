package com.olimpiadas369.backend.registro.application.commandservices;

import com.olimpiadas369.backend.registro.domain.model.commands.CompletarBloqueCommand;
import com.olimpiadas369.backend.registro.domain.model.commands.CreateBloqueCommand;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;

public interface BloqueCommandService {
    Result<Long, ApplicationError> handle(CreateBloqueCommand c);
    Result<Long, ApplicationError> handle(CompletarBloqueCommand c);
    Result<Long, ApplicationError> seleccionarBloque(Long bloqueId);
}
