package com.olimpiadas369.backend.identidad.application.internal.queryservices;

import com.olimpiadas369.backend.identidad.application.queryservices.UsuarioQueryService;
import com.olimpiadas369.backend.identidad.domain.model.aggregates.Usuario;
import com.olimpiadas369.backend.identidad.domain.model.queries.*;
import com.olimpiadas369.backend.identidad.domain.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioQueryServiceImpl implements UsuarioQueryService {

    private final UsuarioRepository repository;

    public UsuarioQueryServiceImpl(UsuarioRepository repository) { this.repository = repository; }

    @Override public Optional<Usuario> handle(GetUsuarioByIdQuery q) { return repository.findById(q.usuarioId()); }
    @Override public Optional<Usuario> handle(GetUsuarioByCorreoQuery q) { return repository.findByCorreo(q.correo()); }
    @Override public List<Usuario> handle(GetAllUsuariosQuery q) { return repository.findAll(); }
}
