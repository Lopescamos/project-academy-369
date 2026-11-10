package com.olimpiadas369.backend.vision.application.commandservices;

import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;
import com.olimpiadas369.backend.vision.domain.model.commands.ProcesarImagenCommand;
import com.olimpiadas369.backend.vision.domain.model.commands.ReprocesarRegistroCommand;

public interface VisionCommandService {
    Result<Long, ApplicationError> handle(ProcesarImagenCommand c);
    Result<Long, ApplicationError> handle(ReprocesarRegistroCommand c);
}
