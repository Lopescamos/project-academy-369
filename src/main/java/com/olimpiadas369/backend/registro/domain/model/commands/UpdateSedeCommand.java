package com.olimpiadas369.backend.registro.domain.model.commands;

import java.time.LocalDate;

public record UpdateSedeCommand(Long sedeId, String nombre, String direccion, LocalDate fechaEvento) {}
