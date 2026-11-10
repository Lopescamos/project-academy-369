package com.olimpiadas369.backend.identidad.domain.model.aggregates;

import com.olimpiadas369.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
public class UsuarioSede extends AbstractDomainAggregateRoot<UsuarioSede> {

    @Setter private Long id;
    @Setter private Long usuarioId;
    @Setter private Long sedeId;
    @Setter private Instant fechaAsignacion;
    @Setter private Boolean activoEnSede;

    public UsuarioSede() {}

    public UsuarioSede(Long usuarioId, Long sedeId) {
        this.usuarioId = usuarioId;
        this.sedeId = sedeId;
        this.fechaAsignacion = Instant.now();
        this.activoEnSede = true;
    }

    public void deactivate() { this.activoEnSede = false; }
    public void reactivate() { this.activoEnSede = true; }
}
