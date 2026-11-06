# ADR-002: Flutter para la app móvil de captura (no navegador)

- Estado: Aceptada
- Fecha: 2026-10-20

## Contexto
La captura debe ser fluida: 20-30 fichas de corrido por bloque, sin ciclos de abrir/cerrar la cámara del sistema entre fotos. Las sedes a veces tienen conexión mala al inicio.

## Decisión
Desarrollar la app de captura en Flutter, con cámara abierta en vivo dentro de la misma pantalla y almacenamiento local del lote hasta terminar la tanda.

## Alternativas consideradas
- Web con `<input capture>`: obliga a abrir/cerrar la cámara del sistema entre fotos, rompe la fluidez.
- PWA con `getUserMedia`: la pantalla de captura depende de que la red responda lo suficiente para cargar la web; en una zona con señal débil puede trabarse antes de llegar a la primera foto. Queda como opción adicional a futuro (sección 10), no reemplazo.
- Nativo separado iOS + Android: doble esfuerzo para un solo desarrollador.

## Consecuencias
Se gana: UX de captura comparable a cualquier cámara nativa, mejor control de enfoque/flash/resolución, compresión madura, mejor comportamiento en celulares económicos. Se acepta perder: segundo stack (Dart) además de Java/TS, publicación en stores (o distribución APK directa) con su propio ciclo, dependencia de un celular físico para pruebas reales.
