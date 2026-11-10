package com.olimpiadas369.backend.identidad.interfaces.rest;

import com.olimpiadas369.backend.identidad.application.commandservices.AuthCommandService;
import com.olimpiadas369.backend.identidad.domain.model.commands.LoginCommand;
import com.olimpiadas369.backend.identidad.interfaces.rest.resources.LoginResource;
import com.olimpiadas369.backend.identidad.interfaces.rest.resources.TokenResource;
import com.olimpiadas369.backend.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/auth", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Auth", description = "Login y emisión de token JWT")
public class AuthController {

    private final AuthCommandService auth;

    public AuthController(AuthCommandService auth) { this.auth = auth; }

    @PostMapping("/login")
    @Operation(summary = "Autenticarse y obtener un JWT")
    public ResponseEntity<?> login(@Valid @RequestBody LoginResource resource) {
        var result = auth.handle(new LoginCommand(resource.correo(), resource.password()));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, TokenResource::new, HttpStatus.OK);
    }
}
