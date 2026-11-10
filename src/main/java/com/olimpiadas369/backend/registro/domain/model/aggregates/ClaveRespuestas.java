package com.olimpiadas369.backend.registro.domain.model.aggregates;

import com.olimpiadas369.backend.registro.domain.model.commands.CreateClaveRespuestasCommand;
import com.olimpiadas369.backend.registro.domain.model.entities.ClaveRespuestaDetalle;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.Curso;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.Grado;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.Nivel;
import com.olimpiadas369.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Clave de respuestas. Scope: curso+nivel+grado (sin sede) — una sola clave sirve en todas las
 * sedes. Es prerrequisito duro: no se puede abrir un bloque sin que su clave exista.
 */
@Getter
public class ClaveRespuestas extends AbstractDomainAggregateRoot<ClaveRespuestas> {

    @Setter private Long id;
    @Setter private Curso curso;
    @Setter private Nivel nivel;
    @Setter private Grado grado;
    @Setter private Long creadoPorId;
    @Setter private Integer version;
    @Setter private List<ClaveRespuestaDetalle> detalles = new ArrayList<>();

    public ClaveRespuestas() { this.version = 1; }

    public ClaveRespuestas(CreateClaveRespuestasCommand c, Long creadoPorId) {
        this.curso = c.curso();
        this.nivel = c.nivel();
        this.grado = c.grado();
        this.creadoPorId = creadoPorId;
        this.version = 1;
        this.detalles = new ArrayList<>(c.detalles());
    }

    public void bumpVersion() { this.version = (this.version == null ? 1 : this.version + 1); }

    public void replaceDetalles(List<ClaveRespuestaDetalle> detalles) {
        this.detalles = new ArrayList<>(detalles);
        bumpVersion();
    }
}
