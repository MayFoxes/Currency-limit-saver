package com.bank.limit.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PAIR_RATE")
public class CurrencyRate {
    @Id
    @JsonAlias({"symbol"})
    private String currencyPairs;
    @Column
    private BigDecimal rate;
    @Column(name = "DATETIME")
    private String timestamp;
}

