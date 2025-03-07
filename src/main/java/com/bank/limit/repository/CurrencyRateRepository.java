package com.bank.limit.repository;

import com.bank.limit.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, String> {

    @Query(value = "Select * from PAIR_RATE where CURRENCY_PAIRS = ?1 ", nativeQuery = true)
    CurrencyRate findCurrencyRate(String currencyPair);
}
