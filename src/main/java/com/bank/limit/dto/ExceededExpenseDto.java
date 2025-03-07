package com.bank.limit.dto;

import com.bank.limit.util.enums.ExpenseCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for transaction(expense + limit) get for account
 */
public record ExceededExpenseDto(LocalDateTime limitDate,
                                 BigDecimal limit,
                                 ExpenseCategory expenseCategory,
                                 BigDecimal restLimit,
                                 BigDecimal sumExpense,
                                 LocalDateTime expenseDate,
                                 boolean limitExceeded) {
}
