-- Identidad y acceso: Usuario, UsuarioSede

CREATE TYPE rol_usuario AS ENUM ('ADMIN', 'TUTOR');
CREATE TYPE estado_usuario AS ENUM ('ACTIVO', 'INACTIVO');

CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombre_completo VARCHAR(160) NOT NULL,
    correo VARCHAR(160) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    desactivado_en TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_usuarios_correo ON usuarios(correo);
CREATE INDEX idx_usuarios_estado ON usuarios(estado);

CREATE TABLE usuario_sedes (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    sede_id BIGINT NOT NULL,
    fecha_asignacion TIMESTAMPTZ NOT NULL,
    activo_en_sede BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE INDEX idx_usuario_sedes_usuario ON usuario_sedes(usuario_id);
CREATE INDEX idx_usuario_sedes_sede ON usuario_sedes(sede_id);
