package com.analysis.application.context;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * A context object to hold shared data for a single stock analysis run.
 * This ensures that the same stockId and snapshotAt timestamp are used
 * across all services and layers.
 */
public final class AnalysisContext {
    private final Long stockId;
    private final OffsetDateTime snapshotAt;

    public AnalysisContext(Long stockId, OffsetDateTime snapshotAt) {
        this.stockId = Objects.requireNonNull(stockId, "stockId must not be null");
        this.snapshotAt = Objects.requireNonNull(snapshotAt, "snapshotAt must not be null");
    }

    public Long getStockId() {
        return stockId;
    }

    public OffsetDateTime getSnapshotAt() {
        return snapshotAt;
    }
}
