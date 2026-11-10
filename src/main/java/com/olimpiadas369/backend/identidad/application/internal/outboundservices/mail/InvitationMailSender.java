package com.olimpiadas369.backend.identidad.application.internal.outboundservices.mail;

/** Puerto para enviar el correo de invitación con credenciales iniciales. */
public interface InvitationMailSender {
    void send(InvitationMessage message);
}
