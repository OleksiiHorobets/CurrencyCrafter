package com.ip12.currencycrafter.controller;

import com.ip12.currencycrafter.entity.ExchangeRate;
import com.ip12.currencycrafter.service.CurrencyService;
import com.ip12.currencycrafter.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequiredArgsConstructor
public class ExchangeRateController {
    private final CurrencyService currencyService;
    private final ExchangeRateService exchangeRateService;

    @GetMapping("/currencies/{currencyId}/exchange-rates")
    public String getAllExchangeRatesByCurrency(Model model, @PathVariable("currencyId") Long currencyId) {
        model.addAttribute("currencyId", currencyId);
        model.addAttribute("exchangeRates", exchangeRateService.getAllByCurrency(currencyId));
        return "exchange-rates-by-currency";
    }

    @GetMapping("/currencies/{currencyId}/exchange-rates/new")
    public String getAddForm(Model model, @PathVariable("currencyId") Long currencyId) {
        model.addAttribute("currencyId", currencyId);
        return "add-exchange-rate";
    }

    @ResponseBody
    @PostMapping("/currencies/{currencyId}/exchange-rates")
    public ResponseEntity<?> addExchangeRate(@PathVariable("currencyId") Long currencyId, @RequestBody ExchangeRate exchangeRateDto) {
        if (exchangeRateDto.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        exchangeRateDto.setCurrency(currencyService.getById(currencyId));
        ExchangeRate exchangeRate = exchangeRateService.save(exchangeRateDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(exchangeRate.getId()).toUri();
        return ResponseEntity.created(uri).body(exchangeRate);
    }
}
