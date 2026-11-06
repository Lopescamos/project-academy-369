# Olimpiadas 369 — One-pager

## Problema

Olimpiadas 369 es un examen interescolar en papel (Matemática y Comunicación) para Inicial, Primaria y Secundaria. Hoy cada alumno llena una ficha física con sus datos y sus respuestas, y ese proceso se digitalizaba a mano (tipeo) — lento, propenso a error y sin forma de volver a revisar el original una vez tipeado. El proyecto pasa de "ayudar a tipear más rápido" a eliminar el tipeo: la ficha se fotografía, un modelo de visión multimodal extrae los datos y las respuestas marcadas, y un tutor humano solo confirma o corrige.

## Usuarios

- **Tutor**: elige un bloque (sede + curso + nivel + grado), fotografía todas las fichas de ese bloque en una sola sesión, revisa/corrige lo que extrajo la IA, confirma y puede exportar lo que él mismo trabajó.
- **Administrador**: todo lo del tutor, más gestión de accesos (altas por correo o invitado al momento, reactivación entre eventos), visibilidad de solo lectura sobre otras sedes en paralelo, ranking y diplomas, log de auditoría, pantalla de historial de colaboradores, y exportación consolidada de su sede.
- **Alumno** (indirecto): su ficha física es la única fuente de datos; no interactúa con el sistema.

## Solución propuesta (MVP)

- App móvil en **Flutter** para capturar en lote todas las fotos de un bloque (cámara abierta una sola vez), con verificación inmediata (Aceptar/Reintentar) y opción de re-tomar una ficha puntual después.
- Backend en **Spring Boot** (monolito modular por bounded context: Registro, Imágenes, Visión, Exportación, Identidad y acceso) que llama server-side a una API de visión (Gemini / Claude Haiku) para extraer identificación y respuestas marcadas (A-E, 20 preguntas), enmascarando DNI/celular antes de enviar la imagen.
- Cálculo automático de correctas/incorrectas contra una clave de respuestas (compartida por curso+nivel+grado entre sedes), con soporte para corregirla después (recalcular, o sumar +1 a todos si la pregunta estaba mal planteada).
- Web en **Angular** responsive (laptop o celular) para listar pendientes, comparar foto vs. datos extraídos, corregir con ayuda de autocompletado (apellidos/colegios), confirmar y exportar a Excel con la plantilla exacta ya definida.
- Gestión de accesos por sede: invitación masiva por correo con credenciales automáticas, invitado agregado al instante, cuentas que se desactivan solas al terminar el evento y se reactivan para el siguiente sin perder el historial de participación.
- Ranking y diplomas de honor calculados por bloque, visibles en vivo desde la app.
- Caso especial de Inicial (3-5 años): calificación interpretativa del propio tutor, sin clave de respuestas ni detección de marcas.

## Fuera de alcance (por ahora)

- Resiliencia offline avanzada (reintento automático en segundo plano, cola persistente ante pérdida total de señal).
- OCR interno/en el dispositivo como respaldo sin internet.
- Cámara web adicional (`getUserMedia`) como alternativa a Flutter para capturar.
- Entidad `Evento` separada de `Sede` (se evaluará solo si una misma ubicación empieza a repetirse entre eventos).
- Pantalla detallada de "casos excepcionales" más allá de los dos ya definidos para corregir la clave de respuestas.

## Métricas de éxito

- Reducir el tiempo de digitalización y calificación de una sede completa (~400 alumnos, ~800 fichas si rinden ambos cursos) frente al proceso manual anterior.
- Costo de procesamiento por sede completa vía API de visión por debajo de USD 1.
- Datos de identificación y respuestas de opción cerrada extraídos correctamente en la gran mayoría de fichas con letra legible, sin intervención manual.
- Cero filtraciones de datos sensibles (DNI, celular) hacia la API de visión o hacia almacenamiento público.

## Restricciones

- Presupuesto mínimo: hosting en capas gratuitas (Render para el backend, Supabase para la base de datos, Firebase para hosting/storage).
- Un solo desarrollador construyendo todo el sistema.
- Datos personales de menores de edad — DNI y celular deben enmascararse antes de salir del backend hacia cualquier proveedor externo.
- Stack obligado por contexto académico: Spring Boot + DDD (backend), Angular, Flutter — alineado a lo que el desarrollador está llevando en la universidad.
- El examen es interescolar: una misma sede puede reunir alumnos de distintos colegios, y varias sedes pueden operar en paralelo el mismo día sin interferirse entre sí.

## Riesgos / dudas abiertas

- Precisión real del modelo de visión sobre letra manuscrita de niños pequeños, en especial el campo DNI (dígitos sueltos, sin contexto que ayude a corregir).
- Comportamiento del sistema ante fichas muy garabateadas o con marcas ambiguas en la sección de respuestas, más allá de las reglas ya definidas (blanco, anulada, "con observación").
- Aún no se ha corrido un piloto real end-to-end (captura → visión → revisión → exportación) con datos de un examen verdadero.
- Posible necesidad futura de una entidad `Evento` si las sedes dejan de ser únicas por fecha.
