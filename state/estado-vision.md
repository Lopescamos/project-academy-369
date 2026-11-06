# Estado — Bounded Context: Visión

**Estado global:** no iniciado. Antes de arrancar, releer `.claude/agents/<capa>.md` — este BC
es el otro con integración externa real (API del proveedor de visión) y requiere cuidado extra
en el puerto/adaptador.

## Alcance

**Sin tablas propias.** Orquesta la llamada al proveedor de visión multimodal sobre la imagen
ya censurada por Imágenes, aplica las reglas de marca y escribe el resultado en `Registro` +
`Respuesta_Alumno` (del BC Registro, Fase B).

Responsabilidades:

- Elegir proveedor (Gemini 2.5 Flash / Claude Haiku 5.5) detrás de un puerto único.
- Enviar únicamente la imagen censurada (invariante compartido con Imágenes).
- Aplicar reglas de marca: en blanco neutral, 2+ marcas = anulada, detección de "con
  observación" cuando aplique.
- Reusar las clases puras de dominio ya prototipadas para calificación y detección de marcas
  (ver `CLAUDE.md`), no reimplementar.

## Dependencias

- Upstream: Imágenes (imagen censurada), Registro Fase A (clave de respuestas, bloque).
- Downstream: Registro Fase B (escribe `Registro` y `Respuesta_Alumno`), Exportación.

## Pendientes propios

- [ ] `domain/`: puerto `ExtractorVision` (contrato con el proveedor), tipos de dominio del
  resultado extraído (identificación + 20 respuestas marcadas).
- [ ] `infrastructure/`: adaptador del proveedor elegido (API key SOLO server-side), manejo de
  rate-limit, reintentos, timeouts.
- [ ] `application/`: orquestar imagen censurada → extracción → reglas de marca → escritura en
  Registro Fase B.
- [ ] `interfaces/rest/`: endpoints internos (no públicos) para disparar la extracción desde
  Imágenes / desde un job.
- [ ] Tests con fixtures de respuesta del proveedor (no se llama a la API real en tests).

## Riesgos / cuidados

- API key del proveedor **solo** en backend.
- Métricas de costo: el objetivo es <USD 1 por sede completa (~800 fichas). El adaptador debe
  loggear tokens/costo por llamada para poder medir.

## Handoff para el siguiente agente

Vacío — todavía no arrancó el flujo.
