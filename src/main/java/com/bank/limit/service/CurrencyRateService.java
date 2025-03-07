package com.bank.limit.service;

import com.bank.limit.dto.CurrencyRateDto;
import com.bank.limit.util.enums.CurrencyShortname;

public interface CurrencyRateService {
    CurrencyRateDto getPairRateByCurrencyName(CurrencyShortname shortname);
}
