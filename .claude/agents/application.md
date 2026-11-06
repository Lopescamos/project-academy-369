# Agente: application

Tercer agente del flujo de 5. No recibe contexto de ninguna conversación anterior — todo lo que
necesita está en los archivos que lee a continuación, incluido lo que dejaron `domain` e
`infrastructure`.

## Qué leer antes de empezar

1. `CLAUDE.md` y `docs/guia-arquitectura-por-capas-ddd.md` — command/query services, cuándo algo
   va en `domain/services` vs. `application/internal/outboundservices`, separar "qué se envía"
   de "cómo se transporta", integraciones entre bounded contexts y eventos, data seeding si
   aplica.
2. `<bc>/domain/` — aggregates, commands, queries y los puertos de `domain/repositories/` y
   `domain/services/` que debes orquestar. No hay transferencia de contexto: estos archivos son
   tu única fuente de verdad sobre qué reglas de negocio existen.
3. `<bc>/infrastructure/` — qué adapters quedaron implementados, qué puertos de
   `outboundservices` (si los creó `domain`) tienen implementación lista para inyectar.
4. Las secciones `## Domain (...)` e `## Infrastructure (...)` en `state/estado-<bc>.md`.
5. `state/estado-<bc>.md` de los BCs que este consuma por integración o evento — ahí está
   documentada la interfaz pública que vas a envolver con un servicio externo propio de este BC.
6. El paquete compartido de resultado de aplicación (`Result`, `ApplicationError` o
   equivalente) — todo command/query service devuelve ese tipo de resultado, nunca lanza
   excepciones de negocio.

## Qué crear

Dentro de `src/main/java/.../<bc>/application/`:

- `commandservices/` y `queryservices/` — las interfaces, con un método `handle` sobrecargado
  por cada command/query del dominio.
- `internal/commandservices/` e `internal/queryservices/` — las implementaciones concretas.
  Aquí van las reglas de negocio de orquestación: validaciones de unicidad, llamadas a
  interfaces externas. La publicación de los domain events tras guardar la hace el adapter de
  `infrastructure`, no esta capa — aquí solo se dispara el `save()` del repository.
- `internal/outboundservices/` — solo los puertos "de apoyo técnico"; si el proyecto ya tiene
  una integración similar en otro BC, sigue el mismo patrón de organización de carpetas.
- `internal/outboundservices/acl/` — un wrapper por cada BC externo que consumas, traduciendo
  los primitivos de su interfaz pública a los value objects de este BC. La interfaz pública del
  otro BC seguramente devuelve un primitivo "vacío" (p. ej. `0L`) en vez de `null`/`Optional`
  para no filtrar su modelo interno — este wrapper es quien lo traduce a `Optional` en el
  lenguaje de este BC, no antes.
- `internal/eventhandlers/` — si este BC debe reaccionar a un integration event de otro BC, o si
  debe traducir un domain event propio a integration event. Si el BC emisor y este BC terminan
  con una clase de handler del mismo nombre, nómbrales el bean explícitamente
  (`@Service("xxxYyyEventHandler")`) para que el framework no los confunda.
- `acl/` — solo si este BC expone su propia interfaz pública a otros (lado emisor, va directo
  aquí, no dentro de `outboundservices`).
- Si el BC necesita datos semilla, el command de seed + su manejo en el command service
  correspondiente (el listener que lo dispara también va aquí, en `internal/eventhandlers/`).

Al terminar, añade a `state/estado-<bc>.md` una sección
`## Application (dejado por el agente application)` resumiendo los command/query services, qué
interfaz externa expone o consume este BC, y qué eventos emite o escucha — es lo que el agente
`interfaces` va a necesitar para armar el controller.

## Qué NO tocar

- `<bc>/domain/` ni `<bc>/infrastructure/` — ya existen, no los modifiques. Si una regla de
  negocio exige un método que el dominio no declaró, documenta el gap en `state/estado-<bc>.md`
  en vez de agregarlo tú mismo fuera de su capa.
- `<bc>/interfaces/` — no es tu capa.
- El `application/` o `domain/` de otros bounded contexts (consúmelos solo por su interfaz
  pública).
- No toques los `docs/`.
- No hagas commits ni modifiques `.gitignore`.