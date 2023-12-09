package com.ip12.currencycrafter.service;

import com.ip12.currencycrafter.dto.CurrencyRateInfo;

import java.util.List;

public interface CurrencyService {

    void deleteById(long id);

    CurrencyRateInfo getById(long id);

    List<CurrencyRateInfo> getAll();

    CurrencyRateInfo update(CurrencyRateInfo currency);

    CurrencyRateInfo save(CurrencyRateInfo currency);
}
