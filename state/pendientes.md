# Pendientes del proyecto

Este archivo lista lo que falta hacer fuera del flujo normal de un bounded context (ese flujo se
coordina vía `state/estado-<bc>.md`). Se mantiene vivo: cuando algo se cierra, se mueve a su
`estado-<bc>.md` correspondiente o se borra de aquí.

## Módulo `shared` (paso cero, antes del primer BC)

- [ ] Portar las 9 clases de `references/shared/` a `com.olimpiadas369.shared` siguiendo el
  inventario de [`state/estado-shared.md`](estado-shared.md). `references/` está gitignoreado:
  es material local de referencia, no se sube. Sin `shared` listo, Identidad y acceso no puede
  arrancar (todos sus handlers devuelven `Result<T, ApplicationError>`).

## Documentación transversal (bloquea a los agentes)

- [ ] **`docs/guia-arquitectura-por-capas-ddd.md`** — todavía no existe. Es la única fuente de
  convenciones de capas, flujo de 15 pasos y tabla de nomenclatura referida por `CLAUDE.md` y
  por los cinco agentes de `.claude/agents/`. **Alta prioridad** — sin ella ningún agente puede
  arrancar sin reinventar convenciones.
- [ ] **`docs/modelo-bd.puml`** — `CLAUDE.md` lo referencia, pero por ahora solo existe
  `docs/olimpiadas369-erd.puml`. Decidir si se renombra o si `modelo-bd.puml` es un diagrama
  distinto (modelo físico con FKs + enums completos).
- [ ] **`docs/user-stories.md`** — referido por `CLAUDE.md` y por el agente `domain`. Pendiente
  de escribir (20 US con criterios de aceptación).

## Decisiones abiertas (ADRs por escribir)

- [ ] **ADR: Identidad y acceso — Firebase Auth vs. JWT propio.** El módulo de negocio
  (`Usuario`, `Usuario_Sede`, invitación por correo, ciclo activo/inactivo entre eventos) es el
  mismo en ambos casos. Decidir si Firebase Auth entra como mecanismo de bajo nivel o si la
  autenticación es 100% JWT propio con `jjwt`. Impacta la capa `infrastructure` de Identidad.
- [ ] **Versión exacta de `springdoc-openapi` 3.x.** `CLAUDE.md` fija 3.x por compatibilidad con
  Spring Boot 4.1.x, pero falta fijar el patch exacto según el patch de Boot en uso (ver
  `pom.xml`). Verificar la matriz de compatibilidad antes de pinnear.

## Infra / entorno

- [ ] Perfiles Spring (`application-dev.yml`, `application-prod.yml`, `application-test.yml`)
  todavía no creados — el repo arranca con la configuración por defecto de Initializr.
- [ ] `Dockerfile` multi-stage (`eclipse-temurin:21-jdk-alpine` → `-jre-alpine`) pendiente.
- [ ] Workflow de GitHub Actions (build → test → deploy a Render vía Deploy Hook) pendiente.
- [ ] `.env.example` en la raíz que documente las variables que el perfil `dev` espera
  (credenciales Postgres local, API key del proveedor de visión, credenciales Firebase Admin).
  El `.env` real nunca se commitea.

## Convenciones de uso

- Cada ítem debe nombrar el archivo o el BC que toca.
- Al cerrar un ítem, moverlo al `estado-<bc>.md` correspondiente (si pertenece a un BC) o
  borrarlo (si es transversal y ya no aplica).
- No duplicar aquí lo que ya está en `CLAUDE.md` — este archivo es solo lo que falta.
