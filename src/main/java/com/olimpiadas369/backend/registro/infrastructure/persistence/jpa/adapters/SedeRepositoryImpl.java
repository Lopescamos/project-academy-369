package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.adapters;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Sede;
import com.olimpiadas369.backend.registro.domain.repositories.SedeRepository;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.assemblers.SedePersistenceAssembler;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.repositories.SedePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SedeRepositoryImpl implements SedeRepository {
    private final SedePersistenceRepository jpa;
    public SedeRepositoryImpl(SedePersistenceRepository jpa) { this.jpa = jpa; }
    @Override public Optional<Sede> findById(Long id) { return jpa.findById(id).map(SedePersistenceAssembler::toDomainFromPersistence); }
    @Override public List<Sede> findAll() { return jpa.findAll().stream().map(SedePersistenceAssembler::toDomainFromPersistence).toList(); }
    @Override public Sede save(Sede s) { return SedePersistenceAssembler.toDomainFromPersistence(jpa.save(SedePersistenceAssembler.toPersistenceFromDomain(s))); }
}
