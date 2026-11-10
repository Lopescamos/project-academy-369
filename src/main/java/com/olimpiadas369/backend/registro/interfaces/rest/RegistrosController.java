package com.olimpiadas369.backend.registro.interfaces.rest;

import com.olimpiadas369.backend.registro.application.commandservices.RegistroCommandService;
import com.olimpiadas369.backend.registro.application.queryservices.RegistroQueryService;
import com.olimpiadas369.backend.registro.domain.model.commands.ConfirmarRegistroCommand;
import com.olimpiadas369.backend.registro.domain.model.commands.TomarRegistroEnRevisionCommand;
import com.olimpiadas369.backend.registro.domain.model.queries.GetRegistroByIdQuery;
import com.olimpiadas369.backend.registro.domain.model.queries.GetRegistrosConfirmadosByBloqueQuery;
import com.olimpiadas369.backend.registro.infrastructure.security.CurrentUser;
import com.olimpiadas369.backend.registro.interfaces.rest.resources.RegistroResource;
import com.olimpiadas369.backend.registro.interfaces.rest.transform.RegistroTransforms;
import com.olimpiadas369.backend.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/registros", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Registros")
public class RegistrosController {

    private final RegistroCommandService commands;
    private final RegistroQueryService queries;
    public RegistrosController(RegistroCommandService commands, RegistroQueryService queries) {
        this.commands = commands; this.queries = queries;
    }

    @PostMapping("/{registroId}/tomar")
    @PreAuthorize("hasRole('TUTOR') or hasRole('ADMIN')")
    public ResponseEntity<?> tomar(@PathVariable Long registroId, @CurrentUser Long usuarioId) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commands.handle(new TomarRegistroEnRevisionCommand(registroId, usuarioId)), id -> id, HttpStatus.OK);
    }

    @PostMapping("/{registroId}/confirmar")
    @PreAuthorize("hasRole('TUTOR') or hasRole('ADMIN')")
    public ResponseEntity<?> confirmar(@PathVariable Long registroId, @CurrentUser Long usuarioId) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commands.handle(new ConfirmarRegistroCommand(registroId, usuarioId)), id -> id, HttpStatus.OK);
    }

    @GetMapping("/{registroId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RegistroResource> getById(@PathVariable Long registroId) {
        return queries.handle(new GetRegistroByIdQuery(registroId)).map(RegistroTransforms::toResourceFromEntity)
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/confirmados")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RegistroResource>> getConfirmadosByBloque(@RequestParam Long bloqueId) {
        return ResponseEntity.ok(queries.handle(new GetRegistrosConfirmadosByBloqueQuery(bloqueId)).stream()
                .map(RegistroTransforms::toResourceFromEntity).toList());
    }
}
