-- Imágenes: una ficha física capturada por foto

CREATE TABLE imagenes (
    id BIGSERIAL PRIMARY KEY,
    lote_id BIGINT NOT NULL REFERENCES lotes(id),
    orden_en_lote INT NOT NULL,
    storage_path VARCHAR(500) NOT NULL,
    storage_path_censurada VARCHAR(500),
    bytes BIGINT,
    hash_sha256 VARCHAR(64),
    reemplaza_imagen_id BIGINT REFERENCES imagenes(id),
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_imagenes_lote ON imagenes(lote_id);
CREATE INDEX idx_imagenes_reemplaza ON imagenes(reemplaza_imagen_id);
