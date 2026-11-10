-- Audit transversal: log append-only de acciones

CREATE TABLE auditoria_acciones (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT REFERENCES usuarios(id),
    accion VARCHAR(60) NOT NULL,
    entidad VARCHAR(40) NOT NULL,
    entidad_id BIGINT,
    detalle_json JSONB,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_auditoria_usuario ON auditoria_acciones(usuario_id);
CREATE INDEX idx_auditoria_entidad ON auditoria_acciones(entidad, entidad_id);
CREATE INDEX idx_auditoria_created ON auditoria_acciones(created_at DESC);
