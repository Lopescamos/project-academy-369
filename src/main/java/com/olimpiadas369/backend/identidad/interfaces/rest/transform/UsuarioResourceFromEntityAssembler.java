package com.olimpiadas369.backend.identidad.interfaces.rest.transform;

import com.olimpiadas369.backend.identidad.domain.model.aggregates.Usuario;
import com.olimpiadas369.backend.identidad.interfaces.rest.resources.UsuarioResource;

public final class UsuarioResourceFromEntityAssembler {
    private UsuarioResourceFromEntityAssembler() {}
    public static UsuarioResource toResourceFromEntity(Usuario u) {
        return new UsuarioResource(
                u.getId(), u.getNombreCompleto(),
                u.getCorreo() != null ? u.getCorreo().value() : null,
                u.getRol(), u.getEstado(), u.getDesactivadoEn());
    }
}
