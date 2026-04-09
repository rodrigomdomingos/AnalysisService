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
@Table(name = "valuation_snapshot", schema = "investments",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_valuation_snapshot", columnNames = {"stock_id", "snapshot_at"})
        })
public class ValuationSnapshotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private StockEntity stock;

    @Column(name = "snapshot_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime snapshotAt;

    @Column(name = "pe_ratio")
    private BigDecimal peRatio;

    @Column(name = "ev_ebitda")
    private BigDecimal evEbitda;

    @Column(name = "peg_ratio")
    private BigDecimal pegRatio;

    @Column(name = "fcf_yield")
    private BigDecimal fcfYield;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

    public ValuationSnapshotEntity(StockEntity stock, OffsetDateTime snapshotAt, BigDecimal peRatio, BigDecimal evEbitda, BigDecimal pegRatio, BigDecimal fcfYield, OffsetDateTime createdAt) {
        this.stock = stock;
        this.snapshotAt = snapshotAt;
        this.peRatio = peRatio;
        this.evEbitda = evEbitda;
        this.pegRatio = pegRatio;
        this.fcfYield = fcfYield;
        this.createdAt = createdAt;
    }
}
