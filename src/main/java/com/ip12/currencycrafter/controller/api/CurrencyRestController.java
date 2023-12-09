package com.ip12.currencycrafter.controller.api;

import com.ip12.currencycrafter.dto.CurrencyRateInfo;
import com.ip12.currencycrafter.entity.Currency;
import com.ip12.currencycrafter.exception.ResourceNotFoundException;
import com.ip12.currencycrafter.service.CurrencyService;
import com.ip12.currencycrafter.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/v1/currencies")
@RestController
@RequiredArgsConstructor
public class CurrencyRestController {
    private final CurrencyService currencyService;

    @GetMapping
    public List<CurrencyRateInfo> findAll() {
        return currencyService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyRateInfo> findById(@PathVariable Long id) {
        try {
            CurrencyRateInfo currencyRateInfo = currencyService.getById(id);
            return ResponseEntity.ok(currencyRateInfo);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency not found", ex);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        currencyService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyRateInfo> save(@PathVariable Long id, @RequestBody CurrencyRateInfo currencyDto) {
        if (currencyDto.getId() != null && !currencyDto.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        currencyDto.setId(id);
        CurrencyRateInfo currencyRateInfo = currencyService.update(currencyDto);
        return ResponseEntity.ok(currencyRateInfo);
    }

    @PostMapping
    public ResponseEntity<CurrencyRateInfo> create(@RequestBody CurrencyRateInfo currencyDto) {
        if (currencyDto.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        CurrencyRateInfo currencyRateInfo = currencyService.save(currencyDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(currencyRateInfo.getId()).toUri();
        return ResponseEntity.created(uri).body(currencyRateInfo);
    }
}