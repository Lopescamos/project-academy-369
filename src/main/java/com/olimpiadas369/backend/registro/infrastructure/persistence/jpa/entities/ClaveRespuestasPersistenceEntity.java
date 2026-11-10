package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities;

import com.olimpiadas369.backend.registro.domain.model.valueobjects.Curso;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.Grado;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.Nivel;
import com.olimpiadas369.backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "clave_respuestas")
@Getter @Setter @NoArgsConstructor
public class ClaveRespuestasPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Curso curso;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Nivel nivel;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Grado grado;
    private Long creadoPorId;
    @Column(nullable = false) private Integer version;

    @OneToMany(mappedBy = "claveRespuestasId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ClaveRespuestaDetallePersistenceEntity> detalles = new ArrayList<>();
}
