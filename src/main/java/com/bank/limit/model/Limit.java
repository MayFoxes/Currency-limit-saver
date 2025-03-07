package com.bank.limit.model;

import com.bank.limit.util.enums.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "LIMITS")
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIMIT_ID")
    private Long id;
    @Column(name = "ACCOUNT")
    private Integer account;
    @Column(name = "EXPENSE_CATEGORY")
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;
    @Column(name = "LIMIT_SUM")
    private BigDecimal limitSum;
    @Column(name = "REST_LIMIT")
    private BigDecimal restLimit;
    @Column(name = "DATETIME")
    private LocalDateTime dateTime;
}
