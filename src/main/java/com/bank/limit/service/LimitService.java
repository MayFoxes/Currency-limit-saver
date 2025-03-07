package com.bank.limit.service;

import com.bank.limit.dto.LimitDto;
import com.bank.limit.dto.LimitFullDto;
import com.bank.limit.util.enums.ExpenseCategory;

import java.math.BigDecimal;

public interface LimitService {

    LimitDto addLimit(LimitDto limitDto);

    LimitFullDto updateRestLimit(Integer account, ExpenseCategory expenseCategory, BigDecimal sum);
}
