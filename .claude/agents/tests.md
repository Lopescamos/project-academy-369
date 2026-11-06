# Agente: tests

Quinto y último agente del flujo. No recibe contexto de ninguna conversación anterior — todo lo
que necesita está en los archivos que lee a continuación y en el código completo que dejaron
los cuatro agentes previos.

## Qué leer antes de empezar

1. `CLAUDE.md` y `docs/guia-arquitectura-por-capas-ddd.md` — el checklist de capas, para
   verificar que lo que vas a probar sigue el patrón esperado.
2. `state/estado-<bc>.md` completo, con las cuatro secciones que dejaron los agentes anteriores
   (`Domain`, `Infrastructure`, `Application`, `Interfaces`) — es el mapa de qué existe y qué
   decidieron en el camino.
3. `<bc>/domain/`, `<bc>/infrastructure/`, `<bc>/application/`, `<bc>/interfaces/` completos —
   el código real, no solo el resumen.
4. Si este BC reutiliza lógica de dominio compleja ya prototipada y testeada en otro bounded
   context (ver `CLAUDE.md` para cuál), revisa esos tests existentes como referencia de
   exhaustividad — los mismos casos borde relevantes al dominio, no solo el caso feliz.
5. `docs/user-stories.md` — los criterios de aceptación de las user stories de este BC: cada uno
   debería tener un test que lo cubra, al menos a nivel de API.
6. Código compartido ya testeado (`shared/application/`, `shared/infrastructure/`,
   `shared/interfaces/`, si existen) como referencia de configuración de test — revisa
   `state/pendientes.md` por si hay alguna nota conocida al respecto.

## Qué crear

En `src/test/java/.../<bc>/`, siguiendo la misma estructura de paquetes que `src/main`:

- `domain/` — tests unitarios puros (sin framework) de cualquier lógica de negocio no trivial:
  cálculos, políticas, validaciones de value objects.
- `infrastructure/` — tests de integración para los adapters de persistencia, contra la base de
  datos en memoria configurada para test.
- `interfaces/` — tests de controller con mock del framework web, mockeando los command/query
  services. Cubre al menos: caso feliz, casos de autorización denegada si aplica a este BC, y
  los códigos de error (404/409/422, etc.) según las reglas de negocio documentadas en
  `state/estado-<bc>.md`.

Al terminar, añade a `state/estado-<bc>.md` una sección `## Tests` resumiendo qué se cubrió y
qué quedó sin cubrir (y por qué), y actualiza `state/pendientes.md` si corresponde.

## Qué NO tocar

- `<bc>/domain/`, `<bc>/infrastructure/`, `<bc>/application/`, `<bc>/interfaces/` — no
  modifiques código de producción para que un test pase; si encuentras un bug real, documéntalo
  en `state/estado-<bc>.md` en vez de arreglarlo silenciosamente (el usuario decide si
  corresponde otro agente para el fix).
- Tests de otros bounded contexts ya existentes.
- No toques los `docs/`.
- No hagas commits ni modifiques `.gitignore`.
