package com.olimpiadas369.backend.registro.domain.model.aggregates;

import com.olimpiadas369.backend.registro.domain.model.commands.AplicarClaveCorreccionCommand;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.TipoCorreccionClave;
import com.olimpiadas369.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ClaveCorreccion extends AbstractDomainAggregateRoot<ClaveCorreccion> {

    @Setter private Long id;
    @Setter private Long claveRespuestasId;
    @Setter private TipoCorreccionClave tipo;
    @Setter private Short numeroPregunta;
    @Setter private String opcionAnterior;
    @Setter private String opcionNueva;
    @Setter private String motivo;
    @Setter private Long aplicadoPorId;
    @Setter private Integer fichasRecalculadas;

    public ClaveCorreccion() { this.fichasRecalculadas = 0; }

    public ClaveCorreccion(AplicarClaveCorreccionCommand c, Long aplicadoPorId) {
        this.claveRespuestasId = c.claveRespuestasId();
        this.tipo = c.tipo();
        this.numeroPregunta = c.numeroPregunta();
        this.opcionAnterior = c.opcionAnterior();
        this.opcionNueva = c.opcionNueva();
        this.motivo = c.motivo();
        this.aplicadoPorId = aplicadoPorId;
        this.fichasRecalculadas = 0;
    }

    public void registrarFichasRecalculadas(int n) { this.fichasRecalculadas = n; }
}
