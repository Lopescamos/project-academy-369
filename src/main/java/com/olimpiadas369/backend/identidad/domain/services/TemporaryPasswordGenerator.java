package com.olimpiadas369.backend.identidad.domain.services;

import java.security.SecureRandom;

/** Genera la contraseña inicial autogenerada que se envía por correo al invitar un usuario. */
public final class TemporaryPasswordGenerator {

    private static final String ALPHABET =
            "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private TemporaryPasswordGenerator() {}

    public static String generate(int length) {
        if (length < 8) length = 12;
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        return sb.toString();
    }

    public static String generate() { return generate(12); }
}
