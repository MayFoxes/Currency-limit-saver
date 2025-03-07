package com.bank.limit.util.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ExpenseCategory {
    PRODUCT,
    SERVICE;

    public static Optional<ExpenseCategory> from(String stringState) {
        return Arrays.stream(values())
                .filter(category -> category.name().equalsIgnoreCase(stringState))
                .findFirst();
    }
}
