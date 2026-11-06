# Estado — Bounded Context: Identidad y acceso

**Estado global:** no iniciado. Este BC es el primero del orden de implementación (`CLAUDE.md`
§ "Bounded Contexts y orden"): no depende de ningún otro.

## Alcance

Tablas: `Usuario`, `Usuario_Sede`.

Reglas de negocio ancladas:

- JWT (mecanismo de bajo nivel pendiente de ADR — Firebase Auth vs. JWT propio con `jjwt`).
- Invitación por correo con credenciales autogeneradas (envío por SMTP, el adaptador vive en
  `infrastructure/`).
- Ciclo activo/inactivo de usuarios entre eventos: se desactivan solos al cerrar el evento y se
  reactivan para el siguiente sin perder historial.
- `Usuario_Sede` es historial de participación (M:N), no se sobrescribe al reactivar. Ver
  ADR-005: no se mezcla con auditoría de acciones.

## Dependencias

- Upstream: ninguno.
- Downstream: Registro, Imágenes, Visión, Exportación y Audit dependen de este BC (todos
  arrastran el `usuario` que crea/asigna/modifica).

## Pendientes propios

- [ ] `domain/`: aggregates `Usuario`, `UsuarioSede`; value objects (`Email`, `Credencial`,
  enums de rol y estado); repositorios como interfaces puras; puerto `EnviadorInvitaciones`.
- [ ] `infrastructure/`: JPA entities + mappers, implementación SMTP de `EnviadorInvitaciones`,
  implementación JWT (depende del ADR abierto en `state/pendientes.md`).
- [ ] `application/`: handlers con `Result<T, ApplicationError>` (sin excepciones de control de
  flujo). Operaciones de alta por correo, invitación al momento, reactivación entre eventos.
- [ ] `interfaces/rest/`: endpoints bajo `/api/v1/usuarios`, `/api/v1/auth` (login, refresh).
- [ ] Tests por capa.

## Decisiones abiertas que bloquean al BC

- ADR Identidad: Firebase Auth vs. JWT propio (ver `state/pendientes.md`).

## Handoff para el siguiente agente

Vacío — todavía no arrancó el flujo. El primer agente (`/agent domain`) debe inicializar esta
sección al terminar su parte: modelo, interfaces expuestas y supuestos tomados.
