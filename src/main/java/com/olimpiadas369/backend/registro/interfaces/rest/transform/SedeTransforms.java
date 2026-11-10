package com.olimpiadas369.backend.registro.interfaces.rest.transform;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Sede;
import com.olimpiadas369.backend.registro.domain.model.commands.CreateSedeCommand;
import com.olimpiadas369.backend.registro.interfaces.rest.resources.CreateSedeResource;
import com.olimpiadas369.backend.registro.interfaces.rest.resources.SedeResource;

public final class SedeTransforms {
    private SedeTransforms() {}
    public static CreateSedeCommand toCommandFromResource(CreateSedeResource r) {
        return new CreateSedeCommand(r.nombre(), r.direccion(), r.fechaEvento());
    }
    public static SedeResource toResourceFromEntity(Sede s) {
        return new SedeResource(s.getId(), s.getNombre(), s.getDireccion(), s.getFechaEvento(), s.getEstado());
    }
}
