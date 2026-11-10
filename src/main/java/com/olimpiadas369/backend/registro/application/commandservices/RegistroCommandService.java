package com.olimpiadas369.backend.registro.application.commandservices;

import com.olimpiadas369.backend.registro.domain.model.commands.ConfirmarRegistroCommand;
import com.olimpiadas369.backend.registro.domain.model.commands.TomarRegistroEnRevisionCommand;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;

public interface RegistroCommandService {
    Result<Long, ApplicationError> handle(TomarRegistroEnRevisionCommand c);
    Result<Long, ApplicationError> handle(ConfirmarRegistroCommand c);
}
