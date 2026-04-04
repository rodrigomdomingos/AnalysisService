package com.analysis.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "stocks", schema = "investments")
public class StockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ticker;

    @Column(nullable = false)
    private String exchange;

    @Column(nullable = false)
    private String sector;

    @Column(nullable = false)
    private String currency;

    public StockEntity(String ticker, String exchange, String sector, String currency) {
        this.ticker = ticker;
        this.exchange = exchange;
        this.sector = sector;
        this.currency = currency;
    }
}
