package com.bank.limit.service.impl;

import com.bank.limit.dto.LimitDto;
import com.bank.limit.dto.LimitFullDto;
import com.bank.limit.model.Limit;
import com.bank.limit.repository.LimitRepository;
import com.bank.limit.util.enums.ExpenseCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class LimitServiceImplTest {
    @Mock
    LimitRepository repository;
    @InjectMocks
    LimitServiceImpl limitServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddLimit() {
        LocalDateTime now = LocalDateTime.now();
        when(repository.save(any(Limit.class))).thenReturn(new Limit(1L, 0, ExpenseCategory.PRODUCT, new BigDecimal(5), new BigDecimal(5), now));

        LimitDto result = limitServiceImpl.addLimit(new LimitDto(0, ExpenseCategory.PRODUCT, new BigDecimal(0), new BigDecimal(0), now));
        Assertions.assertEquals(new LimitDto(0, ExpenseCategory.PRODUCT, new BigDecimal(5), new BigDecimal(5), now), result);
    }

    @Test
    void testUpdateRestLimit() {
        LocalDateTime now = LocalDateTime.now();
        when(repository.findByAccountAndExpenseCategory(anyInt(), anyString())).thenReturn(Optional.of(new Limit(1L, 0, ExpenseCategory.PRODUCT, new BigDecimal(5), new BigDecimal(5), now)));
        when(repository.save(any(Limit.class))).thenReturn(new Limit(1L, 0, ExpenseCategory.PRODUCT, new BigDecimal(5), new BigDecimal(3), now));

        LimitFullDto result = limitServiceImpl.updateRestLimit(0, ExpenseCategory.PRODUCT, new BigDecimal(5));
        Assertions.assertEquals(new LimitFullDto(1L, 0, ExpenseCategory.PRODUCT, new BigDecimal(5), new BigDecimal(3), now), result);
    }
}
