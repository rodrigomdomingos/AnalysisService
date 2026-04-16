package com.analysis.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Price {
    private final Long id;
    private final Long stockId;
    private final OffsetDateTime snapshotAt;
    private final BigDecimal closePrice;
    private final Long volume;

    public Price(Long id, Long stockId, OffsetDateTime snapshotAt, BigDecimal closePrice, Long volume) {
        this.id = id;
        this.stockId = stockId;
        this.snapshotAt = snapshotAt;
        this.closePrice = closePrice;
        this.volume = volume;
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

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public Long getVolume() {
        return volume;
    }
}
