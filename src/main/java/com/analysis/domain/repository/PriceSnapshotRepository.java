package com.analysis.domain.repository;

import com.analysis.domain.model.PriceSnapshot;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface PriceSnapshotRepository {
    PriceSnapshot save(PriceSnapshot snapshot);
    Optional<PriceSnapshot> findByStockIdAndSnapshotAt(Long stockId, OffsetDateTime snapshotAt);
}
