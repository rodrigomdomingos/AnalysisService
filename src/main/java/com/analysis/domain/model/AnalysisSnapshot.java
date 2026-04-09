package com.analysis.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class AnalysisSnapshot {
    private final Long stockId;
    private final OffsetDateTime snapshotAt;
    private final BigDecimal rsi;
    private final BigDecimal ma50;
    private final BigDecimal ma200;
    private final BigDecimal pe;
    private final BigDecimal evEbitda;
    private final BigDecimal peg;
    private final BigDecimal fcfYield;
    private final BigDecimal revenueGrowth;
    private final BigDecimal netMargin;
    private final BigDecimal roe;
    private final BigDecimal roic;
    private final BigDecimal debtEquity;
    private final BigDecimal freeCashFlow;

    public AnalysisSnapshot(Long stockId, OffsetDateTime snapshotAt, BigDecimal rsi, BigDecimal ma50, BigDecimal ma200, BigDecimal pe, BigDecimal evEbitda, BigDecimal peg, BigDecimal fcfYield, BigDecimal revenueGrowth, BigDecimal netMargin, BigDecimal roe, BigDecimal roic, BigDecimal debtEquity, BigDecimal freeCashFlow) {
        this.stockId = stockId;
        this.snapshotAt = snapshotAt;
        this.rsi = rsi;
        this.ma50 = ma50;
        this.ma200 = ma200;
        this.pe = pe;
        this.evEbitda = evEbitda;
        this.peg = peg;
        this.fcfYield = fcfYield;
        this.revenueGrowth = revenueGrowth;
        this.netMargin = netMargin;
        this.roe = roe;
        this.roic = roic;
        this.debtEquity = debtEquity;
        this.freeCashFlow = freeCashFlow;
    }

    public Long getStockId() {
        return stockId;
    }

    public OffsetDateTime getSnapshotAt() {
        return snapshotAt;
    }

    public BigDecimal getRsi() {
        return rsi;
    }

    public BigDecimal getMa50() {
        return ma50;
    }

    public BigDecimal getMa200() {
        return ma200;
    }

    public BigDecimal getPe() {
        return pe;
    }

    public BigDecimal getEvEbitda() {
        return evEbitda;
    }

    public BigDecimal getPeg() {
        return peg;
    }

    public BigDecimal getFcfYield() {
        return fcfYield;
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
}
