package com.olimpiadas369.backend.registro.domain.model.aggregates;

import com.olimpiadas369.backend.registro.domain.model.commands.CreateSedeCommand;
import com.olimpiadas369.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
public class Sede extends AbstractDomainAggregateRoot<Sede> {

    @Setter private Long id;
    @Setter private String nombre;
    @Setter private String direccion;
    @Setter private LocalDate fechaEvento;
    @Setter private String estado;

    public Sede() { this.estado = "PROGRAMADA"; }

    public Sede(CreateSedeCommand c) {
        this.nombre = c.nombre();
        this.direccion = c.direccion();
        this.fechaEvento = c.fechaEvento();
        this.estado = "PROGRAMADA";
    }

    public void update(String nombre, String direccion, LocalDate fechaEvento) {
        if (nombre != null && !nombre.isBlank()) this.nombre = nombre;
        if (direccion != null) this.direccion = direccion;
        if (fechaEvento != null) this.fechaEvento = fechaEvento;
    }
}
