package com.olimpiadas369.backend.registro.infrastructure.security;

import org.springframework.core.annotation.AliasFor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Atajo: inyecta {@code AuthenticatedUserPrincipal.usuarioId} en parámetros de controller. */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal(expression = "usuarioId")
public @interface CurrentUser {
    @AliasFor(annotation = AuthenticationPrincipal.class, attribute = "errorOnInvalidType")
    boolean errorOnInvalidType() default false;
}
