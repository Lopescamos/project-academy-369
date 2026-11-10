package com.olimpiadas369.backend.identidad.domain.model.valueobjects;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public record Correo(String value) {
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public Correo {
        Objects.requireNonNull(value, "[Correo] value cannot be null");
        value = value.trim().toLowerCase(Locale.ROOT);
        if (!EMAIL.matcher(value).matches())
            throw new IllegalArgumentException("[Correo] invalid email format: " + value);
    }
}
