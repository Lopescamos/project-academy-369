package com.olimpiadas369.backend.imagenes.application.commandservices;

import com.olimpiadas369.backend.imagenes.domain.model.commands.ReplaceImagenCommand;
import com.olimpiadas369.backend.imagenes.domain.model.commands.UploadImagenCommand;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;

public interface ImagenCommandService {
    Result<Long, ApplicationError> handle(UploadImagenCommand c);
    Result<Long, ApplicationError> handle(ReplaceImagenCommand c);
}
