package com.olimpiadas369.backend.registro.application.queryservices;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Bloque;
import com.olimpiadas369.backend.registro.domain.model.aggregates.ClaveRespuestas;
import com.olimpiadas369.backend.registro.domain.model.aggregates.Lote;
import com.olimpiadas369.backend.registro.domain.model.aggregates.Registro;
import com.olimpiadas369.backend.registro.domain.model.aggregates.Sede;
import com.olimpiadas369.backend.registro.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface RegistroQueryService {
    Optional<Sede> handle(GetSedeByIdQuery q);
    List<Sede> handle(GetAllSedesQuery q);
    Optional<Bloque> handle(GetBloqueByIdQuery q);
    List<Bloque> handle(GetBloquesBySedeQuery q);
    Optional<ClaveRespuestas> handle(GetClaveRespuestasByScopeQuery q);
    Optional<Lote> handle(GetLoteByIdQuery q);
    Optional<Registro> handle(GetRegistroByIdQuery q);
    List<Registro> handle(GetRegistrosConfirmadosByBloqueQuery q);
}
