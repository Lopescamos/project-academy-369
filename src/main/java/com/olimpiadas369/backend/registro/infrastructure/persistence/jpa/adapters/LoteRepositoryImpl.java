package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.adapters;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Lote;
import com.olimpiadas369.backend.registro.domain.repositories.LoteRepository;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.assemblers.LotePersistenceAssembler;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.repositories.LotePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LoteRepositoryImpl implements LoteRepository {
    private final LotePersistenceRepository jpa;
    public LoteRepositoryImpl(LotePersistenceRepository jpa) { this.jpa = jpa; }
    @Override public Optional<Lote> findById(Long id) { return jpa.findById(id).map(LotePersistenceAssembler::toDomainFromPersistence); }
    @Override public List<Lote> findByBloqueId(Long id) { return jpa.findByBloqueId(id).stream().map(LotePersistenceAssembler::toDomainFromPersistence).toList(); }
    @Override public Lote save(Lote l) { return LotePersistenceAssembler.toDomainFromPersistence(jpa.save(LotePersistenceAssembler.toPersistenceFromDomain(l))); }
}
