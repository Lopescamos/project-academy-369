package com.olimpiadas369.backend.identidad.infrastructure.persistence.jpa.entities;

import com.olimpiadas369.backend.identidad.domain.model.valueobjects.Correo;
import com.olimpiadas369.backend.identidad.domain.model.valueobjects.EstadoUsuario;
import com.olimpiadas369.backend.identidad.domain.model.valueobjects.Rol;
import com.olimpiadas369.backend.identidad.infrastructure.persistence.jpa.converters.CorreoPersistenceConverter;
import com.olimpiadas369.backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "usuarios")
@Getter @Setter @NoArgsConstructor
public class UsuarioPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false, length = 160)
    private String nombreCompleto;

    @Convert(converter = CorreoPersistenceConverter.class)
    @Column(nullable = false, length = 160, unique = true)
    private Correo correo;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Rol rol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoUsuario estado;

    private Instant desactivadoEn;
}
