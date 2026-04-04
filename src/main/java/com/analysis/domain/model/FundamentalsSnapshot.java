package com.analysis.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class FundamentalsSnapshot {
    private final Long stockId;
    private final LocalDate periodDate;
    private final OffsetDateTime date;
    private final BigDecimal revenueGrowth;
    private final BigDecimal netMargin;
    private final BigDecimal roe;
    private final BigDecimal roic;
    private final BigDecimal debtEquity;
    private final BigDecimal freeCashFlow;
    private final BigDecimal pegRatio;

    public FundamentalsSnapshot(Long stockId, LocalDate periodDate, OffsetDateTime date, BigDecimal revenueGrowth, BigDecimal netMargin, BigDecimal roe, BigDecimal roic, BigDecimal debtEquity, BigDecimal freeCashFlow, BigDecimal pegRatio) {
        this.stockId = stockId;
        this.periodDate = periodDate;
        this.date = date;
        this.revenueGrowth = revenueGrowth;
        this.netMargin = netMargin;
        this.roe = roe;
        this.roic = roic;
        this.debtEquity = debtEquity;
        this.freeCashFlow = freeCashFlow;
        this.pegRatio = pegRatio;
    }

    public Long getStockId() {
        return stockId;
    }

    public LocalDate getPeriodDate() {
        return periodDate;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public BigDecimal getRevenueGrowth() {
        return revenueGrowth;
    }

    public BigDecimal getNetMargin() {
        return netMargin;
    }

    public BigDecimal getRoe() {
        return roe;
    }

    public BigDecimal getRoic() {
        return roic;
    }

    public BigDecimal getDebtEquity() {
        return debtEquity;
    }

    public BigDecimal getFreeCashFlow() {
        return freeCashFlow;
    }

    public BigDecimal getPegRatio() {
        return pegRatio;
    }
}
