workspace "Olimpiadas 369" "Sistema de digitalización de fichas del examen interescolar Olimpiadas 369 — captura por foto, extracción por visión multimodal, revisión asistida y exportación a Excel" {

    model {

        group "Actores del Sistema" {
            tutor         = person "Tutor"         "Captura fotos de las fichas en la sede y valida/corrige los datos extraídos en la pantalla de revisión." "Tutor"
            administrador = person "Administrador" "Superconjunto del tutor: además gestiona cuentas, asigna personas a sedes, observa sedes paralelas y exporta la data completa de su sede." "Administrador"
        }

        olimpiadas369 = softwareSystem "Olimpiadas 369" "Digitaliza las fichas físicas del examen interescolar por foto, extrae identificación y respuestas con un modelo de visión multimodal, calcula puntajes contra una clave y exporta a Excel" "Olimpiadas369" {

            mobileApp = container "App Móvil Flutter"        "App Android/iOS para captura de fichas en lote: cámara en vivo, Aceptar/Reintentar, almacenamiento local hasta terminar la tanda, subida del lote completo al backend" "Flutter / Dart" "MobileApp"
            webApp    = container "Web Angular"              "SPA responsive para login, revisión de fichas (foto + datos + 20 respuestas), gestión de clave, ranking en vivo, exportación y, para el admin, gestión de colaboradores" "Angular 18 / TypeScript" "WebApp"

            backend = container "Backend Spring Boot" "Monolito modular que expone la API REST, orquesta visión, aplica reglas de negocio, calcula puntajes, genera Excel y gestiona auth/accesos" "Spring Boot 4.1 / Java 21" "Backend" {

                // ── BC 1: Identidad y acceso ─────────────────────────────
                identidadBC = component "Identidad y acceso" "Login JWT, invitación con correo y credenciales autogeneradas, ciclo activo/inactivo de cuentas entre eventos, asociación Usuario↔Sede (M:N con historial), filtro por nombre, log de auditoría de acciones" "Módulo Spring Boot" "BoundedContext"

                // ── BC 2: Registro (core) ────────────────────────────────
                registroBC  = component "Registro (core)"    "Sede, Colegio (catálogo global), Bloque (sede+curso+nivel+grado), Lote, Alumno, respuestas, puntaje, fuzzy-match de apellidos/colegios, bloqueo de bloques, ranking, clave y sus correcciones (Caso A y Caso B)" "Módulo Spring Boot" "BoundedContext"

                // ── BC 3: Imágenes ───────────────────────────────────────
                imagenesBC  = component "Imágenes"           "Subida al almacenamiento, compresión, censura por recorte físico (DNI/celular) antes de visión, hash de deduplicación, signed URLs de corta duración para el cliente, reemplazo por re-foto" "Módulo Spring Boot" "BoundedContext"

                // ── BC 4: Visión ─────────────────────────────────────────
                visionBC    = component "Visión"             "Orquesta la llamada al proveedor de visión multimodal (Gemini o Claude), parsea JSON de identificación + 20 respuestas, aplica reglas de marca (1/0/2+/incierta)" "Módulo Spring Boot" "BoundedContext"

                // ── BC 5: Exportación ────────────────────────────────────
                exportacionBC = component "Exportación"      "Genera el Excel respetando la plantilla exacta (banner filas 1-4, título fila 5, encabezados fondo negro fila 6, datos desde fila 7, freeze pane A7, columna hora de finalización)" "Módulo Spring Boot (Apache POI)" "BoundedContext"

                // ── BC 6: Audit (transversal) ────────────────────────────
                auditBC     = component "Audit"               "Log append-only de acciones dentro del sistema (quién confirmó qué ficha, quién marcó un bloque completado, quién editó una clave, etc.). Intercepta comandos de los demás módulos vía Spring AOP, sin que cada uno loguee manualmente. Distinto del historial de participación Usuario↔Sede (eso vive en Identidad y acceso)" "Módulo Spring Boot (Spring AOP)" "BoundedContext"
            }

            database  = container "PostgreSQL" "Base de datos relacional única del sistema. Flyway gestiona migraciones. Todas las tablas del dominio viven aquí." "PostgreSQL 16 (Supabase gestionado)" "Database"

            storage   = container "Firebase Storage" "Blobs de imágenes de las fichas: versión original comprimida y versión censurada (DNI/celular recortados). Acceso restringido al backend por Admin SDK" "Firebase Storage" "Storage"
        }

        // ── Sistemas externos ────────────────────────────────────────────────
        geminiVision   = softwareSystem "Gemini Vision API"   "Modelo multimodal de Google que extrae identificación y respuestas marcadas de la foto de la ficha" "ExternalSystem"
        firebaseAuth   = softwareSystem "Firebase Authentication" "Mecanismo opcional de bajo nivel para manejo de contraseñas/sesión" "ExternalSystem"
        smtpProvider   = softwareSystem "SMTP / SendGrid"     "Envío de correos con credenciales generadas para tutores invitados" "ExternalSystem"

        // ── Actores → Aplicaciones ──────────────────────────────────────────
        tutor         -> mobileApp "Captura fichas del bloque en lote"
        tutor         -> webApp    "Revisa, corrige y confirma fichas; consulta ranking en vivo; exporta su parte"
        administrador -> webApp    "Todo lo del tutor + gestiona colaboradores, asigna sedes, observa sedes paralelas, exporta toda la sede"
        administrador -> mobileApp "Puede capturar también si lo necesita"

        // ── Aplicaciones → Backend ──────────────────────────────────────────
        mobileApp -> backend "Sube lotes de fotos, consulta estado y edita fichas puntuales" "HTTPS / JSON + multipart, JWT"
        webApp    -> backend "Consume toda la API (revisión, clave, ranking, usuarios, exportación)" "HTTPS / JSON, JWT (CORS habilitado)"

        // ── Backend → Infraestructura y externos ────────────────────────────
        backend -> database       "Lee y escribe el modelo relacional" "JDBC"
        backend -> storage        "Guarda imágenes (original comprimida + censurada) y genera signed URLs" "Firebase Admin SDK"
        backend -> geminiVision   "Envía imagen censurada, recibe JSON estructurado (datos + 20 respuestas con confianza)" "HTTPS / JSON (API key solo en backend)"
        backend -> firebaseAuth   "Verifica credenciales si se usa como backend de auth" "HTTPS"
        backend -> smtpProvider   "Envía correo con usuario y contraseña autogenerados" "SMTP / HTTPS API"

        // ── Relaciones entre bounded contexts del backend ───────────────────
        registroBC   -> imagenesBC   "Al cerrar un lote, persiste imágenes y pide censura"
        imagenesBC   -> visionBC     "Al tener imagen censurada lista, dispara extracción"
        visionBC     -> registroBC   "Devuelve datos extraídos; se persisten como ficha PENDIENTE_REVISION"
        registroBC   -> exportacionBC "Pide generar Excel con datos confirmados"
        identidadBC  -> registroBC   "Resuelve usuario autenticado y permisos por sede"
        identidadBC  -> imagenesBC   "Resuelve usuario autenticado"
        identidadBC  -> visionBC     "Resuelve usuario autenticado"
        identidadBC  -> exportacionBC "Resuelve usuario autenticado"

        // ── Audit — intercepta comandos de los demás módulos (AOP) ──────────
        registroBC     -> auditBC "Comando ejecutado (confirmar ficha, marcar bloque completado, editar clave, etc.)" "Spring AOP (interceptado, no llamada directa)"
        imagenesBC     -> auditBC "Comando ejecutado (reemplazar imagen, etc.)" "Spring AOP (interceptado, no llamada directa)"
        visionBC       -> auditBC "Comando ejecutado (reprocesar ficha, etc.)" "Spring AOP (interceptado, no llamada directa)"
        exportacionBC  -> auditBC "Comando ejecutado (exportar Excel)" "Spring AOP (interceptado, no llamada directa)"
        identidadBC    -> auditBC "Comando ejecutado (invitar usuario, reactivar cuenta, etc.)" "Spring AOP (interceptado, no llamada directa)"
        auditBC        -> database "Persiste el log append-only de acciones" "JDBC"
    }

    views {

        systemContext olimpiadas369 "Contexto" {
            include *
            autolayout lr
            description "Diagrama de Contexto (C1): el sistema Olimpiadas 369, sus actores y sus dependencias externas"
        }

        container olimpiadas369 "Contenedores" {
            include *
            autolayout lr
            description "Diagrama de Contenedores (C2): piezas desplegables del sistema y sus relaciones"
        }

        component backend "Componentes_Backend" {
            include *
            autolayout lr
            description "Diagrama de Componentes (C3) del backend: cinco bounded contexts del monolito modular (Identidad y acceso, Registro, Imágenes, Visión, Exportación)"
        }

        styles {
            element "Person" {
                shape person
                background #08427B
                color #ffffff
            }
            element "Tutor" {
                background #1168BD
            }
            element "Administrador" {
                background #0A3B70
            }
            element "Olimpiadas369" {
                background #438DD5
                color #ffffff
            }
            element "MobileApp" {
                background #7CB342
                color #ffffff
                shape MobileDeviceLandscape
            }
            element "WebApp" {
                background #5DADE2
                color #ffffff
                shape WebBrowser
            }
            element "Backend" {
                background #3E8ACC
                color #ffffff
            }
            element "Database" {
                shape cylinder
                background #1E7C3A
                color #ffffff
            }
            element "Storage" {
                shape folder
                background #F4B400
                color #000000
            }
            element "BoundedContext" {
                background #85BBF0
                color #000000
            }
            element "ExternalSystem" {
                background #999999
                color #ffffff
            }
        }
    }
}
