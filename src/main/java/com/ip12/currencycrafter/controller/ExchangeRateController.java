package com.ip12.currencycrafter.controller;

import com.ip12.currencycrafter.service.CurrencyService;
import com.ip12.currencycrafter.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/currency")
@RequiredArgsConstructor
public class ExchangeRateController {
    private final CurrencyService currencyService;
    private final ExchangeRateService exchangeRateService;

    @GetMapping("/addexchangerateform")
    public String getAddExchangeRateForm(Model model, @RequestParam(name = "currencyId") Long currencyId) {
        model.addAttribute("currency", currencyService.getById(currencyId));
        return "addExchangeRate";
    }
}
