package com.ip12.currencycrafter.controller.api;

import com.ip12.currencycrafter.dto.CurrencyRateDto;
import com.ip12.currencycrafter.dto.ExchangeRateDto;
import com.ip12.currencycrafter.exception.ResourceNotFoundException;
import com.ip12.currencycrafter.service.CurrencyService;
import com.ip12.currencycrafter.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/v1/currencies")
@RestController
@RequiredArgsConstructor
public class CurrencyRestController {
    private final CurrencyService currencyService;
    private final ExchangeRateService exchangeRateService;
    private final JdbcTemplate jdbcTemplate;

    @GetMapping
    public List<CurrencyRateDto> findAll() {
        return currencyService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyRateDto> findById(@PathVariable Long id) throws ResourceNotFoundException {
        CurrencyRateDto currencyRateInfo = currencyService.getById(id);
        return ResponseEntity.ok(currencyRateInfo);
    }

    @GetMapping("today/uah")
    public ResponseEntity<ExchangeRateDto> getCurrentUahRateToUsd() throws ResourceNotFoundException {
        String sql = "SELECT ID FROM CURRENCY_SCHEMA.EXCHANGE_RATE WHERE CURRENCY_ID = " +
                "(SELECT ID FROM CURRENCY_SCHEMA.CURRENCY WHERE NAME = 'UAH') AND LOCAL_DATE = CURRENT_DATE";
        int exchangeRateId;
        try {
            exchangeRateId = jdbcTemplate.queryForObject(
                    sql,
                    Integer.class
            );
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }

        ExchangeRateDto exchangeRateInfo = exchangeRateService.getById(exchangeRateId);
        return ResponseEntity.ok(exchangeRateInfo);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        currencyService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyRateDto> update(@PathVariable Long id, @RequestBody CurrencyRateDto currencyDto) throws ResourceNotFoundException {
        if (currencyDto.getId() != null && !currencyDto.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        currencyDto.setId(id);
        CurrencyRateDto currencyRateInfo = currencyService.update(currencyDto);
        return ResponseEntity.ok(currencyRateInfo);
    }

    @PostMapping
    public ResponseEntity<CurrencyRateDto> create(@RequestBody CurrencyRateDto currencyDto) {
        if (currencyDto.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        CurrencyRateDto currencyRateInfo = currencyService.save(currencyDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(currencyRateInfo.getId()).toUri();
        return ResponseEntity.created(uri).body(currencyRateInfo);
    }
}