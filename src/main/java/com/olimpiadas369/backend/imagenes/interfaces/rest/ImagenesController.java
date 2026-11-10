package com.olimpiadas369.backend.imagenes.interfaces.rest;

import com.olimpiadas369.backend.imagenes.application.commandservices.ImagenCommandService;
import com.olimpiadas369.backend.imagenes.application.queryservices.ImagenQueryService;
import com.olimpiadas369.backend.imagenes.domain.model.commands.ReplaceImagenCommand;
import com.olimpiadas369.backend.imagenes.domain.model.commands.UploadImagenCommand;
import com.olimpiadas369.backend.imagenes.domain.model.queries.GetImagenesByLoteQuery;
import com.olimpiadas369.backend.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/imagenes", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Imágenes")
public class ImagenesController {

    private final ImagenCommandService commands;
    private final ImagenQueryService queries;
    public ImagenesController(ImagenCommandService commands, ImagenQueryService queries) {
        this.commands = commands; this.queries = queries;
    }

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('TUTOR') or hasRole('ADMIN')")
    @Operation(summary = "Subir una foto de una ficha (se censura DNI/celular automáticamente)")
    public ResponseEntity<?> upload(@RequestParam Long loteId, @RequestParam Integer ordenEnLote,
                                    @RequestPart MultipartFile file) throws IOException {
        var result = commands.handle(new UploadImagenCommand(loteId, ordenEnLote, file.getBytes(), file.getContentType()));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, id -> id, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{imagenId}/reemplazar", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('TUTOR') or hasRole('ADMIN')")
    public ResponseEntity<?> replace(@PathVariable Long imagenId, @RequestPart MultipartFile file) throws IOException {
        var result = commands.handle(new ReplaceImagenCommand(imagenId, file.getBytes(), file.getContentType()));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, id -> id, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> listByLote(@RequestParam Long loteId) {
        return ResponseEntity.ok(queries.handle(new GetImagenesByLoteQuery(loteId)).stream().map(i -> Map.of(
                "id", i.getId(), "loteId", i.getLoteId(), "orden", i.getOrdenEnLote(),
                "storagePath", i.getStoragePath(), "storagePathCensurada", i.getStoragePathCensurada()
        )).toList());
    }

    @GetMapping("/{imagenId}/signed-url")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> signedUrl(@PathVariable Long imagenId,
                                       @RequestParam(defaultValue = "true") boolean censurada) {
        String url = queries.generarSignedUrl(imagenId, censurada);
        if (url == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("url", url, "expiresInSeconds", 300));
    }
}
