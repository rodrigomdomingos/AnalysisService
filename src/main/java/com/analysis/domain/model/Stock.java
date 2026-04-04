package com.analysis.domain.model;

public class Stock {
    private final Long id;
    private final String ticker;
    private final String exchange;
    private final String sector;
    private final String currency;

    public Stock(Long id, String ticker, String exchange, String sector, String currency) {
        this.id = id;
        this.ticker = ticker;
        this.exchange = exchange;
        this.sector = sector;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public String getTicker() {
        return ticker;
    }

    public String getExchange() {
        return exchange;
    }

    public String getSector() {
        return sector;
    }

    public String getCurrency() {
        return currency;
    }
}
