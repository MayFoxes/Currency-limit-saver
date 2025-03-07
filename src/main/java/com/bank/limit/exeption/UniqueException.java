package com.bank.limit.exeption;

public class UniqueException extends RuntimeException {
    public UniqueException(String message) {
        super(message);
    }
}

