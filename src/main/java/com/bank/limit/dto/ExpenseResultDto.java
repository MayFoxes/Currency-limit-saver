package com.bank.limit.dto;

import com.bank.limit.util.enums.CurrencyShortname;
import com.bank.limit.util.enums.ExpenseCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bank.limit.model.Expense}
 */
public record ExpenseResultDto(Integer accountFrom,
                               Integer accountTo,
                               CurrencyShortname currencyShortname,
                               ExpenseCategory expenseCategory,
                               BigDecimal sumExpense,
                               LocalDateTime time,
                               boolean limitExceeded,
                               Long limitId) {
}