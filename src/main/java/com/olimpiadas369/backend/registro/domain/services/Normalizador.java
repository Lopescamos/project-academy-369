package com.olimpiadas369.backend.registro.domain.services;

import java.text.Normalizer;
import java.util.Locale;

/** Normaliza texto para fuzzy-matching: minúsculas + sin tildes + trim. */
public final class Normalizador {

    private Normalizador() {}

    public static String normalizar(String s) {
        if (s == null) return null;
        String sinTilde = Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return sinTilde.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", " ");
    }
}
