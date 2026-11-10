package com.olimpiadas369.backend.registro.domain.model.aggregates;

import com.olimpiadas369.backend.registro.domain.model.valueobjects.Opcion;
import com.olimpiadas369.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

@Getter
public class RespuestaAlumno extends AbstractDomainAggregateRoot<RespuestaAlumno> {

    @Setter private Long id;
    @Setter private Long registroId;
    @Setter private Short numeroPregunta;
    @Setter private Opcion opcionMarcada;
    @Setter private Boolean esCorrecta;
    @Setter private String estadosOpcionesJson;
    @Setter private Boolean revisadaPorHumano;

    public RespuestaAlumno() { this.revisadaPorHumano = false; }

    public RespuestaAlumno(Long registroId, Short numeroPregunta, Opcion opcion, Boolean esCorrecta, String estadosJson) {
        this.registroId = registroId;
        this.numeroPregunta = numeroPregunta;
        this.opcionMarcada = opcion;
        this.esCorrecta = esCorrecta;
        this.estadosOpcionesJson = estadosJson;
        this.revisadaPorHumano = false;
    }

    public void corregir(Opcion nueva, Boolean esCorrectaNueva) {
        this.opcionMarcada = nueva;
        this.esCorrecta = esCorrectaNueva;
        this.revisadaPorHumano = true;
    }
}
