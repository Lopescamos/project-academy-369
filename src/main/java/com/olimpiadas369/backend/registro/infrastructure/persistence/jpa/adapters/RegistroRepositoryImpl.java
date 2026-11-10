package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.adapters;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Registro;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.EstadoRegistro;
import com.olimpiadas369.backend.registro.domain.repositories.RegistroRepository;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.assemblers.RegistroPersistenceAssembler;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.repositories.RegistroPersistenceRepository;
import com.olimpiadas369.backend.shared.infrastructure.events.SpringDomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RegistroRepositoryImpl implements RegistroRepository {
    private final RegistroPersistenceRepository jpa;
    private final SpringDomainEventPublisher events;
    public RegistroRepositoryImpl(RegistroPersistenceRepository jpa, SpringDomainEventPublisher events) {
        this.jpa = jpa; this.events = events;
    }
    @Override public Optional<Registro> findById(Long id) { return jpa.findById(id).map(RegistroPersistenceAssembler::toDomainFromPersistence); }
    @Override public List<Registro> findByBloqueId(Long id) { return jpa.findByBloqueId(id).stream().map(RegistroPersistenceAssembler::toDomainFromPersistence).toList(); }
    @Override public List<Registro> findConfirmadosByBloqueId(Long id) { return jpa.findByBloqueIdAndEstado(id, EstadoRegistro.CONFIRMADO).stream().map(RegistroPersistenceAssembler::toDomainFromPersistence).toList(); }
    @Override public Registro save(Registro r) {
        var saved = RegistroPersistenceAssembler.toDomainFromPersistence(jpa.save(RegistroPersistenceAssembler.toPersistenceFromDomain(r)));
        events.publishAndClear(r);
        return saved;
    }
}
