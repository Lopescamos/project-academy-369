package com.olimpiadas369.backend.registro.application.internal.queryservices;

import com.olimpiadas369.backend.registro.application.queryservices.RegistroQueryService;
import com.olimpiadas369.backend.registro.domain.model.aggregates.*;
import com.olimpiadas369.backend.registro.domain.model.queries.*;
import com.olimpiadas369.backend.registro.domain.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistroQueryServiceImpl implements RegistroQueryService {

    private final SedeRepository sedes;
    private final BloqueRepository bloques;
    private final ClaveRespuestasRepository claves;
    private final LoteRepository lotes;
    private final RegistroRepository registros;

    public RegistroQueryServiceImpl(SedeRepository sedes, BloqueRepository bloques,
                                    ClaveRespuestasRepository claves, LoteRepository lotes,
                                    RegistroRepository registros) {
        this.sedes = sedes; this.bloques = bloques; this.claves = claves;
        this.lotes = lotes; this.registros = registros;
    }

    @Override public Optional<Sede> handle(GetSedeByIdQuery q) { return sedes.findById(q.sedeId()); }
    @Override public List<Sede> handle(GetAllSedesQuery q) { return sedes.findAll(); }
    @Override public Optional<Bloque> handle(GetBloqueByIdQuery q) { return bloques.findById(q.bloqueId()); }
    @Override public List<Bloque> handle(GetBloquesBySedeQuery q) { return bloques.findBySedeId(q.sedeId()); }
    @Override public Optional<ClaveRespuestas> handle(GetClaveRespuestasByScopeQuery q) { return claves.findByScope(q.curso(), q.nivel(), q.grado()); }
    @Override public Optional<Lote> handle(GetLoteByIdQuery q) { return lotes.findById(q.loteId()); }
    @Override public Optional<Registro> handle(GetRegistroByIdQuery q) { return registros.findById(q.registroId()); }
    @Override public List<Registro> handle(GetRegistrosConfirmadosByBloqueQuery q) { return registros.findConfirmadosByBloqueId(q.bloqueId()); }
}
