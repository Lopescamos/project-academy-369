# ADR-009: Retención prolongada de imágenes (no borrado al confirmar/exportar)

- Estado: Aceptada
- Fecha: 2026-11-02

## Contexto
Una ficha ya confirmada y exportada todavía puede necesitar volver a fotografiarse (ver 9.10, "Editar"). Si la imagen se borrara al confirmar o al exportar, esa edición dejaría de ser posible.

## Decisión
Las imágenes se conservan comprimidas en Firebase Storage. El borrado real ocurre mucho más tarde: una vez que hayan pasado al menos dos eventos posteriores, recién entonces se empiezan a limpiar las imágenes del evento más antiguo (siempre queda el respaldo físico en papel).

## Alternativas consideradas
- Borrar al confirmar: bloquea la edición post-confirmación.
- Borrar al exportar: bloquea la edición post-exportación, que sí sigue siendo parte del flujo.
- Retención indefinida: desperdicia el free tier de 5 GB a mediano plazo.

## Consecuencias
Se gana: posibilidad real de re-tomar fotos después de exportado, sin romper el flujo. Se acepta perder: consumo sostenido de Storage; hace falta una tarea programada (o manual, al inicio) que haga la limpieza cuando toque.
