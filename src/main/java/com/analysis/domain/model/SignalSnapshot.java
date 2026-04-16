package com.analysis.domain.model;

import java.time.OffsetDateTime;

public class SignalSnapshot {
    private final Long id;
    private final Long stockId;
    private final OffsetDateTime snapshotAt;
    private final String peSignal;
    private final String pegSignal;
    private final String fcfYieldSignal;
    private final String roeSignal;
    private final String debtSignal;
    private final String netMarginSignal;
    private final Integer score;
    private final OffsetDateTime createdAt;

    public SignalSnapshot(Long id, Long stockId, OffsetDateTime snapshotAt, String peSignal, String pegSignal, String fcfYieldSignal, String roeSignal, String debtSignal, String netMarginSignal, Integer score, OffsetDateTime createdAt) {
        this.id = id;
        this.stockId = stockId;
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

    public Long getId() {
        return id;
    }

    public Long getStockId() {
        return stockId;
    }

    public OffsetDateTime getSnapshotAt() {
        return snapshotAt;
    }

    public String getPeSignal() {
        return peSignal;
    }

    public String getPegSignal() {
        return pegSignal;
    }

    public String getFcfYieldSignal() {
        return fcfYieldSignal;
    }

    public String getRoeSignal() {
        return roeSignal;
    }

    public String getDebtSignal() {
        return debtSignal;
    }

    public String getNetMarginSignal() {
        return netMarginSignal;
    }

    public Integer getScore() {
        return score;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
