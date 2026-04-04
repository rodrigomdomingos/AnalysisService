package com.analysis.domain.repository;

import com.analysis.domain.model.ValuationSnapshot;

import java.time.LocalDate;
import java.util.Optional;

public interface ValuationRepository {
    Optional<ValuationSnapshot> findLatestByStockId(Long stockId);
    void save(ValuationSnapshot snapshot);
    Optional<ValuationSnapshot> findByStockIdAndSnapshotDate(Long stockId, LocalDate snapshotDate);
}
