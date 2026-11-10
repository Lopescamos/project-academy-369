package com.olimpiadas369.backend.exportacion.interfaces.rest;

import com.olimpiadas369.backend.exportacion.application.commandservices.ExportacionCommandService;
import com.olimpiadas369.backend.exportacion.domain.model.commands.ExportarRegistrosBloqueCommand;
import com.olimpiadas369.backend.shared.application.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/exportacion")
@Tag(name = "Exportación", description = "Generación de Excel con fichas confirmadas")
public class ExportacionController {

    private final ExportacionCommandService commands;
    public ExportacionController(ExportacionCommandService commands) { this.commands = commands; }

    @GetMapping("/bloques/{bloqueId}.xlsx")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TUTOR')")
    @Operation(summary = "Descargar el Excel con el ranking de un bloque")
    public ResponseEntity<?> exportarBloque(@PathVariable Long bloqueId) {
        var result = commands.handle(new ExportarRegistrosBloqueCommand(bloqueId));
        return switch (result) {
            case Result.Success<byte[], ?> s -> {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
                headers.setContentDisposition(ContentDisposition.attachment()
                        .filename("bloque-" + bloqueId + ".xlsx").build());
                yield new ResponseEntity<>(s.value(), headers, 200);
            }
            case Result.Failure<byte[], ?> f -> ResponseEntity.unprocessableEntity()
                    .body("Error: " + f.error());
        };
    }
}
