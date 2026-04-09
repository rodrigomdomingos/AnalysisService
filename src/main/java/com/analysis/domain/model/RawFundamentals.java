package com.analysis.domain.model;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class RawFundamentals {
    private Long stockId;
    private OffsetDateTime referenceDate;
    private BigDecimal revenue;
    private BigDecimal netIncome;
    private BigDecimal debt;
    private BigDecimal equity; 
    private BigDecimal freeCashFlow; 
    private BigDecimal revenueGrowth;
    private BigDecimal roe;
    private BigDecimal roa;
    private BigDecimal profitMargin;
    private BigDecimal pegRatio;
    private BigDecimal marketCap;
    private BigDecimal pe;
    private BigDecimal earningsGrowth;

    public RawFundamentals(Long stockId, OffsetDateTime referenceDate, BigDecimal revenue, BigDecimal netIncome, BigDecimal debt, BigDecimal equity, BigDecimal freeCashFlow, BigDecimal revenueGrowth, BigDecimal roe, BigDecimal roa, BigDecimal profitMargin, BigDecimal pegRatio, BigDecimal marketCap, BigDecimal pe, BigDecimal earningsGrowth) {
        this.stockId = stockId;
        this.referenceDate = referenceDate;
        this.revenue = revenue;
        this.netIncome = netIncome;
        this.debt = debt;
        this.equity = equity;
        this.freeCashFlow = freeCashFlow;
        this.revenueGrowth = revenueGrowth;
        this.roe = roe;
        this.roa = roa;
        this.profitMargin = profitMargin;
        this.pegRatio = pegRatio;
        this.marketCap = marketCap;
        this.pe = pe;
        this.earningsGrowth = earningsGrowth;
    }

    public Long getStockId() {
        return stockId;
    }

    public OffsetDateTime getReferenceDate() {
        return referenceDate;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public BigDecimal getNetIncome() {
        return netIncome;
    }

    public BigDecimal getDebt() {
        return debt;
    }

    public BigDecimal getEquity() {
        return equity;
    }

    public BigDecimal getFreeCashFlow() {
        return freeCashFlow;
    }

    public BigDecimal getRevenueGrowth() {
        return revenueGrowth;
    }

    public BigDecimal getRoe() {
        return roe;
    }

    public BigDecimal getRoa() {
        return roa;
    }

    public BigDecimal getProfitMargin() {
        return profitMargin;
    }

    public BigDecimal getPegRatio() {
        return pegRatio;
    }

    public BigDecimal getMarketCap() {
        return marketCap;
    }

    public BigDecimal getPe() {
        return pe;
    }

    public BigDecimal getEarningsGrowth() {
        return earningsGrowth;
    }
}
