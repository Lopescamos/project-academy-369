# Arquitectura — modelo C4

> Los diagramas renderizados se generan desde `c4-olimpiadas369.dsl` (código Structurizr, archivo aparte). Este documento describe cada diagrama con el detalle de lo que incluye, para que al pegarlos en el documento final el lector entienda qué está viendo.

## Diagrama de Contexto (C1)

**Qué muestra.** El sistema "Olimpiadas 369" como una caja única, los actores humanos que lo usan y los sistemas externos de los que depende.

**Actores:**
- **Tutor.** Persona que captura fotos de las fichas en una sede y las valida en la pantalla de revisión. Usa la app móvil para capturar y la web para revisar/corregir.
- **Administrador.** Superconjunto del tutor. Además gestiona cuentas de colaboradores, asigna personas a sedes, consulta todas las sedes en modo observación, exporta la data completa de su sede.

**Sistemas externos:**
- **Gemini Vision API (Google AI Platform)** — modelo multimodal usado server-side para extraer datos de la ficha (identificación + respuestas marcadas). Alternativa evaluada: Claude Haiku 5.5.
- **Firebase Storage** — almacenamiento de las imágenes de las fichas (compresión + reglas de seguridad + signed URLs).
- **Firebase Authentication** (opcional, como mecanismo de bajo nivel) — manejo de sesión / contraseñas si se decide no implementar JWT 100% a mano.
- **Supabase (PostgreSQL gestionado)** — base de datos de producción.
- **Proveedor SMTP (o SendGrid)** — envío de correos con credenciales generadas para los tutores invitados.

**Relaciones principales:**
- Tutor → Olimpiadas 369 (captura por móvil, revisa por web).
- Administrador → Olimpiadas 369 (todo lo del tutor + gestión + exportación).
- Olimpiadas 369 → Gemini Vision API (envía imagen censurada, recibe JSON estructurado).
- Olimpiadas 369 → Firebase Storage (sube y descarga con signed URLs).
- Olimpiadas 369 → Supabase PostgreSQL (persistencia).
- Olimpiadas 369 → SMTP (envío de credenciales de acceso).

## Diagrama de Contenedores (C2)

**Qué muestra.** Las piezas desplegables de Olimpiadas 369 y cómo se comunican.

**Contenedores:**
- **App Móvil (Flutter).** App Android/iOS para captura de fichas. Cámara abierta en vivo, verificación Aceptar/Reintentar, almacenamiento local del lote hasta terminar la tanda, subida del lote completo al backend. Edita una foto puntual también desde aquí si el tutor lo prefiere al móvil.
- **Web Angular (SPA responsive).** Interfaz principal de trabajo: login, listas de lotes y fichas, pantalla de revisión (foto + datos extraídos + 20 respuestas corregibles), gestión de clave de respuestas, ranking en vivo, exportación, gestión de usuarios (admin). Responsive — se usa igual desde laptop o desde celular.
- **Backend Spring Boot (monolito modular).** Núcleo del sistema. Expone la API REST, orquesta visión, aplica reglas de negocio, calcula puntajes, genera el Excel, gestiona auth y accesos. Organizado internamente en bounded contexts (ver C3).
- **PostgreSQL (Supabase gestionado).** Base de datos relacional única (una sola DB para todos los bounded contexts, con esquemas o prefijos por módulo si hace falta).
- **Firebase Storage.** Blobs de imágenes. Dos versiones por foto: original comprimida y censurada (DNI/celular recortados).

**Relaciones principales:**
- App Móvil ↔ Backend — HTTPS/JSON + `multipart` para subir imágenes; JWT.
- Web Angular ↔ Backend — HTTPS/JSON; JWT. CORS habilitado en el backend porque viven en dominios distintos (Firebase Hosting ↔ Render).
- Backend → PostgreSQL — JDBC + Flyway para migraciones.
- Backend → Firebase Storage — Firebase Admin SDK (server-side; el cliente **nunca** habla directo con Storage).
- Backend → Gemini Vision API — HTTPS (server-side; API key solo en el backend).
- Backend → SMTP / SendGrid — envío de correo de invitación.
- Backend → Firebase Auth (si se usa) — verificación de credenciales.

**Hosting:**
- Web Angular → Firebase Hosting.
- App Móvil → distribuida como APK/IPA.
- Backend → Render (contenedor Java).
- PostgreSQL → Supabase.
- Firebase Storage/Auth → Firebase (Google).

## Diagrama de Componentes (C3) — dentro del Backend

**Qué muestra.** Cómo está organizado por dentro el monolito modular. Seis bounded contexts, cada uno con sus propios controllers / services / repositories / adaptadores. Breve por cada uno:

- **Identidad y acceso.** `AuthController`, `UsuarioController`, `EmpleadosController`; `AuthService`, `UsuarioService`; `UsuarioRepository`, `UsuarioSedeRepository`, `AuditoriaRepository`; adaptadores `JwtProvider`, `PasswordHasher`, `MailSender` (hacia SMTP). Maneja el ciclo activo/inactivo de cuentas, la invitación por correo con credenciales autogeneradas, el agregado en el momento, la asociación de usuarios a sede por evento (muchos a muchos) y el filtro por nombre.

- **Registro.** El corazón del negocio. `BloqueController`, `LoteController`, `RegistroController`, `RespuestaController`, `ClaveRespuestasController`, `SedeController`, `ColegioController`; `BloqueService` (toma y libera bloques, aplica el bloqueo desde la selección), `CapturaService` (gestiona lotes y su estado), `CorreccionService` (fuzzy match de apellidos y colegios — sección 4), `CalificacionService` (compara respuestas contra clave, aplica el recálculo del Caso A y el +1 del Caso B), `RankingService`; repositorios por entidad; adaptador `CatalogoColegiosSearcher` (índice de similitud).

- **Imágenes.** `ImagenController` (subida, descarga firmada, reemplazo). `ImagenService` orquesta compresión, censura de DNI/celular por recorte de coordenadas fijas, cálculo de hash, almacenamiento. `ImagenRepository`. Adaptador `FirebaseStorageAdapter` hacia Firebase Admin SDK.

- **Visión.** `VisionOrchestratorService` — recibe una imagen censurada, llama al proveedor, parsea el JSON de identificación + 20 respuestas, aplica las reglas de marca (1/0/2+/incierta). Separado del módulo Imágenes porque depende de un proveedor externo con costo y riesgo propios. Adaptadores intercambiables: `GeminiAdapter`, `ClaudeAdapter`. `VisionResultadoRepository` para persistir el bruto antes de confirmar.

- **Exportación.** `ExportacionController` (bloque, sede). `ExcelExporterService` que arma el libro respetando la plantilla exacta (filas 1-4 banner, fila 5 título fusionado, fila 6 encabezados fondo negro, datos desde fila 7, freeze pane A7, columna de hora de finalización). Usa Apache POI. No tiene adaptadores externos; consume datos ya consolidados del módulo Registro.

- **Audit** (transversal). Sin controller propio ni tablas de negocio propias más allá de `auditoria_accion`. `AuditAspect` (Spring AOP) intercepta los métodos de comando (`@AuditedCommand` o similar) de los demás módulos y escribe el log append-only, sin que cada módulo tenga que llamarlo manualmente. Distinto del historial `usuario_sede` (eso vive en Identidad y acceso y registra "en qué sedes estuvo" cada persona, no "qué hizo").

**Relaciones entre bounded contexts.**
- Captura (Registro) → Imágenes: al cerrar el lote, pide persistencia de imágenes.
- Imágenes → Visión: al tener imagen lista, dispara el procesamiento.
- Visión → Registro: devuelve datos extraídos que se persisten como ficha `PENDIENTE_REVISION`.
- Registro → Exportación: Excel pide datos finales confirmados.
- Todos → Identidad y acceso: resolución del usuario autenticado y validación de permisos a nivel de controller (filtro JWT + `@PreAuthorize`).
- Todos → Audit: cada comando de negocio ejecutado (confirmar ficha, marcar bloque completado, editar clave, invitar usuario, exportar, etc.) queda interceptado por el aspecto de auditoría — relación transversal, no una llamada explícita desde el código de cada módulo.

## Cómo se usa este documento

Al pegar los diagramas (contexto, contenedor y componente) en el documento final, cada uno queda bajo su subsección correspondiente de arriba con la descripción ya escrita. El código DSL de Structurizr (`c4-olimpiadas369.dsl`) produce los tres.
