# Contrato de API

> Estilo REST sobre JSON. Prefijo global `/api/v1`. Autenticación: **Bearer JWT** (header `Authorization: Bearer <token>`), salvo los endpoints de autenticación. Documentación servida en `/swagger-ui/index.html` (springdoc-openapi).

## Convenciones

- Códigos de estado: `200` éxito con cuerpo, `201` creación, `204` éxito sin cuerpo, `400` validación, `401` sin autenticar, `403` sin permiso, `404` no encontrado, `409` conflicto (ej. bloque ya tomado), `422` regla de negocio incumplida, `500` error no manejado.
- Formato de error (estilo RFC 7807):
```json
{
  "type": "https://olimpiadas369.app/errors/bloque-ya-tomado",
  "title": "El bloque ya está siendo trabajado por otro tutor",
  "status": 409,
  "detail": "El bloque B-42 fue tomado por Kevin Lopez a las 09:14",
  "instance": "/api/v1/bloques/42/seleccionar"
}
```
- Paginación: `?page=0&size=20&sort=created_at,desc`.
- Timestamps en ISO-8601 UTC.

## Identidad y acceso

| Método | Ruta | Descripción | Roles |
|---|---|---|---|
| POST | `/auth/login` | Credenciales → JWT + refresh | Público |
| POST | `/auth/refresh` | Refresh token → nuevo JWT | Autenticado |
| POST | `/auth/logout` | Invalida el refresh | Autenticado |
| GET | `/auth/me` | Perfil + roles + sede activa | Autenticado |
| POST | `/usuarios/invitar` | Correo + nombre + sede — genera credenciales y las envía | ADMIN |
| POST | `/usuarios/{id}/reactivar` | Reactiva cuenta inactiva | ADMIN |
| POST | `/usuarios/{id}/desactivar` | Desactiva cuenta | ADMIN |
| POST | `/usuarios/{id}/asignar-sede` | Asocia a una sede del evento actual | ADMIN |
| POST | `/usuarios/invitado-en-sitio` | Crea tutor al vuelo (sin correo previo) | ADMIN |
| GET | `/usuarios?nombre=&estado=&rol=` | Lista con filtro por nombre, activos/inactivos | ADMIN |
| GET | `/usuarios/{id}/historial-sedes` | Historial de participación | ADMIN, propio usuario |
| GET | `/empleados` | Lista de colaboradores con historial agregado | ADMIN |

## Sedes y bloques

| Método | Ruta | Descripción | Roles |
|---|---|---|---|
| GET | `/sedes?fecha=&estado=` | Lista sedes del día | ADMIN |
| POST | `/sedes` | Crea sede para un evento | ADMIN |
| GET | `/sedes/{id}/bloques` | Bloques de esa sede con su estado | ADMIN, TUTOR (su sede) |
| POST | `/bloques/{id}/seleccionar` | Toma el bloque (bloqueo desde la selección) | TUTOR, ADMIN |
| POST | `/bloques/{id}/liberar` | Libera el bloque sin completarlo | TUTOR dueño, ADMIN |
| POST | `/bloques/{id}/completar` | Marca completado → entra al ranking | TUTOR, ADMIN |
| GET | `/bloques/{id}/ranking` | Ranking en vivo del bloque | TUTOR, ADMIN |

## Clave de respuestas

| Método | Ruta | Descripción | Roles |
|---|---|---|---|
| GET | `/claves?curso=&nivel=&grado=` | Consulta clave actual | TUTOR, ADMIN |
| POST | `/claves` | Crea clave (20 respuestas A-E) | TUTOR, ADMIN |
| PUT | `/claves/{id}` | Caso A — corrige opción, dispara recálculo | TUTOR, ADMIN |
| POST | `/claves/{id}/pregunta-invalida` | Caso B — +1 punto a todos para esa pregunta | TUTOR, ADMIN |
| GET | `/claves/{id}/correcciones` | Historial de correcciones | TUTOR, ADMIN |

## Captura y lotes

| Método | Ruta | Descripción | Roles |
|---|---|---|---|
| POST | `/lotes` | Abre un lote vacío en un bloque | TUTOR |
| POST | `/lotes/{id}/imagenes` | `multipart/form-data`: sube una o más fotos del lote | TUTOR dueño |
| POST | `/lotes/{id}/finalizar` | Marca el lote como subido; dispara procesamiento por visión | TUTOR dueño |
| GET | `/lotes/{id}` | Estado del lote y progreso de visión | TUTOR, ADMIN |
| GET | `/bloques/{id}/lotes-en-progreso` | Para la reanudación (ver 9.10 orden de captura) | TUTOR, ADMIN |

## Revisión y corrección de registros

| Método | Ruta | Descripción | Roles |
|---|---|---|---|
| GET | `/registros?bloqueId=&estado=` | Lista fichas de un bloque, filtrables | TUTOR, ADMIN |
| GET | `/registros/{id}` | Datos extraídos + URL firmada de la imagen | TUTOR, ADMIN |
| POST | `/registros/{id}/tomar` | Toma la ficha (estado → `EN_REVISION`) | TUTOR, ADMIN |
| POST | `/registros/{id}/liberar` | Libera la ficha sin confirmar | TUTOR dueño, ADMIN |
| PUT | `/registros/{id}` | Actualiza nombre/apellidos/colegio/hora | TUTOR dueño, ADMIN |
| PUT | `/registros/{id}/respuestas` | Corrige una o varias respuestas marcadas | TUTOR dueño, ADMIN |
| POST | `/registros/{id}/confirmar` | Confirma y bloquea edición | TUTOR dueño, ADMIN |
| POST | `/registros/{id}/reemplazar-imagen` | Edita: sube nueva foto, re-procesa con visión | TUTOR, ADMIN |
| POST | `/registros/{id}/marcar-observacion` | Fuerza revisión humana total | TUTOR, ADMIN |

Autocompletado (ayuda de corrección — sección 4):

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/sugerencias/apellidos?q=` | Catálogo de apellidos comunes |
| GET | `/sugerencias/colegios?q=` | Catálogo global de colegios (fuzzy) |

## Exportación

| Método | Ruta | Descripción | Roles |
|---|---|---|---|
| GET | `/exportaciones/bloque/{id}.xlsx` | Excel de un bloque específico | TUTOR (los suyos), ADMIN |
| GET | `/exportaciones/sede/{id}.xlsx` | Excel completo de la sede, con una hoja por bloque | ADMIN (su sede activa) |

## Auditoría

| Método | Ruta | Descripción | Roles |
|---|---|---|---|
| GET | `/auditoria?usuarioId=&accion=&desde=&hasta=` | Log de acciones | ADMIN |

## Ejemplo de payload — subir lote

**POST** `/api/v1/lotes/42/imagenes`
`Content-Type: multipart/form-data`
Campos:
- `imagenes[]` — binarios (hasta N por request)
- `metadata` — JSON `[{"orden":1,"tomada_at":"..."},{"orden":2,"tomada_at":"..."}]`

Respuesta `201`:
```json
{
  "loteId": 42,
  "imagenesRecibidas": 2,
  "totalEnLote": 18,
  "pendientesVision": 2
}
```

## Ejemplo de payload — datos extraídos de un registro

**GET** `/api/v1/registros/987`
```json
{
  "id": 987,
  "bloqueId": 12,
  "loteId": 42,
  "imagen": {
    "urlFirmada": "https://firebasestorage.googleapis.com/.../signed?token=...",
    "expiraEn": "2026-11-06T15:30:00Z"
  },
  "nombre": "Ana",
  "apellidos": "Ramírez Soto",
  "colegio": { "id": 301, "nombre": "I.E. Corazón de Cristo" },
  "colegioTextoExtraido": "corazon de cristo",
  "horaFinalizacion": "10:42",
  "correctas": 14,
  "incorrectas": 4,
  "anuladas": 1,
  "blancos": 1,
  "puntaje": 276,
  "estado": "LIBRE",
  "confianzaPromedio": 0.92,
  "respuestas": [
    { "numero": 1, "opcionMarcada": "B", "esCorrecta": true,  "estadosOpciones": {"A":"VACIA","B":"MARCADA","C":"VACIA","D":"VACIA","E":"VACIA"} },
    { "numero": 2, "opcionMarcada": "ANULADA", "esCorrecta": false, "estadosOpciones": {"A":"MARCADA","B":"MARCADA","C":"VACIA","D":"VACIA","E":"VACIA"} }
  ]
}
```
