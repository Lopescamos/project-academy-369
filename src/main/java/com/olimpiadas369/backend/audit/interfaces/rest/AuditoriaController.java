package com.olimpiadas369.backend.audit.interfaces.rest;

import com.olimpiadas369.backend.audit.domain.model.aggregates.AuditoriaAccion;
import com.olimpiadas369.backend.audit.domain.repositories.AuditoriaAccionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/auditoria", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Auditoría", description = "Log append-only de acciones (solo ADMIN)")
public class AuditoriaController {

    private final AuditoriaAccionRepository repo;
    public AuditoriaController(AuditoriaAccionRepository repo) { this.repo = repo; }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar acciones por entidad")
    public ResponseEntity<List<AuditoriaAccion>> byEntidad(@RequestParam String entidad, @RequestParam Long entidadId) {
        return ResponseEntity.ok(repo.findByEntidadAndEntidadId(entidad, entidadId));
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuditoriaAccion>> byUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(repo.findByUsuarioId(usuarioId));
    }
}
