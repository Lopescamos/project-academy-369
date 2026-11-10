package com.olimpiadas369.backend.registro.interfaces.rest;

import com.olimpiadas369.backend.registro.application.commandservices.BloqueCommandService;
import com.olimpiadas369.backend.registro.application.queryservices.RegistroQueryService;
import com.olimpiadas369.backend.registro.domain.model.commands.CompletarBloqueCommand;
import com.olimpiadas369.backend.registro.domain.model.queries.GetBloqueByIdQuery;
import com.olimpiadas369.backend.registro.domain.model.queries.GetBloquesBySedeQuery;
import com.olimpiadas369.backend.registro.infrastructure.security.CurrentUser;
import com.olimpiadas369.backend.registro.interfaces.rest.resources.BloqueResource;
import com.olimpiadas369.backend.registro.interfaces.rest.resources.CreateBloqueResource;
import com.olimpiadas369.backend.registro.interfaces.rest.transform.BloqueTransforms;
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
@RequestMapping(value = "/api/v1/bloques", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Bloques")
public class BloquesController {

    private final BloqueCommandService commands;
    private final RegistroQueryService queries;
    public BloquesController(BloqueCommandService commands, RegistroQueryService queries) {
        this.commands = commands; this.queries = queries;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody CreateBloqueResource r) {
        var result = commands.handle(BloqueTransforms.toCommandFromResource(r));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, id -> id, HttpStatus.CREATED);
    }

    @PostMapping("/{bloqueId}/seleccionar")
    @PreAuthorize("hasRole('TUTOR') or hasRole('ADMIN')")
    @Operation(summary = "Seleccionar un bloque (lo bloquea para otras selecciones concurrentes)")
    public ResponseEntity<?> seleccionar(@PathVariable Long bloqueId) {
        var result = commands.seleccionarBloque(bloqueId);
        return ResponseEntityAssembler.toResponseEntityFromResult(result, id -> id, HttpStatus.OK);
    }

    @PostMapping("/{bloqueId}/completar")
    @PreAuthorize("hasRole('TUTOR') or hasRole('ADMIN')")
    public ResponseEntity<?> completar(@PathVariable Long bloqueId, @CurrentUser Long usuarioId) {
        var result = commands.handle(new CompletarBloqueCommand(bloqueId, usuarioId));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, id -> id, HttpStatus.OK);
    }

    @GetMapping("/{bloqueId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BloqueResource> getById(@PathVariable Long bloqueId) {
        return queries.handle(new GetBloqueByIdQuery(bloqueId)).map(BloqueTransforms::toResourceFromEntity)
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<BloqueResource>> getBySede(@RequestParam Long sedeId) {
        return ResponseEntity.ok(queries.handle(new GetBloquesBySedeQuery(sedeId)).stream().map(BloqueTransforms::toResourceFromEntity).toList());
    }
}
