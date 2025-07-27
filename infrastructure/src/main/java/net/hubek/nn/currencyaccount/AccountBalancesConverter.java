package net.hubek.nn.currencyaccount;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@Converter
public class AccountBalancesConverter implements AttributeConverter<Map<CurrencyCode, BigDecimal>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<CurrencyCode, BigDecimal> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not convert balances to JSON", e);
        }
    }

    @Override
    public Map<CurrencyCode, BigDecimal> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, objectMapper.getTypeFactory()
                    .constructMapType(Map.class, CurrencyCode.class, BigDecimal.class));
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not convert JSON to balances", e);
        }
    }
}