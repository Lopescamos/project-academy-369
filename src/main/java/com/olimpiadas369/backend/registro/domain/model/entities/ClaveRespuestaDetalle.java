package com.olimpiadas369.backend.registro.domain.model.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ClaveRespuestaDetalle {
    @Setter private Long id;
    @Setter private Short numeroPregunta;
    @Setter private String opcionCorrecta;

    public ClaveRespuestaDetalle() {}
    public ClaveRespuestaDetalle(Short numeroPregunta, String opcionCorrecta) {
        this.numeroPregunta = numeroPregunta;
        this.opcionCorrecta = opcionCorrecta;
    }
}
