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
4. El ERD del proyecto en `docs/` (revisa `CLAUDE.md` para el nombre exacto del archivo) —
   nombres de tabla, columnas, tipos y
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
  proceso programado, etc.): el puerto en sí no lo creas tú si ya lo declaró `domain` o
  `application` — aquí solo va la implementación, en `<capacidad>/[<proveedor>/]`. Sigue la
  sección 4.1 de la guía para decidir si va plana o con subcarpeta de proveedor (subcarpeta
  cuando coexiste un proveedor real y uno simulado/logging, o cuando la capacidad es genérica y
  reutilizable entre proyectos). La configuración (API key, timeout, flags) va en una clase
  `Settings` — sección 4.2 de la guía dice si vive en la raíz de la capacidad o dentro de la
  subcarpeta del proveedor —, nunca credenciales hardcodeadas en el código.
- `config/` — clases `@Configuration` propias de este BC, si hace falta alguna.
- `scheduling/` — jobs `@Scheduled` (opcional; solo si este BC necesita uno).
- `startup/` — tareas que corren una vez al arrancar la app con
  `@EventListener(ApplicationReadyEvent.class)` (opcional; p. ej. retomar algo que quedó a medias
  si el servidor se cayó).
- Si este BC necesita filtros o configuración de seguridad de Spring (más allá de generar o
  validar un token), eso va en `security/`, separado de la implementación del mecanismo en sí
  (p. ej. `tokens/jwt/`).
- Si hay una migración nueva, créala siguiendo el patrón de nomenclatura de las anteriores
  (nunca editar una ya aplicada).

Antes de terminar, repasa el checklist de la sección 9 de la guía que te toca a ti (entity
extiende la clase base de auditoría sin redeclarar id/createdAt/updatedAt, assembler final con
constructor privado y el `if (id != null)` antes de asignar, ninguna credencial hardcodeada —
siempre por una clase `Settings` leyendo variables de entorno, subcarpeta por proveedor solo si
coexisten dos implementaciones, un cronjob o tarea de arranque nunca suelto en `application`).

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