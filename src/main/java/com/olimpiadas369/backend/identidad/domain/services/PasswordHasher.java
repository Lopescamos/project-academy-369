package com.olimpiadas369.backend.identidad.domain.services;

/**
 * Hashea y verifica contraseñas de usuario. Puerto de dominio: sin él, un usuario no puede
 * autenticarse de forma segura. Las implementaciones deciden el algoritmo (BCrypt, Argon2...).
 */
public interface PasswordHasher {
    String hash(String rawPassword);
    boolean matches(String rawPassword, String hashedPassword);
}
