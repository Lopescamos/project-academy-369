package com.olimpiadas369.backend.registro.application.internal.commandservices;

import com.olimpiadas369.backend.registro.application.commandservices.ClaveRespuestasCommandService;
import com.olimpiadas369.backend.registro.domain.model.aggregates.ClaveCorreccion;
import com.olimpiadas369.backend.registro.domain.model.aggregates.ClaveRespuestas;
import com.olimpiadas369.backend.registro.domain.model.aggregates.Registro;
import com.olimpiadas369.backend.registro.domain.model.commands.AplicarClaveCorreccionCommand;
import com.olimpiadas369.backend.registro.domain.model.commands.CreateClaveRespuestasCommand;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.EstadoRegistro;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.TipoCorreccionClave;
import com.olimpiadas369.backend.registro.domain.repositories.*;
import com.olimpiadas369.backend.registro.domain.services.PuntajeCalculator;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class ClaveRespuestasCommandServiceImpl implements ClaveRespuestasCommandService {

    private final ClaveRespuestasRepository claves;
    private final ClaveCorreccionRepository correcciones;
    private final RegistroRepository registros;
    private final RespuestaAlumnoRepository respuestas;

    public ClaveRespuestasCommandServiceImpl(ClaveRespuestasRepository claves,
                                             ClaveCorreccionRepository correcciones,
                                             RegistroRepository registros,
                                             RespuestaAlumnoRepository respuestas) {
        this.claves = claves; this.correcciones = correcciones;
        this.registros = registros; this.respuestas = respuestas;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateClaveRespuestasCommand c) {
        if (claves.existsByScope(c.curso(), c.nivel(), c.grado()))
            return Result.failure(ApplicationError.conflict("ClaveRespuestas",
                    "An answer key already exists for scope (curso=%s, nivel=%s, grado=%s)".formatted(c.curso(), c.nivel(), c.grado())));
        try { return Result.success(claves.save(new ClaveRespuestas(c, null)).getId()); }
        catch (Exception e) { return Result.failure(ApplicationError.unexpected("create-clave-respuestas", e.getMessage())); }
    }

    @Override
    public Result<Long, ApplicationError> handle(AplicarClaveCorreccionCommand c) {
        var clave = claves.findById(c.claveRespuestasId());
        if (clave.isEmpty()) return Result.failure(ApplicationError.notFound("ClaveRespuestas", c.claveRespuestasId().toString()));

        try {
            ClaveCorreccion correccion = new ClaveCorreccion(c, null);

            if (c.tipo() == TipoCorreccionClave.ERROR_REGISTRO) {
                // Caso A: corregir opción + recalcular fichas confirmadas
                var cr = clave.get();
                cr.getDetalles().stream()
                        .filter(d -> d.getNumeroPregunta().equals(c.numeroPregunta()))
                        .findFirst()
                        .ifPresent(d -> d.setOpcionCorrecta(c.opcionNueva()));
                cr.bumpVersion();
                claves.save(cr);
                int recalculadas = recalcularFichas(c);
                correccion.registrarFichasRecalculadas(recalculadas);
            } else {
                // Caso B: no toca la clave, suma +1 a todos para esa pregunta
                int sumadas = sumarUnPuntoATodos(c);
                correccion.registrarFichasRecalculadas(sumadas);
            }

            return Result.success(correcciones.save(correccion).getId());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("aplicar-correccion-clave", e.getMessage()));
        }
    }

    private int recalcularFichas(AplicarClaveCorreccionCommand c) {
        // TODO: aquí se iteran registros confirmados cuyo scope coincida con la clave;
        // re-chequear cada RespuestaAlumno de numeroPregunta contra la opción nueva,
        // recalcular correctas/incorrectas/puntaje y guardar.
        return 0;
    }

    private int sumarUnPuntoATodos(AplicarClaveCorreccionCommand c) {
        // TODO: para cada registro confirmado del scope de la clave,
        // puntaje += VALOR_CORRECTA, incorrectas-- si corresponde.
        return 0;
    }
}
