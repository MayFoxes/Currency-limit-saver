package com.bank.limit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class CurrencyLimitApplication {
    public static void main(String[] args) {
        SpringApplication.run(CurrencyLimitApplication.class, args);
    }
}
