package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities;

import com.olimpiadas369.backend.registro.domain.model.valueobjects.Curso;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.Grado;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.Nivel;
import com.olimpiadas369.backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity @Table(name = "bloques")
@Getter @Setter @NoArgsConstructor
public class BloquePersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false) private Long sedeId;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Curso curso;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Nivel nivel;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Grado grado;
    private Long tutorAsignadoId;
    private Instant bloqueadoDesde;
    @Column(nullable = false) private Boolean completado;
    private Instant completadoEn;
    private Long completadoPorId;
}
