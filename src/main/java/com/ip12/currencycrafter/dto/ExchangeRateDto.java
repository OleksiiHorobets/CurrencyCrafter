package com.ip12.currencycrafter.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.ip12.currencycrafter.entity.ExchangeRate} entity
 */
@Data
public class ExchangeRateDto {
    private Long id;
    private LocalDate localDate;
    private BigDecimal rate;
}