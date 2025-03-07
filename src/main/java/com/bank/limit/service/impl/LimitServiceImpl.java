package com.bank.limit.service.impl;

import com.bank.limit.dto.LimitDto;
import com.bank.limit.dto.LimitFullDto;
import com.bank.limit.exeption.NotFoundException;
import com.bank.limit.model.Limit;
import com.bank.limit.repository.LimitRepository;
import com.bank.limit.service.LimitService;
import com.bank.limit.util.DataMapper;
import com.bank.limit.util.enums.ExpenseCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LimitServiceImpl implements LimitService {
    private final LimitRepository repository;

    @Override
    @Transactional
    public LimitDto addLimit(LimitDto limitDto) {
        Limit tempLimit = DataMapper.INSTANCE.dtoToLimit(limitDto);
        return DataMapper.INSTANCE.limitToDto(repository.save(tempLimit));
    }

    @Override
    @Transactional
    public LimitFullDto updateRestLimit(Integer account, ExpenseCategory expenseCategory, BigDecimal restLimit) {
        Limit tempLimit = checkLimitExist(account, expenseCategory);
        BigDecimal newRestRate = tempLimit.getRestLimit().subtract(restLimit).setScale(2, RoundingMode.CEILING);
        tempLimit.setRestLimit(newRestRate);
        return DataMapper.INSTANCE.limitToFullDto(repository.save(tempLimit));
    }

    private Limit checkLimitExist(Integer account, ExpenseCategory expenseCategory) {
        return repository.findByAccountAndExpenseCategory(account, expenseCategory.name())
                .orElseThrow(() -> new NotFoundException(String.format("This account:%d don't exist or don't still in db", account)));
    }
}
