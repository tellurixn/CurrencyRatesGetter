package com.currency.service.configuration;


import com.currency.service.repositories.CurrencyRepository;
import com.currency.service.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;


@Configuration
@EnableScheduling
public class CurrencyShedule {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyRepository currencyRepository;

    /**
     * Добавление курса валют в локальную БД
     * каждый день в 00:00
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void updateCurrencyRates() {
        currencyService.addCurrencyRateToDb(LocalDate.now());
    }
}
