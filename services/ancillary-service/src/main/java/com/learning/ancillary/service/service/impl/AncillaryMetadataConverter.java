package com.learning.ancillary.service.service.impl;

import com.learning.common.domain.AncillaryMetadata;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tools.jackson.databind.ObjectMapper;

@Converter
public class AncillaryMetadataConverter implements AttributeConverter<AncillaryMetadata, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(AncillaryMetadata attribute) {
        if (attribute == null) return null;
        return objectMapper.writeValueAsString(attribute);
    }

    @Override
    public AncillaryMetadata convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return objectMapper.readValue(dbData, AncillaryMetadata.class);
    }
}
