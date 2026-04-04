package com.analysis.domain.repository;

import com.analysis.domain.model.FundamentalsSnapshot;

import java.time.LocalDate;
import java.util.Optional;

public interface FundamentalsRepository {
    Optional<FundamentalsSnapshot> findLatestByStockId(Long stockId);
    void save(FundamentalsSnapshot snapshot);
    Optional<FundamentalsSnapshot> findByStockIdAndPeriodDate(Long stockId, LocalDate referenceDate);
}
