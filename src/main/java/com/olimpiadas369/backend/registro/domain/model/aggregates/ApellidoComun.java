package com.olimpiadas369.backend.registro.domain.model.aggregates;

import com.olimpiadas369.backend.registro.domain.services.Normalizador;
import com.olimpiadas369.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ApellidoComun extends AbstractDomainAggregateRoot<ApellidoComun> {

    @Setter private Long id;
    @Setter private String apellido;
    @Setter private String apellidoNormalizado;

    public ApellidoComun() {}

    public ApellidoComun(String apellido) {
        this.apellido = apellido;
        this.apellidoNormalizado = Normalizador.normalizar(apellido);
    }
}
