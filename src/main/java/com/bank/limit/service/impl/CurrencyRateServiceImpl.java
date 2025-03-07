package com.bank.limit.service.impl;

import com.bank.limit.dto.CurrencyRateDto;
import com.bank.limit.repository.CurrencyRateRepository;
import com.bank.limit.service.CurrencyRateService;
import com.bank.limit.util.DataMapper;
import com.bank.limit.util.enums.CurrencyShortname;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CurrencyRateServiceImpl implements CurrencyRateService {
    private final CurrencyRateRepository currencyRateRepository;

    @Override
    public CurrencyRateDto getPairRateByCurrencyName(CurrencyShortname shortname) {
        if (shortname.equals(CurrencyShortname.USD))
            return new CurrencyRateDto(new BigDecimal("1.00"));
        var currencyRate = currencyRateRepository.findCurrencyRate(shortname.name() + "/USD");
        return DataMapper.INSTANCE.rateToDto(currencyRate);
    }
}
