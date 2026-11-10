package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "apellido_comunes")
@Getter @Setter @NoArgsConstructor
public class ApellidoComunPersistenceEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 80, unique = true) private String apellido;
    @Column(nullable = false, length = 80) private String apellidoNormalizado;
}
