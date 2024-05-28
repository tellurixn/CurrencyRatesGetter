package com.currency.service.controllers;

import com.currency.service.errors.ServiceError;
import com.currency.service.models.Currency;
import com.currency.service.repositories.CurrencyRepository;
import com.currency.service.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
public class CurrencyController {

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    CurrencyService currencyService;


    @GetMapping("/currency")
    public ResponseEntity<Object> getCurrencyRate(
            @RequestParam("currency_code") String code,
            @RequestParam("date") String date) {

        LocalDate localDate = LocalDate.parse(date);

        Currency currency = currencyRepository.findCurrencyByCodeAndDate(code, localDate);

        if(currency == null){
            currencyService.addCurrencyRateToDb(localDate);
            currency = currencyRepository.findCurrencyByCodeAndDate(code, localDate);

            if(currency == null){
                return new ResponseEntity<>(new ServiceError(HttpStatus.NOT_FOUND.value(),
                        "Валюта с кодом " + code + " на дату " + localDate + " не найдена."),
                        HttpStatus.NOT_FOUND);
            }
            else {
                return new ResponseEntity<>(currencyService.mapToCurrencyDto(currency), HttpStatus.OK);
            }
        }
        else
            return new ResponseEntity<>(currencyService.mapToCurrencyDto(currency), HttpStatus.OK);
    }
}
