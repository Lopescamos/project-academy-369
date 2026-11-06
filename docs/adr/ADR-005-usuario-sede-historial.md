# ADR-005: `Usuario` ↔ `Sede` como relación muchos-a-muchos con historial, separada del log de auditoría

- Estado: Aceptada
- Fecha: 2026-11-04

## Contexto
Las cuentas de tutores se desactivan al terminar cada evento y se reactivan para los siguientes, asociándose a la sede que corresponda esa vez. Se necesita saber en qué sedes participó cada persona a lo largo del tiempo, sin perder ese historial al reactivar.

## Decisión
Modelar `usuario_sede` como tabla intermedia muchos-a-muchos, donde cada evento añade una fila nueva sin sobrescribir las anteriores. Esta tabla **no se mezcla** con el log de auditoría de acciones (`auditoria_accion`, poblado por el módulo Audit) — son tablas y pantallas distintas: una registra "en qué sedes estuvo", la otra "qué hizo dentro del sistema".

## Alternativas consideradas
- FK única `usuario.sede_actual`: se pierde el historial al reactivar.
- Reutilizar `auditoria_accion` para registrar "asignado a sede X": mezcla responsabilidades distintas (presencia vs. acciones) y vuelve ambas pantallas confusas.

## Consecuencias
Se gana: historial real por persona visible tanto en su perfil como en la pantalla "Empleados" del admin, sin sobrescribir nada al reactivar. Se acepta perder: una tabla más y una consulta adicional para resolver la "sede activa" al autenticarse.
