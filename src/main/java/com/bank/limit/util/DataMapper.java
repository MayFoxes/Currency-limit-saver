package com.bank.limit.util;

import com.bank.limit.dto.CurrencyRateDto;
import com.bank.limit.dto.ExceededExpenseDto;
import com.bank.limit.dto.ExpenseRequestDto;
import com.bank.limit.dto.ExpenseResultDto;
import com.bank.limit.dto.LimitDto;
import com.bank.limit.dto.LimitFullDto;
import com.bank.limit.model.CurrencyRate;
import com.bank.limit.model.Expense;
import com.bank.limit.model.Limit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DataMapper {
    DataMapper INSTANCE = Mappers.getMapper(DataMapper.class);

    @Mapping(target = "id", ignore = true)
    Limit dtoToLimit(LimitDto dto);

    LimitDto limitToDto(Limit limit);

    LimitFullDto limitToFullDto(Limit limit);

    CurrencyRateDto rateToDto(CurrencyRate currencyRate);

    @Mapping(target = "limitDate", source = "expense.curLimit.dateTime")
    @Mapping(target = "expenseDate", source = "expense.dateTime")
    @Mapping(target = "restLimit", source = "expense.curLimit.restLimit")
    @Mapping(target = "limit", source = "expense.curLimit.limitSum")
    @Mapping(target = "expenseCategory", source = "expense.curLimit.expenseCategory")
    ExceededExpenseDto expToExceedDto(Expense expense);

    @Mapping(target = "curLimit", source = "dto.limitFullDto")
    @Mapping(target = "id", ignore = true)
    Expense dtoToExp(ExpenseRequestDto dto);

    @Mapping(target = "limitId", source = "expense.curLimit.id")
    @Mapping(target = "time", source = "expense.dateTime")
    @Mapping(target = "expenseCategory", source = "expense.curLimit.expenseCategory")
    @Mapping(target = "accountFrom", source = "expense.curLimit.account")
    ExpenseResultDto expToDto(Expense expense);

}
