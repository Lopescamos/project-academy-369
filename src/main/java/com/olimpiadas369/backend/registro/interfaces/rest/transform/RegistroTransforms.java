package com.olimpiadas369.backend.registro.interfaces.rest.transform;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Registro;
import com.olimpiadas369.backend.registro.interfaces.rest.resources.RegistroResource;

public final class RegistroTransforms {
    private RegistroTransforms() {}
    public static RegistroResource toResourceFromEntity(Registro r) {
        return new RegistroResource(r.getId(), r.getLoteId(), r.getBloqueId(), r.getImagenId(),
                r.getNombre(), r.getApellidos(), r.getColegioId(), r.getColegioTextoExtraido(),
                r.getCorrectas(), r.getIncorrectas(), r.getAnuladas(), r.getBlancos(), r.getPuntaje(),
                r.getEstado(), r.getTieneObservacion(), r.getConfianzaPromedio(),
                r.getConfirmadoPorId(), r.getConfirmadoAt());
    }
}
