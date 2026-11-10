package com.olimpiadas369.backend.imagenes.domain.model.aggregates;

import com.olimpiadas369.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Imagen extends AbstractDomainAggregateRoot<Imagen> {
    @Setter private Long id;
    @Setter private Long loteId;
    @Setter private Integer ordenEnLote;
    @Setter private String storagePath;
    @Setter private String storagePathCensurada;
    @Setter private Long bytes;
    @Setter private String hashSha256;
    @Setter private Long reemplazaImagenId;

    public Imagen() {}

    public Imagen(Long loteId, Integer orden, String storagePath, Long bytes, String hash) {
        this.loteId = loteId; this.ordenEnLote = orden; this.storagePath = storagePath;
        this.bytes = bytes; this.hashSha256 = hash;
    }

    public void registrarCensurada(String path) { this.storagePathCensurada = path; }
    public void marcarComoReemplazoDe(Long originalId) { this.reemplazaImagenId = originalId; }
}
