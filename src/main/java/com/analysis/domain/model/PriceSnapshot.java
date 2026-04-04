package com.analysis.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class PriceSnapshot {
    private final Long stockId;
    private final OffsetDateTime date;
    private final OffsetDateTime snapshotDate;
    private final BigDecimal closePrice;
    private final Long volume;
    private final BigDecimal rsi;
    private final BigDecimal ma50;
    private final BigDecimal ma200;
    private final boolean aboveMa200;

    public PriceSnapshot(Long stockId, OffsetDateTime date, OffsetDateTime snapshotDate, BigDecimal closePrice, Long volume, BigDecimal rsi, BigDecimal ma50, BigDecimal ma200) {
        this.stockId = stockId;
        this.date = date;
        this.snapshotDate = snapshotDate;
        this.closePrice = closePrice;
        this.volume = volume;
        this.rsi = rsi;
        this.ma50 = ma50;
        this.ma200 = ma200;
        this.aboveMa200 = closePrice.compareTo(ma200) > 0;
    }

    public Long getStockId() {
        return stockId;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public OffsetDateTime getSnapshotDate() {
        return snapshotDate;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public Long getVolume() {
        return volume;
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

    public boolean isAboveMa200() {
        return aboveMa200;
    }

    @Override
    public String toString() {
        return "PriceSnapshot{" +
                "stockId=" + stockId +
                ", date=" + date +
                ", snapshotDate=" + snapshotDate +
                ", closePrice=" + closePrice +
                ", volume=" + volume +
                ", rsi=" + rsi +
                ", ma50=" + ma50 +
                ", ma200=" + ma200 +
                ", aboveMa200=" + aboveMa200 +
                '}';
    }
}
