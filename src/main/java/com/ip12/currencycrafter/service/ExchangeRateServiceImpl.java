package com.ip12.currencycrafter.service;

import com.ip12.currencycrafter.entities.Currency;
import com.ip12.currencycrafter.entities.ExchangeRate;
import com.ip12.currencycrafter.repositories.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService{

    private final ExchangeRateRepository exchangeRateRepository;
    @Override
    public void deleteById(long id) {
        exchangeRateRepository.deleteById(id);
    }

    @Override
    public ExchangeRate getById(long id) {
        Optional<ExchangeRate> exchangeRate = exchangeRateRepository.findById(id);
        return null;
    }

    @Override
    public List<ExchangeRate> getAll() {
        return null;
    }

    @Override
    public List<ExchangeRate> getAllByCurrency(Currency currency) {
        return null;
    }

    @Override
    public List<ExchangeRate> getAllByCurrency(long currencyId) {
        return null;
    }

    @Override
    public ExchangeRate update(ExchangeRate exchangeRate) {
        return null;
    }

    @Override
    public ExchangeRate save(ExchangeRate exchangeRate) {
        return null;
    }
}
