package com.ip12.currencycrafter.service;

import com.ip12.currencycrafter.dto.ExchangeRateDto;
import com.ip12.currencycrafter.entity.Currency;
import com.ip12.currencycrafter.entity.ExchangeRate;
import com.ip12.currencycrafter.exception.ResourceNotFoundException;
import com.ip12.currencycrafter.mapper.ExchangeRateMapper;
import com.ip12.currencycrafter.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

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
    public ExchangeRateDto update(ExchangeRateDto exchangeRate) {
        if (!exchangeRateRepository.existsById(exchangeRate.getId())) {
            throw new ResourceNotFoundException("No exchangeRateDto with id {%s} found!".formatted(exchangeRate.getId()));
        }
        return exchangeRateMapper
                .toDTO(exchangeRateRepository
                        .save(exchangeRateMapper
                                .toEntity(exchangeRate)));
    }

    @Override
    public ExchangeRateDto save(ExchangeRateDto exchangeRate) {
        return exchangeRateMapper
                .toDTO(exchangeRateRepository
                        .save(exchangeRateMapper
                                .toEntity(exchangeRate)));
    }


}
