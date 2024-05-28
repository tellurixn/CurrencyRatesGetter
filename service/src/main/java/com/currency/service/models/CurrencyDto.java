package com.currency.service.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CurrencyDto {
    double value;
    String code;
    LocalDate date;
}
