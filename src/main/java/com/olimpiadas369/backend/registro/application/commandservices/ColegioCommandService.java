package com.olimpiadas369.backend.registro.application.commandservices;

import com.olimpiadas369.backend.registro.domain.model.commands.CreateColegioCommand;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;

public interface ColegioCommandService {
    Result<Long, ApplicationError> handle(CreateColegioCommand c);
}
