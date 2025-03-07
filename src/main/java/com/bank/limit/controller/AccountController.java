package com.bank.limit.controller;

import com.bank.limit.dto.ExceededExpenseDto;
import com.bank.limit.dto.LimitDto;
import com.bank.limit.service.ExpenseService;
import com.bank.limit.service.LimitService;
import com.bank.limit.util.enums.ExpenseCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/{account}")
public class AccountController {
    private final LimitService limitService;
    private final ExpenseService expenseService;

    @PostMapping(path = "/limit")
    @ResponseStatus(HttpStatus.CREATED)
    public LimitDto addLimit(@PathVariable @NotNull Integer account,
                             @RequestParam(name = "sum",
                                     defaultValue = "1000.00",
                                     required = false) @Min(1000) @Positive BigDecimal limitSum,
                             @RequestParam(name = "cat") @NotNull String expenseCategory) {
        LimitDto dto = new LimitDto(account,
                getCategory(expenseCategory),
                limitSum.setScale(2, RoundingMode.CEILING),
                limitSum.setScale(2, RoundingMode.CEILING),
                LocalDateTime.now());
        log.info("POST request from account:{} to add limit:{}", account, dto);
        return limitService.addLimit(dto);
    }

    @GetMapping
    public List<ExceededExpenseDto> getAllExceededLimitExpenses(@PathVariable @NotNull Integer account) {
        List<ExceededExpenseDto> tempList = expenseService.getAllExpenseExceededLimitByAccount(account);
        log.info("GET request from account:{} find all exceeded limits expenses:{}", account, tempList);
        return tempList;
    }

    private ExpenseCategory getCategory(String name) {
        return ExpenseCategory.from(name)
                .orElseThrow(() -> new ValidationException(String.format("Category must be service or product not a %s", name)));
    }

}

