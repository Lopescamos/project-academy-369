package com.olimpiadas369.backend.vision.infrastructure.ai.stub;

import com.olimpiadas369.backend.vision.domain.services.VisionProvider;
import com.olimpiadas369.backend.vision.domain.services.VisionResult;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/** Fallback para tests: devuelve un resultado vacío sin llamar a ninguna API. */
@Service
@Profile("test")
public class StubVisionProvider implements VisionProvider {
    @Override public VisionResult extraerDesdeUrl(String imagenSignedUrl) {
        return new VisionResult("Alumno Prueba", "Apellidos Prueba", "Colegio Prueba",
                "10:30", BigDecimal.valueOf(0.95), List.of());
    }
}
