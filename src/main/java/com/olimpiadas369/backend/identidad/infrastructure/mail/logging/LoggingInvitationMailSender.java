package com.olimpiadas369.backend.identidad.infrastructure.mail.logging;

import com.olimpiadas369.backend.identidad.application.internal.outboundservices.mail.InvitationMailSender;
import com.olimpiadas369.backend.identidad.application.internal.outboundservices.mail.InvitationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/** Fallback para tests: solo loguea el mensaje, no envía nada. */
@Service
@Profile("test")
@Slf4j
public class LoggingInvitationMailSender implements InvitationMailSender {
    @Override
    public void send(InvitationMessage m) {
        log.info("[INVITATION MAIL] to={}, subject={}, body={}", m.to(), m.subject(), m.body());
    }
}
