package com.ip12.currencycrafter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CurrencyDto {
    @Schema(description = "Currency Id", example = "5")
    private Long id;

    @NotNull
    @Min(value = 3, message = "Currency ISO code must be 3 capital letters")
    @Max(value = 3, message = "Currency ISO code must be 3 capital letters")
    @Pattern(regexp = "[A-Z]", message = "Currency ISO code must be 3 capital letters")
    @NotBlank
    @Schema(description = "Currency Name", example = "UAH")
    private String name;
}
