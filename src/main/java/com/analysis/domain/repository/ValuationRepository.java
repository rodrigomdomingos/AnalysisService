package com.analysis.domain.repository;

import com.analysis.domain.model.ValuationSnapshot;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface ValuationRepository {
    Optional<ValuationSnapshot> findLatestByStockId(Long stockId);
    void save(ValuationSnapshot snapshot);
    Optional<ValuationSnapshot> findByStockIdAndSnapshotAt(Long stockId, OffsetDateTime snapshotAt);
}
