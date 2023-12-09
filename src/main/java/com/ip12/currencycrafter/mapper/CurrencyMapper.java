package com.ip12.currencycrafter.mapper;

import com.ip12.currencycrafter.dto.CurrencyRateInfo;
import com.ip12.currencycrafter.entity.Currency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyRateInfo toDTO(Currency currency);

    Currency toEntity(CurrencyRateInfo currencyRateInfo);
}
