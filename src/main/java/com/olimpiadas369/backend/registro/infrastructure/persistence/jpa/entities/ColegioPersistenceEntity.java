package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities;

import com.olimpiadas369.backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "colegios")
@Getter @Setter @NoArgsConstructor
public class ColegioPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false, length = 160, unique = true) private String nombre;
    @Column(nullable = false, length = 160) private String nombreNormalizado;
}
