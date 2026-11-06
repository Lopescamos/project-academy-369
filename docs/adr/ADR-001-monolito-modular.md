# ADR-001: Monolito modular en Spring Boot en vez de microservicios

- Estado: Aceptada
- Fecha: 2026-10-31

## Contexto
El sistema necesita: captura por móvil, procesamiento server-side con un proveedor de visión, revisión web y exportación. Hay un solo desarrollador y el hosting objetivo (Render plan gratuito) duerme servicios inactivos por separado. El autor ya está aprendiendo DDD con Spring Boot en la universidad.

## Decisión
Construir un monolito modular en Spring Boot, organizado internamente por bounded contexts (Registro, Imágenes, Visión, Exportación, Identidad y acceso, Audit), desplegado como un único contenedor.

## Alternativas consideradas
- Microservicios independientes (uno por BC): cada uno dormiría por su lado en Render → cascada de cold starts, pipeline de despliegue más caro, coordinación de contratos entre servicios con un solo dev.
- Monolito sin modularización (paquete único por capa técnica): más rápido al inicio pero rompe el aislamiento del dominio y entrena malos hábitos para los cursos de DDD.

## Consecuencias
Se gana: un solo despliegue, un solo pipeline, cold start único, coherencia transaccional barata en la DB, encaja con lo que el autor está aprendiendo. Se acepta perder: el módulo Visión no puede escalar aparte sin extraerlo primero; si en el futuro crece mucho, habrá que separarlo.
