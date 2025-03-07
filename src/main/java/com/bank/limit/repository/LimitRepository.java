package com.bank.limit.repository;

import com.bank.limit.model.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LimitRepository extends JpaRepository<Limit, Long> {

    @Query(value = "Select * from limits l where l.account = ?1 and l.expense_category = ?2 order by l.datetime desc limit 1", nativeQuery = true)
    Optional<Limit> findByAccountAndExpenseCategory(Integer account, String expenseCategory);

    @Query(value = "SELECT * from limits l where l.account = ?1 limit 1", nativeQuery = true)
    Optional<Limit> findByAccount(Integer account);
}

