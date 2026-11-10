package com.olimpiadas369.backend.audit.domain.model.aggregates;

import com.olimpiadas369.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
public class AuditoriaAccion extends AbstractDomainAggregateRoot<AuditoriaAccion> {

    @Setter private Long id;
    @Setter private Long usuarioId;
    @Setter private String accion;
    @Setter private String entidad;
    @Setter private Long entidadId;
    @Setter private String detalleJson;
    @Setter private Instant createdAt;

    public AuditoriaAccion() {}

    public AuditoriaAccion(Long usuarioId, String accion, String entidad, Long entidadId, String detalleJson) {
        this.usuarioId = usuarioId;
        this.accion = accion;
        this.entidad = entidad;
        this.entidadId = entidadId;
        this.detalleJson = detalleJson;
        this.createdAt = Instant.now();
    }
}
