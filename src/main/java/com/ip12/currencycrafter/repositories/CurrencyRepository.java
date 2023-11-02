package com.ip12.currencycrafter.repositories;


import com.ip12.currencycrafter.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    public Currency getCurrencyByName(String name);
}
