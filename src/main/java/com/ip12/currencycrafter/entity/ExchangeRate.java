package com.ip12.currencycrafter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "EXCHANGE_RATE")
@Getter
@Setter
@NoArgsConstructor
@Table(name = "EXCHANGE_RATE", schema = "CURRENCY_SCHEMA")
public class ExchangeRate {
    @Id
    @SequenceGenerator(name = "ID_GENERATOR_EXCHANGE_RATE", sequenceName = "CURRENCY_SCHEMA.EXCHANGE_RATE_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR_EXCHANGE_RATE")
    @Column(name = "ID")
    private Long id;

    @Column(name = "\"DATE\"")
    private LocalDate date;

    @Column(name = "RATE")
    private BigDecimal rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENCY_ID")
    private Currency currency;
}
