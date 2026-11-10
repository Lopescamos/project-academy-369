package com.olimpiadas369.backend.identidad.application.commandservices;

import com.olimpiadas369.backend.identidad.domain.model.aggregates.UsuarioSede;
import com.olimpiadas369.backend.identidad.domain.model.commands.*;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;

public interface UsuarioCommandService {
    Result<Long, ApplicationError> handle(CreateUsuarioCommand command);
    Result<Long, ApplicationError> handle(UpdateUsuarioCommand command);
    Result<Long, ApplicationError> handle(DeactivateUsuarioCommand command);
    Result<Long, ApplicationError> handle(ReactivateUsuarioCommand command);
    Result<UsuarioSede, ApplicationError> handle(AssignUsuarioToSedeCommand command);
}
