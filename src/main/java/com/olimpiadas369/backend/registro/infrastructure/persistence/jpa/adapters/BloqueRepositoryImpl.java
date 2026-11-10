package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.adapters;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Bloque;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.*;
import com.olimpiadas369.backend.registro.domain.repositories.BloqueRepository;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.assemblers.BloquePersistenceAssembler;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.repositories.BloquePersistenceRepository;
import com.olimpiadas369.backend.shared.infrastructure.events.SpringDomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BloqueRepositoryImpl implements BloqueRepository {
    private final BloquePersistenceRepository jpa;
    private final SpringDomainEventPublisher events;
    public BloqueRepositoryImpl(BloquePersistenceRepository jpa, SpringDomainEventPublisher events) {
        this.jpa = jpa; this.events = events;
    }
    @Override public Optional<Bloque> findById(Long id) { return jpa.findById(id).map(BloquePersistenceAssembler::toDomainFromPersistence); }
    @Override public List<Bloque> findBySedeId(Long sedeId) { return jpa.findBySedeId(sedeId).stream().map(BloquePersistenceAssembler::toDomainFromPersistence).toList(); }
    @Override public Optional<Bloque> findBySedeAndScope(Long sedeId, Curso c, Nivel n, Grado g) {
        return jpa.findBySedeIdAndCursoAndNivelAndGrado(sedeId, c, n, g).map(BloquePersistenceAssembler::toDomainFromPersistence);
    }
    @Override public boolean existsBySedeAndScope(Long sedeId, Curso c, Nivel n, Grado g) {
        return jpa.existsBySedeIdAndCursoAndNivelAndGrado(sedeId, c, n, g);
    }
    @Override public Bloque save(Bloque b) {
        var saved = BloquePersistenceAssembler.toDomainFromPersistence(jpa.save(BloquePersistenceAssembler.toPersistenceFromDomain(b)));
        events.publishAndClear(b);
        return saved;
    }
}
