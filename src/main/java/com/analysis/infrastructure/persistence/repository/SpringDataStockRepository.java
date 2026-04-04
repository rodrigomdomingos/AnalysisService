package com.analysis.infrastructure.persistence.repository;

import com.analysis.infrastructure.persistence.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataStockRepository extends JpaRepository<StockEntity, Long> {
    Optional<StockEntity> findByTicker(String ticker);
}
