package com.olimpiadas369.backend.identidad.application.internal.commandservices;

import com.olimpiadas369.backend.identidad.application.commandservices.AuthCommandService;
import com.olimpiadas369.backend.identidad.application.internal.outboundservices.tokens.TokenService;
import com.olimpiadas369.backend.identidad.domain.model.commands.LoginCommand;
import com.olimpiadas369.backend.identidad.domain.model.valueobjects.EstadoUsuario;
import com.olimpiadas369.backend.identidad.domain.repositories.UsuarioRepository;
import com.olimpiadas369.backend.identidad.domain.services.PasswordHasher;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class AuthCommandServiceImpl implements AuthCommandService {

    private final UsuarioRepository usuarios;
    private final PasswordHasher hasher;
    private final TokenService tokens;

    public AuthCommandServiceImpl(UsuarioRepository usuarios, PasswordHasher hasher, TokenService tokens) {
        this.usuarios = usuarios;
        this.hasher = hasher;
        this.tokens = tokens;
    }

    @Override
    public Result<String, ApplicationError> handle(LoginCommand command) {
        var maybe = usuarios.findByCorreo(command.correo());
        if (maybe.isEmpty())
            return Result.failure(ApplicationError.unauthorized("Invalid credentials"));
        var u = maybe.get();
        if (u.getEstado() != EstadoUsuario.ACTIVO)
            return Result.failure(ApplicationError.forbidden("Usuario", "User is deactivated"));
        if (!hasher.matches(command.password(), u.getPasswordHash()))
            return Result.failure(ApplicationError.unauthorized("Invalid credentials"));
        return Result.success(tokens.issue(u.getId(), u.getCorreo().value(), u.getRol()));
    }
}
