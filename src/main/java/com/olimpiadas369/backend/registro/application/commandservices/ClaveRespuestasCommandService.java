package com.olimpiadas369.backend.registro.application.commandservices;

import com.olimpiadas369.backend.registro.domain.model.commands.AplicarClaveCorreccionCommand;
import com.olimpiadas369.backend.registro.domain.model.commands.CreateClaveRespuestasCommand;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;

public interface ClaveRespuestasCommandService {
    Result<Long, ApplicationError> handle(CreateClaveRespuestasCommand c);
    Result<Long, ApplicationError> handle(AplicarClaveCorreccionCommand c);
}
