package com.olimpiadas369.backend.identidad.application.acl;

import com.olimpiadas369.backend.identidad.domain.model.valueobjects.EstadoUsuario;
import com.olimpiadas369.backend.identidad.domain.repositories.UsuarioRepository;
import com.olimpiadas369.backend.identidad.interfaces.acl.IdentidadContextFacade;
import org.springframework.stereotype.Service;

@Service
public class IdentidadContextFacadeImpl implements IdentidadContextFacade {

    private final UsuarioRepository usuarios;

    public IdentidadContextFacadeImpl(UsuarioRepository usuarios) { this.usuarios = usuarios; }

    @Override
    public long fetchUsuarioIdIfActive(Long usuarioId) {
        return usuarios.findById(usuarioId)
                .filter(u -> u.getEstado() == EstadoUsuario.ACTIVO)
                .map(u -> u.getId())
                .orElse(0L);
    }

    @Override
    public boolean existsAndActive(Long usuarioId) {
        return fetchUsuarioIdIfActive(usuarioId) != 0L;
    }
}
