package com.olimpiadas369.backend.identidad.application.internal.emails;

import com.olimpiadas369.backend.identidad.application.internal.outboundservices.mail.InvitationMessage;
import org.springframework.stereotype.Component;

/** Decide el CONTENIDO del correo de invitación. No sabe cómo se transporta. */
@Component
public class InvitationEmailComposer {

    public InvitationMessage compose(String nombre, String correo, String temporaryPassword) {
        String subject = "Olimpiadas 369 — Tu cuenta está lista";
        String body = """
                Hola %s,

                Se ha creado tu cuenta en Olimpiadas 369.

                  Correo: %s
                  Contraseña temporal: %s

                Al ingresar por primera vez te pediremos cambiarla.

                — Olimpiadas 369
                """.formatted(nombre, correo, temporaryPassword);
        return new InvitationMessage(correo, subject, body);
    }
}
