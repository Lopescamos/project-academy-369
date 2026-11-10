package com.olimpiadas369.backend.registro.application.commandservices;

import com.olimpiadas369.backend.registro.domain.model.commands.CreateLoteCommand;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;

public interface LoteCommandService {
    Result<Long, ApplicationError> handle(CreateLoteCommand c);
}
