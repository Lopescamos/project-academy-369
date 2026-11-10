package com.olimpiadas369.backend.identidad.domain.model.aggregates;

import com.olimpiadas369.backend.identidad.domain.model.commands.CreateUsuarioCommand;
import com.olimpiadas369.backend.identidad.domain.model.events.UsuarioCreatedEvent;
import com.olimpiadas369.backend.identidad.domain.model.events.UsuarioDeactivatedEvent;
import com.olimpiadas369.backend.identidad.domain.model.valueobjects.Correo;
import com.olimpiadas369.backend.identidad.domain.model.valueobjects.EstadoUsuario;
import com.olimpiadas369.backend.identidad.domain.model.valueobjects.Rol;
import com.olimpiadas369.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
public class Usuario extends AbstractDomainAggregateRoot<Usuario> {

    @Setter private Long id;
    @Setter private String nombreCompleto;
    @Setter private Correo correo;
    @Setter private String passwordHash;
    @Setter private Rol rol;
    @Setter private EstadoUsuario estado;
    @Setter private Instant desactivadoEn;

    public Usuario() {
        this.nombreCompleto = "";
        this.estado = EstadoUsuario.ACTIVO;
    }

    /** Creación: la contraseña ya viene hasheada por el command service. */
    public Usuario(CreateUsuarioCommand command, String passwordHash) {
        this.nombreCompleto = command.nombreCompleto();
        this.correo = new Correo(command.correo());
        this.passwordHash = passwordHash;
        this.rol = command.rol();
        this.estado = EstadoUsuario.ACTIVO;
    }

    public void onCreated() {
        registerDomainEvent(new UsuarioCreatedEvent(this.id, this.correo.value(), this.rol));
    }

    public void deactivate() {
        if (this.estado == EstadoUsuario.INACTIVO) return;
        this.estado = EstadoUsuario.INACTIVO;
        this.desactivadoEn = Instant.now();
        registerDomainEvent(new UsuarioDeactivatedEvent(this.id));
    }

    public void reactivate() {
        this.estado = EstadoUsuario.ACTIVO;
        this.desactivadoEn = null;
    }

    public void updateProfile(String nombreCompleto, Rol rol) {
        if (nombreCompleto != null && !nombreCompleto.isBlank()) this.nombreCompleto = nombreCompleto;
        if (rol != null) this.rol = rol;
    }

    public void changePasswordHash(String newHash) {
        this.passwordHash = newHash;
    }
}
