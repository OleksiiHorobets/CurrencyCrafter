package com.ip12.currencycrafter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class CurrencyRateDto {
    @Schema(description = "Currency Id", example = "5")
    private Long id;
    @Schema(description = "Currency Name", example = "UAH")
    private String name;
    @Schema(description = "Currency Exchange Rates")
    private List<ExchangeRateDto> exchangeRates;
}
