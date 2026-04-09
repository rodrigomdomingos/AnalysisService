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
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "prices", schema = "investments")
public class PriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private StockEntity stock;

    @Column(name = "snapshot_at", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
    private OffsetDateTime snapshotAt;

    @Column(name = "close_price", nullable = false)
    private BigDecimal closePrice;

    @Column(nullable = false)
    private Long volume;

    public PriceEntity(StockEntity stock, OffsetDateTime snapshotAt, BigDecimal closePrice, Long volume) {
        this.stock = stock;
        this.snapshotAt = snapshotAt;
        this.closePrice = closePrice;
        this.volume = volume;
    }
}
