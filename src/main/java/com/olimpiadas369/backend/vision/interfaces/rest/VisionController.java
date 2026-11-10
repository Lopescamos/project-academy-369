package com.olimpiadas369.backend.vision.interfaces.rest;

import com.olimpiadas369.backend.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.olimpiadas369.backend.vision.application.commandservices.VisionCommandService;
import com.olimpiadas369.backend.vision.domain.model.commands.ProcesarImagenCommand;
import com.olimpiadas369.backend.vision.domain.model.commands.ReprocesarRegistroCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/vision", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Visión", description = "Procesamiento de fichas por IA multimodal")
public class VisionController {

    private final VisionCommandService commands;
    public VisionController(VisionCommandService commands) { this.commands = commands; }

    @PostMapping("/procesar-imagen/{imagenId}")
    @PreAuthorize("hasRole('TUTOR') or hasRole('ADMIN')")
    @Operation(summary = "Procesar una imagen y crear el Registro con sus respuestas")
    public ResponseEntity<?> procesarImagen(@PathVariable Long imagenId) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commands.handle(new ProcesarImagenCommand(imagenId)), id -> id, HttpStatus.CREATED);
    }

    @PostMapping("/reprocesar-registro/{registroId}")
    @PreAuthorize("hasRole('TUTOR') or hasRole('ADMIN')")
    public ResponseEntity<?> reprocesar(@PathVariable Long registroId) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commands.handle(new ReprocesarRegistroCommand(registroId)), id -> id, HttpStatus.OK);
    }
}
