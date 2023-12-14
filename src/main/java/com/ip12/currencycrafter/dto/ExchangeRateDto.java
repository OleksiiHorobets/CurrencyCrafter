package com.ip12.currencycrafter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.ip12.currencycrafter.entity.ExchangeRate} entity
 */
@Data
@Builder
public class ExchangeRateDto {
    @Schema(description = "Exchange Rate Id", example = "1")
    private Long id;
    @Schema(description = "Local Date", example = "2012-02-02")
    private LocalDate localDate;
    @Schema(description = "Exchange Rate", example = "0.2")
    private BigDecimal rate;
}