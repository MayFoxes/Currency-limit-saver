package com.bank.limit.conf;

import com.bank.limit.service.ScheduledCurrencyRateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ScheduleCurrencyRateServiceConfiguration {
    @Value(value = "${external.apitoken:}")
    private String token;
    @Value(value = "${external.url:}")
    private String url;

    @Bean
    public ScheduledCurrencyRateService scheduledRateService() {
        return new ScheduledCurrencyRateService(token, url);
    }
}
