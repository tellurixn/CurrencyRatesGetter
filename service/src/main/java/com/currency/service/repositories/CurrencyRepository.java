package com.currency.service.repositories;

import com.currency.service.models.Currency;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {

    Currency findCurrencyByDate(LocalDate date);

    Currency findCurrencyByCode(String code);

    Currency findCurrencyByCodeAndDate(String code, LocalDate date);
}
