package com.olimpiadas369.backend.identidad.application.commandservices;

import com.olimpiadas369.backend.identidad.domain.model.commands.LoginCommand;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;

public interface AuthCommandService {
    /** Devuelve el token firmado si las credenciales son válidas. */
    Result<String, ApplicationError> handle(LoginCommand command);
}
