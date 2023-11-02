package com.ip12.currencycrafter.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "CURRENCY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CURRENCY", schema = "CURRENCY_SCHEMA")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

//    @OneToMany(mappedBy = "currency")
//    private Set<ExchangeRate> exchangeRates;
}
