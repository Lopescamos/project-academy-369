# Modelo de datos (ERD)

> El diagrama ERD visual se genera desde `modelo-bd.puml` (código PlantUML, archivo aparte). Este documento describe cada tabla, sus campos, llaves y las relaciones que ya se decidieron en la Fase 1 y en las reglas de negocio.

## Convenciones generales

- **PK**: `id BIGSERIAL` (o `UUID` si se decide después).
- **Nombres de tabla** en singular, snake_case.
- **Auditoría estándar** en toda tabla: `created_at TIMESTAMPTZ`, `updated_at TIMESTAMPTZ`, ambos con default `now()`.
- **Soft delete** solo donde tiene sentido conservar historial (ej. `usuario`), no en todas las tablas.
- **Enums** modelados como columnas `VARCHAR` con `CHECK CONSTRAINT`, no como `ENUM` nativo de Postgres (más fácil de migrar con Flyway).

## Enums del dominio

| Enum | Valores |
|---|---|
| `curso` | `MATEMATICA`, `COMUNICACION` |
| `nivel` | `INICIAL`, `PRIMARIA`, `SECUNDARIA` |
| `grado` | `INICIAL_3`, `INICIAL_4`, `INICIAL_5`, `PRIM_1`, `PRIM_2`, `PRIM_3`, `PRIM_4`, `PRIM_5`, `PRIM_6`, `SEC_1`, `SEC_2`, `SEC_3`, `SEC_4`, `SEC_5` |
| `rol_usuario` | `TUTOR`, `ADMIN` |
| `estado_usuario` | `ACTIVO`, `INACTIVO` |
| `estado_lote` | `EN_CAPTURA`, `SUBIENDO`, `SUBIDO`, `PROCESADO`, `ERROR` |
| `estado_registro` | `PENDIENTE_VISION`, `LIBRE`, `EN_REVISION`, `CONFIRMADO`, `CON_OBSERVACION` |
| `opcion` | `A`, `B`, `C`, `D`, `E`, `BLANCO`, `ANULADA` |
| `estado_marca_vision` | `VACIA`, `MARCADA`, `INCIERTA` |
| `tipo_correccion_clave` | `ERROR_REGISTRO`, `PREGUNTA_INVALIDA` |

## Tablas

### `sede`
Ubicación física donde se realiza un evento. Distinta de `colegio` (ver aclaración en las reglas de negocio).

| Campo | Tipo | Notas |
|---|---|---|
| `id` | PK | |
| `nombre` | VARCHAR(120) NOT NULL | Ej. "Sede San Isidro" |
| `direccion` | VARCHAR(255) | |
| `fecha_evento` | DATE NOT NULL | Día del operativo |
| `estado` | VARCHAR(20) | `PLANIFICADA`, `EN_CURSO`, `CERRADA` |
| `created_at`, `updated_at` | TIMESTAMPTZ | |

Índices: `(fecha_evento, estado)`.

### `colegio`
Catálogo global de colegios a los que pertenecen los alumnos. **No** depende de la sede.

| Campo | Tipo | Notas |
|---|---|---|
| `id` | PK | |
| `nombre` | VARCHAR(160) NOT NULL UNIQUE | |
| `nombre_normalizado` | VARCHAR(160) NOT NULL | Minúsculas, sin tildes, para fuzzy-match |
| `created_at`, `updated_at` | TIMESTAMPTZ | |

Índices: `(nombre_normalizado)` — usado por la búsqueda aproximada de la sección 4 de las reglas de negocio.

### `apellido_comun`
Catálogo de referencia para corrección manual asistida (Modo 1 de la sección 4).

| Campo | Tipo | Notas |
|---|---|---|
| `id` | PK | |
| `apellido` | VARCHAR(80) NOT NULL UNIQUE | |
| `apellido_normalizado` | VARCHAR(80) NOT NULL | |

### `usuario`
Cuenta de persona con acceso al sistema (tutor o admin). El ciclo de vida activo/inactivo entre eventos vive aquí.

| Campo | Tipo | Notas |
|---|---|---|
| `id` | PK | |
| `nombre_completo` | VARCHAR(160) NOT NULL | |
| `correo` | VARCHAR(160) NOT NULL UNIQUE | |
| `password_hash` | VARCHAR(255) NOT NULL | BCrypt |
| `rol` | VARCHAR(20) NOT NULL | `rol_usuario` |
| `estado` | VARCHAR(20) NOT NULL | `estado_usuario`, default `ACTIVO` |
| `desactivado_en` | TIMESTAMPTZ | Null si está activo |
| `created_at`, `updated_at` | TIMESTAMPTZ | |

Índices: `(correo)`, `(estado, rol)`, `(nombre_completo)` para el filtro por nombre.

### `usuario_sede`
Historial de participación — relación **muchos a muchos** entre `usuario` y `sede`. Cada evento en que una persona ayuda genera una fila nueva, no sobrescribe la anterior.

| Campo | Tipo | Notas |
|---|---|---|
| `id` | PK | |
| `usuario_id` | FK → `usuario(id)` NOT NULL | |
| `sede_id` | FK → `sede(id)` NOT NULL | |
| `fecha_asignacion` | TIMESTAMPTZ NOT NULL | |
| `activo_en_sede` | BOOLEAN NOT NULL default true | Se pone en false cuando termina el evento |

Índices: `(usuario_id)`, `(sede_id)`, `(usuario_id, sede_id, fecha_asignacion)` único.

> Esta tabla **no** es el log de auditoría — solo registra "en qué sedes estuvo cada persona".

### `bloque`
Unidad de trabajo del día. Combinación **sede + curso + nivel + grado**.

| Campo | Tipo | Notas |
|---|---|---|
| `id` | PK | |
| `sede_id` | FK → `sede(id)` NOT NULL | |
| `curso` | VARCHAR(20) NOT NULL | `curso` |
| `nivel` | VARCHAR(20) NOT NULL | `nivel` |
| `grado` | VARCHAR(20) NOT NULL | `grado` |
| `tutor_asignado_id` | FK → `usuario(id)` | Nullable, se asigna al seleccionar el bloque |
| `bloqueado_desde` | TIMESTAMPTZ | Marca de bloqueo (desde la selección, ver 9.11) |
| `completado` | BOOLEAN NOT NULL default false | |
| `completado_en` | TIMESTAMPTZ | |
| `completado_por_id` | FK → `usuario(id)` | Tutor o admin que lo marcó |
| `created_at`, `updated_at` | TIMESTAMPTZ | |

Restricciones: `UNIQUE (sede_id, curso, nivel, grado)` — un solo bloque por combinación en una sede.
Índices: `(tutor_asignado_id)`, `(sede_id, completado)`.

### `clave_respuestas`
Clave de respuestas correctas de un examen. Scope: **curso + nivel + grado** (no por sede). Una sola clave se reutiliza en todas las sedes.

| Campo | Tipo | Notas |
|---|---|---|
| `id` | PK | |
| `curso` | VARCHAR(20) NOT NULL | |
| `nivel` | VARCHAR(20) NOT NULL | |
| `grado` | VARCHAR(20) NOT NULL | |
| `creado_por_id` | FK → `usuario(id)` NOT NULL | |
| `version` | INT NOT NULL default 1 | Se incrementa en cada corrección (Caso A) |
| `created_at`, `updated_at` | TIMESTAMPTZ | |

Restricciones: `UNIQUE (curso, nivel, grado)` — una clave activa por combinación.

> Para Inicial no se crea clave: la calificación la hace el tutor a mano (ver 9.13). El código de servicio simplemente no exige su existencia cuando el nivel es `INICIAL`.

### `clave_respuesta_detalle`
Las 20 respuestas correctas (A-E) de una clave.

| Campo | Tipo | Notas |
|---|---|---|
| `id` | PK | |
| `clave_respuestas_id` | FK → `clave_respuestas(id)` NOT NULL | |
| `numero_pregunta` | SMALLINT NOT NULL | 1 a 20 |
| `opcion_correcta` | VARCHAR(2) NOT NULL | `A`..`E` |

Restricciones: `UNIQUE (clave_respuestas_id, numero_pregunta)`.

### `clave_correccion`
Historial de correcciones a una clave (Caso A y Caso B de 9.13).

| Campo | Tipo | Notas |
|---|---|---|
| `id` | PK | |
| `clave_respuestas_id` | FK → `clave_respuestas(id)` NOT NULL | |
| `tipo` | VARCHAR(30) NOT NULL | `tipo_correccion_clave` |
| `numero_pregunta` | SMALLINT | Pregunta afectada |
| `opcion_anterior` | VARCHAR(2) | Solo para `ERROR_REGISTRO` |
| `opcion_nueva` | VARCHAR(2) | Solo para `ERROR_REGISTRO` |
| `motivo` | TEXT | Nota libre del admin/tutor |
| `aplicado_por_id` | FK → `usuario(id)` NOT NULL | |
| `fichas_recalculadas` | INT | Cuántos registros se recalcularon (A) o recibieron +1 (B) |
| `created_at` | TIMESTAMPTZ | |

### `lote`
Tanda de fotos tomadas de corrido en una sesión de captura de un bloque.

| Campo | Tipo | Notas |
|---|---|---|
| `id` | PK | |
| `bloque_id` | FK → `bloque(id)` NOT NULL | |
| `tutor_id` | FK → `usuario(id)` NOT NULL | Quien capturó |
| `estado` | VARCHAR(20) NOT NULL | `estado_lote` |
| `total_fotos_esperadas` | INT | Reportado por la app al iniciar la subida |
| `total_fotos_recibidas` | INT NOT NULL default 0 | |
| `ultima_foto_tomada_at` | TIMESTAMPTZ | Para la indicación de reanudación (9.10) |
| `created_at`, `updated_at` | TIMESTAMPTZ | |

Índices: `(bloque_id, estado)`, `(tutor_id)`.

### `imagen`
Foto física de una ficha. Guardada en Firebase Storage; aquí solo vive el metadata y la ruta.

| Campo | Tipo | Notas |
|---|---|---|
| `id` | PK | |
| `lote_id` | FK → `lote(id)` NOT NULL | |
| `orden_en_lote` | INT NOT NULL | Para reanudación y auditoría |
| `storage_path` | VARCHAR(500) NOT NULL | Ruta en Firebase Storage |
| `storage_path_censurada` | VARCHAR(500) | Versión con DNI/celular recortados (la que va a visión) |
| `bytes` | BIGINT | |
| `hash_sha256` | VARCHAR(64) | Para detectar subidas duplicadas |
| `reemplaza_imagen_id` | FK → `imagen(id)` | Si es una re-foto por edición (9.10) |
| `created_at` | TIMESTAMPTZ | |

Índices: `(lote_id, orden_en_lote)`, `(hash_sha256)`.

### `registro`
La "ficha digitalizada" de un alumno. Vinculada 1-a-1 con una imagen vigente (la última versión no reemplazada).

| Campo | Tipo | Notas |
|---|---|---|
| `id` | PK | |
| `lote_id` | FK → `lote(id)` NOT NULL | |
| `bloque_id` | FK → `bloque(id)` NOT NULL | Denormalizado para consultas rápidas |
| `imagen_id` | FK → `imagen(id)` NOT NULL | Imagen vigente |
| `nombre` | VARCHAR(120) | Null hasta que visión lo extraiga |
| `apellidos` | VARCHAR(120) | |
| `colegio_id` | FK → `colegio(id)` | |
| `colegio_texto_extraido` | VARCHAR(160) | Lo que el modelo leyó, antes de matchear al catálogo |
| `hora_finalizacion` | TIME | Criterio de desempate en el ranking |
| `correctas` | SMALLINT | Calculado (o escrito por el tutor en Inicial) |
| `incorrectas` | SMALLINT | |
| `anuladas` | SMALLINT | |
| `blancos` | SMALLINT | |
| `puntaje` | INT | `correctas * 20 − incorrectas * 1` |
| `estado` | VARCHAR(30) NOT NULL | `estado_registro` |
| `tiene_observacion` | BOOLEAN NOT NULL default false | Marcado manual o por baja confianza general |
| `confianza_promedio` | NUMERIC(4,3) | Reportada por visión |
| `revisor_actual_id` | FK → `usuario(id)` | Quien tiene la ficha abierta ahora mismo |
| `revisor_tomada_at` | TIMESTAMPTZ | Para el auto-release a los 15 min |
| `confirmado_por_id` | FK → `usuario(id)` | |
| `confirmado_at` | TIMESTAMPTZ | |
| `created_at`, `updated_at` | TIMESTAMPTZ | |

Índices: `(bloque_id, estado)`, `(revisor_actual_id, estado)`, `(colegio_id)`, `(apellidos, nombre)`.

> El DNI **no** se persiste en texto plano (se va de la imagen enmascarada a visión); la columna no existe. Si en el futuro se necesitara para algo, se almacenaría cifrado — no es prioridad.

### `respuesta_alumno`
Las 20 respuestas marcadas en la ficha de un registro (Primaria/Secundaria). No aplica a Inicial.

| Campo | Tipo | Notas |
|---|---|---|
| `id` | PK | |
| `registro_id` | FK → `registro(id)` NOT NULL | |
| `numero_pregunta` | SMALLINT NOT NULL | 1..20 |
| `opcion_marcada` | VARCHAR(10) NOT NULL | `opcion` (incluye `BLANCO`, `ANULADA`) |
| `es_correcta` | BOOLEAN | Resultado calculado contra la clave |
| `estados_opciones_json` | JSONB | Reporte crudo del modelo: `{"A":"VACIA","B":"MARCADA",...}` |
| `revisada_por_humano` | BOOLEAN NOT NULL default false | |
| `created_at`, `updated_at` | TIMESTAMPTZ | |

Restricciones: `UNIQUE (registro_id, numero_pregunta)`.

### `auditoria_accion`
Log append-only de **acciones dentro del sistema** (distinto de `usuario_sede`, ver 9.12 y 9.14). Poblada por el módulo transversal Audit vía Spring AOP.

| Campo | Tipo | Notas |
|---|---|---|
| `id` | PK | |
| `usuario_id` | FK → `usuario(id)` NOT NULL | |
| `accion` | VARCHAR(60) NOT NULL | Ej. `CONFIRMAR_REGISTRO`, `MARCAR_BLOQUE_COMPLETADO`, `EDITAR_CLAVE`, `EXPORTAR_EXCEL`, `REACTIVAR_USUARIO` |
| `entidad` | VARCHAR(40) NOT NULL | Ej. `REGISTRO`, `BLOQUE`, `CLAVE_RESPUESTAS` |
| `entidad_id` | BIGINT | |
| `detalle_json` | JSONB | Snapshot de lo cambiado (diff ligero) |
| `created_at` | TIMESTAMPTZ NOT NULL default `now()` | |

Índices: `(usuario_id, created_at)`, `(entidad, entidad_id)`.

> Append-only: no hay `UPDATE` ni `DELETE` desde la aplicación — Flyway solo define el `INSERT`.

## Relaciones clave (resumen)

- `sede 1 — N bloque`
- `bloque 1 — N lote` (puede haber varios lotes en un mismo bloque si se corta y retoma)
- `lote 1 — N imagen`
- `lote 1 — N registro`
- `imagen 1 — 1 registro` (vigente); una `imagen` puede apuntar a otra como `reemplaza_imagen_id`.
- `registro 1 — N respuesta_alumno` (0 o 20)
- `colegio 1 — N registro`
- `clave_respuestas 1 — N clave_respuesta_detalle` (20 filas)
- `clave_respuestas 1 — N clave_correccion`
- `usuario N — N sede` vía `usuario_sede`
- `usuario 1 — N auditoria_accion`
