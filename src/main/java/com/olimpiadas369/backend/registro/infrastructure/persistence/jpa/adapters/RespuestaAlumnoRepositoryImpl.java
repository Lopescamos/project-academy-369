package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.adapters;

import com.olimpiadas369.backend.registro.domain.model.aggregates.RespuestaAlumno;
import com.olimpiadas369.backend.registro.domain.repositories.RespuestaAlumnoRepository;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.assemblers.RespuestaAlumnoPersistenceAssembler;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.repositories.RespuestaAlumnoPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RespuestaAlumnoRepositoryImpl implements RespuestaAlumnoRepository {
    private final RespuestaAlumnoPersistenceRepository jpa;
    public RespuestaAlumnoRepositoryImpl(RespuestaAlumnoPersistenceRepository jpa) { this.jpa = jpa; }
    @Override public List<RespuestaAlumno> findByRegistroId(Long id) { return jpa.findByRegistroId(id).stream().map(RespuestaAlumnoPersistenceAssembler::toDomainFromPersistence).toList(); }
    @Override public RespuestaAlumno save(RespuestaAlumno r) { return RespuestaAlumnoPersistenceAssembler.toDomainFromPersistence(jpa.save(RespuestaAlumnoPersistenceAssembler.toPersistenceFromDomain(r))); }
    @Override public void saveAll(List<RespuestaAlumno> rs) {
        jpa.saveAll(rs.stream().map(RespuestaAlumnoPersistenceAssembler::toPersistenceFromDomain).toList());
    }
    @Override @Transactional public void deleteByRegistroId(Long id) { jpa.deleteByRegistroId(id); }
}
