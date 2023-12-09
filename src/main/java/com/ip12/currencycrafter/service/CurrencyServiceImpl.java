package com.ip12.currencycrafter.service;

import com.ip12.currencycrafter.dto.CurrencyRateInfo;
import com.ip12.currencycrafter.entity.Currency;
import com.ip12.currencycrafter.exception.ResourceNotFoundException;
import com.ip12.currencycrafter.exception.ResourceUniqueViolationException;
import com.ip12.currencycrafter.mapper.CurrencyMapper;
import com.ip12.currencycrafter.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyMapper currencyMapper;
    private final CurrencyRepository currencyRepository;


    @Override
    public void deleteById(long id) {
        currencyRepository.deleteById(id);
    }

    @Override
    public CurrencyRateInfo getById(long id) {
        return currencyRepository.findById(id)
                .map(currencyMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("No currency with id {%s} found!".formatted(id)));
    }

    @Override
    public List<CurrencyRateInfo> getAll() {
        return currencyRepository.findAllByOrderByIdAsc()
                .stream()
                .map(currencyMapper::toDTO)
                .toList();
    }

    @Override
    public CurrencyRateInfo update(CurrencyRateInfo currencyDto) {
        Currency currency = currencyMapper.toEntity(currencyDto);

        if(currencyRepository.existsByName(currency.getName())) {
            throw new ResourceUniqueViolationException("Currency with name {%s} already exists!".formatted((currencyDto.getName())));
        }

        if (!currencyRepository.existsById(currencyDto.getId())) {
            throw new ResourceNotFoundException("No currency with id {%s} found!".formatted(currencyDto.getId()));
        }
        return currencyMapper.toDTO(currencyRepository.save(currency));
    }

    @Override
    public CurrencyRateInfo save(CurrencyRateInfo currencyDto) {
        var currency = currencyMapper.toEntity(currencyDto);

        if(currencyRepository.existsByName(currency.getName())) {
            throw new ResourceUniqueViolationException("Currency with name {%s} already exists!".formatted((currencyDto.getName())));
        }

        return currencyMapper.toDTO(currencyRepository.save(currency));
    }
}
