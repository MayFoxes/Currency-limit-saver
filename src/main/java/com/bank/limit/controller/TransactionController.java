package com.bank.limit.controller;

import com.bank.limit.dto.ExpenseRequestDto;
import com.bank.limit.dto.ExpenseResultDto;
import com.bank.limit.dto.LimitFullDto;
import com.bank.limit.service.CurrencyRateService;
import com.bank.limit.service.ExpenseService;
import com.bank.limit.service.LimitService;
import com.bank.limit.util.enums.CurrencyShortname;
import com.bank.limit.util.enums.ExpenseCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expense")
public class TransactionController {
    private final ExpenseService expenseService;
    private final LimitService limitService;
    private final CurrencyRateService currencyRateService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseResultDto addExpense(@RequestParam(name = "from") @NotNull Integer accountFrom,
                                       @RequestParam(name = "to") @NotNull Integer accountTo,
                                       @RequestParam(name = "cur") @NotNull String currency,
                                       @RequestParam(name = "cat") @NotNull String category,
                                       @RequestParam @NotNull @Positive BigDecimal sum,
                                       @RequestParam(name = "time") @NotNull String time) {

        checkAccountEquals(accountTo, accountFrom);
        var expenseCategory = getCategory(category);
        var currencyShortname = getCurrencyShortName(currency);
        BigDecimal currencyRate = currencyRateService.getPairRateByCurrencyName(currencyShortname).rate();
        BigDecimal ratedSum = sum.multiply(currencyRate).setScale(2, RoundingMode.CEILING);
        var limitDto = limitService.updateRestLimit(accountFrom, expenseCategory, ratedSum);
        ExpenseRequestDto expenseRequestDto = new ExpenseRequestDto(accountFrom,
                accountTo,
                currencyShortname,
                expenseCategory,
                ratedSum,
                LocalDateTime.parse(time),
                checkExceededLimit(limitDto),
                limitDto);

        log.info("POST request to add expense:{}", expenseRequestDto);
        return expenseService.addExpense(expenseRequestDto);
    }

    private ExpenseCategory getCategory(String name) {
        return ExpenseCategory.from(name)
                .orElseThrow(() -> new ValidationException(String.format("Currently no such category exist:%s", name)));
    }

    private CurrencyShortname getCurrencyShortName(String name) {
        return CurrencyShortname.from(name)
                .orElseThrow(() -> new ValidationException(String.format("Currently no such currency exist:%s", name)));
    }

    private void checkAccountEquals(Integer to, Integer from) {
        if (to.equals(from))
            throw new ValidationException(String.format("Account from:%d try to expense yourself (to):%d", from, to));
    }

    private boolean checkExceededLimit(LimitFullDto limitDto) {
        return limitDto.restLimit().signum() < 0.00;
    }

}

