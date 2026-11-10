package com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.assemblers;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Registro;
import com.olimpiadas369.backend.registro.infrastructure.persistence.jpa.entities.RegistroPersistenceEntity;

public final class RegistroPersistenceAssembler {
    private RegistroPersistenceAssembler() {}
    public static Registro toDomainFromPersistence(RegistroPersistenceEntity e) {
        if (e == null) return null;
        Registro r = new Registro();
        r.setId(e.getId()); r.setLoteId(e.getLoteId()); r.setBloqueId(e.getBloqueId()); r.setImagenId(e.getImagenId());
        r.setNombre(e.getNombre()); r.setApellidos(e.getApellidos()); r.setColegioId(e.getColegioId());
        r.setColegioTextoExtraido(e.getColegioTextoExtraido()); r.setHoraFinalizacion(e.getHoraFinalizacion());
        r.setCorrectas(e.getCorrectas()); r.setIncorrectas(e.getIncorrectas()); r.setAnuladas(e.getAnuladas()); r.setBlancos(e.getBlancos());
        r.setPuntaje(e.getPuntaje()); r.setEstado(e.getEstado()); r.setTieneObservacion(e.getTieneObservacion());
        r.setConfianzaPromedio(e.getConfianzaPromedio()); r.setRevisorActualId(e.getRevisorActualId()); r.setRevisorTomadaAt(e.getRevisorTomadaAt());
        r.setConfirmadoPorId(e.getConfirmadoPorId()); r.setConfirmadoAt(e.getConfirmadoAt());
        return r;
    }
    public static RegistroPersistenceEntity toPersistenceFromDomain(Registro r) {
        if (r == null) return null;
        RegistroPersistenceEntity e = new RegistroPersistenceEntity();
        if (r.getId() != null) e.setId(r.getId());
        e.setLoteId(r.getLoteId()); e.setBloqueId(r.getBloqueId()); e.setImagenId(r.getImagenId());
        e.setNombre(r.getNombre()); e.setApellidos(r.getApellidos()); e.setColegioId(r.getColegioId());
        e.setColegioTextoExtraido(r.getColegioTextoExtraido()); e.setHoraFinalizacion(r.getHoraFinalizacion());
        e.setCorrectas(r.getCorrectas()); e.setIncorrectas(r.getIncorrectas()); e.setAnuladas(r.getAnuladas()); e.setBlancos(r.getBlancos());
        e.setPuntaje(r.getPuntaje()); e.setEstado(r.getEstado());
        e.setTieneObservacion(r.getTieneObservacion() != null ? r.getTieneObservacion() : false);
        e.setConfianzaPromedio(r.getConfianzaPromedio()); e.setRevisorActualId(r.getRevisorActualId()); e.setRevisorTomadaAt(r.getRevisorTomadaAt());
        e.setConfirmadoPorId(r.getConfirmadoPorId()); e.setConfirmadoAt(r.getConfirmadoAt());
        return e;
    }
}
