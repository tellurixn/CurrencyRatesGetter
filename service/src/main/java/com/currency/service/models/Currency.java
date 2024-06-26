package com.currency.service.models;

import jakarta.persistence.*;
import lombok.*;

import java.lang.annotation.Target;
import java.time.LocalDate;

@Table(name = "currency")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long currencyId;

    @Column(nullable = false, name = "date")
    private LocalDate date;

    @Column(nullable = false, name = "value")
    private double value;

    @Column(nullable = false, name = "code")
    private String code;

    public Currency(LocalDate date, double value, String code){
        this.date = date;
        this.value = value;
        this.code = code;
    }
}
