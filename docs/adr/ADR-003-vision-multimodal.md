# ADR-003: Modelo de visión multimodal server-side en lugar de OCR clásico

- Estado: Aceptada
- Fecha: 2026-10-15

## Contexto
Las fichas están llenadas a mano por niños y adolescentes con letra muy irregular. OCR de caracteres puro (Tesseract) se descartó en pruebas por bajo rendimiento sobre esa letra. El objetivo es extraer identificación + 20 respuestas con mínima corrección humana posterior.

## Decisión
Llamar a un modelo multimodal (Gemini 2.5 Flash como primera opción, Claude Haiku 5.5 como fallback) desde el backend, enviando la imagen ya censurada (DNI y celular recortados por coordenadas fijas). El cliente **nunca** habla directo con el proveedor — la API key vive solo en el backend.

## Alternativas consideradas
- OCR local (Tesseract): descartado por letra manuscrita irregular.
- Entrenar un modelo propio: fuera de alcance para un desarrollador solo.
- Llamar al modelo desde el cliente: la API key quedaría expuesta y la cuenta sería drenable.

## Consecuencias
Se gana: lectura razonable de letra humana, costo controlado (menos de USD 1 por sede completa estimado), privacidad reforzada por recorte físico de los campos sensibles. Se acepta perder: dependencia de un proveedor externo y de conexión a internet para el procesamiento; necesidad del plan de pago por uso (no el gratuito) por la cláusula de no-entrenamiento con los datos.
