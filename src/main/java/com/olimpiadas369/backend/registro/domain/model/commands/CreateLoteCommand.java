package com.olimpiadas369.backend.registro.domain.model.commands;

public record CreateLoteCommand(Long bloqueId, Long tutorId, Integer totalFotosEsperadas) {}
