package com.olimpiadas369.backend.registro.interfaces.rest;

import com.olimpiadas369.backend.registro.application.commandservices.LoteCommandService;
import com.olimpiadas369.backend.registro.application.queryservices.RegistroQueryService;
import com.olimpiadas369.backend.registro.domain.model.queries.GetLoteByIdQuery;
import com.olimpiadas369.backend.registro.interfaces.rest.resources.CreateLoteResource;
import com.olimpiadas369.backend.registro.domain.model.commands.CreateLoteCommand;
import com.olimpiadas369.backend.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/lotes", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Lotes")
public class LotesController {

    private final LoteCommandService commands;
    private final RegistroQueryService queries;
    public LotesController(LoteCommandService commands, RegistroQueryService queries) {
        this.commands = commands; this.queries = queries;
    }

    @PostMapping
    @PreAuthorize("hasRole('TUTOR') or hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody CreateLoteResource r) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commands.handle(new CreateLoteCommand(r.bloqueId(), r.tutorId(), r.totalFotosEsperadas())),
                id -> id, HttpStatus.CREATED);
    }

    @GetMapping("/{loteId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long loteId) {
        var opt = queries.handle(new GetLoteByIdQuery(loteId));
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        var l = opt.get();
        Map<String, Object> body = new java.util.LinkedHashMap<>();
        body.put("id", l.getId()); body.put("bloqueId", l.getBloqueId()); body.put("tutorId", l.getTutorId());
        body.put("estado", l.getEstado());
        body.put("totalFotosEsperadas", l.getTotalFotosEsperadas());
        body.put("totalFotosRecibidas", l.getTotalFotosRecibidas());
        return ResponseEntity.ok(body);
    }
}
