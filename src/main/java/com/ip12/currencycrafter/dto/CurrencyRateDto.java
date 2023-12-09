package com.ip12.currencycrafter.dto;

import lombok.Data;

import java.util.List;

@Data
public class CurrencyRateDto {
    private Long id;
    private String name;
    private List<ExchangeRateDto> exchangeRates;
}
