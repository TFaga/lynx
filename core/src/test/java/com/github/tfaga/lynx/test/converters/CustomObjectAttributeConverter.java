package com.github.tfaga.lynx.test.converters;

import com.github.tfaga.lynx.test.entities.CustomObjectEntity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CustomObjectAttributeConverter implements AttributeConverter<CustomObjectEntity, String> {

    @Override
    public String convertToDatabaseColumn(CustomObjectEntity attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public CustomObjectEntity convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new CustomObjectEntity(dbData);
    }
}
