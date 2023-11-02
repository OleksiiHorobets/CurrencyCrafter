package com.ip12.currencycrafter.controller;

import com.ip12.currencycrafter.service.CurrencyService;
import com.ip12.currencycrafter.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;
    private final ExchangeRateService exchangeRateService;

    @GetMapping
    public String getAllCurrencies(Model model) {
        model.addAttribute("currencies", currencyService.getAll());
        return "currency";
    }

    @GetMapping("/today")
    public String getTodayCurrencies(Model model) {
        model.addAttribute("exchangeRates", exchangeRateService.getAllByDate(LocalDate.of(2022, 2, 5)));
        return "today";
    }

    @ResponseBody
    @DeleteMapping("/{currencyId}")
    public ResponseEntity<?> getTodayCurrencies(@PathVariable(name = "currencyId") Long currencyId) {
        currencyService.deleteById(currencyId);
        return ResponseEntity.ok("ok");
    }
}
