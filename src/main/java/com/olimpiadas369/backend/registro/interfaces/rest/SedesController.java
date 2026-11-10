package com.olimpiadas369.backend.registro.interfaces.rest;

import com.olimpiadas369.backend.registro.application.commandservices.SedeCommandService;
import com.olimpiadas369.backend.registro.application.queryservices.RegistroQueryService;
import com.olimpiadas369.backend.registro.domain.model.queries.GetAllSedesQuery;
import com.olimpiadas369.backend.registro.domain.model.queries.GetSedeByIdQuery;
import com.olimpiadas369.backend.registro.interfaces.rest.resources.CreateSedeResource;
import com.olimpiadas369.backend.registro.interfaces.rest.resources.SedeResource;
import com.olimpiadas369.backend.registro.interfaces.rest.transform.SedeTransforms;
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
@RequestMapping(value = "/api/v1/sedes", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Sedes")
public class SedesController {

    private final SedeCommandService commands;
    private final RegistroQueryService queries;

    public SedesController(SedeCommandService commands, RegistroQueryService queries) {
        this.commands = commands; this.queries = queries;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear una sede")
    public ResponseEntity<?> create(@Valid @RequestBody CreateSedeResource resource) {
        var result = commands.handle(SedeTransforms.toCommandFromResource(resource));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, id -> id, HttpStatus.CREATED);
    }

    @GetMapping("/{sedeId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SedeResource> getById(@PathVariable Long sedeId) {
        return queries.handle(new GetSedeByIdQuery(sedeId))
                .map(SedeTransforms::toResourceFromEntity).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<SedeResource>> getAll() {
        return ResponseEntity.ok(queries.handle(new GetAllSedesQuery()).stream().map(SedeTransforms::toResourceFromEntity).toList());
    }
}
