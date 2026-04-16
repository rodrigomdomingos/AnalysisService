package com.analysis.infrastructure.persistence.repository;

import com.analysis.infrastructure.persistence.entity.PriceSnapshotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface SpringDataPriceSnapshotRepository extends JpaRepository<PriceSnapshotEntity, Long> {
    Optional<PriceSnapshotEntity> findByStockIdAndSnapshotAt(Long stockId, OffsetDateTime snapshotAt);
}
