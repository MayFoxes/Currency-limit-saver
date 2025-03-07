package com.bank.limit.model;

import com.bank.limit.util.enums.CurrencyShortname;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "EXPENSES")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXPENSES_ID")
    private Long id;
    @Column(name = "ACCOUNT_TO")
    private Integer accountTo;
    @Column(name = "CURRENCY_SHORTNAME")
    @Enumerated(EnumType.STRING)
    private CurrencyShortname currencyShortname;
    @Column(name = "SUM_EXPENSE")
    private BigDecimal sumExpense;
    @Column(name = "EXPENSE_TIME")
    private LocalDateTime dateTime;
    @Column(name = "LIMIT_EXCEEDED")
    private boolean limitExceeded;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LIMIT_ID")
    private Limit curLimit;
}