# Agente: domain

Primer agente del flujo de 5. No recibe contexto de ninguna conversación anterior — todo lo que
necesita está en los archivos que lee a continuación.

## Qué leer antes de empezar

1. `CLAUDE.md` (raíz del proyecto) — stack, orden de bounded contexts y cualquier regla de
   negocio crítica que aplique a este BC.
2. `docs/guia-arquitectura-por-capas-ddd.md` — es la única fuente de convenciones de capas y
   nomenclatura; no inventes un patrón distinto.
3. `docs/modelo-bd.puml` (o el ERD equivalente) — columnas, tipos y FKs reales de las tablas del
   bounded context que vas a construir.
4. `docs/user-stories.md` — las user stories que le corresponden a este BC (ver el orden de
   bounded contexts en `CLAUDE.md` para saber cuáles).
5. `state/estado-<bc>.md` de los bounded contexts **upstream** de los que dependas (por FK o por
   integración). Ahí está el modelo y las interfaces que ya exponen — no los reinventes ni los
   leas del código si el doc ya resume lo que necesitas.
6. Si este bounded context ya tiene algo de código (por ejemplo, un `state/estado-<bc>.md`
   marcado "en curso" con una sección de pendientes), léelo también: puede que el `domain/` ya
   exista parcialmente y debas completarlo, no reescribirlo desde cero.

## Qué crear

Dentro de `src/main/java/.../<bc>/domain/`:

- `model/aggregates/` — los aggregates del BC (POJOs puros, cero anotaciones de framework),
  constructor vacío + constructor desde command. Si el proyecto tiene una clase base compartida
  para aggregates (normalmente en `shared/domain/model/aggregates/`, pensada para dar soporte a
  domain events sin arrastrar JPA), extiéndela — no la rediseñes ni la reimplementes aquí.
- `model/entities/` — solo si hay hijos con identidad propia (según el modelo de datos).
- `model/valueobjects/` — records con validación en el constructor compacto, y enums.
- `model/commands/` y `model/queries/` — records, uno por cada operación que de verdad se vaya a
  usar (no generes operaciones que no hagan falta). Si este BC necesita datos semilla al
  arrancar, el command de seed (`SeedXxxCommand`, record vacío) también va aquí — nunca en la
  clase principal de arranque; `application` se encarga de manejarlo.
- `repositories/` — interfaces puras de persistencia (sin anotaciones de framework), solo con
  los métodos que las reglas de negocio de este BC realmente necesiten.
- `services/` — lógica de dominio pura (validaciones, cálculos, políticas) y los puertos de
  integraciones externas que sean "el corazón del aggregate", según el criterio que da la guía
  de arquitectura. Si este BC reutiliza lógica de dominio ya prototipada en otro bounded context
  (ver `CLAUDE.md` para cuál), **no la copies**: referénciala o inyéctala, según lo que el código
  existente permita.
- Si hay domain events relevantes para otro BC, decláralos en `model/events/` y haz que el
  aggregate los registre (el integration event hacia afuera lo crea el agente `interfaces`, no
  tú). Publicarlos después de guardar es responsabilidad de `infrastructure`, no tuya — tú solo
  los declaras y los registras en el aggregate.

Al terminar, deja un resumen breve (qué aggregates, commands, queries y servicios creaste, y qué
métodos de repositorio esperas que `infrastructure` implemente) en `state/estado-<bc>.md` bajo
una sección `## Domain (dejado por el agente domain)`, para que el siguiente agente no tenga que
adivinar.

## Qué NO tocar

- Nada fuera de `<bc>/domain/`. No crees `infrastructure/`, `application/` ni `interfaces/` —
  eso es trabajo de los siguientes agentes.
- No toques los `docs/`.
- No toques el `domain/` de otros bounded contexts, ni siquiera para "mejorarlo".
- No escribas ninguna anotación de framework (persistencia, inyección de dependencias, web) en
  estos archivos.
- No hagas commits ni modifiques `.gitignore`.