package com.olimpiadas369.backend.identidad.interfaces.rest;

import com.olimpiadas369.backend.identidad.application.commandservices.UsuarioCommandService;
import com.olimpiadas369.backend.identidad.application.queryservices.UsuarioQueryService;
import com.olimpiadas369.backend.identidad.domain.model.aggregates.Usuario;
import com.olimpiadas369.backend.identidad.domain.model.commands.*;
import com.olimpiadas369.backend.identidad.domain.model.queries.*;
import com.olimpiadas369.backend.identidad.interfaces.rest.resources.*;
import com.olimpiadas369.backend.identidad.interfaces.rest.transform.*;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;
import com.olimpiadas369.backend.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/usuarios", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Usuarios", description = "Gestión de usuarios y asignación a sedes")
public class UsuariosController {

    private final UsuarioCommandService commands;
    private final UsuarioQueryService queries;

    public UsuariosController(UsuarioCommandService commands, UsuarioQueryService queries) {
        this.commands = commands;
        this.queries = queries;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Invitar un nuevo usuario (envía credenciales por correo)")
    public ResponseEntity<?> createUsuario(@Valid @RequestBody CreateUsuarioResource resource) {
        var command = CreateUsuarioCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commands.handle(command).flatMap(this::fetchUsuarioOrNotFound);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, UsuarioResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    @PutMapping("/{usuarioId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar nombre y/o rol de un usuario")
    public ResponseEntity<?> updateUsuario(@PathVariable Long usuarioId,
                                           @Valid @RequestBody UpdateUsuarioResource resource) {
        var command = UpdateUsuarioCommandFromResourceAssembler.toCommandFromResource(usuarioId, resource);
        var result = commands.handle(command).flatMap(this::fetchUsuarioOrNotFound);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, UsuarioResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }

    private Result<Usuario, ApplicationError> fetchUsuarioOrNotFound(Long id) {
        return queries.handle(new GetUsuarioByIdQuery(id))
                .<Result<Usuario, ApplicationError>>map(Result::success)
                .orElseGet(() -> Result.failure(ApplicationError.notFound("Usuario", id.toString())));
    }

    @PostMapping("/{usuarioId}/desactivar")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Desactivar un usuario (no se borra, mantiene historial)")
    public ResponseEntity<?> deactivate(@PathVariable Long usuarioId) {
        var result = commands.handle(new DeactivateUsuarioCommand(usuarioId));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, id -> id, HttpStatus.OK);
    }

    @PostMapping("/{usuarioId}/reactivar")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Reactivar un usuario desactivado")
    public ResponseEntity<?> reactivate(@PathVariable Long usuarioId) {
        var result = commands.handle(new ReactivateUsuarioCommand(usuarioId));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, id -> id, HttpStatus.OK);
    }

    @PostMapping("/{usuarioId}/sedes")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Asignar un usuario a una sede (reactiva si ya existía)")
    public ResponseEntity<?> assignToSede(@PathVariable Long usuarioId,
                                          @Valid @RequestBody AssignUsuarioToSedeResource resource) {
        var result = commands.handle(new AssignUsuarioToSedeCommand(usuarioId, resource.sedeId()));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, us -> us, HttpStatus.CREATED);
    }

    @GetMapping("/{usuarioId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Obtener un usuario por id")
    public ResponseEntity<UsuarioResource> getById(@PathVariable Long usuarioId) {
        var u = queries.handle(new GetUsuarioByIdQuery(usuarioId));
        if (u.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(UsuarioResourceFromEntityAssembler.toResourceFromEntity(u.get()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos los usuarios")
    public ResponseEntity<List<UsuarioResource>> getAll() {
        return ResponseEntity.ok(queries.handle(new GetAllUsuariosQuery()).stream()
                .map(UsuarioResourceFromEntityAssembler::toResourceFromEntity).toList());
    }
}
