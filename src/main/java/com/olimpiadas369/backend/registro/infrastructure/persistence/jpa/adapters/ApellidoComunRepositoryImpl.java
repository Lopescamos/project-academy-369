package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.adapters;

import com.olimpiadas369.backend.registro.domain.model.aggregates.ApellidoComun;
import com.olimpiadas369.backend.registro.domain.repositories.ApellidoComunRepository;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.assemblers.ApellidoComunPersistenceAssembler;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.repositories.ApellidoComunPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ApellidoComunRepositoryImpl implements ApellidoComunRepository {
    private final ApellidoComunPersistenceRepository jpa;
    public ApellidoComunRepositoryImpl(ApellidoComunPersistenceRepository jpa) { this.jpa = jpa; }
    @Override public Optional<ApellidoComun> findById(Long id) { return jpa.findById(id).map(ApellidoComunPersistenceAssembler::toDomainFromPersistence); }
    @Override public List<ApellidoComun> findAll() { return jpa.findAll().stream().map(ApellidoComunPersistenceAssembler::toDomainFromPersistence).toList(); }
    @Override public boolean existsByNormalizado(String n) { return jpa.existsByApellidoNormalizado(n); }
    @Override public ApellidoComun save(ApellidoComun a) { return ApellidoComunPersistenceAssembler.toDomainFromPersistence(jpa.save(ApellidoComunPersistenceAssembler.toPersistenceFromDomain(a))); }
}
