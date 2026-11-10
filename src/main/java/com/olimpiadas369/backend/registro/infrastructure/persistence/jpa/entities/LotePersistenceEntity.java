package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities;

import com.olimpiadas369.backend.registro.domain.model.valueobjects.EstadoLote;
import com.olimpiadas369.backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity @Table(name = "lotes")
@Getter @Setter @NoArgsConstructor
public class LotePersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false) private Long bloqueId;
    @Column(nullable = false) private Long tutorId;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private EstadoLote estado;
    private Integer totalFotosEsperadas;
    @Column(nullable = false) private Integer totalFotosRecibidas;
    private Instant ultimaFotoTomadaAt;
}
