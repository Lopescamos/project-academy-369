package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "clave_respuesta_detalles")
@Getter @Setter @NoArgsConstructor
public class ClaveRespuestaDetallePersistenceEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private Long claveRespuestasId;
    @Column(nullable = false) private Short numeroPregunta;
    @Column(nullable = false, length = 2) private String opcionCorrecta;
}
