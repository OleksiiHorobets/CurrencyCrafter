package com.ip12.currencycrafter.service;

import com.ip12.currencycrafter.dto.CurrencyRateDto;
import com.ip12.currencycrafter.dto.ExchangeRateDto;
import com.ip12.currencycrafter.entity.Currency;
import com.ip12.currencycrafter.exception.ResourceNotFoundException;
import com.ip12.currencycrafter.exception.ResourceUniqueViolationException;
import com.ip12.currencycrafter.mapper.CurrencyMapper;
import com.ip12.currencycrafter.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyMapper currencyMapper;

    private final ExchangeRateService exchangeRateService;
    private final CurrencyRepository currencyRepository;


    @Override
    public void deleteById(long id) {
        currencyRepository.deleteById(id);
    }

    @Override
    public CurrencyRateDto getById(long id) {
        return currencyRepository.findById(id)
                .map(currencyMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("No currency with id {%s} found!".formatted(id)));
    }

    @Override
    public List<CurrencyRateDto> getAll() {
        return currencyRepository.findAllByOrderByIdAsc()
                .stream()
                .map(currencyMapper::toDTO)
                .toList();
    }

    @Override
    public CurrencyRateDto update(CurrencyRateDto currencyDto) {
        Currency currency = currencyMapper.toEntity(currencyDto);

        if (currencyRepository.existsByName(currency.getName())) {
            throw new ResourceUniqueViolationException("Currency with name {%s} already exists!".formatted((currencyDto.getName())));
        }

        if (!currencyRepository.existsById(currencyDto.getId())) {
            throw new ResourceNotFoundException("No currency with id {%s} found!".formatted(currencyDto.getId()));
        }
        return currencyMapper.toDTO(currencyRepository.save(currency));
    }

    @Override
    public CurrencyRateDto save(CurrencyRateDto currencyDto) {
        var currency = currencyMapper.toEntity(currencyDto);

        if (currencyRepository.existsByName(currency.getName())) {
            throw new ResourceUniqueViolationException("Currency with name {%s} already exists!".formatted((currencyDto.getName())));
        }

        return currencyMapper.toDTO(currencyRepository.save(currency));
    }

    @Override
    public Map<LocalDate, BigDecimal> getAllExchangeRateInRange(Long firstCurrencyId, Long secondCurrencyId, LocalDate startDate, LocalDate endDate) {
        var firstExchangeMap = convertIntoDateToRateMap(exchangeRateService.
                getAllByCurrencyAndDateLimits(firstCurrencyId, startDate, endDate));

        var secondExchangeMap = convertIntoDateToRateMap(exchangeRateService.
                getAllByCurrencyAndDateLimits(secondCurrencyId, startDate, endDate));

//        var resMap = startDate.datesUntil(endDate)
//                        .map()

        firstExchangeMap.keySet()
                .stream()
                .map(dateKey -> Optional.ofNullable(secondExchangeMap.get(dateKey)));

        return Map.of(
                LocalDate.now().minusDays(1), BigDecimal.valueOf(123),
                LocalDate.now(), BigDecimal.valueOf(321)
        );
    }

    private Map<LocalDate, BigDecimal> convertIntoDateToRateMap(List<ExchangeRateDto> exchangeRateDtos) {

        return exchangeRateDtos.stream()
                .collect(toMap(ExchangeRateDto::getLocalDate, ExchangeRateDto::getRate));
    }


}
