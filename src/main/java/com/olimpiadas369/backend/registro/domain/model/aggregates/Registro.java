package com.olimpiadas369.backend.registro.domain.model.aggregates;

import com.olimpiadas369.backend.registro.domain.model.valueobjects.EstadoRegistro;
import com.olimpiadas369.backend.registro.domain.services.PuntajeCalculator;
import com.olimpiadas369.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;

/** Registro de una ficha ya procesada por Visión. Se cierra cuando el revisor confirma. */
@Getter
public class Registro extends AbstractDomainAggregateRoot<Registro> {

    @Setter private Long id;
    @Setter private Long loteId;
    @Setter private Long bloqueId;
    @Setter private Long imagenId;
    @Setter private String nombre;
    @Setter private String apellidos;
    @Setter private Long colegioId;
    @Setter private String colegioTextoExtraido;
    @Setter private LocalTime horaFinalizacion;
    @Setter private Short correctas;
    @Setter private Short incorrectas;
    @Setter private Short anuladas;
    @Setter private Short blancos;
    @Setter private Integer puntaje;
    @Setter private EstadoRegistro estado;
    @Setter private Boolean tieneObservacion;
    @Setter private BigDecimal confianzaPromedio;
    @Setter private Long revisorActualId;
    @Setter private Instant revisorTomadaAt;
    @Setter private Long confirmadoPorId;
    @Setter private Instant confirmadoAt;

    public Registro() {
        this.estado = EstadoRegistro.EXTRAIDO;
        this.tieneObservacion = false;
    }

    public void tomarEnRevision(Long revisorId) {
        this.revisorActualId = revisorId;
        this.revisorTomadaAt = Instant.now();
        this.estado = EstadoRegistro.EN_REVISION;
    }

    public void marcarObservado() {
        this.tieneObservacion = true;
        this.estado = EstadoRegistro.CON_OBSERVACION;
    }

    public void confirmar(Long usuarioId) {
        this.confirmadoPorId = usuarioId;
        this.confirmadoAt = Instant.now();
        this.estado = EstadoRegistro.CONFIRMADO;
    }

    public void recalcularPuntaje() {
        short c = correctas == null ? 0 : correctas;
        short i = incorrectas == null ? 0 : incorrectas;
        this.puntaje = PuntajeCalculator.calcular(c, i);
    }

    public boolean estaConfirmado() { return estado == EstadoRegistro.CONFIRMADO; }
}
