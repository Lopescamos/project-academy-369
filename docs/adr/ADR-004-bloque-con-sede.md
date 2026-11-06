# ADR-004: Bloque = Sede + curso + nivel + grado (no solo curso + nivel + grado)

- Estado: Aceptada
- Fecha: 2026-11-04

## Contexto
El examen es interescolar y se rinde en paralelo en varias sedes. Si el bloque se definiera solo por curso + nivel + grado, dos sedes trabajando "3° de Primaria de Matemática" el mismo día se bloquearían entre sí sin razón.

## Decisión
El bloque incluye la sede como dimensión. La clave de respuestas, en cambio, se mantiene scoped a curso + nivel + grado (sin sede) porque el examen es el mismo en todas las sedes.

## Alternativas consideradas
- Bloque sin sede: provoca contención cruzada entre sedes paralelas.
- Clave de respuestas por sede: obligaría a reingresarla en cada sede sin ganancia; el examen es único por combinación.

## Consecuencias
Se gana: paralelismo real entre sedes sin bloqueos ficticios, modelo más fiel a la realidad (la Sede del evento es distinta del Colegio del alumno). Se acepta perder: una dimensión más en varias consultas y una tabla de bloques con más filas por evento.
