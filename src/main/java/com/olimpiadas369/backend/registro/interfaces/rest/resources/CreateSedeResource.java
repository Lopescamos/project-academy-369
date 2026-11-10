package com.olimpiadas369.backend.registro.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateSedeResource(@NotBlank String nombre, String direccion, @NotNull LocalDate fechaEvento) {}
