package com.olimpiadas369.backend.registro.interfaces.rest.resources;

import java.time.LocalDate;

public record SedeResource(Long id, String nombre, String direccion, LocalDate fechaEvento, String estado) {}
