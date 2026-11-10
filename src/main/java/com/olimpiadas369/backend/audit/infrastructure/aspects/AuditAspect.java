package com.olimpiadas369.backend.audit.infrastructure.aspects;

import com.olimpiadas369.backend.audit.application.internal.commandservices.AuditoriaAccionService;
import com.olimpiadas369.backend.identidad.infrastructure.security.AuthenticatedUserPrincipal;
import com.olimpiadas369.backend.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Intercepta métodos {@code handle(..Command)} de todos los command services de la aplicación
 * (en cualquier BC salvo el propio Audit, para evitar recursión) y registra la acción en
 * {@code auditoria_acciones} tras una ejecución exitosa.
 *
 * El nombre de la acción es {@code SIMPLE_CLASS_NAME} del command (ej: "CreateBloqueCommand").
 * Si el resultado es un {@code Result.Success} y su valor es un Long/aggregate con id, lo guarda
 * como {@code entidad_id}.
 */
@Aspect
@Component
@Slf4j
public class AuditAspect {

    private final AuditoriaAccionService auditoria;

    public AuditAspect(AuditoriaAccionService auditoria) { this.auditoria = auditoria; }

    @Around("execution(* com.olimpiadas369.backend..application.internal.commandservices..*CommandServiceImpl.handle(..)) " +
            "&& !within(com.olimpiadas369.backend.audit..*)")
    public Object aroundCommandServiceHandle(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        try {
            Object[] args = pjp.getArgs();
            if (args == null || args.length == 0) return result;
            Object command = args[0];
            String accion = command.getClass().getSimpleName();
            String entidad = inferirEntidadDesdeCommand(accion);
            Long entidadId = extraerIdDesdeResultado(result);
            Long usuarioId = usuarioIdActual();
            auditoria.registrar(usuarioId, accion, entidad, entidadId, null);
        } catch (Exception e) {
            // Nunca romper la operación por un fallo de auditoría.
            log.warn("[AuditAspect] skip audit: {}", e.getMessage());
        }
        return result;
    }

    private static String inferirEntidadDesdeCommand(String accion) {
        // "CreateBloqueCommand" → "Bloque"; "LoginCommand" → "Login"; "AplicarClaveCorreccionCommand" → "ClaveCorreccion"
        String s = accion.replaceFirst("^(Create|Update|Delete|Deactivate|Reactivate|Confirmar|Completar|Tomar|Aplicar|Login|Upload|Replace|Procesar|Reprocesar|Exportar|Assign|Seed)", "");
        if (s.endsWith("Command")) s = s.substring(0, s.length() - "Command".length());
        return s.isEmpty() ? "Desconocido" : s;
    }

    private static Long extraerIdDesdeResultado(Object result) {
        if (result instanceof Result.Success<?, ?> s) {
            Object v = s.value();
            if (v instanceof Long l) return l;
            try {
                // Si es un aggregate con getId()
                var m = v.getClass().getMethod("getId");
                Object id = m.invoke(v);
                if (id instanceof Long l) return l;
            } catch (Exception ignored) {}
        }
        return null;
    }

    private static Long usuarioIdActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null) return null;
        if (auth.getPrincipal() instanceof AuthenticatedUserPrincipal p) return p.usuarioId();
        return null;
    }
}
