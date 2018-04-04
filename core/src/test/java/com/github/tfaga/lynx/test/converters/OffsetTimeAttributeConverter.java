package com.github.tfaga.lynx.test.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Time;
import java.time.OffsetTime;
import java.time.ZoneOffset;

@Converter(autoApply = true)
public class OffsetTimeAttributeConverter implements AttributeConverter<OffsetTime, Time> {

    @Override
    public Time convertToDatabaseColumn(OffsetTime attribute) {
        return attribute == null ? null : Time.valueOf(attribute.toLocalTime());
    }

    @Override
    public OffsetTime convertToEntityAttribute(Time dbData) {
        return dbData == null ? null : OffsetTime.of(dbData.toLocalTime(), ZoneOffset.UTC);
    }
}
