package com.olimpiadas369.backend.identidad.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "usuario_sedes")
@Getter @Setter @NoArgsConstructor
public class UsuarioSedePersistenceEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private Long sedeId;

    @Column(nullable = false)
    private Instant fechaAsignacion;

    @Column(nullable = false)
    private Boolean activoEnSede;
}
