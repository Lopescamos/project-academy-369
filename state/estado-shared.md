# Estado — Módulo transversal: `shared`

**Estado global:** **listo**. Las 9 clases están portadas, compilando en `./mvnw compile` y
con tests verdes para `Result` y `ApplicationError`. Identidad y acceso ya puede arrancar.

Paquete raíz final: `com.olimpiadas369.backend.shared` (debajo del paquete de
`BackendApplication` para que el auto-scan de Spring Boot las detecte sin configuración extra).

## Alcance

Paquete raíz: `com.olimpiadas369.shared`.

Nada de aquí es dominio de negocio. Es infraestructura común + primitivas DDD/Result.

### Inventario (basado en `references/shared/`)

> `references/` está en `.gitignore`: es material **local de referencia** copiado de un
> proyecto previo del desarrollador. Nada de esa carpeta se sube. Se usa solo para portar
> clases ajustando paquete, licencia y dependencias.

| Capa | Clase | Qué hace | Ajustes al portar |
|---|---|---|---|
| `domain/model/aggregates` | `AbstractDomainAggregateRoot<T>` | Base para todo aggregate root. Registro de domain events sin JPA/Spring. | Cambiar paquete a `com.olimpiadas369.shared.domain.model.aggregates`. Mantener cero anotaciones de framework. |
| `application/result` | `Result<T, E>` (sealed con `Success`/`Failure`) | Tipo de retorno para handlers: éxito o `ApplicationError`. Nunca excepciones de control de flujo. | Cambiar paquete. Ya usa `jspecify` — confirmar que esté en `pom.xml`. |
| `application/result` | `ApplicationError` (record) | Error estructurado con `code`, `message`, `details` y factories (`notFound`, `validationError`, `businessRuleViolation`, `conflict`, `forbidden`, `unauthorized`, `unexpected`). | Cambiar paquete. |
| `infrastructure/i18n/configuration` | `LocaleConfiguration` | `AcceptHeaderLocaleResolver` con español por defecto, inglés si el `Accept-Language` lo pide. | Cambiar paquete. Confirmar que el default español sigue siendo lo correcto para Olimpiadas 369 (sí — público peruano). |
| `interfaces/rest` | `GlobalExceptionHandler` (`@RestControllerAdvice`) | Traduce excepciones *fuera* del flujo `Result` (validación `@Valid`, JSON malformado, tipo de path var incorrecto, `AccessDenied`, `AuthenticationException`, bugs genuinos) a `ErrorResource`. | Cambiar paquete. Revisar dependencia de `spring-security` — si Identidad termina usando JWT propio sin Spring Security, los handlers de `AccessDeniedException`/`AuthenticationException` pueden desaparecer. Decisión bloqueada por el ADR de Identidad (ver `state/pendientes.md`). |
| `interfaces/rest/resources` | `ErrorResource`, `MessageResource` | DTOs de respuesta comunes. | Cambiar paquete. |
| `interfaces/rest/transform` | `ErrorResponseAssembler` | Construye `ResponseEntity<ErrorResource>` a partir de `ApplicationError`. Mapea `code` a HTTP status. | Cambiar paquete. Revisar la tabla `code → HTTP status` por si falta algún caso propio de Olimpiadas 369. |
| `interfaces/rest/transform` | `ResponseEntityAssembler` | Construye `ResponseEntity` genérico para respuestas exitosas (`201 Created` con `Location`, `200 OK`, `204 No Content`). | Cambiar paquete. |

## Dependencias

- Upstream: ninguna. `shared` es la base.
- Downstream: **todos** los BCs. Identidad y acceso es el primero en consumirlo (extiende
  `AbstractDomainAggregateRoot`, devuelve `Result<T, ApplicationError>`, usa
  `ErrorResponseAssembler`).

## Pendientes propios

- [ ] Crear `src/main/java/com/olimpiadas369/shared/` con la estructura por capa (`domain/`,
  `application/`, `infrastructure/`, `interfaces/`).
- [ ] Portar las 9 clases de `references/shared/` ajustando paquete.
- [ ] Verificar/añadir dependencias en `pom.xml`:
  - `org.jspecify:jspecify` (para `@NullMarked` / `@Nullable`).
  - `org.projectlombok:lombok` (si se mantienen anotaciones `@Slf4j`; alternativa: reemplazar
    por `LoggerFactory` manual y eliminar lombok).
- [ ] Tests mínimos de `Result` (map, flatMap, mapError, recover, getOrElse) y de
  `ApplicationError` (factories y `toCodeSegment`).
- [ ] Mensaje bundle `messages.properties` / `messages_en.properties` si el
  `GlobalExceptionHandler` o los resources los necesitan.

## Decisiones abiertas que bloquean al módulo

- **Lombok sí/no.** La referencia usa `@Slf4j` en `GlobalExceptionHandler`. Si se decide no
  agregar Lombok, hay que sustituir por `LoggerFactory.getLogger(...)` al portar.
- **Spring Security sí/no.** Los handlers de `AccessDeniedException` y
  `AuthenticationException` en `GlobalExceptionHandler` dependen de Spring Security. Si
  Identidad termina siendo JWT propio sin Spring Security, esos dos `@ExceptionHandler`
  sobran. Ver ADR pendiente en `state/pendientes.md`.

## Handoff para el siguiente agente

- **Paquete raíz:** `com.olimpiadas369.backend.shared`.
- **Clases portadas (9):** `AbstractDomainAggregateRoot`, `Result`, `ApplicationError`,
  `LocaleConfiguration`, `ErrorResource`, `MessageResource`, `ErrorResponseAssembler`,
  `ResponseEntityAssembler`, `GlobalExceptionHandler`.
- **Qué se tocó al portar:** solo el paquete. Semántica y firmas intactas.
- **Dependencias nuevas en `pom.xml`:** `org.jspecify:jspecify:1.0.0`. Lombok y Spring
  Security ya estaban — se conservan los handlers de `AccessDeniedException` y
  `AuthenticationException` y el `@Slf4j` en `GlobalExceptionHandler`. Si el ADR de Identidad
  descarta Spring Security, esos dos handlers caen (ver `state/pendientes.md`).
- **Bundles i18n:** `src/main/resources/messages.properties` (es, default) y `messages_en.properties`
  con claves genéricas (`error.validation.message`, `error.not-found.message`, etc.). Cada BC
  puede añadir claves específicas (`error.<code-kebab>.message`) sin tocar `shared`.
- **Tests:** `ResultTest` (6 casos) y `ApplicationErrorTest` (6 casos). Ambos verdes.
- **Siguiente agente (Identidad, `domain`)** debe: extender `AbstractDomainAggregateRoot<Usuario>`,
  devolver `Result<T, ApplicationError>` desde los handlers de `application`, y usar
  `ResponseEntityAssembler.toResponseEntityFromResult(...)` en los controllers REST.
