package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.adapters;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Colegio;
import com.olimpiadas369.backend.registro.domain.repositories.ColegioRepository;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.assemblers.ColegioPersistenceAssembler;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.repositories.ColegioPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ColegioRepositoryImpl implements ColegioRepository {
    private final ColegioPersistenceRepository jpa;
    public ColegioRepositoryImpl(ColegioPersistenceRepository jpa) { this.jpa = jpa; }
    @Override public Optional<Colegio> findById(Long id) { return jpa.findById(id).map(ColegioPersistenceAssembler::toDomainFromPersistence); }
    @Override public Optional<Colegio> findByNombreNormalizado(String n) { return jpa.findByNombreNormalizado(n).map(ColegioPersistenceAssembler::toDomainFromPersistence); }
    @Override public List<Colegio> findAll() { return jpa.findAll().stream().map(ColegioPersistenceAssembler::toDomainFromPersistence).toList(); }
    @Override public Colegio save(Colegio c) { return ColegioPersistenceAssembler.toDomainFromPersistence(jpa.save(ColegioPersistenceAssembler.toPersistenceFromDomain(c))); }
}
