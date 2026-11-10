package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities;

import com.olimpiadas369.backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity @Table(name = "sedes")
@Getter @Setter @NoArgsConstructor
public class SedePersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false, length = 120) private String nombre;
    @Column(length = 255) private String direccion;
    @Column(nullable = false) private LocalDate fechaEvento;
    @Column(nullable = false, length = 20) private String estado;
}
