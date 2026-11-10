package com.olimpiadas369.backend.registro.domain.repositories;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Registro;

import java.util.List;
import java.util.Optional;

public interface RegistroRepository {
    Optional<Registro> findById(Long id);
    List<Registro> findByBloqueId(Long bloqueId);
    List<Registro> findConfirmadosByBloqueId(Long bloqueId);
    Registro save(Registro registro);
}
