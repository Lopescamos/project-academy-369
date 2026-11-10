package com.olimpiadas369.backend.registro.interfaces.rest.transform;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Bloque;
import com.olimpiadas369.backend.registro.domain.model.commands.CreateBloqueCommand;
import com.olimpiadas369.backend.registro.interfaces.rest.resources.BloqueResource;
import com.olimpiadas369.backend.registro.interfaces.rest.resources.CreateBloqueResource;

public final class BloqueTransforms {
    private BloqueTransforms() {}
    public static CreateBloqueCommand toCommandFromResource(CreateBloqueResource r) {
        return new CreateBloqueCommand(r.sedeId(), r.curso(), r.nivel(), r.grado(), r.tutorAsignadoId());
    }
    public static BloqueResource toResourceFromEntity(Bloque b) {
        return new BloqueResource(b.getId(), b.getSedeId(), b.getCurso(), b.getNivel(), b.getGrado(),
                b.getTutorAsignadoId(), b.getBloqueadoDesde(), b.getCompletado());
    }
}
