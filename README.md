# Olimpiadas369 — Backend

Backend del sistema de digitalización de fichas del examen interescolar **Olimpiadas 369**:
captura por foto (app Flutter), extracción de identificación y respuestas por visión
multimodal, cálculo de puntaje contra una clave de respuestas, revisión asistida (web Angular)
y exportación a Excel.

Proyecto personal, un solo desarrollador. Documentación completa en `docs/` y estado vivo del
desarrollo en `state/`.

> Para contexto de negocio ver [`docs/olimpiadas369-one-pager.md`](docs/olimpiadas369-one-pager.md).
> Para contexto técnico y reglas de arquitectura ver [`.claude/CLAUDE.md`](.claude/CLAUDE.md).

## Stack

- **Java 21**, **Spring Boot 4.1.2**, Maven.
- **PostgreSQL** (Supabase en prod, Docker Compose en dev, H2 en test).
- **Apache POI** 5.4.0 para generación del Excel final.
- **Firebase Admin SDK** 9.3.0 para Storage (imágenes de las fichas).
- **jjwt** 0.12.6 para autenticación.
- **springdoc-openapi** 3.x para OpenAPI/Swagger UI.

Deploy objetivo: **Render** (backend) + **Firebase Hosting** (Angular) + **Supabase** (DB) +
**Firebase Storage** (imágenes).

## Módulo `shared` (paso cero)

Kernel técnico reutilizado por todos los BCs: `AbstractDomainAggregateRoot`, `Result<T, E>`,
`ApplicationError`, `GlobalExceptionHandler`, resources y assemblers REST, configuración i18n.
No es un bounded context — se implementa **antes** del primer BC. Ver
[`state/estado-shared.md`](state/estado-shared.md).

## Bounded Contexts

Monolito modular, DDD por capas. Orden de implementación (manda la dependencia entre tablas):

1. **Identidad y acceso** — `Usuario`, `Usuario_Sede`.
2. **Registro (core domain, Fase A)** — `Sede`, `Colegio`, `Apellido_Comun`, `Bloque`,
   `Clave_Respuestas` (+ detalle + corrección), `Lote`.
3. **Imágenes** — `Imagen`. Firebase Storage + censura por recorte físico.
4. **Visión** — sin tablas; API de visión multimodal (Gemini / Claude Haiku).
5. **Registro (cierre, Fase B)** — `Registro`, `Respuesta_Alumno`.
6. **Exportación** — sin tablas; Apache POI.
7. **Audit** — `Auditoria_Accion`, Spring AOP transversal.

Estado de cada uno: `state/estado-<bc>.md`. Pendientes transversales: `state/pendientes.md`.

## Estructura del repositorio

```
.
├── .claude/              # Instrucciones para Claude Code y agentes por capa
│   ├── CLAUDE.md
│   └── agents/           # domain, infrastructure, application, interfaces, tests
├── docs/                 # Diseño estable (no lo tocan los agentes)
│   ├── adr/              # Architecture Decision Records
│   ├── arquitectura-c4.md
│   ├── contrato-api.md
│   ├── epicas.md
│   ├── modelo-datos.md
│   ├── olimpiadas369-erd.puml
│   ├── olimpiadas369-one-pager.md
│   ├── olimpiadas369-structurizr.dsl
│   └── requisitos.md
├── state/                # Estado vivo (los agentes leen y escriben aquí)
│   ├── pendientes.md
│   └── estado-<bc>.md    # uno por bounded context
├── src/                  # Código Java
├── compose.yaml          # Postgres local para perfil dev
└── pom.xml
```

## Cómo correr en local

Requisitos: Java 21, Docker (para Postgres local).

```bash
docker compose up -d        # levanta Postgres local
./mvnw spring-boot:run      # arranca el backend (perfil dev por defecto)
./mvnw verify               # tests (perfil test con H2 en memoria)
```

Variables de entorno (perfil `dev`): se cargan desde `.env` (nunca commiteado). Ver
`state/pendientes.md` para el `.env.example` todavía por escribir.

## Convenciones

- **Gitflow**: `master`, `develop`, `feature/<nombre>`, `bugfix/<nombre>`, `hotfix/<nombre>`,
  `release/<version>`.
- **Conventional Commits**: `feat`, `fix`, `docs`, `refactor`, `test`, `chore`.
- **Arquitectura por capas DDD** (`domain` → `infrastructure` → `application` →
  `interfaces/rest`). Detalle completo en `docs/guia-arquitectura-por-capas-ddd.md`
  (pendiente — ver `state/pendientes.md`).
- Tablas en plural y snake_case. URLs REST en minúscula y plural
  (`/api/v1/bloques`, `/api/v1/registros`).

## Seguridad

- **DNI y celular nunca persisten y nunca salen del backend.** Se censuran por recorte físico
  sobre la imagen antes de llamar a la API de visión (ADR-008).
- API key del proveedor de visión y credenciales de Firebase Admin **solo** en backend. Nunca
  en Flutter ni en Angular.
- `.env` nunca se commitea.

## Licencia

Ver [`LICENSE`](LICENSE).
