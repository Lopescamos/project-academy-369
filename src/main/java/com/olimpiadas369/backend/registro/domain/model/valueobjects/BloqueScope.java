package com.olimpiadas369.backend.registro.domain.model.valueobjects;

import java.util.Objects;

/** Scope curso+nivel+grado — define la identidad lógica de una clave de respuestas. */
public record BloqueScope(Curso curso, Nivel nivel, Grado grado) {
    public BloqueScope {
        Objects.requireNonNull(curso, "[BloqueScope] curso cannot be null");
        Objects.requireNonNull(nivel, "[BloqueScope] nivel cannot be null");
        Objects.requireNonNull(grado, "[BloqueScope] grado cannot be null");
    }

    public boolean esInicial() { return nivel == Nivel.INICIAL; }
}
