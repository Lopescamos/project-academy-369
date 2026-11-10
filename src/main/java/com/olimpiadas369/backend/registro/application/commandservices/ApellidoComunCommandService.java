package com.olimpiadas369.backend.registro.application.commandservices;

import com.olimpiadas369.backend.registro.domain.model.commands.CreateApellidoComunCommand;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;

public interface ApellidoComunCommandService {
    Result<Long, ApplicationError> handle(CreateApellidoComunCommand c);
}
