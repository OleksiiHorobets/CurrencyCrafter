package com.ip12.currencycrafter.controller.api;

import com.ip12.currencycrafter.entity.Currency;
import com.ip12.currencycrafter.service.CurrencyService;
import com.ip12.currencycrafter.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/v1/currencies")
@RestController
@RequiredArgsConstructor
public class CurrencyRestController {
    private final CurrencyService currencyService;

    @GetMapping
    public List<Currency> findAll() {
        return currencyService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Currency> findById(@PathVariable Long id) {
        Currency currency = currencyService.getById(id);
        if (currency == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(currency);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        currencyService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Currency> save(@PathVariable Long id, @RequestBody Currency currencyDto) {
        if (currencyDto.getId() != null && !currencyDto.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        currencyDto.setId(id);
        Currency currency = currencyService.update(currencyDto);
        return ResponseEntity.ok(currency);
    }

    @PostMapping
    public ResponseEntity<Currency> create(@RequestBody Currency currencyDto) {
        if (currencyDto.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        Currency currency = currencyService.save(currencyDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(currency.getId()).toUri();
        return ResponseEntity.created(uri).body(currency);
    }
}
