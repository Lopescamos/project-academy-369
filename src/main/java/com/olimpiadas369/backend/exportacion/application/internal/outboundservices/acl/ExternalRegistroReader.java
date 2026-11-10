package com.olimpiadas369.backend.exportacion.application.internal.outboundservices.acl;

import com.olimpiadas369.backend.registro.application.queryservices.RegistroQueryService;
import com.olimpiadas369.backend.registro.domain.model.aggregates.Registro;
import com.olimpiadas369.backend.registro.domain.model.queries.GetRegistrosConfirmadosByBloqueQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/** Consume el BC Registro para leer las fichas ya confirmadas que se van a exportar. */
@Service
public class ExternalRegistroReader {
    private final RegistroQueryService queries;
    public ExternalRegistroReader(RegistroQueryService queries) { this.queries = queries; }
    public List<Registro> registrosConfirmados(Long bloqueId) {
        return queries.handle(new GetRegistrosConfirmadosByBloqueQuery(bloqueId));
    }
}
