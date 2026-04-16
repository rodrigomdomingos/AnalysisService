package com.analysis.application.event;

import java.time.OffsetDateTime;
import java.util.Objects;

public record MarketDataUpdatedEvent(Long stockId, OffsetDateTime snapshotAt) {
    public MarketDataUpdatedEvent {
        Objects.requireNonNull(stockId, "stockId must not be null");
        Objects.requireNonNull(snapshotAt, "snapshotAt must not be null");
    }
}
