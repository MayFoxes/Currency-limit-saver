package com.bank.limit.util.enums;

import java.util.Arrays;
import java.util.Optional;

public enum CurrencyShortname {
    RUB,
    USD,
    KZT;

    public static Optional<CurrencyShortname> from(String stringState) {
        return Arrays.stream(values())
                .filter(currency -> currency.name().equalsIgnoreCase(stringState))
                .findFirst();
    }
}
