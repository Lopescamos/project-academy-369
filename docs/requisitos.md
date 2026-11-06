# Requisitos Funcionales (RF) y No Funcionales (RNF)

> Basado en el one-pager y en todas las reglas de negocio y decisiones de arquitectura acordadas hasta ahora.

## 1. Requisitos Funcionales (RF)

### 1.1 Identidad y acceso

| ID | Requisito |
|---|---|
| RF-01 | El sistema debe permitir al administrador crear una sede para un evento. |
| RF-02 | El sistema debe permitir al administrador registrar tutores por correo + nombre, asociados a una sede específica. |
| RF-03 | El sistema debe generar automáticamente usuario y contraseña, y enviarlos por correo al tutor registrado. |
| RF-04 | El sistema debe permitir al administrador crear un usuario "invitado" en el momento, sin pasar por el flujo de invitación por correo. |
| RF-05 | El sistema debe desactivar automáticamente todas las cuentas de tutor al finalizar un evento. |
| RF-06 | El sistema debe permitir al administrador mantener activas, de forma explícita, ciertas cuentas después de un evento. |
| RF-07 | El sistema debe permitir reactivar una cuenta de tutor existente (en vez de crear una nueva) para un evento posterior, asociándola a la sede que corresponda. |
| RF-08 | El sistema debe mantener un historial de en qué sede(s) participó cada tutor (relación muchos a muchos Usuario↔Sede). |
| RF-09 | El sistema debe ofrecer un filtro de búsqueda por nombre para ubicar cuentas activas o desactivadas. |
| RF-10 | El sistema debe ofrecer una pantalla exclusiva de administrador ("Empleados"/Colaboradores) con el historial de sedes de cada tutor, distinta del log de auditoría. |
| RF-11 | El sistema debe restringir al administrador: edición/exportación solo en su sede activa; visibilidad de solo lectura (avance) sobre otras sedes en paralelo. |
| RF-12 | El sistema debe registrar un log de auditoría de acciones (quién confirmó qué ficha, quién marcó un bloque como completado, etc.). |

### 1.2 Captura de fichas (Flutter)

| ID | Requisito |
|---|---|
| RF-13 | El sistema debe permitir a un tutor seleccionar libremente un bloque (sede+curso+nivel+grado) para trabajar. |
| RF-14 | El sistema debe bloquear un bloque para otros tutores de la misma sede desde el momento en que es seleccionado. |
| RF-15 | El sistema debe impedir trabajar un bloque de Primaria/Secundaria si la clave de respuestas de ese curso+nivel+grado no está cargada. |
| RF-16 | El sistema debe permitir fotografiar todas las fichas de un bloque en una sola sesión de captura continua. |
| RF-17 | El sistema debe mostrar un panel de Aceptar/Reintentar inmediatamente después de cada foto tomada. |
| RF-18 | El sistema debe almacenar las fotos localmente en el dispositivo mientras dura la sesión de captura, antes de subirlas. |
| RF-19 | El sistema debe subir el lote completo de fotos al backend solo al finalizar la sesión de captura. |
| RF-20 | El sistema debe indicar al tutor cuál fue la última foto tomada, para reanudar una sesión interrumpida sin repetir ni saltar fichas. |
| RF-21 | El sistema debe mantener bloqueado un lote para otros tutores mientras no se haya subido por completo, sin liberarlo por inactividad. |

### 1.3 Procesamiento por visión (IA)

| ID | Requisito |
|---|---|
| RF-22 | El sistema debe enmascarar/recortar los campos DNI y celular de la imagen antes de enviarla a la API de visión. |
| RF-23 | El sistema debe extraer, vía API de visión server-side, los datos de identificación y las respuestas marcadas (A-E, 20 preguntas) de cada ficha de Primaria/Secundaria. |
| RF-24 | El sistema debe extraer, para fichas de Inicial, solo los datos de identificación y el resultado de buenas/malas ya escrito a mano por el tutor. |
| RF-25 | El sistema debe determinar el estado de cada pregunta (marcada / en blanco / anulada / incierta) según las reglas de detección de marcas definidas. |
| RF-26 | El sistema debe calcular automáticamente correctas/incorrectas comparando las respuestas detectadas contra la clave de respuestas del curso+nivel+grado correspondiente. |

### 1.4 Revisión y corrección (Angular)

| ID | Requisito |
|---|---|
| RF-27 | El sistema debe listar, por lote, las fichas pendientes de revisión, mostrando foto y datos extraídos lado a lado. |
| RF-28 | El sistema debe permitir corregir manualmente cualquier campo extraído, con sugerencias de autocompletado (apellidos, colegios) cuando aplique. |
| RF-29 | El sistema debe permitir anular una pregunta (checkbox aparte de las 5 opciones) cuando el alumno la marcó de forma inválida. |
| RF-30 | El sistema debe permitir confirmar una ficha, pasándola a estado "Confirmada". |
| RF-31 | El sistema debe permitir editar/volver a tomar la foto de una ficha específica en cualquier momento, incluso después de exportada, regenerando sus datos. |
| RF-32 | El sistema debe ofrecer el flujo de edición tanto "desde web" como "desde móvil". |
| RF-33 | El sistema debe liberar automáticamente una ficha en estado "En revisión" tras ~15 minutos de inactividad. |
| RF-34 | El sistema debe permitir a un tutor solicitar continuar un lote/ficha dejado a medias por otro tutor de la misma sede, si no está en revisión activa y el lote ya está completo. |
| RF-35 | El sistema debe permitir marcar una ficha como "con observación" (manual o automáticamente por baja confianza del modelo). |
| RF-36 | El sistema debe permitir que tutor o administrador marquen un bloque como "completado". |

### 1.5 Clave de respuestas

| ID | Requisito |
|---|---|
| RF-37 | El sistema debe permitir cargar la clave de respuestas de un curso+nivel+grado mediante un formulario (A-E por pregunta), no por foto. |
| RF-38 | El sistema debe permitir editar una clave ya cargada, aunque ya existan fichas confirmadas de ese curso+nivel+grado. |
| RF-39 | Al corregir un error de registro en la clave, el sistema debe recalcular correctas/incorrectas de todas las fichas ya confirmadas de ese curso+nivel+grado. |
| RF-40 | El sistema debe permitir otorgar +1 punto a todos los alumnos de un curso+nivel+grado cuando una pregunta se considera mal planteada, sin modificar la clave. |
| RF-41 | El sistema debe permitir tanto a tutores como a administradores cargar/editar la clave de respuestas. |

### 1.6 Ranking y exportación

| ID | Requisito |
|---|---|
| RF-42 | El sistema debe calcular el ranking y los diplomas de honor por bloque (sede+curso+nivel+grado) al marcarse como completado, sin mezclar sedes. |
| RF-43 | El sistema debe mostrar el ranking en vivo desde la app, sin depender de la exportación a Excel. |
| RF-44 | El sistema debe permitir a un tutor exportar a Excel únicamente lo que él mismo haya trabajado/confirmado. |
| RF-45 | El sistema debe permitir al administrador exportar a Excel toda la información de su sede activa (organizada por bloque) o un bloque específico. |
| RF-46 | El Excel exportado debe seguir la plantilla exacta acordada (banner, título, encabezados, freeze panes, columna de hora de finalización, fórmula de puntaje). |

### 1.7 Privacidad y retención

| ID | Requisito |
|---|---|
| RF-47 | El sistema no debe exponer la API key de visión ni las credenciales de Storage al cliente (Flutter/Angular) bajo ninguna circunstancia. |
| RF-48 | El sistema debe generar URLs firmadas de corta duración para mostrar imágenes desde el frontend, nunca enlaces permanentes. |
| RF-49 | El sistema no debe borrar las imágenes al confirmar ni al exportar una ficha; debe conservarlas comprimidas hasta pasados al menos dos eventos posteriores. |

---

## 2. Requisitos No Funcionales (RNF) — por atributo de calidad

### 2.1 Seguridad

| ID | Requisito |
|---|---|
| RNF-01 | Las claves de API y credenciales de servicios externos deben vivir únicamente en el backend, nunca en clientes. |
| RNF-02 | El acceso a imágenes en Storage debe estar restringido al backend; el frontend solo accede vía URLs firmadas de corta duración. |
| RNF-03 | Las contraseñas de usuario deben almacenarse con hashing seguro, nunca en texto plano. |
| RNF-04 | CORS debe configurarse explícitamente, limitando los orígenes permitidos al dominio de Angular. |

### 2.2 Privacidad (crítico por tratarse de datos de menores de edad)

| ID | Requisito |
|---|---|
| RNF-05 | DNI y celular deben enmascararse/recortarse de la imagen antes de salir del backend hacia la API de visión. |
| RNF-06 | El DNI no debe usarse como clave de comparación ni de detección de duplicados en ningún proceso del sistema. |
| RNF-07 | El procesamiento de datos reales debe hacerse en el nivel de pago de la API de visión (sin uso para entrenamiento); el nivel gratuito solo para pruebas con datos ficticios. |

### 2.3 Disponibilidad y confiabilidad

| ID | Requisito |
|---|---|
| RNF-08 | El bloqueo de bloques/fichas debe liberarse automáticamente por inactividad (15 min) o permitir solicitud de continuación cuando corresponda, para no depender de un solo tutor conectado. |
| RNF-09 | El sistema debe tolerar el fallo de subida de un lote sin pérdida de las fotos ya capturadas localmente (reintento manual). |
| RNF-10 | El backend en capa gratuita puede "dormir" tras 15 min sin tráfico; el primer request del día debe tolerar hasta ~1 min de arranque en frío sin romper la experiencia. |

### 2.4 Rendimiento

| ID | Requisito |
|---|---|
| RNF-11 | Las imágenes deben comprimirse antes de subir, para minimizar tiempo de subida y costo de la API de visión. |
| RNF-12 | El procesamiento de un lote completo (~20-30 fichas) debe completarse en un tiempo que no bloquee al tutor en espera (umbral a definir en diseño técnico detallado). |

### 2.5 Usabilidad

| ID | Requisito |
|---|---|
| RNF-13 | La interfaz debe ser profesional y minimalista: sin emojis, tipografía limpia, colores sobrios, íconos sutiles, buen uso del espacio en blanco. |
| RNF-14 | La pantalla de validación debe ser usable tanto desde laptop como desde celular (responsive), con una interacción adaptada a pantallas chicas. |
| RNF-15 | La app de captura debe tener una interfaz precargada que no dependa de la velocidad de red para abrirse, dado el contexto de señal variable en los colegios. |

### 2.6 Escalabilidad y costo

| ID | Requisito |
|---|---|
| RNF-16 | La arquitectura debe operar dentro de los límites de las capas gratuitas de Render, Supabase y Firebase mientras el volumen lo permita. |
| RNF-17 | El costo de procesamiento por sede completa (~800 fichas) debe mantenerse por debajo de USD 1. |
| RNF-18 | El diseño modular (monolito por bounded context) debe permitir extraer un módulo (ej. Visión) como servicio independiente si el volumen lo exige, sin reescritura completa. |

### 2.7 Mantenibilidad

| ID | Requisito |
|---|---|
| RNF-19 | El backend debe organizarse como monolito modular por bounded context, sin que un módulo acceda directamente a las tablas de otro. |
| RNF-20 | Las credenciales de conexión deben inyectarse por variables de entorno, nunca hardcodeadas, para que la misma imagen funcione en dev/staging/producción. |

### 2.8 Compatibilidad / portabilidad

| ID | Requisito |
|---|---|
| RNF-21 | La app de captura debe funcionar de forma consistente en celulares de gama económica/antigua. |
| RNF-22 | El sistema no debe depender de capacidades no soportadas en iOS Safari (ej. Background Sync) para su funcionamiento básico — queda fuera del alcance actual, no es bloqueante. |
