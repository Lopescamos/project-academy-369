-- Registro: Sede, Colegio, ApellidoComun, Bloque, ClaveRespuestas (+ detalle + corrección), Lote
-- Y tablas de cierre: Registro, RespuestaAlumno

CREATE TYPE curso AS ENUM ('COMUNICACION', 'MATEMATICA', 'CIENCIA');
CREATE TYPE nivel AS ENUM ('INICIAL', 'PRIMARIA', 'SECUNDARIA');
CREATE TYPE grado AS ENUM ('TRES', 'CUATRO', 'CINCO', 'PRIMERO', 'SEGUNDO', 'TERCERO', 'CUARTO', 'QUINTO', 'SEXTO');
CREATE TYPE estado_lote AS ENUM ('ABIERTO', 'SUBIENDO', 'SUBIDO', 'PROCESADO');
CREATE TYPE estado_registro AS ENUM ('EXTRAIDO', 'EN_REVISION', 'CON_OBSERVACION', 'CONFIRMADO');
CREATE TYPE opcion AS ENUM ('A', 'B', 'C', 'D', 'E');
CREATE TYPE tipo_correccion_clave AS ENUM ('ERROR_REGISTRO', 'PREGUNTA_MAL_PLANTEADA');

CREATE TABLE sedes (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    direccion VARCHAR(255),
    fecha_evento DATE NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PROGRAMADA',
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE colegios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(160) NOT NULL UNIQUE,
    nombre_normalizado VARCHAR(160) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_colegios_normalizado ON colegios(nombre_normalizado);

CREATE TABLE apellido_comunes (
    id BIGSERIAL PRIMARY KEY,
    apellido VARCHAR(80) NOT NULL UNIQUE,
    apellido_normalizado VARCHAR(80) NOT NULL
);

CREATE INDEX idx_apellido_comunes_normalizado ON apellido_comunes(apellido_normalizado);

CREATE TABLE bloques (
    id BIGSERIAL PRIMARY KEY,
    sede_id BIGINT NOT NULL REFERENCES sedes(id),
    curso VARCHAR(20) NOT NULL,
    nivel VARCHAR(20) NOT NULL,
    grado VARCHAR(20) NOT NULL,
    tutor_asignado_id BIGINT REFERENCES usuarios(id),
    bloqueado_desde TIMESTAMPTZ,
    completado BOOLEAN NOT NULL DEFAULT FALSE,
    completado_en TIMESTAMPTZ,
    completado_por_id BIGINT REFERENCES usuarios(id),
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    UNIQUE (sede_id, curso, nivel, grado)
);

CREATE TABLE clave_respuestas (
    id BIGSERIAL PRIMARY KEY,
    curso VARCHAR(20) NOT NULL,
    nivel VARCHAR(20) NOT NULL,
    grado VARCHAR(20) NOT NULL,
    creado_por_id BIGINT REFERENCES usuarios(id),
    version INT NOT NULL DEFAULT 1,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    UNIQUE (curso, nivel, grado)
);

CREATE TABLE clave_respuesta_detalles (
    id BIGSERIAL PRIMARY KEY,
    clave_respuestas_id BIGINT NOT NULL REFERENCES clave_respuestas(id) ON DELETE CASCADE,
    numero_pregunta SMALLINT NOT NULL,
    opcion_correcta VARCHAR(2) NOT NULL,
    UNIQUE (clave_respuestas_id, numero_pregunta)
);

CREATE TABLE clave_correcciones (
    id BIGSERIAL PRIMARY KEY,
    clave_respuestas_id BIGINT NOT NULL REFERENCES clave_respuestas(id),
    tipo VARCHAR(40) NOT NULL,
    numero_pregunta SMALLINT NOT NULL,
    opcion_anterior VARCHAR(2),
    opcion_nueva VARCHAR(2),
    motivo TEXT,
    aplicado_por_id BIGINT REFERENCES usuarios(id),
    fichas_recalculadas INT DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE lotes (
    id BIGSERIAL PRIMARY KEY,
    bloque_id BIGINT NOT NULL REFERENCES bloques(id),
    tutor_id BIGINT NOT NULL REFERENCES usuarios(id),
    estado VARCHAR(20) NOT NULL DEFAULT 'ABIERTO',
    total_fotos_esperadas INT,
    total_fotos_recibidas INT NOT NULL DEFAULT 0,
    ultima_foto_tomada_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_lotes_bloque ON lotes(bloque_id);
