package com.analysis.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class FundamentalsSnapshot {
    private final Long stockId;
    private final OffsetDateTime snapshotAt;
    private final BigDecimal revenueGrowth;
    private final BigDecimal netMargin;
    private final BigDecimal roe;
    private final BigDecimal roic;
    private final BigDecimal debtEquity;
    private final BigDecimal freeCashFlow;

    public FundamentalsSnapshot(Long stockId, OffsetDateTime snapshotAt, BigDecimal revenueGrowth, BigDecimal netMargin, BigDecimal roe, BigDecimal roic, BigDecimal debtEquity, BigDecimal freeCashFlow) {
        this.stockId = stockId;
        this.snapshotAt = snapshotAt;
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
