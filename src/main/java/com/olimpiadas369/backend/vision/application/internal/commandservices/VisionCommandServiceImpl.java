package com.olimpiadas369.backend.vision.application.internal.commandservices;

import com.olimpiadas369.backend.imagenes.domain.model.queries.GetImagenByIdQuery;
import com.olimpiadas369.backend.imagenes.application.queryservices.ImagenQueryService;
import com.olimpiadas369.backend.registro.domain.model.aggregates.Registro;
import com.olimpiadas369.backend.registro.domain.model.aggregates.RespuestaAlumno;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.EstadoRegistro;
import com.olimpiadas369.backend.registro.domain.model.valueobjects.Opcion;
import com.olimpiadas369.backend.registro.domain.repositories.RegistroRepository;
import com.olimpiadas369.backend.registro.domain.repositories.RespuestaAlumnoRepository;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;
import com.olimpiadas369.backend.vision.application.commandservices.VisionCommandService;
import com.olimpiadas369.backend.vision.application.internal.outboundservices.acl.VisionExternalServices;
import com.olimpiadas369.backend.vision.domain.model.commands.ProcesarImagenCommand;
import com.olimpiadas369.backend.vision.domain.model.commands.ReprocesarRegistroCommand;
import com.olimpiadas369.backend.vision.domain.services.VisionProvider;
import com.olimpiadas369.backend.vision.domain.services.VisionResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class VisionCommandServiceImpl implements VisionCommandService {

    private final VisionProvider provider;
    private final VisionExternalServices external;
    private final ImagenQueryService imagenQueries;
    private final RegistroRepository registros;
    private final RespuestaAlumnoRepository respuestas;

    public VisionCommandServiceImpl(VisionProvider provider, VisionExternalServices external,
                                    ImagenQueryService imagenQueries,
                                    RegistroRepository registros, RespuestaAlumnoRepository respuestas) {
        this.provider = provider; this.external = external;
        this.imagenQueries = imagenQueries; this.registros = registros; this.respuestas = respuestas;
    }

    @Override @Transactional
    public Result<Long, ApplicationError> handle(ProcesarImagenCommand c) {
        var img = imagenQueries.handle(new GetImagenByIdQuery(c.imagenId()));
        if (img.isEmpty()) return Result.failure(ApplicationError.notFound("Imagen", c.imagenId().toString()));

        var signedUrl = external.fetchSignedUrlCensurada(c.imagenId());
        if (signedUrl.isEmpty())
            return Result.failure(ApplicationError.businessRuleViolation("Imagen", "No censored image available for id " + c.imagenId()));

        try {
            VisionResult vr = provider.extraerDesdeUrl(signedUrl.get());
            Registro r = crearRegistroDesde(vr, img.get().getLoteId(), c.imagenId());
            Registro saved = registros.save(r);
            persistirRespuestas(saved.getId(), vr);
            return Result.success(saved.getId());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("procesar-imagen", e.getMessage()));
        }
    }

    @Override @Transactional
    public Result<Long, ApplicationError> handle(ReprocesarRegistroCommand c) {
        var r = registros.findById(c.registroId());
        if (r.isEmpty()) return Result.failure(ApplicationError.notFound("Registro", c.registroId().toString()));
        return handle(new ProcesarImagenCommand(r.get().getImagenId()));
    }

    private Registro crearRegistroDesde(VisionResult vr, Long loteId, Long imagenId) {
        Registro r = new Registro();
        r.setLoteId(loteId);
        r.setImagenId(imagenId);
        // bloqueId lo asigna el flujo que agrupa fichas por scope — por ahora se setea a nivel de uso.
        r.setNombre(vr.nombre());
        r.setApellidos(vr.apellidos());
        r.setColegioTextoExtraido(vr.colegioTexto());
        r.setConfianzaPromedio(vr.confianzaPromedio());
        r.setEstado(EstadoRegistro.EXTRAIDO);
        r.setCorrectas((short) 0);
        r.setIncorrectas((short) 0);
        r.setAnuladas((short) 0);
        r.setBlancos((short) 0);
        r.setPuntaje(0);
        r.setTieneObservacion(false);
        return r;
    }

    private void persistirRespuestas(Long registroId, VisionResult vr) {
        List<RespuestaAlumno> list = new ArrayList<>();
        for (var resp : vr.respuestas()) {
            Opcion op = null;
            try { if (resp.opcionMarcada() != null && !resp.opcionMarcada().isBlank()) op = Opcion.valueOf(resp.opcionMarcada()); }
            catch (IllegalArgumentException ignored) {}
            list.add(new RespuestaAlumno(registroId, resp.numeroPregunta(), op, null, resp.estadosOpcionesJson()));
        }
        if (!list.isEmpty()) respuestas.saveAll(list);
    }
}
