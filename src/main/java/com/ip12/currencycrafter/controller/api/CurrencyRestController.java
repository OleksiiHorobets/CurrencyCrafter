package com.ip12.currencycrafter.controller.api;

import com.ip12.currencycrafter.dto.CurrencyRateDto;
import com.ip12.currencycrafter.exception.ResourceNotFoundException;
import com.ip12.currencycrafter.service.CurrencyService;
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
    public List<CurrencyRateDto> findAll() {
        return currencyService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyRateDto> findById(@PathVariable Long id) throws ResourceNotFoundException {
        CurrencyRateDto currencyRateInfo = currencyService.getById(id);
        return ResponseEntity.ok(currencyRateInfo);
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