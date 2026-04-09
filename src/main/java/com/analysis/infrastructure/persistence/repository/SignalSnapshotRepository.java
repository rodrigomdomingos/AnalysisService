package com.analysis.infrastructure.persistence.repository;

import com.analysis.infrastructure.persistence.entity.SignalSnapshotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface SignalSnapshotRepository extends JpaRepository<SignalSnapshotEntity, Long> {
    Optional<SignalSnapshotEntity> findByStockIdAndSnapshotAt(Long stockId, OffsetDateTime snapshotAt);
}
