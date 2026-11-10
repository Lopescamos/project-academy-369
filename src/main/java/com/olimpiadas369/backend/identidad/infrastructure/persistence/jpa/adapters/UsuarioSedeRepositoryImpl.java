package com.olimpiadas369.backend.identidad.infrastructure.persistence.jpa.adapters;

import com.olimpiadas369.backend.identidad.domain.model.aggregates.UsuarioSede;
import com.olimpiadas369.backend.identidad.domain.repositories.UsuarioSedeRepository;
import com.olimpiadas369.backend.identidad.infrastructure.persistence.jpa.assemblers.UsuarioSedePersistenceAssembler;
import com.olimpiadas369.backend.identidad.infrastructure.persistence.jpa.repositories.UsuarioSedePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioSedeRepositoryImpl implements UsuarioSedeRepository {

    private final UsuarioSedePersistenceRepository jpa;

    public UsuarioSedeRepositoryImpl(UsuarioSedePersistenceRepository jpa) { this.jpa = jpa; }

    @Override public List<UsuarioSede> findByUsuarioId(Long id) {
        return jpa.findByUsuarioId(id).stream().map(UsuarioSedePersistenceAssembler::toDomainFromPersistence).toList();
    }
    @Override public List<UsuarioSede> findBySedeId(Long id) {
        return jpa.findBySedeId(id).stream().map(UsuarioSedePersistenceAssembler::toDomainFromPersistence).toList();
    }
    @Override public Optional<UsuarioSede> findByUsuarioIdAndSedeId(Long usuarioId, Long sedeId) {
        return jpa.findByUsuarioIdAndSedeId(usuarioId, sedeId).map(UsuarioSedePersistenceAssembler::toDomainFromPersistence);
    }
    @Override public boolean existsByUsuarioIdAndSedeId(Long usuarioId, Long sedeId) {
        return jpa.existsByUsuarioIdAndSedeId(usuarioId, sedeId);
    }
    @Override public UsuarioSede save(UsuarioSede us) {
        return UsuarioSedePersistenceAssembler.toDomainFromPersistence(
                jpa.save(UsuarioSedePersistenceAssembler.toPersistenceFromDomain(us)));
    }
}
