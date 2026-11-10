package com.olimpiadas369.backend.registro.domain.model.commands;

import java.time.LocalDate;

public record CreateSedeCommand(String nombre, String direccion, LocalDate fechaEvento) {}
