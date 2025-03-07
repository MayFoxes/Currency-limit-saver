package com.bank.limit.service.impl;

import com.bank.limit.dto.ExceededExpenseDto;
import com.bank.limit.model.Expense;
import com.bank.limit.model.Limit;
import com.bank.limit.repository.ExpenseRepository;
import com.bank.limit.repository.LimitRepository;
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
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

class ExpenseServiceImplTest {
    @Mock
    ExpenseRepository expenseRepository;
    @Mock
    LimitRepository limitRepository;
    @InjectMocks
    ExpenseServiceImpl expenseServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetAllExpenseExceededLimitByAccount() {
        LocalDateTime now = LocalDateTime.now();
        when(expenseRepository.getAllExpenseExceededLimitByAccount(anyInt())).thenReturn(List.of(new Expense(1L, 0, CurrencyShortname.RUB, new BigDecimal(0), now, true, new Limit(1L, 0, ExpenseCategory.PRODUCT, new BigDecimal(5), new BigDecimal(5), now))));
        when(limitRepository.findByAccount(anyInt())).thenReturn(Optional.of(new Limit(1L, 0, ExpenseCategory.PRODUCT, new BigDecimal(5), new BigDecimal(5), now)));

        List<ExceededExpenseDto> result = expenseServiceImpl.getAllExpenseExceededLimitByAccount(0);
        Assertions.assertEquals(List.of(new ExceededExpenseDto(now, new BigDecimal(5), ExpenseCategory.PRODUCT, new BigDecimal(5), new BigDecimal(0), now, true)), result);
    }
}
