# User stories con criterios de aceptación

> 20 historias (US-01 a US-20) organizadas bajo las 8 épicas de `epicas.md`.

## Épica 1 — Gestión de accesos y sedes

**US-01** — Como administrador, quiero registrar una sede nueva, para organizar el trabajo de un evento en una ubicación distinta.
- Dado que estoy en la pantalla de gestión de accesos, cuando creo una sede con un nombre, entonces queda disponible para asociarle tutores y bloques.

**US-02** — Como administrador, quiero invitar tutores por correo antes del evento, para que lleguen con su cuenta ya lista.
- Dado que ingreso correos y nombres para una sede, cuando confirmo la invitación, entonces el sistema genera usuario y contraseña y los envía a cada correo.
- Si un correo ya existe en el sistema, entonces se me ofrece reactivar esa cuenta en vez de crear una nueva.

**US-03** — Como administrador, quiero agregar un invitado el mismo día del evento, para no depender del flujo de invitación por correo.
- Dado que estoy en mi sede activa, cuando creo un usuario invitado con nombre y contraseña manual, entonces esa persona obtiene acceso inmediato a esa sede.

**US-04** — Como administrador, quiero que las cuentas de tutor se desactiven solas al terminar un evento, para evitar accesos indefinidos.
- Dado que un evento finaliza, cuando el sistema procesa el cierre, entonces todas las cuentas de tutor de esa sede quedan desactivadas, salvo las que marqué explícitamente para mantener activas.

**US-05** — Como administrador, quiero ver en qué sedes ha participado cada colaborador, para reconocer a quienes ayudan recurrentemente.
- Dado que abro la pantalla de "Empleados" y busco por nombre, entonces veo su historial de sedes/eventos, separado del log de auditoría de acciones.

## Épica 2 — Captura de fichas en campo

**US-06** — Como tutor, quiero seleccionar un bloque disponible, para empezar a fotografiar sus fichas.
- Dado que un bloque no está siendo trabajado por nadie, cuando lo selecciono, entonces queda bloqueado para otros tutores de mi sede.
- Si la clave de respuestas de ese curso+nivel+grado no está cargada (y no es Inicial), entonces el sistema me impide empezar y me avisa que falta cargarla.

**US-07** — Como tutor, quiero fotografiar todas las fichas de un bloque de corrido, para no perder tiempo abriendo y cerrando la cámara.
- Dado que inicié una sesión de captura, cuando tomo cada foto, entonces aparece un panel de Aceptar/Reintentar antes de pasar a la siguiente.
- Cuando termino de fotografiar todas las fichas, entonces puedo subir el lote completo de una sola vez.

**US-08** — Como tutor, quiero que el sistema recuerde la última foto tomada, para continuar sin repetir ni saltarme fichas si tuve que parar.
- Dado que dejé una sesión de captura a medias, cuando la reabro, entonces el sistema me muestra cuál fue la última ficha fotografiada.

## Épica 3 — Extracción y calificación automática

**US-09** — Como sistema, quiero extraer los datos de identificación y las respuestas marcadas de cada ficha subida, para evitar el tipeo manual.
- Dado que un lote terminó de subirse, cuando el backend procesa cada imagen, entonces guarda un registro "pendiente de revisión" con los datos extraídos, con el DNI/celular ya enmascarados antes de llamar a la API externa.

**US-10** — Como sistema, quiero comparar las respuestas detectadas contra la clave de respuestas, para calcular correctas/incorrectas automáticamente.
- Dado que una pregunta tiene una sola marca con confianza alta, cuando se compara contra la clave, entonces se determina correcta o incorrecta.
- Si una pregunta tiene 2+ marcas con confianza alta, entonces se considera anulada.
- Si una pregunta está en blanco, entonces no suma ni resta.

**US-11** — Como administrador o tutor, quiero poder corregir la clave de respuestas después de cargada, para manejar errores detectados durante o después del examen.
- Dado que corrijo un error de registro en la clave, cuando guardo el cambio, entonces el sistema recalcula correctas/incorrectas de todas las fichas ya confirmadas de ese curso+nivel+grado.
- Dado que una pregunta estaba mal planteada, cuando aplico "dar el punto a todos", entonces todos los alumnos de ese curso+nivel+grado reciben +1 sin cambiar la clave.

## Épica 4 — Revisión y corrección de fichas

**US-12** — Como tutor, quiero ver la foto junto a los datos extraídos, para confirmar o corregir cada ficha.
- Dado que abro una ficha pendiente, cuando la reviso, entonces veo la imagen y los campos extraídos lado a lado, con sugerencias de autocompletado cuando el dato no se registró con confianza alta.

**US-13** — Como tutor, quiero anular fácilmente una pregunta mal marcada, para no decidir manualmente cuál de varias marcas cuenta.
- Dado que el alumno marcó más de una opción, cuando activo el cuadradito de "anular", entonces esa pregunta queda como mala sin elegir una opción puntual.

**US-14** — Como tutor o administrador, quiero volver a tomar la foto de una ficha específica, para corregir una ilegible, incluso después de exportada.
- Dado que una ficha ya fue exportada, cuando elijo "Editar" sobre ella, entonces puedo re-tomar la foto (desde web o desde móvil) y el sistema regenera sus datos.

## Épica 5 — Concurrencia y colaboración entre tutores

**US-15** — Como tutor, quiero continuar un lote que otro tutor dejó a medias, para no perder tiempo si esa persona ya no está disponible.
- Dado que un lote de otro tutor de mi sede está completamente subido y sin fichas en revisión activa, cuando solicito continuar, entonces puedo seguir validando desde donde quedó.
- Si el lote tiene fotos sin subir, o una ficha está en revisión activa, entonces la solicitud se rechaza.

## Épica 6 — Ranking, diplomas y exportación

**US-16** — Como tutor o administrador, quiero marcar un bloque como completado, para que entre al cálculo de ranking.
- Dado que todas las fichas de un bloque están confirmadas, cuando lo marco como completado, entonces el sistema calcula ranking y diplomas, visibles de inmediato en la app.

**US-17** — Como tutor, quiero exportar a Excel lo que yo mismo trabajé, para entregar mi avance.
- Dado que tengo fichas confirmadas por mí, cuando exporto, entonces se genera un Excel solo con esas fichas, respetando la plantilla acordada.

**US-18** — Como administrador, quiero exportar toda la información de mi sede en un solo Excel organizado por bloque, para tener un respaldo consolidado.
- Dado que estoy en mi sede activa, cuando exporto "todo", entonces obtengo un Excel con los datos de todos los bloques de esa sede, separados por partes, sin mezclar con otras sedes.

## Épica 7 — Privacidad y seguridad de datos sensibles

**US-19** — Como responsable del sistema, quiero que el DNI y el celular nunca lleguen sin enmascarar a servicios externos, para proteger datos de menores.
- Dado que una imagen va a enviarse a la API de visión, cuando el backend la procesa, entonces las regiones de DNI y celular ya están recortadas/enmascaradas antes del envío.

## Épica 8 — Caso especial Inicial

**US-20** — Como tutor, quiero registrar el resultado de un examen de Inicial sin pasar por detección de marcas, para reflejar la calificación interpretativa que ya hice a mano.
- Dado que la ficha corresponde a Inicial, cuando la fotografío, entonces el sistema solo lee identificación y el resultado de buenas/malas que ya anoté a mano, sin intentar detectar marcas de respuestas.
