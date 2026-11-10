package com.olimpiadas369.backend.identidad.infrastructure.persistence.jpa.adapters;

import com.olimpiadas369.backend.identidad.domain.model.aggregates.Usuario;
import com.olimpiadas369.backend.identidad.domain.model.valueobjects.Correo;
import com.olimpiadas369.backend.identidad.domain.repositories.UsuarioRepository;
import com.olimpiadas369.backend.identidad.infrastructure.persistence.jpa.assemblers.UsuarioPersistenceAssembler;
import com.olimpiadas369.backend.identidad.infrastructure.persistence.jpa.repositories.UsuarioPersistenceRepository;
import com.olimpiadas369.backend.shared.infrastructure.events.SpringDomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final UsuarioPersistenceRepository jpa;
    private final SpringDomainEventPublisher events;

    public UsuarioRepositoryImpl(UsuarioPersistenceRepository jpa, SpringDomainEventPublisher events) {
        this.jpa = jpa;
        this.events = events;
    }

    @Override public Optional<Usuario> findById(Long id) {
        return jpa.findById(id).map(UsuarioPersistenceAssembler::toDomainFromPersistence);
    }

    @Override public Optional<Usuario> findByCorreo(String correo) {
        return jpa.findByCorreo(new Correo(correo)).map(UsuarioPersistenceAssembler::toDomainFromPersistence);
    }

    @Override public boolean existsByCorreo(String correo) { return jpa.existsByCorreo(new Correo(correo)); }

    @Override public List<Usuario> findAll() {
        return jpa.findAll().stream().map(UsuarioPersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override public Usuario save(Usuario usuario) {
        var saved = UsuarioPersistenceAssembler.toDomainFromPersistence(
                jpa.save(UsuarioPersistenceAssembler.toPersistenceFromDomain(usuario)));
        // Los domain events se registran en el aggregate entrante. Se publican tras persistir.
        events.publishAndClear(usuario);
        return saved;
    }

    @Override public long count() { return jpa.count(); }
}
