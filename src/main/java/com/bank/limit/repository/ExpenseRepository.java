package com.bank.limit.repository;

import com.bank.limit.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query(value = "SELECT E.* " +
            "FROM EXPENSES E JOIN LIMITS L ON E.LIMIT_ID = L.LIMIT_ID " +
            "WHERE L.ACCOUNT = ?1 AND E.LIMIT_EXCEEDED IS TRUE", nativeQuery = true)
    List<Expense> getAllExpenseExceededLimitByAccount(Integer account);
}
