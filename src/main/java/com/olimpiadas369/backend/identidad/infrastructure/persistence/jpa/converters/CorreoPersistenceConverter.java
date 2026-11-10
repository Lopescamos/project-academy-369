package com.olimpiadas369.backend.identidad.infrastructure.persistence.jpa.converters;

import com.olimpiadas369.backend.identidad.domain.model.valueobjects.Correo;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class CorreoPersistenceConverter implements AttributeConverter<Correo, String> {
    @Override public String convertToDatabaseColumn(Correo c) { return c == null ? null : c.value(); }
    @Override public Correo convertToEntityAttribute(String s) { return s == null ? null : new Correo(s); }
}
