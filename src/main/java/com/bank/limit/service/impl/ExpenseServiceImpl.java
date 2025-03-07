package com.bank.limit.service.impl;

import com.bank.limit.dto.ExceededExpenseDto;
import com.bank.limit.dto.ExpenseRequestDto;
import com.bank.limit.dto.ExpenseResultDto;
import com.bank.limit.exeption.NotFoundException;
import com.bank.limit.model.Expense;
import com.bank.limit.repository.ExpenseRepository;
import com.bank.limit.repository.LimitRepository;
import com.bank.limit.service.ExpenseService;
import com.bank.limit.util.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final LimitRepository limitRepository;

    @Override
    @Transactional
    public ExpenseResultDto addExpense(ExpenseRequestDto dto) {
        Expense tempExpense = DataMapper.INSTANCE.dtoToExp(dto);
        return DataMapper.INSTANCE.expToDto(expenseRepository.save(tempExpense));
    }

    @Override
    public List<ExceededExpenseDto> getAllExpenseExceededLimitByAccount(Integer account) {
        checkLimitExistByAccountExist(account);
        List<Expense> tempExpenses = expenseRepository.getAllExpenseExceededLimitByAccount(account);
        return tempExpenses.stream()
                .map(DataMapper.INSTANCE::expToExceedDto)
                .toList();

    }

    private void checkLimitExistByAccountExist(Integer account) {
        limitRepository.findByAccount(account)
                .orElseThrow(() -> new NotFoundException(String.format("This account:%d don't exist or don't still in db", account)));
    }
}
