# Estado — Bounded Context: Exportación

**Estado global:** no iniciado.

## Alcance

**Sin tablas propias.** Lee `Registro` ya confirmado y genera el Excel con la plantilla exacta
usando Apache POI (`poi` + `poi-ooxml` 5.4.0 — ver `CLAUDE.md`).

Responsabilidades:

- Exportación por sede (admin consolida su sede entera).
- Exportación por tutor (solo lo que ese tutor trabajó).
- Respetar la plantilla ya definida (headers, orden de columnas, formato de puntaje).
- Reusar las clases puras de dominio de calificación — no recalcular con otro algoritmo.

## Dependencias

- Upstream: Registro Fase B (`Registro` confirmado).
- Downstream: ninguno.

## Pendientes propios

- [ ] `domain/`: puerto `GeneradorReporte` si hace falta abstraer el formato; tipos de dominio
  del reporte (filas por alumno, ranking).
- [ ] `infrastructure/`: adaptador Apache POI con la plantilla fija.
- [ ] `application/`: consulta → construcción del modelo del reporte → entrega del XLSX.
- [ ] `interfaces/rest/`: `/api/v1/exportaciones/...` (descarga del XLSX).
- [ ] Tests con snapshot de las primeras filas del XLSX generado.

## Handoff para el siguiente agente

Vacío — todavía no arrancó el flujo.
