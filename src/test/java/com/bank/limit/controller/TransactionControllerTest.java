package com.bank.limit.controller;

import com.bank.limit.dto.CurrencyRateDto;
import com.bank.limit.dto.ExpenseRequestDto;
import com.bank.limit.dto.ExpenseResultDto;
import com.bank.limit.dto.LimitFullDto;
import com.bank.limit.service.CurrencyRateService;
import com.bank.limit.service.ExpenseService;
import com.bank.limit.service.LimitService;
import com.bank.limit.util.enums.CurrencyShortname;
import com.bank.limit.util.enums.ExpenseCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

class TransactionControllerTest {
    @Mock
    ExpenseService expenseService;
    @Mock
    LimitService limitService;
    @Mock
    CurrencyRateService currencyRateService;
    @InjectMocks
    TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddExpense() {
        when(expenseService.addExpense(any(ExpenseRequestDto.class))).thenReturn(new ExpenseResultDto(0, 1, CurrencyShortname.RUB, ExpenseCategory.PRODUCT, new BigDecimal(1), LocalDateTime.of(2025, Month.MARCH, 7, 22, 14, 19), true, 1L));
        when(limitService.updateRestLimit(anyInt(), any(ExpenseCategory.class), any(BigDecimal.class))).thenReturn(new LimitFullDto(1L, 0, ExpenseCategory.PRODUCT, new BigDecimal(3), new BigDecimal(2), LocalDateTime.of(2025, Month.MARCH, 7, 22, 14, 19)));
        when(currencyRateService.getPairRateByCurrencyName(any(CurrencyShortname.class))).thenReturn(new CurrencyRateDto(new BigDecimal(0)));

        ExpenseResultDto result = transactionController.addExpense(0, 1, "RUB", "PRODUCT", new BigDecimal(0), "2025-03-07T16:07:25.9397984");
        Assertions.assertEquals(new ExpenseResultDto(0, 1, CurrencyShortname.RUB, ExpenseCategory.PRODUCT, new BigDecimal(1), LocalDateTime.of(2025, Month.MARCH, 7, 22, 14, 19), true, 1L), result);
    }

    @Test
    void testAddExpenseExceededFalseThenTrue() {
        when(expenseService.addExpense(any(ExpenseRequestDto.class))).thenReturn(new ExpenseResultDto(0, 1, CurrencyShortname.RUB, ExpenseCategory.PRODUCT, new BigDecimal(-1), LocalDateTime.of(2025, Month.MARCH, 7, 22, 14, 19), true, 1L));
        when(limitService.updateRestLimit(anyInt(), any(ExpenseCategory.class), any(BigDecimal.class))).thenReturn(new LimitFullDto(1L, 0, ExpenseCategory.PRODUCT, new BigDecimal(0), new BigDecimal(-1), LocalDateTime.of(2025, Month.MARCH, 7, 22, 14, 19)));
        when(currencyRateService.getPairRateByCurrencyName(any(CurrencyShortname.class))).thenReturn(new CurrencyRateDto(new BigDecimal("0.01")));

        ExpenseResultDto result = transactionController.addExpense(0, 1, "RUB", "product", new BigDecimal(1), "2025-03-07T16:07:25.9397984");
        Assertions.assertEquals(new ExpenseResultDto(0, 1, CurrencyShortname.RUB, ExpenseCategory.PRODUCT, new BigDecimal(-1), LocalDateTime.of(2025, Month.MARCH, 7, 22, 14, 19), true, 1L), result);
    }
}
