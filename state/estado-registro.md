# Estado — Bounded Context: Registro (core domain)

**Estado global:** no iniciado. Es el core domain. Se implementa en dos fases por sus
dependencias cruzadas con Imágenes y Visión (ver `CLAUDE.md` § orden de BCs).

## Alcance

**Fase A (apertura — antes de Imágenes y Visión):** `Sede`, `Colegio`, `Apellido_Comun`,
`Bloque`, `Clave_Respuestas` + `Clave_Respuesta_Detalle` + `Clave_Correccion`, `Lote`.

**Fase B (cierre — después de Imágenes y Visión):** `Registro`, `Respuesta_Alumno`. Dependen
de `Imagen`, `Colegio` y `Clave_Respuestas`, por eso se cierran al final.

Reglas de negocio ancladas (ver `CLAUDE.md` § "Reglas de negocio críticas"):

- `Sede ≠ Colegio del alumno`. `Bloque = sede + curso + nivel + grado`: dos sedes en paralelo
  nunca se bloquean entre sí.
- `Clave_Respuestas` es scope curso+nivel+grado, sin sede — una sola clave reutilizada en todas
  las sedes. Prerrequisito duro para abrir un bloque.
- Fórmula de puntaje: `correctas × 20 − incorrectas × 1`. En blanco neutral. 2+ marcas =
  anulada.
- Corrección de clave, dos casos (ver `CLAUDE.md`): Caso A recalcula todas las fichas
  confirmadas en cualquier sede; Caso B suma +1 a todos sin tocar la clave.
- Caso Inicial (3-5 años): sin clave, sin cálculo automático, sin `Respuesta_Alumno`.
- Bloqueo desde la **selección** del bloque, no desde la subida de fotos. Ficha individual "en
  revisión" se libera a los ~15 min de inactividad. Lote incompleto nunca se libera por
  inactividad.
- La lógica de calificación y detección de marcas ya está prototipada como clases puras de
  dominio — Visión y Exportación la reutilizan, no la reimplementan.

## Dependencias

- Upstream: Identidad y acceso (usuario que crea/asigna).
- Downstream (Fase A): Imágenes (necesita `Lote`), Visión (escribe en `Registro` y
  `Respuesta_Alumno`), Exportación (lee `Registro` confirmado).

## Pendientes propios

Fase A:
- [ ] `domain/` de `Sede`, `Colegio`, `Apellido_Comun`, `Bloque`, `Clave_Respuestas` + detalle +
  corrección, `Lote`. Incluye la lógica pura de calificación y detección de marcas (ya
  prototipada — se migra, no se reinventa).
- [ ] `infrastructure/`, `application/`, `interfaces/rest/` para las entidades de Fase A.
- [ ] Endpoints: `/api/v1/bloques`, `/api/v1/claves-respuestas`, `/api/v1/lotes`, etc.

Fase B (bloqueada por Imágenes y Visión):
- [ ] `domain/` de `Registro` y `Respuesta_Alumno`.
- [ ] Recálculo masivo (Caso A) y bonificación global por pregunta (Caso B).
- [ ] Ranking por bloque.

## Handoff para el siguiente agente

Vacío — todavía no arrancó el flujo.
