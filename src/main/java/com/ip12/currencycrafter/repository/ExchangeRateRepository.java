package com.ip12.currencycrafter.repository;


import com.ip12.currencycrafter.entity.Currency;
import com.ip12.currencycrafter.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
//    ExchangeRate findByCurrencyAndDate(Currency currency, Date date);
//
//    List<ExchangeRate> findAllByCurrencyOrderByDateDesc(Currency currency);

    List<ExchangeRate> findAllByCurrency_Id(Long currencyId);

    List<ExchangeRate> findAllByCurrency(Currency currency);

    List<ExchangeRate> findAllByLocalDate(LocalDate date);
}
