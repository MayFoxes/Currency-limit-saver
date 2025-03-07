package com.bank.limit.controller;

import com.bank.limit.dto.ExceededExpenseDto;
import com.bank.limit.dto.LimitDto;
import com.bank.limit.service.ExpenseService;
import com.bank.limit.service.LimitService;
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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

class AccountControllerTest {
    @Mock
    LimitService limitService;
    @Mock
    ExpenseService expenseService;
    @InjectMocks
    AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddLimit() {
        LocalDateTime now = LocalDateTime.now();
        LimitDto expected = new LimitDto(0, ExpenseCategory.PRODUCT, new BigDecimal(0), new BigDecimal(0), now);
        when(limitService.addLimit(any(LimitDto.class))).thenReturn(expected);

        LimitDto result = accountController.addLimit(0, new BigDecimal(0), ExpenseCategory.PRODUCT.name());
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testGetAllExceededLimitExpenses() {
        LocalDateTime now = LocalDateTime.now();
        ExceededExpenseDto expenseDto = new ExceededExpenseDto(now, new BigDecimal(0), ExpenseCategory.PRODUCT, new BigDecimal(0), new BigDecimal(0), now, true);
        when(expenseService.getAllExpenseExceededLimitByAccount(anyInt())).thenReturn(List.of(expenseDto));

        List<ExceededExpenseDto> result = accountController.getAllExceededLimitExpenses(0);
        Assertions.assertEquals(List.of(expenseDto), result);
    }
}