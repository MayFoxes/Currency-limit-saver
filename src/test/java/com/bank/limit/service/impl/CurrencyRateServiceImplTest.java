package com.bank.limit.service.impl;

import com.bank.limit.dto.CurrencyRateDto;
import com.bank.limit.model.CurrencyRate;
import com.bank.limit.repository.CurrencyRateRepository;
import com.bank.limit.util.enums.CurrencyShortname;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class CurrencyRateServiceImplTest {
    @Mock
    CurrencyRateRepository currencyRateRepository;
    @InjectMocks
    CurrencyRateServiceImpl currencyRateServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPairRateByCurrencyName() {
        when(currencyRateRepository.findCurrencyRate(anyString())).thenReturn(new CurrencyRate("currencyPairs", new BigDecimal(0), "timestamp"));

        CurrencyRateDto result = currencyRateServiceImpl.getPairRateByCurrencyName(CurrencyShortname.RUB);
        Assertions.assertEquals(new CurrencyRateDto(new BigDecimal(0)), result);
    }
}