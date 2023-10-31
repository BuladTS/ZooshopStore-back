package ru.sfu.zooshopback.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.convert.ReadingConverter;

import java.math.BigDecimal;

@Slf4j
@Converter
public class MoneyConverter implements AttributeConverter<BigDecimal, BigDecimal> {


    @Override
    public BigDecimal convertToDatabaseColumn(BigDecimal attribute) {
        return null;
    }

    @Override
    public BigDecimal convertToEntityAttribute(BigDecimal dbData) {
        return null;
    }
}
