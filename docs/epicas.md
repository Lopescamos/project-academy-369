# Épicas, priorización y estimación

## 1. Épicas

| # | Épica | Resumen |
|---|---|---|
| E1 | Gestión de accesos y sedes | Identidad y acceso: sedes, invitación de tutores, invitados, ciclo de vida de cuentas, historial de participación. |
| E2 | Captura de fichas en campo | App Flutter: selección de bloque, captura en lote, verificación inmediata, reanudación. |
| E3 | Extracción y calificación automática | Integración con API de visión, detección de marcas, cálculo de correctas/incorrectas, clave de respuestas. |
| E4 | Revisión y corrección de fichas | Pantallas Angular de validación, corrección asistida, edición posterior de fotos. |
| E5 | Concurrencia y colaboración entre tutores | Bloqueo de bloques/fichas, handoff seguro, liberación por inactividad. |
| E6 | Ranking, diplomas y exportación | Cálculo de ranking por bloque, visualización en vivo, exportación a Excel. |
| E7 | Privacidad y seguridad de datos sensibles | Enmascarado de DNI/celular, manejo de API keys, retención de imágenes. |
| E8 | Caso especial — Nivel Inicial | Flujo alterno de calificación interpretativa sin clave de respuestas. |

Ver `user-stories.md` para el detalle de cada épica con criterios de aceptación.

---

## 2. Priorización del MVP (MoSCoW)

| Prioridad | Épicas / elementos |
|---|---|
| **Must** | E1 (Gestión de accesos y sedes — versión básica), E2 (Captura de fichas), E3 (Extracción y calificación), E4 (Revisión y corrección), E6 (Ranking y exportación por bloque), E7 (Privacidad y seguridad), E8 (Caso Inicial) |
| **Should** | E5 (Concurrencia y handoff entre tutores), Historial de participación / pantalla "Empleados" (parte de E1), Exportación consolidada de toda la sede en un Excel (parte de E6) |
| **Could** | Edición "desde móvil" además de "desde web", Pantalla de observación de otras sedes para el administrador, Autocompletado de catálogos (apellidos/colegios) como ayuda adicional |
| **Won't (por ahora)** | Resiliencia offline avanzada (reintento automático, cola persistente), OCR interno/en el dispositivo, cámara web adicional (`getUserMedia`), entidad `Evento` separada de `Sede`, sección ampliada de casos excepcionales |

---

## 3. Estimación rápida (tallas S / M / L)

| Épica | Talla | Nota |
|---|---|---|
| E1 — Gestión de accesos y sedes | **L** | Invitación por correo, credenciales automáticas, ciclo de vida de cuentas, historial muchos-a-muchos. |
| E2 — Captura de fichas (Flutter) | **L** | App móvil completa: cámara en vivo, lote, verificación, reanudación. |
| E3 — Extracción y calificación | **L** | Integración con API de visión + lógica de calificación + recálculo de clave. |
| E4 — Revisión y corrección (Angular) | **M** | Pantallas de comparación foto/datos, corrección asistida, edición posterior. |
| E5 — Concurrencia y colaboración | **M** | Estados de bloque/ficha, liberación por inactividad, handoff. |
| E6 — Ranking y exportación | **M** | Cálculo de ranking, Excel con plantilla, exportación por tutor/admin. |
| E7 — Privacidad y seguridad | **S** | Reglas transversales aplicadas dentro de los módulos anteriores, no un módulo aparte. |
| E8 — Caso especial Inicial | **S** | Variante simplificada del flujo de captura/revisión ya construido. |

> Esta talla es una señal de tamaño relativo para planear sprints, no una estimación en horas — lo importante es detectar que E1, E2 y E3 son las más grandes y probablemente conviene partirlas en historias más chicas antes de meterlas a un sprint.
