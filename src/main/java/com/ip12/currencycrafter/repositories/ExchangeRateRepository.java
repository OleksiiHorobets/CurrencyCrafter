package com.ip12.currencycrafter.repositories;


import com.ip12.currencycrafter.entities.Currency;
import com.ip12.currencycrafter.entities.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {
    ExchangeRate findByCurrencyAndDate(Currency currency, Date date);

    List<ExchangeRate> findAllByCurrencyOrderByDateDesc(Currency currency);
}
