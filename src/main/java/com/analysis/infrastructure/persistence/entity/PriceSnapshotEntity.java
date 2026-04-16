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
@Table(name = "price_snapshot", schema = "investments",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_price_snapshot", columnNames = {"stock_id", "snapshot_at"})
        })
public class PriceSnapshotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private StockEntity stock;

    @Column(name = "snapshot_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime snapshotAt;

    @Column(name = "close_price")
    private BigDecimal closePrice;

    @Column
    private Long volume;

    @Column
    private BigDecimal rsi;

    @Column
    private BigDecimal ma50;

    @Column
    private BigDecimal ma200;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

    public PriceSnapshotEntity(StockEntity stock, OffsetDateTime snapshotAt, BigDecimal closePrice, Long volume, BigDecimal rsi, BigDecimal ma50, BigDecimal ma200, OffsetDateTime createdAt) {
        this.stock = stock;
        this.snapshotAt = snapshotAt;
        this.closePrice = closePrice;
        this.volume = volume;
        this.rsi = rsi;
        this.ma50 = ma50;
        this.ma200 = ma200;
        this.createdAt = createdAt;
    }
}
