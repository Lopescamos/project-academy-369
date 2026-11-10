package com.olimpiadas369.backend.imagenes.infrastructure.persistence.jpa.adapters;

import com.olimpiadas369.backend.imagenes.domain.model.aggregates.Imagen;
import com.olimpiadas369.backend.imagenes.domain.repositories.ImagenRepository;
import com.olimpiadas369.backend.imagenes.infrastructure.persistence.jpa.assemblers.ImagenPersistenceAssembler;
import com.olimpiadas369.backend.imagenes.infrastructure.persistence.jpa.repositories.ImagenPersistenceRepository;
import com.olimpiadas369.backend.shared.infrastructure.events.SpringDomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ImagenRepositoryImpl implements ImagenRepository {
    private final ImagenPersistenceRepository jpa;
    private final SpringDomainEventPublisher events;
    public ImagenRepositoryImpl(ImagenPersistenceRepository jpa, SpringDomainEventPublisher events) {
        this.jpa = jpa; this.events = events;
    }
    @Override public Optional<Imagen> findById(Long id) { return jpa.findById(id).map(ImagenPersistenceAssembler::toDomainFromPersistence); }
    @Override public List<Imagen> findByLoteId(Long id) { return jpa.findByLoteIdOrderByOrdenEnLote(id).stream().map(ImagenPersistenceAssembler::toDomainFromPersistence).toList(); }
    @Override public Imagen save(Imagen i) {
        var saved = ImagenPersistenceAssembler.toDomainFromPersistence(jpa.save(ImagenPersistenceAssembler.toPersistenceFromDomain(i)));
        events.publishAndClear(i);
        return saved;
    }
}
