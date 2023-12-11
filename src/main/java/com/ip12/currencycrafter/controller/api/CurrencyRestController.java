package com.ip12.currencycrafter.controller.api;

import com.ip12.currencycrafter.dto.CurrencyRateDto;
import com.ip12.currencycrafter.dto.ExchangeRateDto;
import com.ip12.currencycrafter.exception.ResourceNotFoundException;
import com.ip12.currencycrafter.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/v1/currencies")
@RestController
@RequiredArgsConstructor
@Tag(name = "Currency Rest Controller", description = "Operations with currencies (REST API)")
public class CurrencyRestController {
    private final CurrencyService currencyService;
    private final JdbcTemplate jdbcTemplate;

    @GetMapping
    @Operation(summary = "Get all", description = "Get a list of all Currencies in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received")
    })
    public List<CurrencyRateDto> findAll() {
        return currencyService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get by Id",
            description = "Get Currency by its Identifier",
            parameters = {@Parameter(name = "id", description = "Currency Id", example = "5")}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    public ResponseEntity<CurrencyRateDto> findById(@PathVariable Long id) throws ResourceNotFoundException {
        CurrencyRateDto currencyRateInfo = currencyService.getById(id);
        return ResponseEntity.ok(currencyRateInfo);
    }

    @GetMapping("/today/uah")
    public ResponseEntity<ExchangeRateDto> getCurrentUahRateToUsd() throws ResourceNotFoundException {
        String sql = """
                SELECT exchange_rate.id, local_date, rate
                FROM CURRENCY_SCHEMA.EXCHANGE_RATE as exchange_rate
                         LEFT JOIN CURRENCY_SCHEMA.CURRENCY as currency on exchange_rate.currency_id = currency.id
                WHERE currency.name = 'UAH'
                  and exchange_rate.local_date = CURRENT_DATE
                 """;

        var exchangeRateDto = jdbcTemplate.queryForObject(
                sql,
                mapResultSetToExchangeRateDto()
        );
        return ResponseEntity.ok(exchangeRateDto);
    }

    private RowMapper<ExchangeRateDto> mapResultSetToExchangeRateDto() {
        return (rs, rowNum) -> ExchangeRateDto.builder()
                .id(rs.getLong("id"))
                .localDate(rs.getObject("local_date", LocalDate.class))
                .rate(rs.getBigDecimal("rate"))
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete by Id",
            description = "Delete Currency with specified Identifier",
            parameters = {@Parameter(name = "id", description = "Currency Id", example = "10")}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
    })
    public void deleteById(@PathVariable Long id) {
        currencyService.deleteById(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update by Id",
            description = "Update Currency with specified Identifier",
            parameters = {@Parameter(name = "id", description = "Currency Id", example = "7")}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    public ResponseEntity<CurrencyRateDto> update(@PathVariable Long id, @RequestBody CurrencyRateDto currencyDto) throws ResourceNotFoundException {
        if (currencyDto.getId() != null && !currencyDto.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        currencyDto.setId(id);
        CurrencyRateDto currencyRateInfo = currencyService.update(currencyDto);
        return ResponseEntity.ok(currencyRateInfo);
    }

    @PostMapping
    @Operation(
            summary = "Create",
            description = "Create Currency with specified Identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Currency cannot be created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Currency with specified name already exists", content = @Content)
    })
    public ResponseEntity<CurrencyRateDto> create(@RequestBody CurrencyRateDto currencyDto) {
        if (currencyDto.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        CurrencyRateDto currencyRateInfo = currencyService.save(currencyDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(currencyRateInfo.getId()).toUri();
        return ResponseEntity.created(uri).body(currencyRateInfo);
    }
}