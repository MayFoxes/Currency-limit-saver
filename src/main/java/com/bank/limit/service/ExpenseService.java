package com.bank.limit.service;

import com.bank.limit.dto.ExceededExpenseDto;
import com.bank.limit.dto.ExpenseRequestDto;
import com.bank.limit.dto.ExpenseResultDto;

import java.util.List;

public interface ExpenseService {
    ExpenseResultDto addExpense(ExpenseRequestDto dto);

    List<ExceededExpenseDto> getAllExpenseExceededLimitByAccount(Integer account);
}
