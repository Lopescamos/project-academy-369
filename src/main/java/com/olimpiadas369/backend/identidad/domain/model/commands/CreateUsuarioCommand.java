package com.olimpiadas369.backend.identidad.domain.model.commands;

import com.olimpiadas369.backend.identidad.domain.model.valueobjects.Rol;

/** Crea un usuario. La contraseña se autogenera y se envía por correo. */
public record CreateUsuarioCommand(String nombreCompleto, String correo, Rol rol) {}
