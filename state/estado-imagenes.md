# Estado — Bounded Context: Imágenes

**Estado global:** no iniciado. Antes de arrancar, releer `.claude/agents/<capa>.md` — este BC
es uno de los dos con integración externa real (Firebase Storage) y requiere cuidado extra en
el puerto/adaptador.

## Alcance

Tabla: `Imagen`.

Responsabilidades:

- Subida a Firebase Storage con signed URLs de corta duración.
- Compresión antes de persistir.
- **Censura por recorte físico de coordenadas fijas** (DNI, celular) antes de enviar a Visión.
  Ver ADR-008. El DNI nunca se persiste.
- Detección de duplicados por orden de captura + marca de última foto tomada (no por DNI).
- Retención: no se borran al confirmar ni al exportar — se limpian recién cuando han pasado al
  menos dos eventos posteriores. Ver ADR-009.

## Dependencias

- Upstream: Registro Fase A (`Lote`).
- Downstream: Visión (consume la imagen ya censurada).

## Pendientes propios

- [ ] `domain/`: aggregate `Imagen`, value objects (coordenadas de recorte, hash de duplicado),
  puerto `AlmacenImagenes` (Firebase Storage por detrás), puerto `CensoradorImagen` (recorte
  físico).
- [ ] `infrastructure/`: adaptador Firebase Admin SDK (credenciales SOLO server-side — ver
  `CLAUDE.md`), adaptador de censura (Java 2D / librería de imágenes), JPA entity + mapper.
- [ ] `application/`: subir lote de imágenes, generar signed URL, marcar última foto, job de
  limpieza con política de dos-eventos.
- [ ] `interfaces/rest/`: `/api/v1/imagenes` (subida multipart, generación de signed URL).
- [ ] Tests, incluyendo verificación de que la imagen que sale hacia Visión está censurada.

## Riesgos / cuidados

- API key de Firebase Admin **solo** en backend. Nunca se expone a Flutter ni a Angular.
- La imagen que llega a Visión debe estar censurada antes de salir del backend — es un
  invariante de seguridad (datos personales de menores).

## Handoff para el siguiente agente

Vacío — todavía no arrancó el flujo.
