package com.olimpiadas369.backend.identidad.interfaces.rest.transform;

import com.olimpiadas369.backend.identidad.domain.model.commands.UpdateUsuarioCommand;
import com.olimpiadas369.backend.identidad.interfaces.rest.resources.UpdateUsuarioResource;

public final class UpdateUsuarioCommandFromResourceAssembler {
    private UpdateUsuarioCommandFromResourceAssembler() {}
    public static UpdateUsuarioCommand toCommandFromResource(Long usuarioId, UpdateUsuarioResource r) {
        return new UpdateUsuarioCommand(usuarioId, r.nombreCompleto(), r.rol());
    }
}
