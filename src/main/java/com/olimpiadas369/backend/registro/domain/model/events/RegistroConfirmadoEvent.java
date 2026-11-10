package com.olimpiadas369.backend.registro.domain.model.events;

public record RegistroConfirmadoEvent(Long registroId, Long bloqueId, Long usuarioId) {}
