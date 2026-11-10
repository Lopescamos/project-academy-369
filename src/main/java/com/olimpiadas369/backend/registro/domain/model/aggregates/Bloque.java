package com.olimpiadas369.backend.registro.domain.model.aggregates;

import com.olimpiadas369.backend.registro.domain.model.commands.CreateBloqueCommand;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.Curso;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.Grado;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.Nivel;
import com.olimpiadas369.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
public class Bloque extends AbstractDomainAggregateRoot<Bloque> {

    @Setter private Long id;
    @Setter private Long sedeId;
    @Setter private Curso curso;
    @Setter private Nivel nivel;
    @Setter private Grado grado;
    @Setter private Long tutorAsignadoId;
    @Setter private Instant bloqueadoDesde;
    @Setter private Boolean completado;
    @Setter private Instant completadoEn;
    @Setter private Long completadoPorId;

    public Bloque() { this.completado = false; }

    public Bloque(CreateBloqueCommand c) {
        this.sedeId = c.sedeId();
        this.curso = c.curso();
        this.nivel = c.nivel();
        this.grado = c.grado();
        this.tutorAsignadoId = c.tutorAsignadoId();
        this.completado = false;
    }

    /** Un bloque se bloquea desde la SELECCIÓN, no desde la subida de fotos. */
    public void bloquearPorSeleccion() {
        if (this.bloqueadoDesde == null) this.bloqueadoDesde = Instant.now();
    }

    public void marcarCompletado(Long porUsuarioId) {
        this.completado = true;
        this.completadoEn = Instant.now();
        this.completadoPorId = porUsuarioId;
    }

    public void asignarTutor(Long tutorId) { this.tutorAsignadoId = tutorId; }
}
