package com.olimpiadas369.backend.registro.domain.services;

/**
 * Lógica pura de dominio: puntaje = correctas × 20 − incorrectas × 1.
 * En blanco no suma ni resta; anuladas tampoco.
 */
public final class PuntajeCalculator {

    public static final int VALOR_CORRECTA = 20;
    public static final int PENALIZACION_INCORRECTA = 1;

    private PuntajeCalculator() {}

    public static int calcular(int correctas, int incorrectas) {
        return correctas * VALOR_CORRECTA - incorrectas * PENALIZACION_INCORRECTA;
    }
}
