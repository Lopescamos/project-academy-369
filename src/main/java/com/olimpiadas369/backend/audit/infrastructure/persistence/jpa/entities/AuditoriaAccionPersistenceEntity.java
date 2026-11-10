package com.olimpiadas369.backend.audit.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity @Table(name = "auditoria_acciones")
@Getter @Setter @NoArgsConstructor
public class AuditoriaAccionPersistenceEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long usuarioId;
    @Column(nullable = false, length = 60) private String accion;
    @Column(nullable = false, length = 40) private String entidad;
    private Long entidadId;
    @Column(columnDefinition = "TEXT") private String detalleJson;
    @Column(nullable = false) private Instant createdAt;
}
