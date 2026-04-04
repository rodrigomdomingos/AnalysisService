package com.analysis.infrastructure.persistence.repository;

import com.analysis.infrastructure.persistence.entity.PriceSnapshotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPriceSnapshotRepository extends JpaRepository<PriceSnapshotEntity, Long> {
}
