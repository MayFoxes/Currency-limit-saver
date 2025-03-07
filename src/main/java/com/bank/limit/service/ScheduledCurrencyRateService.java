package com.bank.limit.service;

import com.bank.limit.model.CurrencyRate;
import com.bank.limit.repository.CurrencyRateRepository;
import com.bank.limit.util.enums.CurrencyShortname;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Transactional
@RequiredArgsConstructor
public class ScheduledCurrencyRateService {
    private final String API_KEY;
    private final String BASE_URL;

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    @Scheduled(cron = "0 0 6 * * ?")
    public void fetchAndStoreRates() {
        log.info("Start fetch external data from:{}", BASE_URL);
        WebClient webClient = WebClient.create(BASE_URL);
        for (CurrencyShortname name : CurrencyShortname.values()) {
            if (!name.equals(CurrencyShortname.USD))
                fetchCurrencyRates(webClient, name.toString())
                        .log(String.format("Currency:%s fetch and saved", name)).block();
        }
    }

    private Mono<Void> fetchCurrencyRates(WebClient webClient, String currency) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                        .path("exchange_rate")
                        .queryParam("symbol", currency + "/USD")
                        .queryParam("apikey", API_KEY)
                        .build()
                ).retrieve()
                .bodyToMono(CurrencyRate.class)
                .onErrorResume(e -> {
                    log.error(e.getMessage());
                    return Mono.empty();
                }).switchIfEmpty(Mono.empty())
                .flatMap(this::saveCurrencyRate)
                .then();
    }

    private Mono<Void> saveCurrencyRate(CurrencyRate currencyRate) {
        return Mono.fromRunnable(() -> currencyRateRepository.save(currencyRate));
    }
}
