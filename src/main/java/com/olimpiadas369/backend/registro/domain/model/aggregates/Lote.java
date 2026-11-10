package com.olimpiadas369.backend.registro.domain.model.aggregates;

import com.olimpiadas369.backend.registro.domain.model.commands.CreateLoteCommand;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.EstadoLote;
import com.olimpiadas369.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
public class Lote extends AbstractDomainAggregateRoot<Lote> {

    @Setter private Long id;
    @Setter private Long bloqueId;
    @Setter private Long tutorId;
    @Setter private EstadoLote estado;
    @Setter private Integer totalFotosEsperadas;
    @Setter private Integer totalFotosRecibidas;
    @Setter private Instant ultimaFotoTomadaAt;

    public Lote() { this.estado = EstadoLote.ABIERTO; this.totalFotosRecibidas = 0; }

    public Lote(CreateLoteCommand c) {
        this.bloqueId = c.bloqueId();
        this.tutorId = c.tutorId();
        this.estado = EstadoLote.ABIERTO;
        this.totalFotosEsperadas = c.totalFotosEsperadas();
        this.totalFotosRecibidas = 0;
    }

    public void registrarFotoRecibida() {
        this.totalFotosRecibidas = (this.totalFotosRecibidas == null ? 0 : this.totalFotosRecibidas) + 1;
        this.ultimaFotoTomadaAt = Instant.now();
        this.estado = EstadoLote.SUBIENDO;
        if (totalFotosEsperadas != null && totalFotosRecibidas.equals(totalFotosEsperadas))
            this.estado = EstadoLote.SUBIDO;
    }

    public void marcarProcesado() { this.estado = EstadoLote.PROCESADO; }

    public boolean estaCompleto() {
        return totalFotosEsperadas != null && totalFotosRecibidas != null
                && totalFotosRecibidas.intValue() >= totalFotosEsperadas.intValue();
    }
}
