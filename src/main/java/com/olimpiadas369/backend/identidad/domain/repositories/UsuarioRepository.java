package com.olimpiadas369.backend.identidad.domain.repositories;

import com.olimpiadas369.backend.identidad.domain.model.aggregates.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    List<Usuario> findAll();
    Usuario save(Usuario usuario);
    long count();
}
