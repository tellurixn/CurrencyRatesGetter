package com.currency.service.repositories;

import com.currency.service.models.Currency;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {

    List<Currency> findCurrencyByDate(LocalDate date);

    List<Currency> findCurrencyByCode(String code);

    Currency findCurrencyByCodeAndDate(String code, LocalDate date);
}
