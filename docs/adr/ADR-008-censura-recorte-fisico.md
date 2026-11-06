# ADR-008: Censurado por recorte físico de la imagen (no "ignorar DNI" por prompt)

- Estado: Aceptada
- Fecha: 2026-10-31

## Contexto
Las fichas contienen DNI y celular de menores de edad. Pedir al modelo "ignora esos campos" no evita que reciba la imagen completa y los procese internamente.

## Decisión
El backend recorta por coordenadas fijas las regiones de DNI y celular antes de enviar la imagen al proveedor de visión. El DNI nunca entra como campo persistido del sistema, y por tanto no se usa para detección de duplicados (se maneja por orden de captura y marca de "última foto tomada", ver 9.10).

## Alternativas consideradas
- Prompt engineering ("ignora el DNI"): no es protección real, el modelo recibe los píxeles.
- Guardar el DNI cifrado localmente: añade complejidad sin beneficio claro para el alcance actual.

## Consecuencias
Se gana: protección efectiva de datos sensibles de menores, costo de API algo menor (menos píxeles/tokens por imagen). Se acepta perder: no se puede validar unicidad por DNI, hay que confiar en el orden físico de las fichas y en la UI de reanudación; el recorte depende de que el layout de la ficha se mantenga fijo.
