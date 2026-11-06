# Pendientes del proyecto

Este archivo lista lo que falta hacer fuera del flujo normal de un bounded context (ese flujo se
coordina vía `state/estado-<bc>.md`). Se mantiene vivo: cuando algo se cierra, se mueve a su
`estado-<bc>.md` correspondiente o se borra de aquí.

## Módulo `shared` (paso cero, antes del primer BC)

- [x] ~~Portar las 9 clases de `references/shared/` a `com.olimpiadas369.backend.shared`~~ —
  hecho en `feature/shared`. Ver [`state/estado-shared.md`](estado-shared.md).

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

- [x] ~~Perfiles Spring (`dev`, `prod`, `test`) y `.env.example`~~ — hechos como
  `application-*.properties`; `compose.yaml` alineado con las variables `DB_*`.
- [ ] `SnakeCaseWithPluralizedTablePhysicalNamingStrategy` en
  `shared/infrastructure/persistence/jpa/configuration/strategy/` (no existe en `references/`,
  hay que escribirla). Luego descomentar `spring.jpa.properties.hibernate.physical_naming_strategy`
  en `application.properties`. Bloquea la primera entidad JPA (Identidad).
- [ ] Dependencias que las propiedades ya anticipan pero el `pom.xml` aún no tiene: `jjwt`
  (Identidad), `spring-boot-starter-mail` (Identidad), `springdoc-openapi` (ver ítem de arriba),
  `firebase-admin` (Imágenes), `poi`/`poi-ooxml` (Exportación). Agregar cada una con su BC.
- [ ] `BackendApplicationTests` (scaffold de Initializr) usa Testcontainers y requiere Docker
  corriendo; `CLAUDE.md` define H2 en el perfil `test`. Decidir si se pasa a `@ActiveProfiles("test")`.
- [ ] `Dockerfile` multi-stage (`eclipse-temurin:21-jdk-alpine` → `-jre-alpine`) pendiente.
- [ ] Workflow de GitHub Actions (build → test → deploy a Render vía Deploy Hook) pendiente.

## Convenciones de uso

- Cada ítem debe nombrar el archivo o el BC que toca.
- Al cerrar un ítem, moverlo al `estado-<bc>.md` correspondiente (si pertenece a un BC) o
  borrarlo (si es transversal y ya no aplica).
- No duplicar aquí lo que ya está en `CLAUDE.md` — este archivo es solo lo que falta.
