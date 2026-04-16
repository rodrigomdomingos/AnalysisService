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
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fundamentals_snapshot", schema = "investments",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_fundamentals_snapshot", columnNames = {"stock_id", "snapshot_at"})
        })
public class FundamentalsSnapshotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private StockEntity stock;

    @Column(name = "snapshot_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime snapshotAt;

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

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

    public FundamentalsSnapshotEntity(StockEntity stock, OffsetDateTime snapshotAt, BigDecimal revenueGrowth, BigDecimal netMargin, BigDecimal roe, BigDecimal roic, BigDecimal debtEquity, BigDecimal freeCashFlow, OffsetDateTime createdAt) {
        this.stock = stock;
        this.snapshotAt = snapshotAt;
        this.revenueGrowth = revenueGrowth;
        this.netMargin = netMargin;
        this.roe = roe;
        this.roic = roic;
        this.debtEquity = debtEquity;
        this.freeCashFlow = freeCashFlow;
        this.createdAt = createdAt;
    }
}
