package com.olimpiadas369.backend.imagenes.infrastructure.persistence.jpa.entities;

import com.olimpiadas369.backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "imagenes")
@Getter @Setter @NoArgsConstructor
public class ImagenPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false) private Long loteId;
    @Column(nullable = false) private Integer ordenEnLote;
    @Column(nullable = false, length = 500) private String storagePath;
    @Column(length = 500) private String storagePathCensurada;
    private Long bytes;
    @Column(length = 64) private String hashSha256;
    private Long reemplazaImagenId;
}
