package com.analysis.domain.repository;

import com.analysis.domain.model.FundamentalsSnapshot;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface FundamentalsRepository {
    Optional<FundamentalsSnapshot> findLatestByStockId(Long stockId);
    void save(FundamentalsSnapshot snapshot);
    Optional<FundamentalsSnapshot> findByStockIdAndSnapshotAt(Long stockId, OffsetDateTime snapshotAt);
}
