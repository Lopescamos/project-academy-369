package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities;

import com.olimpiadas369.backend.registro.domain.model.valueobjects.TipoCorreccionClave;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity @Table(name = "clave_correcciones")
@Getter @Setter @NoArgsConstructor
public class ClaveCorreccionPersistenceEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private Long claveRespuestasId;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 40) private TipoCorreccionClave tipo;
    @Column(nullable = false) private Short numeroPregunta;
    @Column(length = 2) private String opcionAnterior;
    @Column(length = 2) private String opcionNueva;
    @Column(columnDefinition = "TEXT") private String motivo;
    private Long aplicadoPorId;
    private Integer fichasRecalculadas;
    @Column(nullable = false) private Instant createdAt;
    @PrePersist public void prePersist() { if (createdAt == null) createdAt = Instant.now(); }
}
