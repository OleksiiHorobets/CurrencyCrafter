package com.ip12.currencycrafter.repositories;


import com.ip12.currencycrafter.entities.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
//    ExchangeRate findByCurrencyAndDate(Currency currency, Date date);
//
//    List<ExchangeRate> findAllByCurrencyOrderByDateDesc(Currency currency);
}
