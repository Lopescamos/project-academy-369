# ADR-007: Spring Boot 4.1.x con springdoc-openapi 3.x

- Estado: Aceptada
- Fecha: 2026-11-06

## Contexto
Spring Boot 4.x corre sobre Spring Framework 7 y Java 21. La librería springdoc-openapi cambia de línea mayor con cada versión de Spring Boot: v2.x solo soporta Boot 3.x, v3.x soporta Boot 4.x.

## Decisión
Fijar Spring Boot 4.1.x como versión base del proyecto y usar `springdoc-openapi-starter-webmvc-ui` en su línea 3.x (último patch compatible con el minor 4.1 exacto), no 2.x.

## Alternativas consideradas
- Quedarse en Spring Boot 3.x con springdoc 2.x: más documentación y ejemplos existentes, pero queda inmediatamente desalineado con lo que se usa hoy.
- Usar springdoc 2.x con Boot 4: incompatible (artefactos cambiaron de paquetes, falla en el arranque).

## Consecuencias
Se gana: stack alineado a lo actual, Java 21 completo (records, pattern matching, virtual threads). Se acepta perder: ecosistema de ejemplos y Stack Overflow todavía inclinado a Boot 3 → algunas recetas hay que adaptarlas; los patches de springdoc 3.x avanzan rápido y hay que verificar el compatible exacto antes de fijar.
