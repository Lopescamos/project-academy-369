package com.olimpiadas369.backend.exportacion.application.commandservices;

import com.olimpiadas369.backend.exportacion.domain.model.commands.ExportarRegistrosBloqueCommand;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;

public interface ExportacionCommandService {
    /** Devuelve los bytes del Excel generado. */
    Result<byte[], ApplicationError> handle(ExportarRegistrosBloqueCommand c);
}
