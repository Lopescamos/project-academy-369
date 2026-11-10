package com.olimpiadas369.backend.vision.infrastructure.ai.gemini;

import com.olimpiadas369.backend.vision.domain.services.VisionProvider;
import com.olimpiadas369.backend.vision.domain.services.VisionResult;
import com.olimpiadas369.backend.vision.infrastructure.ai.VisionSettings;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Llama a Gemini 2.5 Flash enviando la signed URL de la foto censurada + un prompt estructurado.
 * El parseo del JSON de respuesta es intencionalmente mínimo: la plantilla de prompt real y el
 * schema de respuesta se afinan según pruebas con fichas reales.
 */
@Service
@EnableConfigurationProperties(VisionSettings.class)
@Profile({"dev", "prod"})
public class GeminiVisionProvider implements VisionProvider {

    private final VisionSettings settings;
    private final RestClient http;

    public GeminiVisionProvider(VisionSettings settings) {
        this.settings = settings;
        this.http = RestClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com")
                .build();
    }

    @Override
    public VisionResult extraerDesdeUrl(String imagenSignedUrl) {
        if (settings.apiKey() == null || settings.apiKey().isBlank())
            return emptyPlaceholder();

        String prompt = """
                Eres un extractor estructurado para fichas del examen Olimpiadas 369.
                Devuelve SOLO JSON con el siguiente formato exacto:
                { "nombre":"", "apellidos":"", "colegio":"", "horaFinalizacion":"HH:MM",
                  "confianzaPromedio":0.0, "respuestas":[{"numeroPregunta":1,"opcionMarcada":"A","estadosOpcionesJson":"[]"}] }
                Para una pregunta con 2 o más marcas, deja opcionMarcada vacía (se cuenta como ANULADA).
                """;

        try {
            // TODO: ajustar endpoint exacto + modelo; esto es la forma general del call.
            Map<?, ?> response = http.post()
                    .uri("/v1beta/models/{model}:generateContent?key={key}", settings.model(), settings.apiKey())
                    .body(Map.of(
                            "contents", List.of(Map.of("parts", List.of(
                                    Map.of("text", prompt),
                                    Map.of("file_data", Map.of("mime_type", "image/jpeg", "file_uri", imagenSignedUrl))
                            )))
                    ))
                    .retrieve()
                    .body(Map.class);
            // Parseo mínimo de la respuesta: en la práctica se usa Jackson + records.
            return emptyPlaceholder();
        } catch (Exception e) {
            return emptyPlaceholder();
        }
    }

    private VisionResult emptyPlaceholder() {
        return new VisionResult("", "", "", null, BigDecimal.ZERO, List.of());
    }
}
