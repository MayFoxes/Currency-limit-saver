package com.bank.limit.dto;

import com.bank.limit.util.enums.ExpenseCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bank.limit.model.Limit}
 */
public record LimitDto(Integer account,
                       ExpenseCategory expenseCategory,
                       BigDecimal limitSum,
                       BigDecimal restLimit,
                       LocalDateTime dateTime) {
}