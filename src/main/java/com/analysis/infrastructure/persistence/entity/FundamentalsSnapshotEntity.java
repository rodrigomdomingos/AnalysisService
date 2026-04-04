package com.analysis.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fundamentals_snapshot", schema = "investments")
public class FundamentalsSnapshotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private StockEntity stock;

    @Column(name = "period_date", nullable = false)
    private LocalDate periodDate;

    @Column(name = "date", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
    private OffsetDateTime date;

    @Column(name = "revenue_growth")
    private BigDecimal revenueGrowth;

    @Column(name = "net_margin")
    private BigDecimal netMargin;

    @Column
    private BigDecimal roe;

    @Column
    private BigDecimal roic;

    @Column(name = "debt_equity")
    private BigDecimal debtEquity;

    @Column(name = "free_cash_flow")
    private BigDecimal freeCashFlow;

    @Column(name = "peg_ratio")
    private BigDecimal pegRatio;

    public FundamentalsSnapshotEntity(StockEntity stock, LocalDate periodDate, OffsetDateTime date, BigDecimal revenueGrowth, BigDecimal netMargin, BigDecimal roe, BigDecimal roic, BigDecimal debtEquity, BigDecimal freeCashFlow, BigDecimal pegRatio) {
        this.stock = stock;
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
}
