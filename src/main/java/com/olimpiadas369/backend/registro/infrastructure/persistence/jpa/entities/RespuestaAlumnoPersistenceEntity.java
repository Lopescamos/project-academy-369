package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities;

import com.olimpiadas369.backend.registro.domain.model.valueobjects.Opcion;
import com.olimpiadas369.backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "respuesta_alumnos")
@Getter @Setter @NoArgsConstructor
public class RespuestaAlumnoPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false) private Long registroId;
    @Column(nullable = false) private Short numeroPregunta;
    @Enumerated(EnumType.STRING) @Column(length = 2) private Opcion opcionMarcada;
    private Boolean esCorrecta;
    @Column(columnDefinition = "TEXT") private String estadosOpcionesJson;
    @Column(nullable = false) private Boolean revisadaPorHumano;
}
