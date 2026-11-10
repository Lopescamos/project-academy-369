package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities;

import com.olimpiadas369.backend.registro.domain.model.valueobjects.EstadoRegistro;
import com.olimpiadas369.backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;

@Entity @Table(name = "registros")
@Getter @Setter @NoArgsConstructor
public class RegistroPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false) private Long loteId;
    @Column(nullable = false) private Long bloqueId;
    @Column(nullable = false) private Long imagenId;
    @Column(length = 120) private String nombre;
    @Column(length = 120) private String apellidos;
    private Long colegioId;
    @Column(length = 160) private String colegioTextoExtraido;
    private LocalTime horaFinalizacion;
    private Short correctas;
    private Short incorrectas;
    private Short anuladas;
    private Short blancos;
    private Integer puntaje;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 30) private EstadoRegistro estado;
    @Column(nullable = false) private Boolean tieneObservacion;
    @Column(precision = 4, scale = 3) private BigDecimal confianzaPromedio;
    private Long revisorActualId;
    private Instant revisorTomadaAt;
    private Long confirmadoPorId;
    private Instant confirmadoAt;
}
