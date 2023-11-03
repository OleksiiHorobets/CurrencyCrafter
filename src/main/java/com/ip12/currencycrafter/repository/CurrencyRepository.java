package com.ip12.currencycrafter.repository;


import com.ip12.currencycrafter.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

}
