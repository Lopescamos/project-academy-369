package com.olimpiadas369.backend.identidad.application.internal.commandservices;

import com.olimpiadas369.backend.identidad.application.commandservices.UsuarioCommandService;
import com.olimpiadas369.backend.identidad.application.internal.emails.InvitationEmailComposer;
import com.olimpiadas369.backend.identidad.application.internal.outboundservices.mail.InvitationMailSender;
import com.olimpiadas369.backend.identidad.domain.model.aggregates.Usuario;
import com.olimpiadas369.backend.identidad.domain.model.aggregates.UsuarioSede;
import com.olimpiadas369.backend.identidad.domain.model.commands.*;
import com.olimpiadas369.backend.identidad.domain.repositories.UsuarioRepository;
import com.olimpiadas369.backend.identidad.domain.repositories.UsuarioSedeRepository;
import com.olimpiadas369.backend.identidad.domain.services.PasswordHasher;
import com.olimpiadas369.backend.identidad.domain.services.TemporaryPasswordGenerator;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class UsuarioCommandServiceImpl implements UsuarioCommandService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioSedeRepository usuarioSedeRepository;
    private final PasswordHasher passwordHasher;
    private final InvitationEmailComposer composer;
    private final InvitationMailSender mailSender;

    public UsuarioCommandServiceImpl(UsuarioRepository usuarioRepository,
                                     UsuarioSedeRepository usuarioSedeRepository,
                                     PasswordHasher passwordHasher,
                                     InvitationEmailComposer composer,
                                     InvitationMailSender mailSender) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioSedeRepository = usuarioSedeRepository;
        this.passwordHasher = passwordHasher;
        this.composer = composer;
        this.mailSender = mailSender;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateUsuarioCommand command) {
        if (usuarioRepository.existsByCorreo(command.correo())) {
            return Result.failure(ApplicationError.conflict("Usuario",
                    "A user with email '%s' already exists".formatted(command.correo())));
        }
        try {
            String tempPassword = TemporaryPasswordGenerator.generate();
            String hash = passwordHasher.hash(tempPassword);
            Usuario usuario = new Usuario(command, hash);
            Usuario saved = usuarioRepository.save(usuario);
            // Registrar evento sobre el aggregate con id ya asignado y guardar otra vez
            // para que el adapter publique UsuarioCreatedEvent.
            saved.onCreated();
            usuarioRepository.save(saved);
            mailSender.send(composer.compose(command.nombreCompleto(), command.correo(), tempPassword));
            return Result.success(saved.getId());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-usuario", e.getMessage()));
        }
    }

    @Override
    public Result<Long, ApplicationError> handle(UpdateUsuarioCommand command) {
        var existing = usuarioRepository.findById(command.usuarioId());
        if (existing.isEmpty())
            return Result.failure(ApplicationError.notFound("Usuario", command.usuarioId().toString()));
        try {
            Usuario u = existing.get();
            u.updateProfile(command.nombreCompleto(), command.rol());
            return Result.success(usuarioRepository.save(u).getId());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("update-usuario", e.getMessage()));
        }
    }

    @Override
    public Result<Long, ApplicationError> handle(DeactivateUsuarioCommand command) {
        var existing = usuarioRepository.findById(command.usuarioId());
        if (existing.isEmpty())
            return Result.failure(ApplicationError.notFound("Usuario", command.usuarioId().toString()));
        try {
            Usuario u = existing.get();
            u.deactivate();
            return Result.success(usuarioRepository.save(u).getId());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("deactivate-usuario", e.getMessage()));
        }
    }

    @Override
    public Result<Long, ApplicationError> handle(ReactivateUsuarioCommand command) {
        var existing = usuarioRepository.findById(command.usuarioId());
        if (existing.isEmpty())
            return Result.failure(ApplicationError.notFound("Usuario", command.usuarioId().toString()));
        try {
            Usuario u = existing.get();
            u.reactivate();
            return Result.success(usuarioRepository.save(u).getId());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("reactivate-usuario", e.getMessage()));
        }
    }

    @Override
    public Result<UsuarioSede, ApplicationError> handle(AssignUsuarioToSedeCommand command) {
        if (usuarioRepository.findById(command.usuarioId()).isEmpty())
            return Result.failure(ApplicationError.notFound("Usuario", command.usuarioId().toString()));
        // Patrón: historial M:N — si ya existe el vínculo, se reactiva en lugar de duplicar.
        var existing = usuarioSedeRepository.findByUsuarioIdAndSedeId(command.usuarioId(), command.sedeId());
        try {
            UsuarioSede us = existing.orElseGet(() -> new UsuarioSede(command.usuarioId(), command.sedeId()));
            us.reactivate();
            return Result.success(usuarioSedeRepository.save(us));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("assign-usuario-sede", e.getMessage()));
        }
    }
}
