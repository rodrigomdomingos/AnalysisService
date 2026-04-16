package com.analysis.infrastructure.persistence.repository;

import com.analysis.infrastructure.persistence.entity.ValuationSnapshotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface SpringDataValuationRepository extends JpaRepository<ValuationSnapshotEntity, Long> {
    Optional<ValuationSnapshotEntity> findFirstByStockIdOrderBySnapshotAtDesc(Long stockId);
    Optional<ValuationSnapshotEntity> findByStockIdAndSnapshotAt(Long stockId, OffsetDateTime snapshotAt);
}
