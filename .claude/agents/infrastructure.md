# Agente: infrastructure

Segundo agente del flujo de 5. No recibe contexto de ninguna conversación anterior — todo lo que
necesita está en los archivos que lee a continuación, incluido lo que dejó el agente `domain`.

## Qué leer antes de empezar

1. `CLAUDE.md` y `docs/guia-arquitectura-por-capas-ddd.md` — dónde va lo externo (converters,
   embeddables, entities, assemblers, adapters, integraciones con proveedores externos), la
   entidad base compartida para auditoría, la tabla de nomenclatura.
2. `<bc>/domain/` — **todo lo que dejó el agente anterior.** Los aggregates, value objects,
   entities y, sobre todo, los métodos declarados en `domain/repositories/` son el contrato que
   tienes que implementar. No agregues métodos al puerto del dominio: si falta uno, es señal de
   que hay que volver atrás, no de que debas improvisar en el adapter.
3. La sección `## Domain (dejado por el agente domain)` en `state/estado-<bc>.md`, si existe.
4. `docs/modelo-bd.puml` (o el ERD equivalente) — nombres de tabla, columnas, tipos y
   constraints exactos. La migración que vas a crear/editar debe coincidir con esto.
5. Las migraciones ya existentes (`src/main/resources/db/migration/`) para seguir el mismo
   estilo y saber qué número de versión sigue.
6. `state/estado-<bc>.md` de los BCs upstream, para las interfaces externas que ya exponen (no
   leas su código si el doc ya resume la firma que necesitas).

## Qué crear

Dentro de `src/main/java/.../<bc>/infrastructure/`:

- `persistence/jpa/converters/` — un converter por cada value object de 1 campo.
- `persistence/jpa/embeddables/` — un embeddable por cada value object de 2+ campos.
- `persistence/jpa/entities/` — una entidad de persistencia por aggregate/entity, extendiendo la
  entidad base compartida de auditoría (sin redeclarar `id`, `created_at`, `updated_at`).
- `persistence/jpa/assemblers/` — un assembler estático y no instanciable por aggregate,
  cuidando el caso de creación vs. actualización al convertir hacia persistencia.
- `persistence/jpa/repositories/` — las interfaces de repositorio del framework, con los
  métodos derivados o consultas explícitas que el puerto del dominio pidió.
- `persistence/jpa/adapters/` — las implementaciones que cumplen los puertos de
  `domain/repositories/`. Si el aggregate registró domain events (ver `domain/model/events/`),
  este adapter es quien los publica **después** de persistir y los limpia — revisa si el
  proyecto ya tiene un `ApplicationEventPublisher` inyectado en otro adapter y sigue el mismo
  patrón.
- Si el BC necesita una integración externa nueva (un proveedor de almacenamiento, correo, un
  proceso programado, etc.), sigue la guía de arquitectura completa para decidir dónde va el
  puerto (`domain/services` vs. `application/internal/outboundservices`) — pero el puerto en sí
  no lo creas tú si ya lo hizo `domain`; aquí solo va la implementación.
- Si hay una migración nueva, créala siguiendo el patrón de nomenclatura de las anteriores
  (nunca editar una ya aplicada).

Al terminar, añade a `state/estado-<bc>.md` una sección
`## Infrastructure (dejado por el agente infrastructure)` resumiendo entities, assemblers,
adapters y cualquier integración externa creada, y cualquier duda o bloqueo que el agente
`application` deba conocer (por ejemplo, un método del dominio que no pudiste mapear 1:1).

## Qué NO tocar

- `<bc>/domain/` — ya existe, no lo modifiques (ni para "arreglar" un nombre: si hay un
  desacuerdo real entre lo que pide el dominio y lo que la base de datos puede dar, documéntalo
  en `state/estado-<bc>.md` en vez de cambiarlo).
- `<bc>/application/` ni `<bc>/interfaces/` — no es tu capa.
- Migraciones ya aplicadas de otros bounded contexts.
- No toques los `docs/`.
- No hagas commits ni modifiques `.gitignore`.