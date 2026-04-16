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

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "signal_snapshot", schema = "investments",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_signal_snapshot", columnNames = {"stock_id", "snapshot_at"})
        })
public class SignalSnapshotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private StockEntity stock;

    @Column(name = "snapshot_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime snapshotAt;

    @Column(name = "pe_signal", columnDefinition = "TEXT")
    private String peSignal;

    @Column(name = "peg_signal", columnDefinition = "TEXT")
    private String pegSignal;

    @Column(name = "fcf_yield_signal", columnDefinition = "TEXT")
    private String fcfYieldSignal;

    @Column(name = "roe_signal", columnDefinition = "TEXT")
    private String roeSignal;

    @Column(name = "debt_signal", columnDefinition = "TEXT")
    private String debtSignal;

    @Column(name = "net_margin_signal", columnDefinition = "TEXT")
    private String netMarginSignal;

    @Column
    private Integer score;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

    public SignalSnapshotEntity(StockEntity stock, OffsetDateTime snapshotAt, String peSignal, String pegSignal, String fcfYieldSignal, String roeSignal, String debtSignal, String netMarginSignal, Integer score, OffsetDateTime createdAt) {
        this.stock = stock;
        this.snapshotAt = snapshotAt;
        this.peSignal = peSignal;
        this.pegSignal = pegSignal;
        this.fcfYieldSignal = fcfYieldSignal;
        this.roeSignal = roeSignal;
        this.debtSignal = debtSignal;
        this.netMarginSignal = netMarginSignal;
        this.score = score;
        this.createdAt = createdAt;
    }
}
