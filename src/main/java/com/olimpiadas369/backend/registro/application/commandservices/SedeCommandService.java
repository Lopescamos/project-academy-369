package com.olimpiadas369.backend.registro.application.commandservices;

import com.olimpiadas369.backend.registro.domain.model.commands.CreateSedeCommand;
import com.olimpiadas369.backend.registro.domain.model.commands.UpdateSedeCommand;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;

public interface SedeCommandService {
    Result<Long, ApplicationError> handle(CreateSedeCommand c);
    Result<Long, ApplicationError> handle(UpdateSedeCommand c);
}
