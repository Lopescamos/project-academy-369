package com.olimpiadas369.backend.identidad.infrastructure.mail.smtp;

import com.olimpiadas369.backend.identidad.application.internal.outboundservices.mail.InvitationMailSender;
import com.olimpiadas369.backend.identidad.application.internal.outboundservices.mail.InvitationMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Profile({"dev", "prod"})
public class SmtpInvitationMailSender implements InvitationMailSender {

    private final JavaMailSender mailer;
    private final String from;

    public SmtpInvitationMailSender(JavaMailSender mailer, @Value("${app.mail.from}") String from) {
        this.mailer = mailer;
        this.from = from;
    }

    @Override
    public void send(InvitationMessage m) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(m.to());
        msg.setSubject(m.subject());
        msg.setText(m.body());
        mailer.send(msg);
    }
}
