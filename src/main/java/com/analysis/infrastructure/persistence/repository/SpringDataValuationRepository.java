package com.analysis.infrastructure.persistence.repository;

import com.analysis.infrastructure.persistence.entity.ValuationSnapshotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SpringDataValuationRepository extends JpaRepository<ValuationSnapshotEntity, Long> {
    Optional<ValuationSnapshotEntity> findFirstByStockIdOrderByDateDesc(Long stockId);
    Optional<ValuationSnapshotEntity> findByStockIdAndSnapshotDate(Long stockId, LocalDate snapshotDate);
}
