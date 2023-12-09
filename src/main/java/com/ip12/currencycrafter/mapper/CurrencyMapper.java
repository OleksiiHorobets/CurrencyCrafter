package com.ip12.currencycrafter.mapper;

import com.ip12.currencycrafter.dto.CurrencyRateInfo;
import com.ip12.currencycrafter.entity.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {
    @Mapping(source = "id", target = "id")
    CurrencyRateInfo toDTO(Currency currency);

    @Mapping(source = "id", target = "id")
    Currency toEntity(CurrencyRateInfo currencyRateInfo);
}
