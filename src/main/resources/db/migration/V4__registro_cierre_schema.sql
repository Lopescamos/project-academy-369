-- Registro (cierre): Registro + Respuesta_Alumno
-- Se separan de V2 porque dependen de imagenes (V3) y colegios (V2).

CREATE TABLE registros (
    id BIGSERIAL PRIMARY KEY,
    lote_id BIGINT NOT NULL REFERENCES lotes(id),
    bloque_id BIGINT NOT NULL REFERENCES bloques(id),
    imagen_id BIGINT NOT NULL REFERENCES imagenes(id),
    nombre VARCHAR(120),
    apellidos VARCHAR(120),
    colegio_id BIGINT REFERENCES colegios(id),
    colegio_texto_extraido VARCHAR(160),
    hora_finalizacion TIME,
    correctas SMALLINT DEFAULT 0,
    incorrectas SMALLINT DEFAULT 0,
    anuladas SMALLINT DEFAULT 0,
    blancos SMALLINT DEFAULT 0,
    puntaje INT DEFAULT 0,
    estado VARCHAR(30) NOT NULL DEFAULT 'EXTRAIDO',
    tiene_observacion BOOLEAN NOT NULL DEFAULT FALSE,
    confianza_promedio NUMERIC(4,3),
    revisor_actual_id BIGINT REFERENCES usuarios(id),
    revisor_tomada_at TIMESTAMPTZ,
    confirmado_por_id BIGINT REFERENCES usuarios(id),
    confirmado_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_registros_lote ON registros(lote_id);
CREATE INDEX idx_registros_bloque ON registros(bloque_id);
CREATE INDEX idx_registros_estado ON registros(estado);
CREATE INDEX idx_registros_colegio ON registros(colegio_id);

CREATE TABLE respuesta_alumnos (
    id BIGSERIAL PRIMARY KEY,
    registro_id BIGINT NOT NULL REFERENCES registros(id) ON DELETE CASCADE,
    numero_pregunta SMALLINT NOT NULL,
    opcion_marcada VARCHAR(2),
    es_correcta BOOLEAN,
    estados_opciones_json JSONB,
    revisada_por_humano BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    UNIQUE (registro_id, numero_pregunta)
);
