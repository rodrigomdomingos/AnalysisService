package com.analysis.infrastructure.persistence.repository;

import com.analysis.infrastructure.persistence.entity.FundamentalsSnapshotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SpringDataFundamentalsRepository extends JpaRepository<FundamentalsSnapshotEntity, Long> {
    Optional<FundamentalsSnapshotEntity> findFirstByStockIdOrderByDateDesc(Long stockId);
    Optional<FundamentalsSnapshotEntity> findByStockIdAndPeriodDate(Long stockId, LocalDate periodDate);
}
