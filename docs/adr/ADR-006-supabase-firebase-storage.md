# ADR-006: Postgres en Supabase (no en Render) + Imágenes en Firebase Storage

- Estado: Aceptada
- Fecha: 2026-10-31

## Contexto
El Postgres gratuito de Render se borra a los 30 días de creado; el servicio web gratuito no tiene disco persistente. Ninguno de los dos sirve para la base ni para las imágenes.

## Decisión
Base de datos en Supabase (plan gratuito: 500 MB, 2 proyectos activos; pausa a la semana sin tráfico pero sin pérdida de datos). Imágenes en Firebase Storage (5 GB gratuitos, con reglas de seguridad que solo dejen al backend acceder vía Admin SDK; signed URLs de corta duración para que la web/móvil las muestren).

## Alternativas consideradas
- Postgres en Render: expira en 30 días.
- Guardar imágenes en el filesystem del contenedor de Render: se pierde en cada redeploy/reinicio.
- AWS S3 + RDS: fuera del objetivo "cero costo mensual fijo".

## Consecuencias
Se gana: persistencia real sin costo mensual, aislamiento de secretos (Admin SDK solo en el backend). Se acepta perder: dependencia de dos proveedores gestionados más (Supabase, Firebase) con sus propios límites; la DB puede pausarse tras una semana inactiva y hay que reactivarla a mano desde el dashboard; y para producción con datos reales hay que pasar al plan de pago por uso de la API de visión (ver ADR-003), no es el único costo variable.
