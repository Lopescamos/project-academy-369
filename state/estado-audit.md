# Estado — Bounded Context: Audit (transversal)

**Estado global:** no iniciado. Es el **último** BC a implementar — depende de que los demás ya
expongan sus comandos para poder interceptarlos vía Spring AOP (ver `CLAUDE.md`).

## Alcance

Tabla: `Auditoria_Accion`.

Responsabilidades:

- Interceptar comandos de los demás módulos vía Spring AOP y registrarlos en
  `Auditoria_Accion`.
- No se mezcla con `Usuario_Sede`: eso es historial de participación, no de acciones. Ver
  ADR-005.

## Dependencias

- Upstream: todos los demás BCs ya tienen sus comandos definidos y expuestos.
- Downstream: ninguno.

## Pendientes propios

- [ ] `domain/`: aggregate `AuditoriaAccion`, value objects (tipo de acción, metadatos).
- [ ] `infrastructure/`: aspecto AOP + JPA entity + mapper.
- [ ] `application/`: política de qué comandos se auditan y con qué detalle.
- [ ] `interfaces/rest/`: consulta de auditoría (solo admin).

## Handoff para el siguiente agente

Vacío — todavía no arrancó el flujo.
