package com.ip12.currencycrafter.service;

import com.ip12.currencycrafter.dto.ExchangeRateDto;
import com.ip12.currencycrafter.entity.Currency;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRateService {

    void deleteById(long id);

    ExchangeRateDto getById(long id);

    List<ExchangeRateDto> getAll();

    List<ExchangeRateDto> getAllByCurrency(Currency currency);

    List<ExchangeRateDto> getAllByCurrency(long currencyId);

    List<ExchangeRateDto> getAllByCurrencyAndDateLimits(long currencyId, LocalDate startDate, LocalDate endDate);

    List<ExchangeRateDto> getAllByDate(LocalDate date);

    ExchangeRateDto update(ExchangeRateDto exchangeRate);

    ExchangeRateDto save(ExchangeRateDto exchangeRate);


}
