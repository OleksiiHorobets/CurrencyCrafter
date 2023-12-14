package com.ip12.currencycrafter.service;

import com.ip12.currencycrafter.dto.AddExchangeRateRequest;
import com.ip12.currencycrafter.dto.ExchangeRateDto;
import com.ip12.currencycrafter.dto.UpdateExchangeRateDto;
import com.ip12.currencycrafter.entity.Currency;
import com.ip12.currencycrafter.entity.ExchangeRate;
import com.ip12.currencycrafter.exception.ResourceNotFoundException;
import com.ip12.currencycrafter.mapper.ExchangeRateMapper;
import com.ip12.currencycrafter.repository.CurrencyRepository;
import com.ip12.currencycrafter.repository.ExchangeRateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final CurrencyRepository currencyRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateMapper exchangeRateMapper;

    @Override
    public void deleteById(long id) {
        exchangeRateRepository.deleteById(id);
    }

    @Override
    public ExchangeRateDto getById(long id) {
        return exchangeRateRepository.findById(id)
                .map(exchangeRateMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("No exchangeRateDto with id {%s} found!".formatted(id)));
    }

    @Override
    public List<ExchangeRateDto> getAll() {
        return exchangeRateRepository.findAll()
                .stream()
                .map(exchangeRateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExchangeRateDto> getAllByCurrency(Currency currency) {
        return exchangeRateRepository.findAllByCurrency(currency)
                .stream()
                .map(exchangeRateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExchangeRateDto> getAllByCurrency(long currencyId) {
        return exchangeRateRepository.findAllByCurrency_Id(currencyId)
                .stream()
                .map(exchangeRateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExchangeRateDto> getAllByCurrencyAndDateLimits(long currencyId, LocalDate startDate, LocalDate endDate) {
        List<ExchangeRate> exchangeRateList = exchangeRateRepository.findAllByCurrencyIdAndDateLimits(currencyId, startDate, endDate);

        return exchangeRateList.stream()
                .map(exchangeRateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExchangeRateDto> getAllByDate(LocalDate date) {
        return exchangeRateRepository.findAllByLocalDate(date)
                .stream()
                .map(exchangeRateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExchangeRateDto update(Long id, UpdateExchangeRateDto exchangeRate) {
        var exchangeRateToUpdate = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No exchangeRateDto with id {%s} found!".formatted(id)));

        exchangeRateToUpdate.setRate(exchangeRate.getRate());
        exchangeRateToUpdate.setLocalDate(exchangeRate.getLocalDate());

        return exchangeRateMapper
                .toDTO(exchangeRateRepository.save(exchangeRateToUpdate));
    }

    @Override
    @Transactional
    public ExchangeRateDto save(AddExchangeRateRequest exchangeRateDto) {
        var usd = currencyRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("No Dollar!"));

        if (usd.getExchangeRates().stream().noneMatch(exchangeRate -> exchangeRate.getLocalDate().equals(exchangeRateDto.getLocalDate()))) {
            ExchangeRate exchangeRate = ExchangeRate.builder()
                    .rate(BigDecimal.ONE)
                    .currency(usd)
                    .localDate(exchangeRateDto.getLocalDate())
                    .build();
            usd.getExchangeRates().add(exchangeRate);
            currencyRepository.save(usd);
        }

        var currency = currencyRepository.findByName(exchangeRateDto.getCurrencyName())
                .orElseThrow(() -> new ResourceNotFoundException("No such currency name present!"));

        var exchangeRate = exchangeRateRepository.findByLocalDateAndCurrency_Id(exchangeRateDto.getLocalDate(), currency.getId())
                .orElseGet(ExchangeRate::new);

        exchangeRate.setRate(BigDecimal.ONE.divide(exchangeRateDto.getRateToUsd(), MathContext.DECIMAL128));
        exchangeRate.setLocalDate(exchangeRateDto.getLocalDate());
        exchangeRate.setCurrency(currency);

        return exchangeRateMapper
                .toDTO(exchangeRateRepository.save(exchangeRate));
    }
}
