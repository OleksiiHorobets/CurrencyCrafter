package com.ip12.currencycrafter.service;

import com.ip12.currencycrafter.entities.Currency;
import com.ip12.currencycrafter.entities.ExchangeRate;

import java.util.List;
import java.util.Optional;

public interface ExchangeRateService {

    void deleteById(long id);
    ExchangeRate getById(long id);

    List<ExchangeRate> getAll();
    List<ExchangeRate> getAllByCurrency(Currency currency);
    List<ExchangeRate>  getAllByCurrency(long currencyId);

    ExchangeRate update(ExchangeRate exchangeRate);

    ExchangeRate save(ExchangeRate exchangeRate);

}
