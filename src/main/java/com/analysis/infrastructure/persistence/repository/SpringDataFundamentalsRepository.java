package com.analysis.infrastructure.persistence.repository;

import com.analysis.infrastructure.persistence.entity.FundamentalsSnapshotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface SpringDataFundamentalsRepository extends JpaRepository<FundamentalsSnapshotEntity, Long> {
    Optional<FundamentalsSnapshotEntity> findFirstByStockIdOrderBySnapshotAtDesc(Long stockId);
    Optional<FundamentalsSnapshotEntity> findByStockIdAndSnapshotAt(Long stockId, OffsetDateTime snapshotAt);
}
