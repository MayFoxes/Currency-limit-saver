package com.bank.limit.dto;

import java.math.BigDecimal;

/**
 * DTO for {@link com.bank.limit.model.CurrencyRate}
 */
public record CurrencyRateDto(BigDecimal rate) {
}
