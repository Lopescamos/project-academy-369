# Agente: interfaces

Cuarto agente del flujo de 5. No recibe contexto de ninguna conversación anterior — todo lo que
necesita está en los archivos que lee a continuación, incluido lo que dejaron `domain`,
`infrastructure` y `application`.

## Qué leer antes de empezar

1. `CLAUDE.md` y `docs/guia-arquitectura-por-capas-ddd.md` — resources, assemblers REST, el
   controller, integration events (`interfaces/events/`), integraciones expuestas lado emisor
   (`interfaces/acl/`), tabla de nomenclatura, formato de documentación de API esperado.
2. `<bc>/domain/` — los aggregates y commands/queries que vas a envolver en resources.
3. `<bc>/application/` — las interfaces de command/query service que el controller va a
   inyectar, y qué errores de aplicación pueden devolver (para mapear a HTTP).
4. Las secciones `## Domain`, `## Infrastructure` y `## Application` de `state/estado-<bc>.md`
   dejadas por los agentes anteriores.
5. `docs/user-stories.md` — qué rol puede llamar cada endpoint y con qué criterios de
   aceptación (define la autorización que necesites).
6. Un controller ya existente de otro BC completo, como referencia de estilo: paginación,
   formato de respuesta, formato de URL.
7. El manejo de errores HTTP ya compartido en el proyecto (`shared/interfaces/` o equivalente) —
   no reinventes el manejo de errores.

## Qué crear

Dentro de `src/main/java/.../<bc>/interfaces/`:

- `rest/resources/` — un resource de entrada por cada command con body, y un resource de salida
  por cada aggregate que se devuelva.
- `rest/transform/` — un assembler de command-desde-resource por resource de entrada y un
  assembler de resource-desde-entidad por aggregate de salida.
- `rest/XxxController.java` (nombre en plural) — URLs en minúscula y plural
  (`/api/v1/<recurso>`), usando la anotación de autorización con el alcance o rol que
  corresponda (revisa si ya existe un bean de autorización para este BC en
  `infrastructure/security/`; si no existe y se necesita, avísalo en el resumen final en vez de
  crearlo tú mismo fuera de tu capa — es una pieza de infraestructura).
- `acl/` — solo si la implementación de `application` ya existe y necesita su interfaz pública
  (lado emisor).
- `events/` — solo si este BC emite un evento que otro BC debe escuchar.

Documenta cada endpoint con `@Operation` (OpenAPI) en vez de Javadoc aparte — ya cumple el rol
de documentación, no dupliques.

Antes de terminar, repasa el checklist de la sección 9 de la guía que te toca a ti (URL en
minúscula y plural, un resource por cada command y uno por el aggregate, un assembler REST por
resource, el controller nunca arma el mensaje de error a mano — solo traduce el
`ApplicationError` ya armado a HTTP).

Al terminar, añade a `state/estado-<bc>.md` una sección
`## Interfaces (dejado por el agente interfaces)` con la lista final de endpoints (método, URL,
rol requerido, user story que cubre) — es lo que el agente `tests` va a usar para decidir qué
casos cubrir.

## Qué NO tocar

- `<bc>/domain/`, `<bc>/infrastructure/` ni `<bc>/application/` — ya existen, no los
  modifiques. Si falta un método de autorización o un query, documenta el gap en
  `state/estado-<bc>.md` en vez de crearlo fuera de su capa.
- El `interfaces/` de otros bounded contexts.
- No toques los `docs/`.
- No hagas commits ni modifiques `.gitignore`.