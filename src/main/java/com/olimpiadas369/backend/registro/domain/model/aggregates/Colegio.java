package com.olimpiadas369.backend.registro.domain.model.aggregates;

import com.olimpiadas369.backend.registro.domain.services.Normalizador;
import com.olimpiadas369.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Colegio extends AbstractDomainAggregateRoot<Colegio> {

    @Setter private Long id;
    @Setter private String nombre;
    @Setter private String nombreNormalizado;

    public Colegio() {}

    public Colegio(String nombre) {
        this.nombre = nombre;
        this.nombreNormalizado = Normalizador.normalizar(nombre);
    }
}
