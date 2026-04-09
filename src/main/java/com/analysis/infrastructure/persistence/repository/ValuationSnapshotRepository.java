package com.analysis.infrastructure.persistence.repository;

import com.analysis.infrastructure.persistence.entity.ValuationSnapshotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface ValuationSnapshotRepository extends JpaRepository<ValuationSnapshotEntity, Long> {
    Optional<ValuationSnapshotEntity> findByStockIdAndSnapshotAt(Long stockId, OffsetDateTime snapshotAt);
}
