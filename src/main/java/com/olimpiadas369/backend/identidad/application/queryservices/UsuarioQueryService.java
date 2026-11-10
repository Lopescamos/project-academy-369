package com.olimpiadas369.backend.identidad.application.queryservices;

import com.olimpiadas369.backend.identidad.domain.model.aggregates.Usuario;
import com.olimpiadas369.backend.identidad.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface UsuarioQueryService {
    Optional<Usuario> handle(GetUsuarioByIdQuery query);
    Optional<Usuario> handle(GetUsuarioByCorreoQuery query);
    List<Usuario> handle(GetAllUsuariosQuery query);
}
