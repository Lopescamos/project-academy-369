package com.olimpiadas369.backend.identidad.application.internal.outboundservices.mail;

/** DTO plano con el mensaje ya armado por el composer de application. */
public record InvitationMessage(String to, String subject, String body) {}
