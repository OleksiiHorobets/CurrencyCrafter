package com.ip12.currencycrafter.controller;

import com.ip12.currencycrafter.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping
    public String getAllCurrencies(Model model) {
        model.addAttribute("currencies", currencyService.getAll());
        return "currency";
    }
}
