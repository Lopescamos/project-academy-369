package com.olimpiadas369.backend.identidad.interfaces.rest.transform;

import com.olimpiadas369.backend.identidad.domain.model.commands.CreateUsuarioCommand;
import com.olimpiadas369.backend.identidad.interfaces.rest.resources.CreateUsuarioResource;

public final class CreateUsuarioCommandFromResourceAssembler {
    private CreateUsuarioCommandFromResourceAssembler() {}
    public static CreateUsuarioCommand toCommandFromResource(CreateUsuarioResource r) {
        return new CreateUsuarioCommand(r.nombreCompleto(), r.correo(), r.rol());
    }
}
